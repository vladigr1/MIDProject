package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import enums.Affiliation;
import enums.CustomerType;
import enums.FuelCompanyName;
import enums.PricingModelName;
import enums.ProductName;
import enums.PurchasingProgramName;
import enums.ShipmentType;

@SuppressWarnings("deprecation")
public class DefaultTableInserts {

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

	public static void insertDefaultUser(Connection con) throws SQLException {
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

	public static void insertDefaultEmployee(Connection con) throws SQLException {
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

	public static void insertDefaultCustomer(Connection con) throws SQLException {
		// "customerID", "FK_userName", "creditCard", "customerType", "deleted"
		Object[] values1 = { "111111111", "IsraelThePersonCustomer", "1111-2222-3333-4444",
				CustomerType.Person.toString(), false };
		TableInserts.insertCustomer(con, values1);
		Object[] values2 = { "222222222", "IsraelTheCompanyCustomer", "4444-3333-2222-1111",
				CustomerType.Company.toString(), false };
		TableInserts.insertCustomer(con, values2);
	}

	public static void insertDefaultSalesPattern(Connection con) throws SQLException {
		// "durationInMinutes"
		Object[] values1 = { 30 };
		TableInserts.insertSalesPattern(con, values1);
		// "durationInMinutes"
		Object[] values2 = { 60 };
		TableInserts.insertSalesPattern(con, values2);
	}

	public static void insertDefaultFuelStationManager(Connection con) throws SQLException {
		// "FK_employeeID", "phoneNo"
		Object[] values1 = { "1", "0501111111" };
		TableInserts.insertFuelStationManager(con, values1);
		Object[] values2 = { "2", "0502222222" };
		TableInserts.insertFuelStationManager(con, values2);
		Object[] values3 = { "9", "0509999999" };
		TableInserts.insertFuelStationManager(con, values3);
	}

	public static void insertDefaultProduct(Connection con) throws SQLException {
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

	public static void insertDefaultProductInSalesPattern(Connection con) throws SQLException {
		// "FK_salesPatternID", "FK_productName", "salesDiscount"
		Object[] values1 = { "1", ProductName.Diesel.toString(), 1.5 };
		TableInserts.insertProductInSalesPattern(con, values1);
		Object[] values2 = { "1", ProductName.MotorbikeFuel.toString(), 3 };
		TableInserts.insertProductInSalesPattern(con, values2);
		Object[] values3 = { "2", ProductName.Gasoline.toString(), 1 };
		TableInserts.insertProductInSalesPattern(con, values3);
	}

	public static void insertDefaultSale(Connection con) throws SQLException {
		// "FK_salesPatternID", "active", "startTime", "endTime"
		Object[] values1 = { "1", false, new Date(119, 5, 7, 18, 30), new Date(119, 5, 7, 19, 00) };
		TableInserts.insertSale(con, values1);
		Object[] values2 = { "2", false, new Date(119, 5, 14, 12, 00), new Date(119, 5, 14, 13, 00) };
		TableInserts.insertSale(con, values2);
	}

	public static void insertDefaultProductRatesUpdateRequest(Connection con) throws SQLException {
		// 1 - "requestDate", "assessed"
		// 2 - "requestDate", "assessed", "approved"
		Object[] values1 = { new Date(119, 5, 16), false };
		TableInserts.insertProductRatesUpdateRequest1(con, values1);
		Object[] values2 = { new Date(119, 5, 13), true, false };
		TableInserts.insertProductRatesUpdateRequest1(con, values2);
		Object[] values3 = { new Date(119, 5, 5), true, true };
		TableInserts.insertProductRatesUpdateRequest1(con, values3);
	}

	public static void insertDefaultProductInRequest(Connection con) throws SQLException {
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

	public static void insertDefaultFuelCompany(Connection con) throws SQLException {
		// "fuelCompanyName", "FK_employeeID" = supplier
		Object[] values1 = { FuelCompanyName.Sonol.toString(), "6" };
		TableInserts.insertFuelCompany(con, values1);
		Object[] values2 = { FuelCompanyName.Paz.toString(), "7" };
		TableInserts.insertFuelCompany(con, values2);
		Object[] values3 = { FuelCompanyName.Delek.toString(), "8" };
		TableInserts.insertFuelCompany(con, values3);
	}

	public static void insertDefaultFuelStation(Connection con) throws SQLException {
		// "FK_fuelCompanyName", "FK_employeeID", "stationName", "address"
		Object[] values1 = { "Sonol", "1", "Sonol Neighborhood", "Peretz St, Kiryat Ata" };
		TableInserts.insertFuelStation(con, values1);
		Object[] values2 = { "Paz", "2", "Paz Gas Ltd", "Road 6" };
		TableInserts.insertFuelStation(con, values2);
		Object[] values3 = { "Delek", "9", "Delek Dror", "Dror St, Karmiel" };
		TableInserts.insertFuelStation(con, values3);
	}

	public static void insertDefaultProductInStation(Connection con) throws SQLException {
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

	public static void insertDefaultQuarterlyReport(Connection con) throws SQLException {
		// "repQuarter", "repYear", "FK_fuelStationID", "dateCreated"
		Object[] values1 = { 2, "2019", "1", new Date(119, 7, 1) };
		TableInserts.insertQuarterlyReport(con, values1);
		Object[] values2 = { 2, "2019", "2", new Date(119, 7, 1) };
		TableInserts.insertQuarterlyReport(con, values2);
		Object[] values3 = { 2, "2019", "3", new Date(119, 7, 1) };
		TableInserts.insertQuarterlyReport(con, values3);
	}

	public static void insertDefaultIncomeReport(Connection con) throws SQLException {
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID", "totalIncome"
		Object[] values1 = { 2, "2019", "1", 25650 * 3 };
		TableInserts.insertIncomeReport(con, values1);
		Object[] values2 = { 2, "2019", "2", 256500 };
		TableInserts.insertIncomeReport(con, values2);
		Object[] values3 = { 2, "2019", "3", 256500 / 5 };
		TableInserts.insertIncomeReport(con, values3);
	}

	public static void insertDefaultProductInIncomeReport(Connection con) throws SQLException {
		// "FK_productInStationID", "FK_repQuarter_IncomeReport",
		// "FK_repYear_IncomeReport", "incomePerProduct", "avgPrice"
		Object[] values1 = { "1", 2, "2019", 25650, 5 };
		TableInserts.insertProductInIncomeReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 25650, 6 };
		TableInserts.insertProductInIncomeReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 25650, 7.5 };
		TableInserts.insertProductInIncomeReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 256500 / 3, 5 };
		TableInserts.insertProductInIncomeReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 256500 / 3, 6 };
		TableInserts.insertProductInIncomeReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 256500 / 3, 7.5 };
		TableInserts.insertProductInIncomeReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 256500 / 5 / 3, 5 };
		TableInserts.insertProductInIncomeReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 256500 / 5 / 3, 6 };
		TableInserts.insertProductInIncomeReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 256500 / 5 / 3, 7.5 };
		TableInserts.insertProductInIncomeReport(con, values9);
	}

	public static void insertDefaultOutcomeReport(Connection con) throws SQLException {
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID"
		Object[] values1 = { 2, "2019", "1" };
		TableInserts.insertOutcomeReport(con, values1);
		Object[] values2 = { 2, "2019", "2" };
		TableInserts.insertOutcomeReport(con, values2);
		Object[] values3 = { 2, "2019", "3" };
		TableInserts.insertOutcomeReport(con, values3);
	}

	public static void insertDefaultProductInOutcomeReport(Connection con) throws SQLException {
		// "FK_productInStationID", "FK_repQuarter_outcomeReport",
		// "FK_repYear_outcomeReport", "amountBoughtFromSupplier"
		Object[] values1 = { "1", 2, "2019", 5130 };
		TableInserts.insertProductInOutcomeReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 4275 };
		TableInserts.insertProductInOutcomeReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 3420 };
		TableInserts.insertProductInOutcomeReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 17100 };
		TableInserts.insertProductInOutcomeReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 14250 };
		TableInserts.insertProductInOutcomeReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 11400 };
		TableInserts.insertProductInOutcomeReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 3420 };
		TableInserts.insertProductInOutcomeReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 2850 };
		TableInserts.insertProductInOutcomeReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 2280 };
		TableInserts.insertProductInOutcomeReport(con, values9);
	}

	public static void insertDefaultInventoryReport(Connection con) throws SQLException {
		// "FK_repQuarter", "FK_repYear", "FK_fuelStationID"
		Object[] values1 = { 2, "2019", "1" };
		TableInserts.insertInventoryReport(con, values1);
		Object[] values2 = { 2, "2019", "2" };
		TableInserts.insertInventoryReport(con, values2);
		Object[] values3 = { 2, "2019", "3" };
		TableInserts.insertInventoryReport(con, values3);
	}

	public static void insertDefaultProductInInventoryReport(Connection con) throws SQLException {
		// "FK_productInStationID", "FK_repQuarter_inventoryReport",
		// "FK_repYear_inventoryReport", "amountSold", "amountBegin", "amountEnd"
		Object[] values1 = { "1", 2, "2019", 5130, 1340 * 3, 1340 * 3 };
		TableInserts.insertProductInInventoryReport(con, values1);
		Object[] values2 = { "2", 2, "2019", 4275, 895 * 3, 895 * 3 };
		TableInserts.insertProductInInventoryReport(con, values2);
		Object[] values3 = { "3", 2, "2019", 3420, 1500, 1500 };
		TableInserts.insertProductInInventoryReport(con, values3);
		Object[] values4 = { "4", 2, "2019", 17100, 13400, 13400 };
		TableInserts.insertProductInInventoryReport(con, values4);
		Object[] values5 = { "5", 2, "2019", 14250, 8950, 8950 };
		TableInserts.insertProductInInventoryReport(con, values5);
		Object[] values6 = { "6", 2, "2019", 11400, 5000, 5000 };
		TableInserts.insertProductInInventoryReport(con, values6);
		Object[] values7 = { "7", 2, "2019", 3420, 13400 / 5, 13400 / 5 };
		TableInserts.insertProductInInventoryReport(con, values7);
		Object[] values8 = { "8", 2, "2019", 2850, 8950 / 5, 8950 / 5 };
		TableInserts.insertProductInInventoryReport(con, values8);
		Object[] values9 = { "9", 2, "2019", 2280, 1000, 1000 };
		TableInserts.insertProductInInventoryReport(con, values9);
	}

	public static void insertDefaultCustomerBoughtInSale(Connection con) throws SQLException {
		// "FK_saleID", "FK_customerID", "amountPaid"
		Object[] values1 = { "1", "111111111", 250 };
		TableInserts.insertCustomerBoughtInSale(con, values1);
		Object[] values2 = { "1", "222222222", 130 };
		TableInserts.insertCustomerBoughtInSale(con, values2);
		Object[] values3 = { "2", "222222222", 65 };
		TableInserts.insertCustomerBoughtInSale(con, values3);
		Object[] values4 = { "2", "222222222", 89 };
		TableInserts.insertCustomerBoughtInSale(con, values4);
		Object[] values5 = { "2", "222222222", 34 };
		TableInserts.insertCustomerBoughtInSale(con, values5);
	}

	public static void insertDefaultSaleCommentsReport(Connection con) throws SQLException {
		// "FK_saleID", "numberOfCustomersBought", "sumOfPurchases", "dateCreated"
		Object[] values1 = { "1", 2, 380, new Date(119, 5, 30) };
		TableInserts.insertSaleCommentsReport(con, values1);
		Object[] values2 = { "2", 1, 188, new Date(119, 5, 30) };
		TableInserts.insertSaleCommentsReport(con, values2);
	}

	public static void insertDefaultCar(Connection con) throws SQLException {
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

	public static void insertDefaultRankingSheet(Connection con) throws SQLException {
		// "FK_customerID", "customerTypeRank", "fuelingHoursRank", "fuelTypesRank",
		// "updatedForDate"
		Object[] values1 = { "111111111", "8", "6", "5", new Date(119, 12, 30) };
		TableInserts.insertRankingSheet(con, values1);
		Object[] values2 = { "222222222", "10", "10", "9", new Date(119, 12, 30) };
		TableInserts.insertRankingSheet(con, values2);
	}

	public static void insertDefaultPricingModelType(Connection con) throws SQLException {
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
		double defaultDiscount3 = 0.1;
		Object[] values3 = { pricingModelType3, description3, defaultDiscount3 };
		TableInserts.insertPricingModelType(con, values3);
		String pricingModelType4 = PricingModelName.FullProgramSingleCar.toString();
		String description4 = "Payment every month for the amount bought in practice in the previous month, by price like 'Monthly Program Multiple Cars' + extra discount of 3% (per liter)";
		double defaultDiscount4 = 0.13;
		Object[] values4 = { pricingModelType4, description4, defaultDiscount4 };
		TableInserts.insertPricingModelType(con, values4);
	}

	public static void insertDefaultPricingModel(Connection con) throws SQLException {
		// 1 - "FK_customerID", "FK_pricingModelName", "currentDiscount"
		// 2 - "FK_customerID", "FK_pricingModelName", "currentDiscount",
		// "lastMonthUtillization"
		Object[] values1 = { "222222222", PricingModelName.MonthlyProgramMultipleCars.toString(), 0.38 };
		TableInserts.insertPricingModel1(con, values1);
		Object[] values2 = { "111111111", PricingModelName.FullProgramSingleCar.toString(), 0.17, 100 };
		TableInserts.insertPricingModel1(con, values2);
	}

	public static void insertDefaultNotification(Connection con) throws SQLException {
		// "FK_employeeID", "message", "dismissed", "dateCreated"
		Object[] values1 = { "1", "a station order is ready to be assessed", true, new Date(119, 5, 25) };
		TableInserts.insertNotification(con, values1);
		Object[] values2 = { "2", "a station order is ready to be assessed", true, new Date(119, 6, 4) };
		TableInserts.insertNotification(con, values2);
		Object[] values3 = { "3", "a station order is ready to be assessed", true, new Date(119, 6, 18) };
		TableInserts.insertNotification(con, values3);
		Object[] values4 = { "1", "a station order is ready to be assessed", false, new Date(119, 5, 26) };
		TableInserts.insertNotification(con, values4);
	}

	public static void insertDefaultShipmentMethod(Connection con) throws SQLException {
		// "shipmentType", "shipmentPrice", "shipmentMultiplier", "deliveryTime"
		Object[] values1 = { ShipmentType.Regular.toString(), 5.5, 1, "5-10 Days" };
		TableInserts.insertShipmentMethod(con, values1);
		Object[] values2 = { ShipmentType.Urgent.toString(), 5.5, 1.02, "6 Hours" };
		TableInserts.insertShipmentMethod(con, values2);
	}

	public static void insertDefaultOrders(Connection con) throws SQLException {
		// "orderTime", "amountBought", "address"
		Object[] values1 = { new Date(119, 5, 4, 12, 30), 600, "4th Hertzel St, Haifa" }; // Regular
		TableInserts.insertOrders(con, values1);
		Object[] values2 = { new Date(119, 6, 24, 12, 30), 400, "4th Hertzel St, Haifa" }; // Urgent
		TableInserts.insertOrders(con, values2);
		Object[] values3 = { new Date(119, 5, 25, 12, 30), 5130, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values3);
		Object[] values4 = { new Date(119, 5, 25, 12, 30), 4275, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values4);
		Object[] values5 = { new Date(119, 5, 25, 12, 30), 3420, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values5);
		Object[] values6 = { new Date(119, 6, 4, 12, 30), 17100, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values6);
		Object[] values7 = { new Date(119, 6, 4, 12, 30), 14250, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values7);
		Object[] values8 = { new Date(119, 6, 4, 12, 30), 11400, "Road 6" }; // fsmanager2
		TableInserts.insertOrders(con, values8);
		Object[] values9 = { new Date(119, 6, 18, 12, 30), 3420, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values9);
		Object[] values10 = { new Date(119, 6, 18, 12, 30), 2850, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values10);
		Object[] values11 = { new Date(119, 6, 18, 12, 30), 2280, "Dror St, Karmiel" }; // fsmanager3
		TableInserts.insertOrders(con, values11);
		Object[] values12 = { new Date(119, 5, 26, 12, 30), 5130, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values12);
		Object[] values13 = { new Date(119, 7, 2, 12, 30), 4275, "Peretz St, Kiryat Ata" }; // fsmanager1
		TableInserts.insertOrders(con, values13);
	}

	public static void insertDefaultFuelStationOrder(Connection con) throws SQLException {
		// 1 - "FK_ordersID", "FK_productInStationID", "assessed", "supplied"
		// 2 - "FK_ordersID", "FK_productInStationID", "assessed", "approved",
		// "reasonDismissal", "supplied"
		// 3 - "FK_ordersID", "FK_productInStationID", "assessed", "approved",
		// "reasonDismissal", "supplied", "timeSupplied"
		Object[] values1 = { "3", "1", true, true, "Nan", true, new Date(119, 5, 26, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values1);
		Object[] values2 = { "4", "2", true, true, "Nan", true, new Date(119, 5, 26, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values2);
		Object[] values3 = { "5", "3", true, true, "Nan", true, new Date(119, 5, 26, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values3);
		Object[] values4 = { "6", "4", true, true, "Nan", true, new Date(119, 6, 5, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values4);
		Object[] values5 = { "7", "5", true, true, "Nan", true, new Date(119, 6, 5, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values5);
		Object[] values6 = { "8", "6", true, true, "Nan", true, new Date(119, 6, 5, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values6);
		Object[] values7 = { "9", "7", true, true, "Nan", true, new Date(119, 6, 19, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values7);
		Object[] values8 = { "10", "8", true, true, "Nan", true, new Date(119, 6, 19, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values8);
		Object[] values9 = { "11", "9", true, true, "Nan", true, new Date(119, 6, 19, 12, 30) };
		TableInserts.insertFuelStationOrder3(con, values9);
		Object[] values10 = { "12", "1", true, false, "supply already on route", false };
		TableInserts.insertFuelStationOrder2(con, values10);
		Object[] values11 = { "12", "2", false, false };
		TableInserts.insertFuelStationOrder1(con, values11);
	}

	public static void insertDefaultPurchasingProgramType(Connection con) throws SQLException {
		// "purchasingProgramName", "description", "monthlyPrice"
		Object[] values1 = { PurchasingProgramName.Standard.toString(),
				"Fast fueling in fuel stations of only 1 fuel company", 10 };
		TableInserts.insertPurchasingProgramType(con, values1);
		Object[] values2 = { PurchasingProgramName.Premium.toString(),
				"Fast fueling in fuel stations of 2-3 fuel companies", 20 };
		TableInserts.insertPurchasingProgramType(con, values2);
	}

	public static void insertDefaultHomeFuelOrder(Connection con) throws SQLException {
		// "FK_ordersID", "FK_customerID", "FK_product_Name", "FK_shipmentType",
		// "duetime", "finalPrice"
		Object[] values1 = { "1", "111111111", ProductName.HomeFuel.toString(), ShipmentType.Regular.toString(),
				new Date(110, 5, 14, 12, 30), 1169.34 };
		TableInserts.insertHomeFuelOrder(con, values1);
		Object[] values2 = { "2", "111111111", ProductName.HomeFuel.toString(), ShipmentType.Urgent.toString(),
				new Date(110, 6, 24, 18, 30), 821.5 };
		TableInserts.insertHomeFuelOrder(con, values2);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/********************************************************************************/

	public static void insertDefaultFastFuel(Connection con) throws SQLException {

		String customerID = "1212";
		int productInStaion = 1;

		Object[] values5 = { customerID, productInStaion, new Date(119, 3, 3), 30, 200.19 };
		TableInserts.insertFastFuel(con, values5);

	}

	public static void insertDefaultPurchasingProgram(Connection con) throws SQLException {
		String supplieruserName = "Supplier2UserName";
		String customerUserName = "customer13UserName";
		int fkemployeeID;
		String fkcustomer = "1212";
		String fkpurchasingProgramType = PurchasingProgramName.Premium.toString();
		String fkfuel_Company_Name = "paz";

		// customer
		Object[] values1 = { customerUserName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		TableInserts.insertUser(con, values1);

		Object[] values2 = { fkcustomer, customerUserName, "1111-2222-3333-4444", CustomerType.Person.toString(),
				false };
		TableInserts.insertCustomer(con, values2);

//		// PurchasingProgramType
//		Object[] values3 = { fkpurchasingProgramType, "for not single car", 100.4 };
//		TableInserts.insertPurchasingProgramType(con, values3);

		// fuelCompany
		Object[] values4 = { supplieruserName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		TableInserts.insertUser(con, values4);

		Object[] values5 = { "role", Affiliation.Marketing.toString(), supplieruserName };
		fkemployeeID = TableInserts.insertEmployee(con, values5);

		Object[] values6 = { fkfuel_Company_Name, fkemployeeID };
		TableInserts.insertFuelCompany(con, values6);

		// purchasing program
		Object[] values7 = { fkcustomer, fkpurchasingProgramType, fkfuel_Company_Name, fkfuel_Company_Name };
		TableInserts.insertPurchasingProgram1(con, values7);
	}

	public static void insertCustomerBoughtFromCompany(Connection con) throws SQLException {
		String supplieruserName = "Supplier3UserName";
		String customerUserName = "customer6UserName";
		int fkemployeeID;
		String fkcustomer = "221212";
		String fkfuel_Company_Name = "zap";

		// customer
		Object[] values1 = { customerUserName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		TableInserts.insertUser(con, values1);

		Object[] values2 = { fkcustomer, customerUserName, "1111-2222-3333-4444", CustomerType.Person.toString(),
				false };
		TableInserts.insertCustomer(con, values2);

		// fuelCompany
		Object[] values4 = { supplieruserName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		TableInserts.insertUser(con, values4);
		Object[] values5 = { "role", Affiliation.Marketing.toString(), supplieruserName };
		fkemployeeID = TableInserts.insertEmployee(con, values5);

		Object[] values6 = { fkfuel_Company_Name, fkemployeeID };
		TableInserts.insertFuelCompany(con, values6);

		// CustomerBoughtFromCompany
		Object[] values7 = { fkcustomer, fkfuel_Company_Name, 3.4, 34.0, new Date(119, 2, 5) };
		TableInserts.insertCustomerBoughtFromCompany(con, values7);
	}

	public static void insertDefaultPeriodicCustomersReport(Connection con) throws SQLException {
		Object[] values = { new Date(System.currentTimeMillis()), new Date(120, 11, 12),
				new Date(System.currentTimeMillis()) };
		TableInserts.insertPeriodicCustomersReport(con, values);
	}

	public static void insertDefaultActivity(Connection con) throws SQLException {
		int employeeID = 1;
		String action = "add customer with ID = 1212";
		Object[] values = { employeeID, action, new Date(120, 10, 22) };
		TableInserts.insertActivity(con, values);
	}

}
