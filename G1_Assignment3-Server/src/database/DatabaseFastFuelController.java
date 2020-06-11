package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.FastFuel;
import enums.FuelCompanyName;
import enums.ProductName;

/**
 * controller for fast fuel
 * 
 * @version Basic
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
					"SELECT FK_fuelCompanyName1, FK_fuelCompanyName2, FK_fuelCompanyName3 FROM purchasing_program WHERE FK_customerID = ?");
			pStmt.setString(1, customerID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("purchasing program doesn't exist");
				return fastFuel;
			}
			String fuelCompanyName1 = rs.getString(1);
			String fuelCompanyName2 = rs.getString(2);
			String fuelCompanyName3 = rs.getString(3);
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
			pStmt = this.connection
					.prepareStatement("SELECT currentDiscount FROM pricing_model WHERE FK_customerID = ?");
			pStmt.setString(1, customerID);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				fastFuel.setFunction("pricing model doesn't exist");
				return fastFuel;
			}
			double pricingModelDiscount = rs.getDouble(1);
			rs.close();

			// 1 - check if there is active sale on fuelType
			boolean activeSaleFlag = false;
			int saleID = -1, salesPatternID = -1;
			pStmt = this.connection.prepareStatement("SELECT saleID, FK_salesPatternID FROM sale WHERE active = 1");
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				activeSaleFlag = false;
			} else {
				activeSaleFlag = true;
				saleID = rs.getInt(1);
				salesPatternID = rs.getInt(2);
			}
			rs.close();

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

}
