package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import entities.FastFuel;
import enums.FuelCompanyName;
import enums.PricingModelName;
import enums.ProductName;
import enums.PurchasingProgramName;

/**
 * controller for fast fuel
 * 
 * @version Final
 * @author Lior
 */
public class DatabaseFastFuelController {

	private static DatabaseFastFuelController instance;
	private Connection connection;

	/**
	 * singleton class constructor
	 */
	private DatabaseFastFuelController(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * @return instance of this class
	 */
	public static DatabaseFastFuelController getInstance(Connection connection) {
		if (instance == null) {
			instance = new DatabaseFastFuelController(connection);
		}
		return instance;
	}

	/**
	 * 
	 * @param fastFuel
	 * @return fuel type of car and price per liter after discounts
	 */
	public FastFuel getFuelTypeAndPricePerLiter(FastFuel fastFuel) {
		try {
			String regPlate = fastFuel.getRegistrationPlate();
			int fuelStationID = fastFuel.getFuelStationID();

			// check regPlate exist
			int exists = DatabaseMarketingRepresentativeController.getInstance(connection).checkCarExists(regPlate);
			if (exists != 0) {
				fastFuel.setFunction("car doesn't exist");
				return fastFuel;
			}

			// get customerID by regPlate
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT FK_customerID FROM car WHERE registrationPlate = ? AND deleted = 0");
			pStmt.setString(1, regPlate);
			ResultSet rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("customer doesn't exist");
				return fastFuel;
			}
			String customerID = rs.getString(1);
			rs.close();

			// get fuelCompanyName by fuelStationID
			pStmt = this.connection
					.prepareStatement("SELECT FK_fuelCompanyName FROM fuel_station WHERE fuelStationID = ?");
			pStmt.setInt(1, fuelStationID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("fuel station doesn't exist");
				return fastFuel;
			}
			String fuelCompanyName = rs.getString(1);
			rs.close();

			// check fuelCompanyName in customer's purchasing program
			pStmt = this.connection.prepareStatement(
					"SELECT FK_fuelCompanyName1, FK_fuelCompanyName2, FK_fuelCompanyName3, FK_purchasingProgramName FROM purchasing_program WHERE FK_customerID = ?");
			pStmt.setString(1, customerID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("purchasing program doesn't exist");
				return fastFuel;
			}
			String fuelCompanyName1 = rs.getString(1);
			String fuelCompanyName2 = rs.getString(2);
			String fuelCompanyName3 = rs.getString(3);
			PurchasingProgramName purchasingProgramName = PurchasingProgramName.valueOf(rs.getString(4));
			rs.close();

			if (!fuelCompanyName.equals(fuelCompanyName1) && !fuelCompanyName.equals(fuelCompanyName2)
					&& !fuelCompanyName.equals(fuelCompanyName3)) {
				fastFuel.setFunction("purchasing program doesn't fit");
				return fastFuel;
			}

			// get car's fuelType by regPlate
			pStmt = this.connection
					.prepareStatement("SELECT FK_productName FROM car WHERE registrationPlate = ? AND deleted = 0");
			pStmt.setString(1, regPlate);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("fuel type doesn't exist");
				return fastFuel;
			}
			String productName = rs.getString(1).replaceAll("\\s", "");
			ProductName fuelType = ProductName.valueOf(productName);
			rs.close();

			// get product's current price by productName
			pStmt = this.connection.prepareStatement("SELECT currentPrice FROM product WHERE productName = ?");
			pStmt.setString(1, fuelType.toString());
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("current price of fuel type doesn't exist");
				return fastFuel;
			}
			double currentPrice = rs.getDouble(1);
			rs.close();

			// get pricingModelDiscount by customerID
			pStmt = this.connection.prepareStatement(
					"SELECT currentDiscount, FK_pricingModelName FROM pricing_model WHERE FK_customerID = ?");
			pStmt.setString(1, customerID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("pricing model doesn't exist");
				return fastFuel;
			}
			double pricingModelDiscount = rs.getDouble(1);
			PricingModelName pricingModelName = PricingModelName.valueOf(rs.getString(2).replaceAll("\\s", ""));
			rs.close();

			// 1 - check if there is active sale on fuelType
			boolean activeSaleFlag = false;
			int saleID = -1, salesPatternID = -1;
			pStmt = this.connection.prepareStatement("SELECT saleID, FK_salesPatternID, startTime, endTime FROM sale");
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				activeSaleFlag = false;
			}
			do {
				saleID = rs.getInt(1);
				salesPatternID = rs.getInt(2);
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date startTime = formatter.parse(rs.getString(3));
				Date endTime = formatter.parse(rs.getString(4));
				if (startTime.compareTo(new Date()) <= 0 && endTime.compareTo(new Date()) >= 0) {
					activeSaleFlag = true;
					break;
				}
			} while (rs.next());
			rs.close();
			if (activeSaleFlag == false) {
				saleID = -1;
				salesPatternID = -1;
			}

			double salesDiscount = 0;
			// 2 - yes? save salesDiscount
			if (activeSaleFlag == true) {
				pStmt = this.connection.prepareStatement(
						"SELECT salesDiscount FROM product_in_sales_pattern WHERE FK_salesPatternID = ? AND FK_productName = ?");
				pStmt.setInt(1, salesPatternID);
				pStmt.setString(2, fuelType.toString());
				rs = pStmt.executeQuery();
				if (!rs.next()) {
					salesDiscount = 0;
					saleID = -1;
				} else {
					salesDiscount = rs.getDouble(1);
				}
				rs.close();
			}

			double finalPrice = (currentPrice - salesDiscount) * (1 - pricingModelDiscount);

			fastFuel.setFuelCompanyName(FuelCompanyName.valueOf(fuelCompanyName));
			fastFuel.setFinalPrice(finalPrice); // price per liter
			fastFuel.setSaleID(saleID);
			fastFuel.setFuelType(fuelType);
			fastFuel.setCustomerID(customerID);
			fastFuel.setFastFuelTime(new Date());
			fastFuel.setPricingModelDiscount(pricingModelDiscount);
			fastFuel.setPurchasingProgramName(purchasingProgramName);
			fastFuel.setPricingModelName(pricingModelName);
			fastFuel.setCurrentPrice(currentPrice);
			fastFuel.setSalesDiscount(salesDiscount);
			fastFuel.setFunction("getFuelTypeAndPricePerLiter success");
			return fastFuel;

		} catch (SQLException e) {
			e.printStackTrace();
			fastFuel.setFunction("fail");
			return fastFuel;
		} catch (Exception ex) {
			ex.printStackTrace();
			fastFuel.setFunction("fail");
			return fastFuel;
		}
	}

	/**
	 * 
	 * @param fastFuel
	 * @return message of success or fail in fastFuel function
	 */
	public FastFuel saveFastFuel(FastFuel fastFuel) {
		try {
			String regPlate = fastFuel.getRegistrationPlate();
			String customerID = fastFuel.getCustomerID();
			Date fastFuelTime = fastFuel.getFastFuelTime();
			double amountBought = fastFuel.getAmountBought();
			double finalPrice = fastFuel.getFinalPrice() * amountBought;
			String productName = fastFuel.getFuelType().toString();
			int fuelStationID = fastFuel.getFuelStationID();
			int saleID = fastFuel.getSaleID();

			// get productInStationID by productName and fuelStationID
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT productInStationID, capacity, threshold FROM product_in_station WHERE FK_productName = ? AND FK_fuelStationID = ?");
			pStmt.setString(1, productName);
			pStmt.setInt(2, fuelStationID);
			ResultSet rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("productInStationID doesn't exist");
				return fastFuel;
			}
			int productInStationID = rs.getInt(1);
			double capacity = rs.getDouble(2) - amountBought;
			double threshold = rs.getDouble(3);
			rs.close();
			if (capacity < threshold / 2) {
				fastFuel.setFunction("emulation fail - amountBought is too much and not realistic");
				return fastFuel;
			}

			// "FK_registrationPlate", "FK_customerID", "FK_productInStationID",
			// "fastFuelTime", "amountBought", "finalPrice"
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fastFuelTime);
			calendar.add(Calendar.HOUR, -2);
			calendar.add(Calendar.MINUTE, -30);
			fastFuelTime = calendar.getTime();

			Object[] values1 = { regPlate, customerID, productInStationID, fastFuelTime, amountBought, finalPrice };
			TableInserts.insertFastFuel(connection, values1);

			// update stock of productInStationID
			pStmt = this.connection
					.prepareStatement("UPDATE product_in_station SET capacity = ? WHERE productInStationID = ?");
			pStmt.setDouble(1, capacity);
			pStmt.setInt(2, productInStationID);
			pStmt.executeUpdate();
			if (capacity < threshold) {
				if (makeNewFuelStationOrder(fastFuelTime, productInStationID, capacity, threshold) == false) {
					// and make notification for fuelStationManager
					fastFuel.setFunction("fail makeNewFuelStationOrder");
					return fastFuel;
				}
			}

			pStmt = this.connection.prepareStatement(
					"SELECT FK_pricingModelName, lastMonthUtillization FROM pricing_model WHERE FK_customerID = ?");
			pStmt.setString(1, customerID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("pricingModelName doesn't exist");
				return fastFuel;
			}
			String pricingModelName = rs.getString(1);
			double lastMonthUtillization = rs.getDouble(2) + 0.17;
			rs.close();
			if (pricingModelName.equals(PricingModelName.FullProgramSingleCar.toString())) {
				pStmt = this.connection
						.prepareStatement("UPDATE pricing_model SET lastMonthUtillization = ? WHERE FK_customerID = ?");
				pStmt.setDouble(1, lastMonthUtillization);
				pStmt.setString(2, customerID);
				pStmt.executeUpdate();
			}

			if (saleID != -1) {
				if (updateCustomerBoughtInSale(saleID, customerID, finalPrice) == false) {
					fastFuel.setFunction("fail updateCustomerBoughtInSale");
					return fastFuel;
				}
			}

			String fuelCompanyName = fastFuel.getFuelCompanyName().toString();
			if (updateCustomerBoughtFromCompany(customerID, fastFuelTime, fuelCompanyName, amountBought,
					finalPrice) == false) {
				fastFuel.setFunction("fail updateCustomerBoughtFromCompany");
				return fastFuel;
			}

			fastFuel.setFinalPrice(finalPrice);
			fastFuel.setFunction("save fast fuel success");
			return fastFuel;

		} catch (SQLException e) {
			e.printStackTrace();
			fastFuel.setFunction("fail");
			return fastFuel;
		} catch (Exception ex) {
			ex.printStackTrace();
			fastFuel.setFunction("fail");
			return fastFuel;
		}
	}

	/**********************************
	 * private methods
	 ******************************************/
	/**
	 * 
	 * @param saleID
	 * @param customerID
	 * @param amountPaid
	 * @return boolean
	 */

	private boolean updateCustomerBoughtInSale(int saleID, String customerID, double amountPaid) {
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT amountPaid FROM customer_bought_in_sale WHERE FK_saleID = ? AND FK_customerID = ?");
			pStmt.setInt(1, saleID);
			pStmt.setString(2, customerID);
			ResultSet rs = pStmt.executeQuery();

			if (!rs.next()) { // doesn't already exist
				// "FK_saleID", "FK_customerID", "amountPaid"
				Object[] values1 = { saleID, customerID, amountPaid };
				TableInserts.insertCustomerBoughtInSale(connection, values1);

			} else { // does exist
				amountPaid += rs.getDouble(1);

				pStmt = this.connection.prepareStatement(
						"UPDATE customer_bought_in_sale SET amountPaid = ? WHERE FK_saleID = ? AND FK_customerID = ?");
				pStmt.setDouble(1, amountPaid);
				pStmt.setInt(2, saleID);
				pStmt.setString(3, customerID);
				pStmt.executeUpdate();
			}

			rs.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}
	/**
	 * 
	 * @param customerID
	 * @param fastFuelTime
	 * @param fuelCompanyName
	 * @param amountBought
	 * @param amountPaid
	 * @return boolean
	 */

	private boolean updateCustomerBoughtFromCompany(String customerID, Date fastFuelTime, String fuelCompanyName,
			double amountBought, double amountPaid) {
		try {
			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT amountBoughtFromCompany, amountPaidCompany FROM customer_bought_from_company WHERE FK_customerID = ? AND FK_fuelCompanyName = ? AND DATE(dateOfPurchase) = ?");
			pStmt.setString(1, customerID);
			pStmt.setString(2, fuelCompanyName);
			pStmt.setDate(3, new java.sql.Date(fastFuelTime.getTime()));
			ResultSet rs = pStmt.executeQuery();

			if (!rs.next()) { // doesn't already exist
				// "FK_customerID", "FK_fuelCompanyName", "dateOfPurchase",
				// "amountBoughtFromCompany", "amountPaidCompany"
				Object[] values1 = { customerID, fuelCompanyName, fastFuelTime, amountBought, amountPaid };
				TableInserts.insertCustomerBoughtFromCompany(connection, values1);

			} else { // does exist
				amountBought += rs.getDouble(1);
				amountPaid += rs.getDouble(2);

				pStmt = this.connection.prepareStatement(
						"UPDATE customer_bought_from_company SET amountBoughtFromCompany = ?, amountPaidCompany = ? WHERE FK_customerID = ? AND FK_fuelCompanyName = ? AND DATE(dateOfPurchase) = ?");
				pStmt.setDouble(1, amountBought);
				pStmt.setDouble(2, amountPaid);
				pStmt.setString(3, customerID);
				pStmt.setString(4, fuelCompanyName);
				pStmt.setDate(5, new java.sql.Date(fastFuelTime.getTime()));
				pStmt.executeUpdate();
			}

			rs.close();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * 
	 * @param fastFuelTime
	 * @param productInStationID
	 * @param capacity
	 * @param threshold
	 * @return boolean
	 */

	private boolean makeNewFuelStationOrder(Date fastFuelTime, int productInStationID, double capacity,
			double threshold) {
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT FK_fuelStationID FROM product_in_station WHERE productInStationID = ?");
			pStmt.setInt(1, productInStationID);
			ResultSet rs = pStmt.executeQuery();
			if (!rs.next()) {
				return false;
			}
			int fuelStationID = rs.getInt(1);
			rs.close();

			pStmt = this.connection
					.prepareStatement("SELECT FK_employeeID, address FROM fuel_station WHERE fuelStationID = ?");
			pStmt.setInt(1, fuelStationID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				return false;
			}
			int fuelStationManagerID = rs.getInt(1);
			String address = rs.getString(2);
			rs.close();

			// "orderTime", "amountBought", "address"
			Object[] values1 = { fastFuelTime, (threshold - capacity) * 2, address };
			TableInserts.insertOrders(connection, values1);

			pStmt = this.connection.prepareStatement("SELECT MAX(ordersID) FROM orders");
			rs = pStmt.executeQuery();
			if (!rs.next())
				return false;
			int ordersID = rs.getInt(1);
			rs.close();

			// 1 - "FK_ordersID", "FK_productInStationID", "assessed", "supplied"
			Object[] values2 = { ordersID, productInStationID, false, false };
			TableInserts.insertFuelStationOrder1(connection, values2);

			// "FK_employeeID", "message", "dismissed", "dateCreated"
			Object[] values3 = { fuelStationManagerID, "station order " + ordersID + " is ready to be assessed", false,
					fastFuelTime };
			TableInserts.insertNotification(connection, values3);

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}
