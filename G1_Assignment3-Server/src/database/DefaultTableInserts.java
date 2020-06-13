package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import enums.Affiliation;
import enums.CustomerType;
import enums.FuelCompanyName;
import enums.PricingModelName;
import enums.ProductName;
import enums.PurchasingProgramName;
import enums.ShipmentType;

/**
 * fills the database with predetermined "true" information
 * 
 * @author Elroy, Lior, Vlad
 */
public class DefaultTableInserts {

	/**
	 * 
	 * @param con
	 * @return message for server window
	 * @throws SQLException
	 */
	public static String InsertDefaultTables(Connection con) throws SQLException {
		try {
			insertDefaultUser(con);
			insertDefaultEmployee(con);
			insertDefaultCustomer(con);
			insertDefaultSalesPattern(con);
			insertDefaultFuelStationManager(con);
			insertDefaultProduct(con);
			insertDefaultProductInSalesPattern(con);
			insertDefaultSale(con);
			insertDefaultProductRatesUpdateRequest(con);
			insertDefaultProductInRequest(con);
			insertDefaultFuelCompany(con);
			insertDefaultFuelStation(con);
			insertDefaultProductInStation(con);
			insertDefaultQuarterlyReport(con);
			insertDefaultIncomeReport(con);
			insertDefaultProductInIncomeReport(con);
			insertDefaultOutcomeReport(con);
			insertDefaultProductInOutcomeReport(con);
			insertDefaultInventoryReport(con);
			insertDefaultProductInInventoryReport(con);
			insertDefaultCustomerBoughtInSale(con);
			insertDefaultSaleCommentsReport(con);
			insertDefaultCar(con);
			insertDefaultRankingSheet(con);
			insertDefaultPricingModelType(con);
			insertDefaultPricingModel(con);
			insertDefaultNotification(con);
			insertDefaultShipmentMethod(con);
			insertDefaultOrders(con);
			insertDefaultFuelStationOrder(con);
			insertDefaultPurchasingProgramType(con);
			insertDefaultHomeFuelOrder(con);
			insertDefaultPurchasingProgram(con);
			insertCustomerBoughtFromCompany(con);
			insertDefaultPeriodicCustomersReport(con);
			insertDefaultActivity(con);
			insertDefaultFastFuel(con);
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return "Filling tables succeeded";
	}

	/**
	 * 
	 * @param con
	 * @param tableName
	 * @return true if table is empty
	 * @throws SQLException
	 */
	private static boolean checkTableEmpty(Connection con, String tableName) throws SQLException {
		PreparedStatement pStmt = con.prepareStatement("SELECT COUNT(*) FROM " + tableName);
		ResultSet rs1 = pStmt.executeQuery();
		rs1.next();
		int count = rs1.getInt(1);
		rs1.close();
		if (count == 0)
			return true;
		return false;
	}

	private static void insertDefaultUser(Connection con) throws SQLException {
		if (checkTableEmpty(con, "user") == false)
			return;
		// "username", "password", "connected", "email", "firstName", "surname"
		Object[] values1 = { "IsraelThePersonCustomer", "1234", false, "IsraelThePersonCustomer@gmail.com", "Israel",
				"ThePersonCustomer" };
		TableInserts.insertUser(con, values1);
		Object[] values2 = { "IsraelTheCompanyCustomer", "1234", false, "IsraelTheCompanyCustomer@gmail.com", "Israel",
				"TheCompanyCustomer" };
		TableInserts.insertUser(con, values2);
		Object[] values3 = { "IsraelTheSonolFuelStationManager", "1234", false,
				"IsraelTheSonolFuelStationManager@gmail.com", "Israel", "TheSonolFuelStationManager" };
		TableInserts.insertUser(con, values3);
		Object[] values4 = { "IsraelThePazFuelStationManager", "1234", false,
				"IsraelThePazFuelStationManager@gmail.com", "Israel", "ThePazFuelStationManager" };
		TableInserts.insertUser(con, values4);
		Object[] values5 = { "IsraelTheMarketingManager", "1234", false, "IsraelTheMarketingManager@gmail.com",
				"Israel", "TheMarketingManager" };
		TableInserts.insertUser(con, values5);
		Object[] values6 = { "IsraelTheMarketingRepresentative", "1234", false,
				"IsraelTheMarketingRepresentative@gmail.com", "Israel", "TheMarketingRepresentative" };
		TableInserts.insertUser(con, values6);
		Object[] values7 = { "IsraelTheNetworkManager", "1234", false, "IsraelTheNetworkManager@gmail.com", "Israel",
				"TheNetworkManager" };
		TableInserts.insertUser(con, values7);
		Object[] values8 = { "IsraelTheSonolSupplier", "1234", false, "IsraelTheSonolSupplier@gmail.com", "Israel",
				"TheSonolSupplier" };
		TableInserts.insertUser(con, values8);
		Object[] values9 = { "IsraelThePazSupplier", "1234", false, "IsraelThePazSupplier@gmail.com", "Israel",
				"ThePazSupplier" };
		TableInserts.insertUser(con, values9);
		Object[] values10 = { "IsraelTheDelekSupplier", "1234", false, "IsraelTheDelekSupplier@gmail.com", "Israel",
				"TheDelekSupplier" };
		TableInserts.insertUser(con, values10);
		Object[] values11 = { "IsraelTheDelekFuelStationManager", "1234", false,
				"IsraelTheDelekFuelStationManager@gmail.com", "Israel", "TheDelekFuelStationManager" };
		TableInserts.insertUser(con, values11);
	}

	private static void insertDefaultEmployee(Connection con) throws SQLException {
		if (checkTableEmpty(con, "employee") == false)
			return;
		// "FK_userName", "role", "affiliation"
		Object[] values1 = { "IsraelTheSonolFuelStationManager", "FuelStationManager",
				Affiliation.FuelStation.toString() };
		TableInserts.insertEmployee(con, values1);
		Object[] values2 = { "IsraelThePazFuelStationManager", "FuelStationManager",
				Affiliation.FuelStation.toString() };
		TableInserts.insertEmployee(con, values2);
		Object[] values3 = { "IsraelTheMarketingManager", "MarketingManager", Affiliation.Marketing.toString() };
		TableInserts.insertEmployee(con, values3);
		Object[] values4 = { "IsraelTheMarketingRepresentative", "MarketingRepresentative",
				Affiliation.Marketing.toString() };
		TableInserts.insertEmployee(con, values4);
		Object[] values5 = { "IsraelTheNetworkManager", "NetworkManager", Affiliation.Management.toString() };
		TableInserts.insertEmployee(con, values5);
		Object[] values6 = { "IsraelTheSonolSupplier", "Supplier", Affiliation.Supplier.toString() };
		TableInserts.insertEmployee(con, values6);
		Object[] values7 = { "IsraelThePazSupplier", "Supplier", Affiliation.Supplier.toString() };
		TableInserts.insertEmployee(con, values7);
		Object[] values8 = { "IsraelTheDelekSupplier", "Supplier", Affiliation.Supplier.toString() };
		TableInserts.insertEmployee(con, values8);
		Object[] values9 = { "IsraelTheDelekFuelStationManager", "FuelStationManager",
				Affiliation.FuelStation.toString() };
		TableInserts.insertEmployee(con, values9);
	}

	private static void insertDefaultCustomer(Connection con) throws SQLException {
		if (checkTableEmpty(con, "customer") == false)
			return;
		// "customerID", "FK_userName", "creditCard", "customerType", "deleted"
		Object[] values1 = { "111111111", "IsraelThePersonCustomer", "1111222233334444", CustomerType.Person.toString(),
				false };
		TableInserts.insertCustomer(con, values1);
		Object[] values2 = { "222222222", "IsraelTheCompanyCustomer", "4444333322221111",
				CustomerType.Company.toString(), false };
		TableInserts.insertCustomer(con, values2);
	}

	private static void insertDefaultSalesPattern(Connection con) throws SQLException {
		if (checkTableEmpty(con, "sales_pattern") == false)
			return;
		// "durationInMinutes"
		Object[] values1 = { 30 };
		TableInserts.insertSalesPattern(con, values1);
		// "durationInMinutes"
		Object[] values2 = { 60 };
		TableInserts.insertSalesPattern(con, values2);
	}

	private static void insertDefaultFuelStationManager(Connection con) throws SQLException {
		if (checkTableEmpty(con, "fuel_station_manager") == false)
			return;
		// "FK_employeeID", "phoneNo"
		Object[] values1 = { "1", "0501111111" };
		TableInserts.insertFuelStationManager(con, values1);
		Object[] values2 = { "2", "0502222222" };
		TableInserts.insertFuelStationManager(con, values2);
		Object[] values3 = { "9", "0509999999" };
		TableInserts.insertFuelStationManager(con, values3);
	}

	private static void insertDefaultProduct(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product") == false)
			return;
		// "productName", "maxPrice", "currentPrice"
		Object[] values1 = { ProductName.Gasoline.toString(), 10, 5 };
		TableInserts.insertProduct(con, values1);
		Object[] values2 = { ProductName.Diesel.toString(), 10, 6 };
		TableInserts.insertProduct(con, values2);
		Object[] values3 = { ProductName.MotorbikeFuel.toString(), 15, 7.5 };
		TableInserts.insertProduct(con, values3);
		Object[] values4 = { ProductName.HomeFuel.toString(), 5, 2 };
		TableInserts.insertProduct(con, values4);
	}

	private static void insertDefaultProductInSalesPattern(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_sales_pattern") == false)
			return;
		// "FK_salesPatternID", "FK_productName", "salesDiscount"
		Object[] values1 = { "1", ProductName.Diesel.toString(), 1.5 };
		TableInserts.insertProductInSalesPattern(con, values1);
		Object[] values2 = { "1", ProductName.MotorbikeFuel.toString(), 3 };
		TableInserts.insertProductInSalesPattern(con, values2);
		Object[] values3 = { "2", ProductName.Gasoline.toString(), 1 };
		TableInserts.insertProductInSalesPattern(con, values3);
	}

	private static void insertDefaultSale(Connection con) throws SQLException {
		if (checkTableEmpty(con, "sale") == false)
			return;
		// "FK_salesPatternID", "startTime", "endTime"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 18 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.YEAR, 2019);
		calendar2.set(Calendar.MONTH, 5 - 1);
		calendar2.set(Calendar.DAY_OF_MONTH, 7);
		calendar2.set(Calendar.HOUR, 19 - 2 - 12);
		calendar2.set(Calendar.MINUTE, 00 - 30);
		Object[] values1 = { "1", calendar1.getTime(), calendar2.getTime() };
		TableInserts.insertSale(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		calendar2.set(Calendar.YEAR, 2019);
		calendar2.set(Calendar.MONTH, 5 - 1);
		calendar2.set(Calendar.DAY_OF_MONTH, 14);
		calendar2.set(Calendar.HOUR, 13 - 2 - 12);
		calendar2.set(Calendar.MINUTE, 00 - 30);
		Object[] values2 = { "2", calendar1.getTime(), calendar2.getTime() };
		TableInserts.insertSale(con, values2);
	}

	private static void insertDefaultProductRatesUpdateRequest(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_rates_update_request") == false)
			return;
		// 1 - "requestDate", "assessed"
		// 2 - "requestDate", "assessed", "approved"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 16 - 1);
		Object[] values1 = { calendar1.getTime(), false };
		TableInserts.insertProductRatesUpdateRequest1(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 13 - 1);
		Object[] values2 = { calendar1.getTime(), true, false };
		TableInserts.insertProductRatesUpdateRequest2(con, values2);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 5 - 1);
		Object[] values3 = { calendar1.getTime(), true, true };
		TableInserts.insertProductRatesUpdateRequest2(con, values3);
	}

	private static void insertDefaultProductInRequest(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_request") == false)
			return;
		// "FK_updateRateRequestID", "FK_productName", "requestedRate"
		Object[] values1 = { "1", ProductName.Diesel.toString(), 7 };
		TableInserts.insertProductInRequest(con, values1);
		Object[] values2 = { "1", ProductName.MotorbikeFuel.toString(), 6.5 };
		TableInserts.insertProductInRequest(con, values2);
		Object[] values3 = { "2", ProductName.Gasoline.toString(), 9 };
		TableInserts.insertProductInRequest(con, values3);
		Object[] values4 = { "3", ProductName.Gasoline.toString(), 5 };
		TableInserts.insertProductInRequest(con, values4);
		Object[] values5 = { "3", ProductName.Diesel.toString(), 4 };
		TableInserts.insertProductInRequest(con, values5);
		Object[] values6 = { "3", ProductName.MotorbikeFuel.toString(), 7.5 };
		TableInserts.insertProductInRequest(con, values6);
		Object[] values7 = { "3", ProductName.HomeFuel.toString(), 3 };
		TableInserts.insertProductInRequest(con, values7);
	}

	private static void insertDefaultFuelCompany(Connection con) throws SQLException {
		if (checkTableEmpty(con, "fuel_company") == false)
			return;
		// "fuelCompanyName", "FK_employeeID" = supplier
		Object[] values1 = { FuelCompanyName.Sonol.toString(), "6" };
		TableInserts.insertFuelCompany(con, values1);
		Object[] values2 = { FuelCompanyName.Paz.toString(), "7" };
		TableInserts.insertFuelCompany(con, values2);
		Object[] values3 = { FuelCompanyName.Delek.toString(), "8" };
		TableInserts.insertFuelCompany(con, values3);
	}

	private static void insertDefaultFuelStation(Connection con) throws SQLException {
		if (checkTableEmpty(con, "fuel_station") == false)
			return;
		// "FK_fuelCompanyName", "FK_employeeID", "stationName", "address"
		Object[] values1 = { "Sonol", "1", "Sonol Neighborhood", "Peretz St, Kiryat Ata" };
		TableInserts.insertFuelStation(con, values1);
		Object[] values2 = { "Paz", "2", "Paz Gas Ltd", "Road 6" };
		TableInserts.insertFuelStation(con, values2);
		Object[] values3 = { "Delek", "9", "Delek Dror", "Dror St, Karmiel" };
		TableInserts.insertFuelStation(con, values3);
	}

	private static void insertDefaultProductInStation(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_station") == false)
			return;
		// "FK_productName", "FK_fuelStationID", "capacity", "threshold"
		Object[] values1 = { ProductName.Gasoline.toString(), "1", 1340 * 3, 750 * 3 };
		TableInserts.insertProductInStation(con, values1);
		Object[] values2 = { ProductName.Diesel.toString(), "1", 895 * 3, 400 * 3 };
		TableInserts.insertProductInStation(con, values2);
		Object[] values3 = { ProductName.MotorbikeFuel.toString(), "1", 500 * 3, 150 * 3 };
		TableInserts.insertProductInStation(con, values3);
		Object[] values4 = { ProductName.Gasoline.toString(), "2", 13400, 7500 };
		TableInserts.insertProductInStation(con, values4);
		Object[] values5 = { ProductName.Diesel.toString(), "2", 8950, 4000 };
		TableInserts.insertProductInStation(con, values5);
		Object[] values6 = { ProductName.MotorbikeFuel.toString(), "2", 5000, 1500 };
		TableInserts.insertProductInStation(con, values6);
		Object[] values7 = { ProductName.Gasoline.toString(), "3", 13400 / 5, 7500 / 5 };
		TableInserts.insertProductInStation(con, values7);
		Object[] values8 = { ProductName.Diesel.toString(), "3", 8950 / 5, 4000 / 5 };
		TableInserts.insertProductInStation(con, values8);
		Object[] values9 = { ProductName.MotorbikeFuel.toString(), "3", 5000 / 5, 1500 / 5 };
		TableInserts.insertProductInStation(con, values9);
	}

	private static void insertDefaultQuarterlyReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "quarterly_report") == false)
			return;
		// "repQuarter", "repYear", "FK_fuelStationID", "dateCreated"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 7 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1 - 1);
		Object[] values1 = { 2, "2019", "1", calendar1.getTime() };
		TableInserts.insertQuarterlyReport(con, values1);
		Object[] values2 = { 2, "2019", "2", calendar1.getTime() };
		TableInserts.insertQuarterlyReport(con, values2);
		Object[] values3 = { 2, "2019", "3", calendar1.getTime() };
		TableInserts.insertQuarterlyReport(con, values3);
	}

	private static void insertDefaultIncomeReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "income_report") == false)
			return;
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID", "totalIncome"
		Object[] values1 = { 2, "2019", "1", 875 };
		TableInserts.insertIncomeReport(con, values1);
		Object[] values2 = { 2, "2019", "2", 455 };
		TableInserts.insertIncomeReport(con, values2);
		Object[] values3 = { 2, "2019", "3", 1113 };
		TableInserts.insertIncomeReport(con, values3);
	}

	private static void insertDefaultProductInIncomeReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_income_report") == false)
			return;
		// "FK_productInStationID", "FK_repQuarter_IncomeReport",
		// "FK_repYear_IncomeReport", "incomePerProduct", "avgPrice"
		Object[] values1 = { "1", 2, "2019", 455 / 3, 3.5 };
		TableInserts.insertProductInIncomeReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 455 / 3, 3 };
		TableInserts.insertProductInIncomeReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 455 / 3, 6.5 };
		TableInserts.insertProductInIncomeReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 875 / 3, 3.5 };
		TableInserts.insertProductInIncomeReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 875 / 3, 3 };
		TableInserts.insertProductInIncomeReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 875 / 3, 6.5 };
		TableInserts.insertProductInIncomeReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 1113 / 3, 3.5 };
		TableInserts.insertProductInIncomeReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 1113 / 3, 3 };
		TableInserts.insertProductInIncomeReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 1113 / 3, 6.5 };
		TableInserts.insertProductInIncomeReport(con, values9);
	}

	private static void insertDefaultOutcomeReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "outcome_report") == false)
			return;
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID",
		// "totalAmountBoughtFromSupplier"
		Object[] values1 = { 2, "2019", "1", 117.22 };
		TableInserts.insertOutcomeReport(con, values1);
		Object[] values2 = { 2, "2019", "2", 225.43 };
		TableInserts.insertOutcomeReport(con, values2);
		Object[] values3 = { 2, "2019", "3", 286.74 };
		TableInserts.insertOutcomeReport(con, values3);
	}

	private static void insertDefaultProductInOutcomeReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_outcome_report") == false)
			return;
		// "FK_productInStationID", "FK_repQuarter_outcomeReport",
		// "FK_repYear_outcomeReport", "amountBoughtFromSupplier"
		Object[] values1 = { "1", 2, "2019", 455 / 3 / 3.5 };
		TableInserts.insertProductInOutcomeReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 455 / 3 / 3 };
		TableInserts.insertProductInOutcomeReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 455 / 3 / 6.5 };
		TableInserts.insertProductInOutcomeReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 875 / 3 / 3.5 };
		TableInserts.insertProductInOutcomeReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 875 / 3 / 3 };
		TableInserts.insertProductInOutcomeReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 875 / 3 / 6.5 };
		TableInserts.insertProductInOutcomeReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 1113 / 3 / 3.5 };
		TableInserts.insertProductInOutcomeReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 1113 / 3 / 3 };
		TableInserts.insertProductInOutcomeReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 1113 / 3 / 6.5 };
		TableInserts.insertProductInOutcomeReport(con, values9);
	}

	private static void insertDefaultInventoryReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "inventory_report") == false)
			return;
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID", "totalAmountSold"
		Object[] values1 = { 2, "2019", "1", 117.22 };
		TableInserts.insertInventoryReport(con, values1);
		Object[] values2 = { 2, "2019", "2", 225.43 };
		TableInserts.insertInventoryReport(con, values2);
		Object[] values3 = { 2, "2019", "3", 286.74 };
		TableInserts.insertInventoryReport(con, values3);
	}

	private static void insertDefaultProductInInventoryReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "product_in_inventory_report") == false)
			return;
		// "FK_productInStationID", "FK_repQuarter_inventoryReport",
		// "FK_repYear_inventoryReport", "amountSold"
		Object[] values1 = { "1", 2, "2019", 455 / 3 / 3.5 };
		TableInserts.insertProductInInventoryReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 455 / 3 / 3 };
		TableInserts.insertProductInInventoryReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 455 / 3 / 6.5 };
		TableInserts.insertProductInInventoryReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 875 / 3 / 3.5 };
		TableInserts.insertProductInInventoryReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 875 / 3 / 3 };
		TableInserts.insertProductInInventoryReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 875 / 3 / 6.5 };
		TableInserts.insertProductInInventoryReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 1113 / 3 / 3.5 };
		TableInserts.insertProductInInventoryReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 1113 / 3 / 3 };
		TableInserts.insertProductInInventoryReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 1113 / 3 / 6.5 };
		TableInserts.insertProductInInventoryReport(con, values9);
	}

	private static void insertDefaultCustomerBoughtInSale(Connection con) throws SQLException {
		if (checkTableEmpty(con, "customer_bought_in_sale") == false)
			return;
		// "FK_saleID", "FK_customerID", "amountPaid"
		Object[] values1 = { "1", "111111111", 250 };
		TableInserts.insertCustomerBoughtInSale(con, values1);
		Object[] values2 = { "1", "222222222", 130 };
		TableInserts.insertCustomerBoughtInSale(con, values2);
		Object[] values3 = { "2", "222222222", 188 };
		TableInserts.insertCustomerBoughtInSale(con, values3);
	}

	private static void insertDefaultSaleCommentsReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "sale_comments_report") == false)
			return;
		// "FK_saleID", "numberOfCustomersBought", "sumOfPurchases", "dateCreated"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28 - 1);
		Object[] values1 = { "1", 2, 380, calendar1.getTime() };
		TableInserts.insertSaleCommentsReport(con, values1);
		Object[] values2 = { "2", 1, 188, calendar1.getTime() };
		TableInserts.insertSaleCommentsReport(con, values2);
	}

	private static void insertDefaultCar(Connection con) throws SQLException {
		if (checkTableEmpty(con, "car") == false)
			return;
		// "registrationPlate", "FK_customerID", "FK_productName", "ownerName",
		// "deleted"
		Object[] values1 = { "9959599", "111111111", ProductName.Gasoline.toString(), "Israel A", false };
		TableInserts.insertCar(con, values1);
		Object[] values2 = { "9958599", "222222222", ProductName.Gasoline.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values2);
		Object[] values3 = { "9957599", "222222222", ProductName.Diesel.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values3);
		Object[] values4 = { "9956599", "222222222", ProductName.Gasoline.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values4);
		Object[] values5 = { "9955599", "222222222", ProductName.Diesel.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values5);
		Object[] values6 = { "9954599", "222222222", ProductName.Gasoline.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values6);
		Object[] values7 = { "9953599", "222222222", ProductName.MotorbikeFuel.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values7);
		Object[] values8 = { "9951599", "222222222", ProductName.MotorbikeFuel.toString(), "Israel Ltd", false };
		TableInserts.insertCar(con, values8);
	}

	private static void insertDefaultRankingSheet(Connection con) throws SQLException {
		if (checkTableEmpty(con, "ranking_sheet") == false)
			return;
		// "FK_customerID", "customerTypeRank", "fuelingHoursRank", "fuelTypesRank",
		// "updatedForDate"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 12 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28 - 1);
		Object[] values1 = { "111111111", "8", "6", "5", calendar1.getTime() };
		TableInserts.insertRankingSheet(con, values1);
		Object[] values2 = { "222222222", "10", "10", "9", calendar1.getTime() };
		TableInserts.insertRankingSheet(con, values2);
	}

	private static void insertDefaultPricingModelType(Connection con) throws SQLException {
		if (checkTableEmpty(con, "pricing_model_type") == false)
			return;
		// "pricingModelName", "description", "defaultDiscount"
		String pricingModelType1 = PricingModelName.PayInPlace.toString();
		String description1 = "Max price (per liter)";
		double defaultDiscount1 = 0;
		Object[] values1 = { pricingModelType1, description1, defaultDiscount1 };
		TableInserts.insertPricingModelType(con, values1);
		String pricingModelType2 = PricingModelName.MonthlyProgramSingleCar.toString();
		String description2 = "4% Discount from max price (per liter)";
		double defaultDiscount2 = 0.04;
		Object[] values2 = { pricingModelType2, description2, defaultDiscount2 };
		TableInserts.insertPricingModelType(con, values2);
		String pricingModelType3 = PricingModelName.MonthlyProgramMultipleCars.toString();
		String description3 = "Like 'Monthly Program Single Car' x number of cars + general discount of 10% (per liter)";
		double defaultDiscount3 = 0.14;
		Object[] values3 = { pricingModelType3, description3, defaultDiscount3 };
		TableInserts.insertPricingModelType(con, values3);
		String pricingModelType4 = PricingModelName.FullProgramSingleCar.toString();
		String description4 = "Payment every month for the amount bought in practice in the previous month, by price like 'Monthly Program Multiple Cars' + extra discount of 3% (per liter)";
		double defaultDiscount4 = 0.17;
		Object[] values4 = { pricingModelType4, description4, defaultDiscount4 };
		TableInserts.insertPricingModelType(con, values4);
	}

	private static void insertDefaultPricingModel(Connection con) throws SQLException {
		if (checkTableEmpty(con, "pricing_model") == false)
			return;
		// 1 - "FK_customerID", "FK_pricingModelName", "currentDiscount"
		// 2 - "FK_customerID", "FK_pricingModelName", "currentDiscount",
		// "lastMonthUtillization"
		Object[] values1 = { "222222222", PricingModelName.MonthlyProgramMultipleCars.toString(), 0.14 };
		TableInserts.insertPricingModel1(con, values1);
		Object[] values2 = { "111111111", PricingModelName.FullProgramSingleCar.toString(), 0.17, 0.99 };
		TableInserts.insertPricingModel2(con, values2);
	}

	private static void insertDefaultNotification(Connection con) throws SQLException {
		if (checkTableEmpty(con, "notification") == false)
			return;
		// "FK_employeeID", "message", "dismissed", "dateCreated"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 25 - 1);
		Object[] values1 = { "1", "a station order is ready to be assessed", true, calendar1.getTime() };
		TableInserts.insertNotification(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 4 - 1);
		Object[] values2 = { "2", "a station order is ready to be assessed", true, calendar1.getTime() };
		TableInserts.insertNotification(con, values2);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 18 - 1);
		Object[] values3 = { "9", "a station order is ready to be assessed", true, calendar1.getTime() };
		TableInserts.insertNotification(con, values3);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 26 - 1);
		Object[] values4 = { "1", "a station order is ready to be assessed", false, calendar1.getTime() };
		TableInserts.insertNotification(con, values4);
	}

	private static void insertDefaultShipmentMethod(Connection con) throws SQLException {
		if (checkTableEmpty(con, "shipment_method") == false)
			return;
		// "shipmentType", "shipmentPrice", "shipmentMultiplier", "deliveryTime"
		Object[] values1 = { ShipmentType.Regular.toString(), 5.5, 1, "5-10 Days" };
		TableInserts.insertShipmentMethod(con, values1);
		Object[] values2 = { ShipmentType.Urgent.toString(), 5.5, 1.02, "6 Hours" };
		TableInserts.insertShipmentMethod(con, values2);
	}

	private static void insertDefaultOrders(Connection con) throws SQLException {
		if (checkTableEmpty(con, "orders") == false)
			return;
		// "orderTime", "amountBought", "address"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 4);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values1 = { calendar1.getTime(), 600, "4th Hertzel St, Haifa" }; // Regular
		TableInserts.insertOrders(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 24);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values2 = { calendar1.getTime(), 400, "4th Hertzel St, Haifa" }; // Urgent
		TableInserts.insertOrders(con, values2);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 25);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values3 = { calendar1.getTime(), 5130, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values3);
		Object[] values4 = { calendar1.getTime(), 4275, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values4);
		Object[] values5 = { calendar1.getTime(), 3420, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values5);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 4);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values6 = { calendar1.getTime(), 17100, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values6);
		Object[] values7 = { calendar1.getTime(), 14250, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values7);
		Object[] values8 = { calendar1.getTime(), 11400, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values8);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 18);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values9 = { calendar1.getTime(), 3420, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values9);
		Object[] values10 = { calendar1.getTime(), 2850, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values10);
		Object[] values11 = { calendar1.getTime(), 2280, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values11);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 26);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values12 = { calendar1.getTime(), 5130, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values12);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 7 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 2);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values13 = { calendar1.getTime(), 4275, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values13);
	}

	private static void insertDefaultFuelStationOrder(Connection con) throws SQLException {
		if (checkTableEmpty(con, "fuel_station_order") == false)
			return;
		// 1 - "FK_ordersID", "FK_productInStationID", "assessed", "supplied"
		// 2 - "FK_ordersID", "FK_productInStationID", "assessed", "approved",
		// "reasonDismissal", "supplied"
		// 3 - "FK_ordersID", "FK_productInStationID", "assessed", "approved",
		// "reasonDismissal", "supplied", "timeSupplied"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 26);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values1 = { "3", "1", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values1);
		Object[] values2 = { "4", "2", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values2);
		Object[] values3 = { "5", "3", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values3);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 5);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values4 = { "6", "4", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values4);
		Object[] values5 = { "7", "5", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values5);
		Object[] values6 = { "8", "6", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values6);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 19);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values7 = { "9", "7", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values7);
		Object[] values8 = { "10", "8", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values8);
		Object[] values9 = { "11", "9", true, true, "Nan", true, calendar1.getTime() };
		TableInserts.insertFuelStationOrder3(con, values9);
		Object[] values10 = { "12", "1", true, false, "supply already on route", false };
		TableInserts.insertFuelStationOrder2(con, values10);
		Object[] values11 = { "13", "2", false, false };
		TableInserts.insertFuelStationOrder1(con, values11);
	}

	private static void insertDefaultPurchasingProgramType(Connection con) throws SQLException {
		if (checkTableEmpty(con, "purchasing_program_type") == false)
			return;
		// "purchasingProgramName", "description", "monthlyPrice"
		Object[] values1 = { PurchasingProgramName.Standard.toString(),
				"Fast fueling in fuel stations of only 1 fuel company", 10 };
		TableInserts.insertPurchasingProgramType(con, values1);
		Object[] values2 = { PurchasingProgramName.Premium.toString(),
				"Fast fueling in fuel stations of 2-3 fuel companies", 20 };
		TableInserts.insertPurchasingProgramType(con, values2);
	}

	private static void insertDefaultHomeFuelOrder(Connection con) throws SQLException {
		if (checkTableEmpty(con, "home_fuel_order") == false)
			return;
		// "FK_ordersID", "FK_customerID", "FK_product_Name", "FK_shipmentType",
		// "duetime", "finalPrice"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 14);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values1 = { "1", "111111111", ProductName.HomeFuel.toString(), ShipmentType.Regular.toString(),
				calendar1.getTime(), 1169.34 };
		TableInserts.insertHomeFuelOrder(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 24);
		calendar1.set(Calendar.HOUR, 18 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values2 = { "2", "111111111", ProductName.HomeFuel.toString(), ShipmentType.Urgent.toString(),
				calendar1.getTime(), 821.5 };
		TableInserts.insertHomeFuelOrder(con, values2);
	}

	private static void insertDefaultPurchasingProgram(Connection con) throws SQLException {
		if (checkTableEmpty(con, "purchasing_program") == false)
			return;
		// 1 - "FK_customerID", "FK_purchasingProgramName", "FK_fuelCompanyName1"
		// 3 - "FK_customerID", "FK_purchasingProgramName", "FK_fuelCompanyName1",
		// "FK_fuelCompanyName2", "FK_fuelCompanyName3"
		Object[] values1 = { "111111111", PurchasingProgramName.Standard.toString(), FuelCompanyName.Paz.toString() };
		TableInserts.insertPurchasingProgram1(con, values1);
		Object[] values2 = { "222222222", PurchasingProgramName.Premium.toString(), FuelCompanyName.Sonol.toString(),
				FuelCompanyName.Paz.toString(), FuelCompanyName.Delek.toString() };
		TableInserts.insertPurchasingProgram3(con, values2);
	}

	private static void insertCustomerBoughtFromCompany(Connection con) throws SQLException {
		if (checkTableEmpty(con, "customer_bought_from_company") == false)
			return;
		// "FK_customerID", "FK_fuelCompanyName", "dateOfPurchase",
		// "amountBoughtFromCompany", "amountPaidCompany"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7 - 1);
		Object[] values1 = { "111111111", FuelCompanyName.Paz.toString(), calendar1.getTime(), 3791.67, 875 };
		TableInserts.insertCustomerBoughtFromCompany(con, values1);
		Object[] values2 = { "222222222", FuelCompanyName.Sonol.toString(), calendar1.getTime(), 1971.67, 455 };
		TableInserts.insertCustomerBoughtFromCompany(con, values2);
		Object[] values3 = { "222222222", FuelCompanyName.Delek.toString(), calendar1.getTime(), 4823, 1113 };
		TableInserts.insertCustomerBoughtFromCompany(con, values3);
	}

	private static void insertDefaultPeriodicCustomersReport(Connection con) throws SQLException {
		if (checkTableEmpty(con, "periodic_customers_report") == false)
			return;
		// "dateFrom", "dateTo", "dateCreated"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1 - 1);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.set(Calendar.YEAR, 2019);
		calendar2.set(Calendar.MONTH, 5 - 1);
		calendar2.set(Calendar.DAY_OF_MONTH, 28 - 1);
		Calendar calendar3 = Calendar.getInstance();
		calendar3.set(Calendar.YEAR, 2019);
		calendar3.set(Calendar.MONTH, 5 - 1);
		calendar3.set(Calendar.DAY_OF_MONTH, 28 - 1);
		Object[] values1 = { calendar1.getTime(), calendar2.getTime(), calendar3.getTime() };
		TableInserts.insertPeriodicCustomersReport(con, values1);
	}

	private static void insertDefaultActivity(Connection con) throws SQLException {
		if (checkTableEmpty(con, "activity") == false)
			return;
		// "FK_employeeID", "time", "action"
		// sql adds 2:30 hours to input
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values1 = { "4", calendar1.getTime(), "added person customer with ID = 111111111" };
		TableInserts.insertActivity(con, values1);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 29);
		Object[] values2 = { "4", calendar1.getTime(), "added company customer with ID = 222222222" };
		TableInserts.insertActivity(con, values2);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 28);
		Object[] values3 = { "4", calendar1.getTime(), "added car with registration plate = 9959599" };
		TableInserts.insertActivity(con, values3);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 27);
		Object[] values4 = { "4", calendar1.getTime(), "added car with registration plate = 9958599" };
		TableInserts.insertActivity(con, values4);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 26);
		Object[] values5 = { "4", calendar1.getTime(), "added car with registration plate = 9957599" };
		TableInserts.insertActivity(con, values5);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 25);
		Object[] values6 = { "4", calendar1.getTime(), "added car with registration plate = 9956599" };
		TableInserts.insertActivity(con, values6);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 24);
		Object[] values7 = { "4", calendar1.getTime(), "added car with registration plate = 9955599" };
		TableInserts.insertActivity(con, values7);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 23);
		Object[] values8 = { "4", calendar1.getTime(), "added car with registration plate = 9954599" };
		TableInserts.insertActivity(con, values8);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 22);
		Object[] values9 = { "4", calendar1.getTime(), "added car with registration plate = 9953599" };
		TableInserts.insertActivity(con, values9);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 28);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 21);
		Object[] values10 = { "4", calendar1.getTime(), "added car with registration plate = 9951599" };
		TableInserts.insertActivity(con, values10);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 7 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values11 = { "1", calendar1.getTime(), "generated quarterly report for 2019 quarter 2" };
		TableInserts.insertActivity(con, values11);
		Object[] values12 = { "2", calendar1.getTime(), "generated quarterly report for 2019 quarter 2" };
		TableInserts.insertActivity(con, values12);
		Object[] values13 = { "9", calendar1.getTime(), "generated quarterly report for 2019 quarter 2" };
		TableInserts.insertActivity(con, values13);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 18 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values14 = { "3", calendar1.getTime(), "initiated sale with salespatternid 1" };
		TableInserts.insertActivity(con, values14);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values15 = { "3", calendar1.getTime(), "initiated sale with salespatternid 1" };
		TableInserts.insertActivity(con, values15);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 14);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values16 = { "5", calendar1.getTime(), "declined rates update request 2" };
		TableInserts.insertActivity(con, values16);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 5);
		calendar1.set(Calendar.HOUR, 15 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values17 = { "5", calendar1.getTime(), "approved rates update request 3" };
		TableInserts.insertActivity(con, values17);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 1);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values18 = { "1", calendar1.getTime(), "updated minimum storage threshold" };
		TableInserts.insertActivity(con, values18);
		Object[] values19 = { "2", calendar1.getTime(), "updated minimum storage threshold" };
		TableInserts.insertActivity(con, values19);
		Object[] values20 = { "9", calendar1.getTime(), "updated minimum storage threshold" };
		TableInserts.insertActivity(con, values20);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 27);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values21 = { "6", calendar1.getTime(), "applied supplyment of fuelstationorder 1" };
		TableInserts.insertActivity(con, values21);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 27);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 29);
		Object[] values22 = { "6", calendar1.getTime(), "applied supplyment of fuelstationorder 2" };
		TableInserts.insertActivity(con, values22);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 27);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 28);
		Object[] values23 = { "6", calendar1.getTime(), "applied supplyment of fuelstationorder 3" };
		TableInserts.insertActivity(con, values23);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 6);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values24 = { "7", calendar1.getTime(), "applied supplyment of fuelstationorder 4" };
		TableInserts.insertActivity(con, values24);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 6);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 29);
		Object[] values25 = { "7", calendar1.getTime(), "applied supplyment of fuelstationorder 5" };
		TableInserts.insertActivity(con, values25);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 6);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 28);
		Object[] values26 = { "7", calendar1.getTime(), "applied supplyment of fuelstationorder 6" };
		TableInserts.insertActivity(con, values26);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 20);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 30);
		Object[] values27 = { "8", calendar1.getTime(), "applied supplyment of fuelstationorder 7" };
		TableInserts.insertActivity(con, values27);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 20);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 29);
		Object[] values28 = { "8", calendar1.getTime(), "applied supplyment of fuelstationorder 8" };
		TableInserts.insertActivity(con, values28);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 6 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 20);
		calendar1.set(Calendar.HOUR, 9 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 00 - 28);
		Object[] values29 = { "8", calendar1.getTime(), "applied supplyment of fuelstationorder 9" };
		TableInserts.insertActivity(con, values29);
	}

	private static void insertDefaultFastFuel(Connection con) throws SQLException {
		if (checkTableEmpty(con, "fast_fuel") == false)
			return;
		// "FK_registrationPlate", "FK_customerID", "FK_productInStationID",
		// "fastFuelTime", "amountBought", "finalPrice"
		Calendar calendar1 = Calendar.getInstance();
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 30 - 30);
		Object[] values1 = { "9959599", "111111111", "4", calendar1.getTime(), 455 / 3 / 3.5, 455 / 3 }; // Paz
		TableInserts.insertFastFuel(con, values1);
		Object[] values2 = { "9957599", "222222222", "5", calendar1.getTime(), 455 / 3 / 3, 455 / 3 }; // Paz
		TableInserts.insertFastFuel(con, values2);
		Object[] values3 = { "9951599", "222222222", "6", calendar1.getTime(), 455 / 3 / 6.5, 455 / 3 }; // Paz
		TableInserts.insertFastFuel(con, values3);
		Object[] values4 = { "9958599", "222222222", "1", calendar1.getTime(), 875 / 3 / 3.5, 875 / 3 }; // Sonol
		TableInserts.insertFastFuel(con, values4);
		Object[] values5 = { "9955599", "222222222", "2", calendar1.getTime(), 875 / 3 / 3, 875 / 3 }; // Sonol
		TableInserts.insertFastFuel(con, values5);
		Object[] values6 = { "9953599", "222222222", "3", calendar1.getTime(), 875 / 3 / 6.5, 875 / 3 }; // Sonol
		TableInserts.insertFastFuel(con, values6);
		Object[] values7 = { "9954599", "222222222", "7", calendar1.getTime(), 1113 / 3 / 3.5, 1113 / 3 }; // Delek
		TableInserts.insertFastFuel(con, values7);
		calendar1.set(Calendar.YEAR, 2019);
		calendar1.set(Calendar.MONTH, 5 - 1);
		calendar1.set(Calendar.DAY_OF_MONTH, 7);
		calendar1.set(Calendar.HOUR, 12 - 2 - 12);
		calendar1.set(Calendar.MINUTE, 20 - 30);
		Object[] values8 = { "9957599", "222222222", "8", calendar1.getTime(), 1113 / 3 / 3, 1113 / 3 }; // Delek
		TableInserts.insertFastFuel(con, values8);
		Object[] values9 = { "9953599", "222222222", "9", calendar1.getTime(), 1113 / 3 / 6.5, 1113 / 3 }; // Delek
		TableInserts.insertFastFuel(con, values9);
	}

}
