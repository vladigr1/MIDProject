package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entities.FuelStationOrder;
import entities.Orders;
import entities.Product;
import entities.ProductInStation;
import entities.StationProductInOrder;
import entities.UnassedOrderIDList;
import enums.ProductName;

public class DatabaseFuelStationManagerController {

	private static DatabaseFuelStationManagerController instance;

	private Connection connection;

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

	//////////////////////////////// functions/////////////////////////////////////////////
	public UnassedOrderIDList getUnassesdOrderID(String userName) {
		UnassedOrderIDList unassedOrderList = new UnassedOrderIDList(new ArrayList<>());
		int employeeID = getEmployeeID(userName);
		if (employeeID == -1)
			return unassedOrderList;
		int fuelStationID = getFuelStationID(employeeID);
		if (fuelStationID == -1)
			return unassedOrderList;
		List<Integer> productInStationID = getProductInStationIDs(fuelStationID);
		if (productInStationID.isEmpty())
			return unassedOrderList;
		List<Integer> fuelStationOrdersID = new ArrayList<Integer>();
		for (Integer productID : productInStationID)
			fuelStationOrdersID.addAll(getUnassessedFuelStationOrdersID(productID));

		unassedOrderList.setList(fuelStationOrdersID);
		return unassedOrderList;
	}

	public Object[] getStationProductInOrderbyOrderID(int orderID) throws ParseException {
		ProductInStation productInStation = getProductInStationByOrderID(orderID);
		Orders order = getFuelStationOrderByID(orderID);
		return new Object[] { productInStation, order };
	}

	private FuelStationOrder getFuelStationOrderByID(int orderID) throws ParseException {
		int productInStationID = -1;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
						timeSupplied=new Date();
						timeSupplied =formatter.parse(rs.getString(7));
						timeSupplied = rs.getDate(7);
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
		ProductInStation result = new ProductInStation(productName, maxPrice, currentPrice, fuelStationID, capacity,
				threshold);
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
				fuelStationID=rs.getInt(3);
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
			result = new ProductInStation(productName, maxPrice, currentPrice, fuelStationID, capacity, threshold);

		} catch (SQLException e) {
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
					"SELECT FK_ordersID FROM fuel_station_order WHERE FK_productInStationID = ? AND assessed=0");
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
