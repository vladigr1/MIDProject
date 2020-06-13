package database;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TreeSet;
import entities.FuelStationOrder;
import entities.MyIncomeReport;
import entities.MyInventoryReport;
import entities.MyOutcomeReport;
import entities.Orders;
import entities.ProductInStation;
import entities.QuarterlyReport;
import enums.ProductName;

public class DatabaseFuelStationManagerController {

	private static DatabaseFuelStationManagerController instance;

	private Connection connection;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * singleton class constructor
	 */
	private DatabaseFuelStationManagerController(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * @return instance of this class
	 */
	public static DatabaseFuelStationManagerController getInstance(Connection connection) {
		if (instance == null) {
			instance = new DatabaseFuelStationManagerController(connection);
		}
		return instance;
	}

	/**
	 * function gets username returns list with unassesd orders id
	 * 
	 * @param username
	 * @return Object[string,TreeSet<Integer>]
	 * @throws MyFuelException
	 */
	public Object[] getUnassesdOrderID(String username) {
		int employeeID = getEmployeeID(username);
		if (employeeID == -1)
			return null;
		int fuelStationID = getFuelStationID(employeeID);
		if (fuelStationID == -1)
			return null;
		List<Integer> productInStationID = getProductInStationIDs(fuelStationID);
		if (productInStationID.isEmpty())
			return null;
		TreeSet<Integer> fuelStationOrdersID = new TreeSet<Integer>();
		for (Integer productID : productInStationID)
			fuelStationOrdersID.addAll(getUnassessedFuelStationOrdersID(productID));
		Object[] result = new Object[] { "Unassesd Order IDs", fuelStationOrdersID };
		return result;
	}

	/**
	 * function gets orderID and returns Orders object with the orderID and
	 * ProductInStation object with the product in the order
	 * 
	 * @param orderID
	 * @return Object[productInStation,order]
	 * @throws ParseException
	 */
	public Object[] getStationProductInOrderbyOrderID(int orderID) {
		ProductInStation productInStation = getProductInStationByOrderID(orderID);
		Orders order = getFuelStationOrderByID(orderID);
		Object[] result = new Object[] { productInStation, order };
		return result;
	}

	public ArrayList<ProductInStation> getStationProductThresholdOrderbyUsername(String username) {
		int employeeID = getEmployeeID(username);
		int fuelStationID = getFuelStationID(employeeID);
		ArrayList<ProductInStation> result = getProductInStationByfuelStationID(fuelStationID);
		return result;
	}

	public Boolean updateProductInStationThresholdbyUsername(String username, String newThresholds) {
		int employeeID = getEmployeeID(username);
		int fuelStationID = getFuelStationID(employeeID);
		String[] params = newThresholds.split(" ");
		Boolean result = updateProductInStationThresholdbyfuelStationID(fuelStationID, params);
		if (!result)
			return null;
		return result;
	}

	public Boolean updateOrderDoneAssesmentbyOrderID(String username, String params) {
		String[] parameters = params.split(" ");
		int orderID = Integer.parseInt(parameters[0]);
		int assessed = 1;
		int approved = 0;
		if (parameters[1].equals("true"))
			approved = 1;
		String reasonDismissal = "";
		for (int i = 2; i < parameters.length; i++) {
			reasonDismissal += parameters[i] + " ";
		}
		try {
			PreparedStatement pStmt;
			pStmt = this.connection.prepareStatement(
					"UPDATE fuel_station_order SET assessed = ? , approved = ? , reasonDismissal = ? WHERE FK_ordersID = ? ");
			pStmt.setInt(1, assessed);
			pStmt.setInt(2, approved);
			pStmt.setString(3, reasonDismissal);
			pStmt.setInt(4, orderID);
			pStmt.executeUpdate();
		} catch (SQLException e) {
			return null;
		}
		return true;
	}

	public Object[] getQuarterlyReportDataByUsernameYearQuarter(String username, String params) {
		String[] paramSplit = params.split(" ");
		String year = paramSplit[0];
		int quarter = Integer.parseInt(paramSplit[1]);
		boolean reportExist = false;
		Date dateCreated = new Date();
		int employeeID = getEmployeeID(username);
		int fuelStationID = getFuelStationID(employeeID);
		ArrayList<ProductInStation> productInStationList = getProductInStationByfuelStationID(fuelStationID);
		
		ArrayList<QuarterlyReport> quarterlyReportlist = getQuarterlyReportbyFuelStationID(fuelStationID);
		
		for (QuarterlyReport qr : quarterlyReportlist) {
			if (qr.getRepYear().equals(year) && qr.getRepQuarter() == quarter) {
				dateCreated = qr.getDateCreated();
				reportExist = true;
			}
		}
		if (reportExist == false) {
			if (createQuarterReport(fuelStationID, year, quarter, productInStationList) == false) {
				System.out.println("failed to create");
				return null;
			}
		}
		MyIncomeReport incomeReport = getExistIncomeReport(fuelStationID, dateCreated, year, quarter,
				productInStationList);
		if (incomeReport == null) {
			System.out.println("failed income");
			return null;
		}

		MyOutcomeReport outcomeReport = getExistOutcomeReport(fuelStationID, dateCreated, year, quarter,
				productInStationList);
		if (outcomeReport == null) {
			System.out.println("failed outcomeReport");
			return null;
		}
		MyInventoryReport inventoryReport = getExistInventoryReport(fuelStationID, dateCreated, year, quarter,
				productInStationList);
		if (inventoryReport == null) {
			System.out.println("failed inventoryReport");
			return null;
		}
		Object[] result = new Object[] { "Quarter Report", incomeReport, outcomeReport, inventoryReport };
		return result;
	}

	private boolean createQuarterReport(int fuelStationID, String repYear, int repQuarter,
			ArrayList<ProductInStation> productInStationList) {
		int startMonth = -1;
		switch (repQuarter) {
		case 1:
			startMonth = 1;
			break;
		case 2:
			startMonth = 4;
			break;
		case 3:
			startMonth = 7;
			break;
		case 4:
			startMonth = 10;
			break;
		default:
			return false;
		}
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		cal.setTime(now);
		cal.add(Calendar.HOUR, -2);
		cal.add(Calendar.MINUTE, -30);
		now = cal.getTime();
		try {
			// insert QuarterlyReport "FK_repQuarter", "FK_repYear", "FK_fuelStationID",
			// "DateCreated"
			Object[] values = { repQuarter, repYear, fuelStationID, now };
			TableInserts.insertQuarterlyReport(connection, values);
			System.out.println("TableInserts.insertQuarterlyReport");
			// insert IncomeReport "FK_repQuarter", "FK_repYear", "FK_fuelStationID",
			// "totalIncome"
			values = new Object[] { repQuarter, repYear, fuelStationID, 0 };
			TableInserts.insertIncomeReport(connection, values);
			System.out.println("TableInserts.insertIncomeReport");
			TableInserts.insertInventoryReport(connection, values);
			System.out.println("insertInventoryReport");
			TableInserts.insertOutcomeReport(connection, values);
			System.out.println("TableInserts.insertOutcomeReport");
			
			double totalIncome = 0;
			double totalAmountBoughtFromSupllier = 0;
			double totalAmountSold = 0;
			for (ProductInStation pis : productInStationList) {
				int productID = pis.getProductInStationID();
				double incomePerProduct = 0;
				double avgPerProduct = 0;
				double amountBoughtFromSupplier = 0;
				double amountSold = 0;
				// income Report
				PreparedStatement pStmt = this.connection.prepareStatement(
						"SELECT SUM(finalPrice),AVG(finalPrice/amountBought),SUM(amountBought) FROM fast_fuel WHERE FK_productInStationID = ? AND YEAR(fastFuelTime)=? AND MONTH(fastFuelTime) >= ? AND MONTH(fastFuelTime) <= ? ");
				pStmt.setInt(1, productID);
				pStmt.setString(2, repYear);
				pStmt.setInt(3, startMonth);
				pStmt.setInt(4, startMonth + 2);
				ResultSet rs1 = pStmt.executeQuery();
				System.out.println("pStmt1");
				if (rs1.next()) {
					incomePerProduct = rs1.getDouble(1);
					avgPerProduct = rs1.getDouble(2);
					amountSold = rs1.getDouble(3);
					totalIncome += incomePerProduct;
					totalAmountSold += amountSold;
				}
				rs1.close();
				// outcome Report
				pStmt = this.connection.prepareStatement(
						"SELECT SUM(amountBought) FROM orders WHERE ordersID IN( SELECT FK_ordersID  FROM fuel_station_order WHERE FK_productInStationID = ? AND supplied = 1 AND YEAR(timeSupplied)= ? AND MONTH(timeSupplied) >= ?  AND MONTH(timeSupplied) <= ? )");
				pStmt.setInt(1, productID);
				pStmt.setString(2, repYear);
				pStmt.setInt(3, startMonth);
				pStmt.setInt(4, startMonth + 2);
				rs1 = pStmt.executeQuery();
				System.out.println("pStmt2");
				if (rs1.next()) {
					amountBoughtFromSupplier = rs1.getDouble(1);
					totalAmountBoughtFromSupllier += amountBoughtFromSupplier;
				}
				rs1.close();
				// "FK_productInStationID", "FK_repQuarter_IncomeReport",
				// "FK_repYear_IncomeReport", "incomePerProduct", "avgPrice"
				values = new Object[] { productID, repQuarter, repYear, incomePerProduct, avgPerProduct };
				TableInserts.insertProductInIncomeReport(connection, values);
				System.out.println("TableInserts.insertProductInIncomeReport");
				// "FK_productInStationID", "FK_repQuarter_outcomeReport",
				// "FK_repYear_outcomeReport", "amountBoughtFromSupplier"
				values = new Object[] { productID, repQuarter, repYear, amountBoughtFromSupplier };
				TableInserts.insertProductInOutcomeReport(connection, values);
				System.out.println("TableInserts.insertProductInOutcomeReport");
				// "FK_productInStationID", "FK_repQuarter_inventoryReport",
				// "FK_repYear_inventoryReport", "amountSold"
				values = new Object[] { productID, repQuarter, repYear, amountSold };
				TableInserts.insertProductInInventoryReport(connection, values);
				System.out.println("TableInserts.insertProductInInventoryReport");
			}

			PreparedStatement pStmt;
			pStmt = this.connection.prepareStatement(
					"UPDATE income_report SET totalIncome = ? WHERE FK_repQuarter = ? AND FK_repYear = ? AND FK_fuelStationID = ?");
			pStmt.setDouble(1, totalIncome);
			pStmt.setInt(2, repQuarter);
			pStmt.setString(3, repYear);
			pStmt.setInt(4, fuelStationID);
			pStmt.executeUpdate();
			System.out.println("update 1");
			
			pStmt = this.connection.prepareStatement(
					"UPDATE outcome_report SET totalAmountBoughtFromSupplier = ? WHERE FK_repQuarter = ? AND FK_repYear = ? AND FK_fuelStationID = ?");
			pStmt.setDouble(1, totalAmountBoughtFromSupllier);
			pStmt.setInt(2, repQuarter);
			pStmt.setString(3, repYear);
			pStmt.setInt(4, fuelStationID);
			pStmt.executeUpdate();
			System.out.println("update 2");
			
			pStmt = this.connection.prepareStatement(
					"UPDATE inventory_report SET totalAmountSold = ? WHERE FK_repQuarter = ? AND FK_repYear = ? AND FK_fuelStationID = ?");
			pStmt.setDouble(1, totalAmountSold);
			pStmt.setInt(2, repQuarter);
			pStmt.setString(3, repYear);
			pStmt.setInt(4, fuelStationID);
			pStmt.executeUpdate();
			System.out.println("update 3");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	private MyInventoryReport getExistInventoryReport(int fuelStationID, Date dateCreated, String repYear,
			int repQuarter, ArrayList<ProductInStation> productInStationList) {
		HashMap<ProductInStation, Double> amountsPerProduct = new HashMap<>();
		double totalAmountSold = 0;
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT amountSold FROM product_in_inventory_report WHERE FK_repYear_inventoryReport = ? AND FK_repQuarter_inventoryReport = ? AND FK_productInStationID = ?");
			for (ProductInStation pis : productInStationList) {
				pStmt.setString(1, repYear);
				pStmt.setInt(2, repQuarter);
				pStmt.setInt(3, pis.getProductInStationID());
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next())
					return null;
				else {
					Double amountSold = rs.getDouble(1);
					totalAmountSold += amountSold;
					amountsPerProduct.put(pis, amountSold);
				}
			}
		} catch (SQLException e) {
			return null;
		}
		MyInventoryReport result = new MyInventoryReport(repQuarter, repYear, fuelStationID, dateCreated,
				amountsPerProduct, totalAmountSold);
		return result;
	}

	private MyOutcomeReport getExistOutcomeReport(int fuelStationID, Date dateCreated, String repYear, int repQuarter,
			ArrayList<ProductInStation> productInStationList) {
		double totalAmountBoughtFromSupplier = 0;
		HashMap<ProductInStation, Double> amountBoughtPerProduct = new HashMap<>();
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT amountBoughtFromSupplier FROM product_in_outcome_report WHERE FK_repYear_outcomeReport = ? AND FK_repQuarter_outcomeReport = ? AND FK_productInStationID = ?");
			for (ProductInStation pis : productInStationList) {
				pStmt.setString(1, repYear);
				pStmt.setInt(2, repQuarter);
				pStmt.setInt(3, pis.getProductInStationID());
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next())
					return null;
				else {
					Double productBought = rs.getDouble(1);
					amountBoughtPerProduct.put(pis, productBought);
					totalAmountBoughtFromSupplier += productBought;
				}
			}
		} catch (SQLException e) {
			return null;
		}
		MyOutcomeReport result = new MyOutcomeReport(repQuarter, repYear, fuelStationID, dateCreated,
				totalAmountBoughtFromSupplier, amountBoughtPerProduct);
		return result;
	}

	private MyIncomeReport getExistIncomeReport(int fuelStationID, Date dateCreated, String repYear, int repQuarter,
			ArrayList<ProductInStation> productInStationList) {
		double totalIncome = 0;
		HashMap<ProductInStation, Double> incomePerProduct = new HashMap<>();
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT incomePerProduct FROM product_in_income_report WHERE FK_repYear_IncomeReport = ? AND FK_repQuarter_IncomeReport = ? AND FK_productInStationID = ?");
			for (ProductInStation pis : productInStationList) {
				pStmt.setString(1, repYear);
				pStmt.setInt(2, repQuarter);
				pStmt.setInt(3, pis.getProductInStationID());
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next()) {
					return null;
				} else {
					Double productIncome = rs.getDouble(1);
					incomePerProduct.put(pis, productIncome);
					totalIncome += productIncome;
				}
			}
		} catch (SQLException e) {
			return null;
		}
		MyIncomeReport result = new MyIncomeReport(repQuarter, repYear, fuelStationID, dateCreated, totalIncome,
				incomePerProduct);
		return result;
	}

	private ArrayList<QuarterlyReport> getQuarterlyReportbyFuelStationID(int fuelStationID) {
		int repQuarter = -1;
		String repYear = "";
		Date dateCreated;

		ArrayList<QuarterlyReport> result = new ArrayList<QuarterlyReport>();
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT * FROM quarterly_report WHERE FK_fuelStationID = ?");
			pStmt.setInt(1, fuelStationID);
			ResultSet rs = pStmt.executeQuery();
			if (!rs.next())
				return result;
			do {
				repQuarter = rs.getInt(1);
				repYear = rs.getString(2);
				dateCreated = formatter.parse(rs.getString(4));
				result.add(new QuarterlyReport(repQuarter, repYear, fuelStationID, dateCreated));
			} while (rs.next());
			rs.close();
		} catch (SQLException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
		return result;

	}

	private Boolean updateProductInStationThresholdbyfuelStationID(int fuelStationID, String[] newThresholds) {
		int amountOfProduct = newThresholds.length / 2;
		try {
			for (int i = 0; i < newThresholds.length; i += 2) {
				String productName = newThresholds[i];
				Double newThreashold = Double.parseDouble(newThresholds[i + 1]);
				productName = productName.replace("-", " ");
				PreparedStatement pStmt;
				pStmt = this.connection.prepareStatement(
						"UPDATE product_in_station SET threshold = ? WHERE FK_fuelStationID = ? AND FK_productName = ?");
				pStmt.setDouble(1, newThreashold);
				pStmt.setInt(2, fuelStationID);
				pStmt.setString(3, productName);
				pStmt.executeUpdate();
				amountOfProduct--;
			}
		} catch (SQLException e) {
			return null;
		}
		if (amountOfProduct == 0) {
			return true;
		}

		return false;
	}

	private ArrayList<ProductInStation> getProductInStationByfuelStationID(int fuelStationID) {
		ArrayList<ProductInStation> result = new ArrayList<>();
		int productInStationID = -1;
		ProductName productName = ProductName.Nan;
		String productNameStr = "Nan";
		double capacity = -1.0;
		double threshold = -1.0;
		double maxPrice = -1.0;
		double currentPrice = -1.0;
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT * FROM product_in_station WHERE FK_fuelStationID = ?");
			pStmt.setInt(1, fuelStationID);
			ResultSet rs = pStmt.executeQuery();
			if (!rs.next())
				return null;
			do {
				productInStationID = rs.getInt(1);
				productNameStr = rs.getString(2).replaceAll("\\s", "");
				productName = ProductName.valueOf(productNameStr);
				capacity = rs.getDouble(4);
				threshold = rs.getDouble(5);

				PreparedStatement pStmt2 = this.connection
						.prepareStatement("SELECT * FROM product WHERE productName = ?");
				pStmt2.setString(1, productName.toString());
				ResultSet rs2 = pStmt2.executeQuery();
				if (!rs2.next())
					return null;
				else {
					maxPrice = rs2.getDouble(2);
					currentPrice = rs2.getDouble(3);
				}
				rs2.close();
				result.add(new ProductInStation(productInStationID, productName, maxPrice, currentPrice, fuelStationID,
						capacity, threshold));
			} while (rs.next());
			rs.close();
		} catch (SQLException e) {
			return null;
		}
		return result;
	}

	/**
	 * 
	 * @param orderID
	 * @return
	 * @throws ParseException
	 */
	private FuelStationOrder getFuelStationOrderByID(int orderID) {
		int productInStationID = -1;
		boolean assessed = false;
		boolean approved = false;
		boolean supplied = false;
		int ordersID = -1;
		double amountBought = -1.0;
		String reasonDismissal = "";
		String address = "";
		Date timeSupplied = null;
		Date orderTime = null;
		FuelStationOrder result = new FuelStationOrder(ordersID, orderTime, amountBought, address, productInStationID,
				assessed, approved, reasonDismissal, supplied, timeSupplied);
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT * FROM fuel_station_order WHERE FK_ordersID = ?");
			pStmt.setInt(1, orderID);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				productInStationID = rs.getInt(2);
				if (rs.getInt(3) == 1) {// assessed==1
					assessed = true;
					if (rs.getInt(4) == 1) {// approved==1
						approved = true;
						reasonDismissal = rs.getString(5);
					}
					if (rs.getInt(6) == 1) {// supplied==1
						supplied = true;
						timeSupplied =formatter.parse(rs.getString(7));
					}
				}
			}
			pStmt = this.connection.prepareStatement("SELECT * FROM orders WHERE ordersID = ?");
			pStmt.setInt(1, orderID);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				ordersID = rs.getInt(1);
				orderTime = formatter.parse(rs.getString(2));
				amountBought = rs.getDouble(3);
				address = rs.getString(4);
				result = new FuelStationOrder(ordersID, orderTime, amountBought, address, productInStationID, assessed,
						approved, reasonDismissal, supplied, timeSupplied);
			}
			rs.close();
		} catch (SQLException e) {
			return null;
		} catch (ParseException e) {
			return null;
		}
		return result;
	}

	private ProductInStation getProductInStationByOrderID(int orderID) {
		int productInStationID = -1;
		ProductName productName = ProductName.Nan;
		String productNameStr = "Nan";
		double capacity = -1.0;
		double threshold = -1.0;
		double maxPrice = -1.0;
		double currentPrice = -1.0;
		int fuelStationID = -1;
		ProductInStation result = new ProductInStation(productInStationID, productName, maxPrice, currentPrice,
				fuelStationID, capacity, threshold);
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT FK_productInStationID FROM fuel_station_order WHERE FK_ordersID = ?");
			pStmt.setInt(1, orderID);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next())
				productInStationID = rs.getInt(1);
			rs.close();
			if (productInStationID == -1)
				return result;
			pStmt = this.connection.prepareStatement("SELECT * FROM product_in_station WHERE productInStationID = ?");
			pStmt.setInt(1, productInStationID);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				productInStationID = rs.getInt(1);
				productNameStr = rs.getString(2).replaceAll("\\s", "");
				productName = ProductName.valueOf(productNameStr);
				fuelStationID = rs.getInt(3);
				capacity = rs.getDouble(4);
				threshold = rs.getDouble(5);
			}
			rs.close();
			if (productNameStr.equals("Nan"))
				return result;
			pStmt = this.connection.prepareStatement("SELECT * FROM product WHERE productName = ?");
			pStmt.setString(1, productNameStr);
			rs = pStmt.executeQuery();
			if (rs.next()) {
				maxPrice = rs.getDouble(2);
				currentPrice = rs.getDouble(3);
			}
			rs.close();
			result = new ProductInStation(productInStationID, productName, maxPrice, currentPrice, fuelStationID,
					capacity, threshold);

		} catch (SQLException e) {
			return null;
		}
		return result;
	}

	private int getEmployeeID(String username) {
		int result = -1;
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT employeeID FROM employee WHERE FK_userName = ?");
			pStmt.setString(1, username);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {

		}
		return result;
	}

	private int getFuelStationID(int employeeID) {
		int result = -1;
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT fuelStationID FROM fuel_station WHERE FK_employeeID = ?");
			pStmt.setInt(1, employeeID);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
			rs.close();
		} catch (SQLException e) {

		}
		return result;
	}

	private List<Integer> getProductInStationIDs(int fuelStationID) {
		List<Integer> result = new ArrayList<Integer>();
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT productInStationID FROM product_in_station WHERE FK_fuelStationID = ?");
			pStmt.setInt(1, fuelStationID);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {

		}
		return result;
	}

	private List<Integer> getUnassessedFuelStationOrdersID(int productInStationID) {
		List<Integer> result = new ArrayList<Integer>();
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT FK_ordersID FROM fuel_station_order WHERE FK_productInStationID = ? AND assessed = 0");
			pStmt.setInt(1, productInStationID);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				result.add(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {

		}
		return result;
	}

}
