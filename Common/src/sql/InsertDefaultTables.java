package sql;

import java.sql.Connection;
import java.util.Date;

import enums.Affiliation;
import enums.CustomerType;
import enums.FuelCompanyName;
import enums.PricingModelName;
import enums.ProductName;
import enums.PurchasingProgramName;
import enums.ShipmentType;

public class InsertDefaultTables {

	public InsertDefaultTables(Connection con) {

		insertDefaultUser(con);
		insertDefaultEmployee(con);
		insertDefaultCustomer(con);
		insertDefaultSalesPattern(con);
		insertDefaultSale(con);
		insertDefaultFuelStationManager(con);
		
		// add here

		// elro additions
		insertDefaultProduct(con);
		insertDefaultProductInSalePattern(con);

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

		insertDefaultInventroyReport(con);
		insertDefaultProductInInventroyReport(con);
		insertDefaultCustomerboughtInSale(con);
		insertDefaultSaleCommentsReport(con);

		insertDefaultCar(con);////
		insertDefaultRankingSheet(con);///
		insertDefaultPricingModelType(con);///
		insertDefaultPricingModel(con);////
		insertDefaultFullSingleMemberMonthly(con);////

		// Vlad additions
		insertDefaultNotification(con);
		insertDefaultShipmentMethod(con);
		insertDefaultOrders(con);
		insertDefaultFuelStationOrder(con);/////////
		insertDefaultHomeFuelOrder(con);
		insertDefaultPurchasingProgramType(con);
		insertDefaultPurchasingProgram(con);
		insertCustomerBoughtFromCompany(con);
		insertDefaultPeriodicCustomersReport(con);
		insertDefaultActivity(con);
		insertDefaultFastFuel(con);

		System.out.println("finished insertions");
	}

	// only add insert default method and after waards add the to
	// InsertDefaultTables
	// elro

	public void insertDefaultPricingModel(Connection con) {
		String pricingModelType = PricingModelName.MonthlyProgramMultipleCars.toString();
		String customerID = "212354252";
		double currentDiscount = 0.15;
		Object[] values = { pricingModelType, customerID, currentDiscount };
		InsertTables.insertPricingModel(con, values);
	}

	public void insertDefaultPricingModelType(Connection con) {
		String pricingModelType = PricingModelName.PayInPlace.toString();
		String description = "Max price (per liter)";
		double defaultDiscount = 0;

		Object[] values = { pricingModelType, description, defaultDiscount };
		InsertTables.insertPricingModelType(con, values);
		
		String pricingModelType2 = PricingModelName.MonthlyProgramSingleCar.toString();
		String description2 = "4% discount from max price (per liter)";
		double defaultDiscount2 = 0.04;
		
		Object[] values2 = { pricingModelType2, description2, defaultDiscount2 };
		InsertTables.insertPricingModelType(con, values2);
		
		String pricingModelType3 = PricingModelName.MonthlyProgramMultipleCars.toString();
		String description3 = "Like 'Monthly Program Single Car' x number of cars + general discount of 10% (per liter)";
		double defaultDiscount3 = 0.1;
		
		Object[] values3 = { pricingModelType3, description3, defaultDiscount3 };
		InsertTables.insertPricingModelType(con, values3);
		
		String pricingModelType4 = PricingModelName.FullProgramSingleCar.toString();
		String description4 = "Payment every month for the amount bought in practice in the previous month, by price like 'Monthly Program Multiple Cars' + extra discount of 3% (per liter)";
		double defaultDiscount4 = 0.13;
		
		Object[] values4 = { pricingModelType4, description4, defaultDiscount4 };
		InsertTables.insertPricingModelType(con, values4);
		
		

	}

	public void insertDefaultRankingSheet(Connection con) {
		String userName = "elro2222";
		String customerID = "212354252";
		Object[] values = { userName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { customerID, userName, "1331-2222-3333-4444", CustomerType.Person.toString(), false };
		InsertTables.insertCustomer(con, values2);

		double customerTypeRank = 0.75;
		double fuelingHoursRank = 0.89;
		double fuelTypesRank = 0.43;

		Object[] values3 = { customerTypeRank, fuelingHoursRank, fuelTypesRank, customerID, new Date(119, 2, 5) };
		InsertTables.insertRankingSheet(con, values3);

	}

	public void insertDefaultCar(Connection con) {
		String userName = "elro21";
		String customerID = "212334252";
		Object[] values = { userName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { customerID, userName, "1321-2222-3333-4444", CustomerType.Person.toString(), false };
		InsertTables.insertCustomer(con, values2);

		String registrationPlate = "65-101-33";
		String ownerName = "Kevin";
		Object[] values3 = { registrationPlate, ownerName, ProductName.Diesel.toString(), customerID, false };
		InsertTables.insertCar(con, values3);

	}

	public void insertDefaultUser(Connection con) {
		String userName = "user";
		Object[] values = { userName, "r", false, "firstName", "surname", "email" };
		InsertTables.insertUser(con, values);
	}

	public void insertDefaultEmployee(Connection con) {
		String userName = "eUserName";
		Object[] values = { userName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { "MarketingRepresentative", Affiliation.Marketing.toString(), userName };
		InsertTables.insertEmployee(con, values2);
		
	}

	public void insertDefaultCustomer(Connection con) {
		String userName = "elro";
		String customerID = "2555555";
		Object[] values = { userName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { customerID, userName, "1111-2222-3333-4444", CustomerType.Person.toString(), false };
		InsertTables.insertCustomer(con, values2);
	}

	public void insertDefaultSalesPattern(Connection con) {
		Object[] values = { 310 };
		InsertTables.insertSalesPattern(con, values);
		Object[] values2 = { 222 };
		InsertTables.insertSalesPattern(con, values2);
		Object[] values3 = { 777 };
		InsertTables.insertSalesPattern(con, values3);

	}

	public void insertDefaultSale(Connection con) {
		Object[] values = { 55 };
		int generateKey = InsertTables.insertSalesPattern(con, values);
		Object[] values2 = { generateKey, false, new Date(120, 11, 3), new Date(120, 5, 3) };
		InsertTables.insertSale(con, values2);

	}

	public void insertDefaultFuelStationManager(Connection con) {
		String userName = "fsmUserName";
		Object[] values = { userName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { "role", Affiliation.FuelStation.toString(), userName };
		int fkemployeeID = InsertTables.insertEmployee(con, values2);
		Object[] values3 = { "0000", fkemployeeID };
		InsertTables.insertFuelStationManager(con, values3);

	}

	public void insertDefaultProduct(Connection con) {
		Object[] values = { ProductName.Diesel.toString(), 212.3456, 3.14159265 };
		InsertTables.insertProduct(con, values);
		Object[] values2 = { ProductName.Gasoline.toString(), 15.5, 10.25 };
		InsertTables.insertProduct(con, values2);

	}

	public void insertDefaultProductInSalePattern(Connection con) { // extends product
		Object[] values3 = { 332 };
		int generatedKey = InsertTables.insertSalesPattern(con, values3);
		Object[] values2 = { ProductName.Diesel.toString(), generatedKey, 0.5 };
		InsertTables.insertProductInSalePattern(con, values2);

	}

	public void insertDefaultProductRatesUpdateRequest(Connection con) {
		Object[] values = { false, false };
		InsertTables.insertProductRatesUpdateRequest(con, values);

	}

	public void insertDefaultProductInRequest(Connection con) {
		Object[] values = { false, false };
		int generatedKey = InsertTables.insertProductRatesUpdateRequest(con, values);
		Object[] values2 = { ProductName.Diesel.toString(), generatedKey, 15.2, 100 };
		InsertTables.insertProductInRequest(con, values2);

	}

	public void insertDefaultFuelStation(Connection con) {

		String userName = "Manager333";
		Object[] values = { userName, "22", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { "FuelStationManager", Affiliation.FuelStation.toString(), userName };
		int fkemployeeID = InsertTables.insertEmployee(con, values2);
		Object[] values3 = { "111111", fkemployeeID };
		int generatedKey = InsertTables.insertFuelStationManager(con, values3);

		String fuelComapny = FuelCompanyName.Sonol.toString();
		Object[] values4 = { "Gazim", fkemployeeID, "Road 6",fuelComapny };
		InsertTables.insertFuelStation(con, values4);

	}

	public void insertDefaultFastFuel(Connection con) {

		String customerID = "1212";
		int productInStaion = 1;

		Object[] values5 = { customerID, productInStaion, new Date(119, 3, 3), 30, 200.19 };
		InsertTables.insertFastFuel(con, values5);

	}

	public void insertDefaultProductInStation(Connection con) {

		Object[] values = { ProductName.MotorBikeFuel.toString(), 230.5, 2.2727 };
		InsertTables.insertProduct(con, values);

		String userName = "ManagerSad";
		String fuelComapny = FuelCompanyName.Sonol.toString();
		Object[] valuess = { userName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, valuess);

		Object[] values2 = { "FuelStationManager", Affiliation.FuelStation.toString(), userName };
		int fkemployeeID = InsertTables.insertEmployee(con, values2);
		Object[] values3 = { "2443322", fkemployeeID };
		int generatedKey = InsertTables.insertFuelStationManager(con, values3);
		Object[] values4 = { "PazSad", fkemployeeID, "Road 62", fuelComapny };
		int generatedKey2 = InsertTables.insertFuelStation(con, values4);

		Object[] values5 = { ProductName.MotorBikeFuel.toString(), generatedKey2, 25, 10 };
		InsertTables.insertProductInStation(con, values5);

	}

	public void insertDefaultQuarterlyReport(Connection con) {

		int fuelStationID = 1;
		int quarter = 1;
		String year = "2020";
		Object[] values = { quarter, year, new Date(120, 2, 3), fuelStationID};
		InsertTables.insertQuarterlyReport(con, values);

	}

	public void insertDefaultIncomeReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 2;
		String year = "2020";
		Object[] values = { quarter, year, new Date(120, 3, 1), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);

		Object[] values2 = { quarter, year, 20.5 };
		InsertTables.insertIncomeReport(con, values2);

	}

	public void insertDefaultProductInIncomeReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 3;
		String year = "2020";
		Object[] values = { quarter, year, new Date(120, 4, 6), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);

		Object[] values2 = { quarter, year, 20.5 };
		InsertTables.insertIncomeReport(con, values2);

		int productInStation = 1; // already created such product in station
		Object[] values3 = { productInStation, quarter, year, 100.82,52.2 };
		InsertTables.insertProductInIncomeReport(con, values3);
	}

	public void insertDefaultOutcomeReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 1;
		String year = "2019";
		Object[] values = { quarter, year, new Date(120, 4, 5), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);

		Object[] values2 = { quarter, year};
		InsertTables.insertOutcomeReport(con, values2);
	}

	public void insertDefaultProductInOutcomeReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 2;
		String year = "2019";
		Object[] values = { quarter, year, new Date(120, 1, 14), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);

		Object[] values2 = { quarter, year };
		InsertTables.insertOutcomeReport(con, values2);

		int productInStation = 1;
		Object[] values3 = { productInStation, quarter, year, 54.65 };
		InsertTables.insertProductInOutcomeReport(con, values3);
	}

	public void insertDefaultInventroyReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 1;
		String year = "2018";
		Object[] values = { quarter, year, new Date(120, 1, 1), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);
		Object[] values2 = { quarter, year };
		InsertTables.insertInventroyReport(con, values2);
	}

	public void insertDefaultProductInInventroyReport(Connection con) {
		int fuelStationID = 1;
		int quarter = 2;
		String year = "2018";
		Object[] values = { quarter, year, new Date(119, 2, 3), fuelStationID };
		InsertTables.insertQuarterlyReport(con, values);

		Object[] values2 = { quarter, year };
		InsertTables.insertInventroyReport(con, values2);

		int productInStation = 1;

		Object[] values3 = { productInStation, quarter, year, 25,32.4,33.3 };
		InsertTables.insertProductInInventroyReport(con, values3);
	}

	public void insertDefaultCustomerboughtInSale(Connection con) {
		String userName = "elro771";
		String customerID = "2557771";
		Object[] values = { userName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { customerID, userName, "1321-2222-3333-4444", CustomerType.Person.toString(), false };
		InsertTables.insertCustomer(con, values2);

		Object[] values3 = { 212 };
		int generateKey = InsertTables.insertSalesPattern(con, values3);
		Object[] values4 = { generateKey, false, new Date(120, 10, 3), new Date(120, 6, 3) };
		int generateKeySale = InsertTables.insertSale(con, values4);

		Object[] values5 = { generateKeySale, customerID, 250.54 };
		InsertTables.insertCustomerboughtInSale(con, values5);

	}

	public void insertDefaultNotification(Connection con) {
		String Username = "fsmUserName2"; // pk and refrance
		int fkemployeeID;
		int FSmanagerID;

		Object[] values = { Username, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { "role", Affiliation.FuelStation.toString(), Username };

		fkemployeeID = InsertTables.insertEmployee(con, values2);
		Object[] values3 = { "0000", fkemployeeID };

		FSmanagerID = InsertTables.insertFuelStationManager(con, values3);

		Object[] values4 = { false, "", fkemployeeID };
		InsertTables.insertNotification(con, values4);

	}

	public void insertDefaultShipmentMethod(Connection con) {
		Object[] values3 = { 5.5, 1, ShipmentType.Regular.toString(),"5-10 Days" };
		InsertTables.insertShipmentMethod(con, values3);
	}

	public void insertDefaultOrders(Connection con) {

		Object[] values = { new Date(System.currentTimeMillis()), 5.5, 100.4, "in israel" };
		InsertTables.insertOrders(con, values);
	}

	public void insertDefaultHomeFuelOrder(Connection con) {
		String username = "hfCusUserName";
		String customerID = "123456";
		int fkorders_ID;
		String fkshipmentType = ShipmentType.Urgent.toString();
		// generate user for customer
		Object[] values = { username, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);
		// generate customer
		Object[] values2 = { customerID, username, "hfcreditCard", CustomerType.Person.toString(), false };
		InsertTables.insertCustomer(con, values2);

		Object[] values3 = { 6.5, 2, fkshipmentType,"6 Hours" };
		InsertTables.insertShipmentMethod(con, values3);

		Object[] values4 = { new Date(System.currentTimeMillis()), 5.5, 100.4, "some where in israel" };
		fkorders_ID = InsertTables.insertOrders(con, values4);
		// generate homefuel order using pre set product DIESEL set in insert...product
		Object[] values5 = { new Date(System.currentTimeMillis()), customerID, fkshipmentType, fkorders_ID,
				ProductName.Diesel.toString() };
		InsertTables.insertHomeFuelOrder(con, values5);
	}

	public void insertDefaultPurchasingProgramType(Connection con) {

		Object[] values = { PurchasingProgramName.Standard.toString(), "Fast fueling in fuel stations of only 1 fuel company", 10 };
		InsertTables.insertPurchasingProgramType(con, values);
		
		Object[] values2 = { PurchasingProgramName.Premium.toString(), "Fast fueling in fuel stations of 2-3 fuel companies", 20 };
		InsertTables.insertPurchasingProgramType(con, values2);
		
	}

	public void insertDefaultFuelCompany(Connection con) {
		String userName = "SupplierUserName";
		int fkemployeeID;
		Object[] values = { userName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values);

		Object[] values2 = { "role", Affiliation.Marketing.toString(), userName };
		fkemployeeID = InsertTables.insertEmployee(con, values2);

		Object[] values3 = { FuelCompanyName.Sonol.toString(), fkemployeeID };
		InsertTables.insertFuelCompany(con, values3);
	}

	public void insertDefaultPurchasingProgram(Connection con) {
		String supplieruserName = "Supplier2UserName";
		String customerUserName = "customer13UserName";
		int fkemployeeID;
		String fkcustomer = "1212";
		String fkpurchasingProgramType = PurchasingProgramName.Premium.toString();
		String fkfuel_Company_Name = "paz";

		// customer
		Object[] values1 = { customerUserName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values1);

		Object[] values2 = { fkcustomer, customerUserName, "1111-2222-3333-4444", CustomerType.Person.toString(),
				false };
		InsertTables.insertCustomer(con, values2);

//		// PurchasingProgramType
//		Object[] values3 = { fkpurchasingProgramType, "for not single car", 100.4 };
//		InsertTables.insertPurchasingProgramType(con, values3);

		// fuelCompany
		Object[] values4 = { supplieruserName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values4);

		Object[] values5 = { "role", Affiliation.Marketing.toString(), supplieruserName };
		fkemployeeID = InsertTables.insertEmployee(con, values5);

		Object[] values6 = { fkfuel_Company_Name, fkemployeeID };
		InsertTables.insertFuelCompany(con, values6);

		// purchasing program
		Object[] values7 = { fkcustomer, fkpurchasingProgramType, fkfuel_Company_Name,fkfuel_Company_Name };
		InsertTables.insertPurchasingProgram(con, values7);
	}

	public void insertCustomerBoughtFromCompany(Connection con) {
		String supplieruserName = "Supplier3UserName";
		String customerUserName = "customer6UserName";
		int fkemployeeID;
		String fkcustomer = "221212";
		String fkfuel_Company_Name = "zap";

		// customer
		Object[] values1 = { customerUserName, "1234", false, "Elroye", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values1);

		Object[] values2 = { fkcustomer, customerUserName, "1111-2222-3333-4444", CustomerType.Person.toString(),
				false };
		InsertTables.insertCustomer(con, values2);

		// fuelCompany
		Object[] values4 = { supplieruserName, "11", false, "Moshe", "Cahana", "Mail@mai.com" };
		InsertTables.insertUser(con, values4);
		Object[] values5 = { "role", Affiliation.Marketing.toString(), supplieruserName };
		fkemployeeID = InsertTables.insertEmployee(con, values5);

		Object[] values6 = { fkfuel_Company_Name, fkemployeeID };
		InsertTables.insertFuelCompany(con, values6);

		// CustomerBoughtFromCompany
		Object[] values7 = { fkcustomer, fkfuel_Company_Name, 3.4, 34.0, new Date(119, 2, 5) };
		InsertTables.insertCustomerBoughtFromCompany(con, values7);
	}

	public void insertDefaultSaleCommentsReport(Connection con) {

		Object[] values3 = { 212 };
		int generateKey = InsertTables.insertSalesPattern(con, values3);
		Object[] values4 = { generateKey, false, new Date(118, 11, 3), new Date(119, 5, 3) };
		int generateKeySale = InsertTables.insertSale(con, values4);

		Object[] values5 = { generateKeySale, 15, 250.54, new Date(120, 4, 26) };
		InsertTables.insertSaleCommentsReport(con, values5);

//		Object[] values6 = { generateKeySale,12,230.54 };    //for checking that the relation is 1 to 1
//		InsertTables.insertSaleCommentsReport(con, values6);

	}

	public void insertDefaultFuelStationOrder(Connection con) {
		int orderID;
		int productInStationID = 1;
		String reason = "not enough cash";

		Object[] values = { new Date(System.currentTimeMillis()), 5.5, 100.4, "in israel" };
		orderID = InsertTables.insertOrders(con, values);

		Object[] values2 = { orderID, productInStationID, false, false, reason,false };
		InsertTables.insertFuelStationOrder(con, values2);

	}

	// vlad add
	public void insertDefaultPeriodicCustomersReport(Connection con) {
		Object[] values = { new Date(System.currentTimeMillis()), new Date(120, 11, 12),
				new Date(System.currentTimeMillis()) };
		InsertTables.insertPeriodicCustomersReport(con, values);

	}

	public void insertDefaultActivity(Connection con) {
		int employeeID = 1;
		String action = "add customer with ID = 1212";
		Object[] values = { employeeID, action, new Date(120, 10, 22) };
		InsertTables.insertActivity(con, values);

	}

}
