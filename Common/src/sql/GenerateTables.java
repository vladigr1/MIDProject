package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GenerateTables { // creating the tables if they are not exists

	public GenerateTables(Connection con) {
		// order by entities alphabetical
		generateActivity(con);
		generateCar(con);
		generateCustomer(con);
		generateCustomerBoughtFromCompany(con);
		generateCustomerBoughtInSale(con);
		generateEmployee(con);
		generateFastFuel(con);
		generateFuelCompany(con);
		generateFuelStation(con);
		generateFuelStationManager(con);
		generateFuelStationOrder(con);
		generateHomeFuelOrder(con);
		generateIncomeReport(con);
		generateInventroyReport(con);
		generateNotification(con);
		generateOrders(con);
		generateOutcomeReport(con);
		generatePeriodicCustomersReport(con);
		generatePricingModel(con);
		generatePricingModelType(con);
		generateProduct(con);
		generateProductInIncomeReport(con);
		generateProductInInventroyReport(con);
		generateProductInOutcomeReport(con);
		generateProductInRequest(con);
		generateProductInSalePattern(con);
		generateProductInStation(con);
		generateProductRatesUpdateRequest(con);
		generatePurchasingProgram(con);
		generatePurchasingProgramType(con);
		generateQuarterlyReport(con);
		generateRankingSheet(con);
		generateSale(con);
		generateSaleCommentsReport(con);
		generateSalePattern(con);
		generateShipmentMethod(con);
		generateUser(con);
		System.out.println("generating tables succeeded");
	}

	/******************* genral generate table function *******************/
	
	public static void generateTable(Connection con, String tableName, String values) {
		PreparedStatement pst;
		String table = "CREATE TABLE if not exists " + tableName + values
				+ "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ";
		try {
			pst = con.prepareStatement(table);
			pst.execute();
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	/******************* generate actual tables *******************/
	
	public void generateActivity(Connection con) { 
		String tableName = "activity";
		String values = "( "
				+ " activityID int NOT NULL AUTO_INCREMENT ," 
				+ " FK_employeeID INT NOT NULL ,"
				+ " time TIMESTAMP NOT NULL ,"
				+ " action varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (activityID) ," 
				//fk1
				+ " KEY activity_ibfk_1 (FK_employeeID) ,"
				+ " CONSTRAINT activity_ibfk_1 FOREIGN KEY (FK_employeeID) "
				+ " REFERENCES employee (employeeID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateCar(Connection con) {
		String tableName = "car";
		String values = "( "
				+ " registrationPlate varchar(32) NOT NULL ,"
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " FK_productName varchar(32) NOT NULL ,"
		        + " ownerName varchar(32) NOT NULL ,"
		        + " deleted varchar(1) NOT NULL ,"
				+ " PRIMARY KEY (registrationPlate) ,"
		        //fk1
		        + " KEY car_ibfk_1 (FK_productName) ,"
				+ " CONSTRAINT car_ibfk_1 FOREIGN KEY (FK_productName) "
				+ " REFERENCES product (productName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY car_ibfk_2 (FK_customerID) ,"
				+ " CONSTRAINT car_ibfk_2 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateCustomer(Connection con) {
		String tableName = "customer";
		String values = "( "
				+ " customerID varchar(32) NOT NULL ,"
				+ " FK_username varchar(32) NOT NULL UNIQUE ," 
				+ " creditCard varchar(32) NOT NULL ,"
				+ " customerType varchar(32) NOT NULL ," 
				+ " deleted varchar(1) NOT NULL ," 
				+ " PRIMARY KEY (customerID) ,"
				//fk1
				+ " KEY customer_ibfk_1 (FK_userName) ," 
				+ " CONSTRAINT customer_ibfk_1 FOREIGN KEY (FK_userName) "
				+ " REFERENCES user (username) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateCustomerBoughtFromCompany(Connection con) {
		String tableName = "customer_bought_from_company";
		String values = "( " 
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " FK_fuelCompanyName varchar(32) NOT NULL ,"
				+ " dateOfPurchase TIMESTAMP NOT NULL ,"
				+ " amountBoughtFromCompany DOUBLE(32,2) NOT NULL ,"
				+ " amountPaidCompany DOUBLE(32,2) NOT NULL ," 
				+ " PRIMARY KEY (FK_customerID,FK_fuelCompanyName,dateOfPurchase) ,"
				//fk1
				+ " KEY CustomerBoughtFromCompany_ibfk_1 (FK_customerID) ,"
				+ " CONSTRAINT CustomerBoughtFromCompany_ibfk_1 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY CustomerBoughtFromCompany_ibfk_2 (FK_fuelCompanyName) ,"
				+ " CONSTRAINT CustomerBoughtFromCompany_ibfk_2 FOREIGN KEY (FK_fuelCompanyName) "
				+ " REFERENCES fuel_company (fuelCompanyName) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateCustomerBoughtInSale(Connection con) {
		String tableName = "customer_bought_in_sale";
		String values =	"( "
				+ " FK_saleID INT NOT NULL ,"
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " amountPaid DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (FK_saleID, FK_customerID) ,"	
				//fk1
				+ " KEY customer_bought_in_sale_ibfk_1 (FK_saleID) ,"
				+ " CONSTRAINT customer_bought_in_sale_ibfk_1 FOREIGN KEY (FK_saleID) "
				+ " REFERENCES sale (saleID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY customer_bought_in_sale_ibfk_2 (FK_customerID) ,"
				+ " CONSTRAINT customer_bought_in_sale_ibfk_2 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateEmployee(Connection con) {
		String tableName = "employee";
		String values = "( "
				+ " employeeID int NOT NULL AUTO_INCREMENT ," 
				+ " FK_userName varchar(32) NOT NULL UNIQUE ,"
				+ " role varchar(32) NOT NULL ,"
				+ " affiliation varchar(32) NOT NULL ," 
				+ " PRIMARY KEY (employeeID) ,"
				//fk1
				+ " KEY employee_ibfk_1 (FK_userName) ,"
				+ " CONSTRAINT employee_ibfk_1 FOREIGN KEY (FK_userName) "
				+ " REFERENCES user (username) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateFastFuel(Connection con) {	
		String tableName = "fast_fuel";
		String values =	"( "
				+ " fastFuelID INT NOT NULL AUTO_INCREMENT ,"
				+ " FK_customerID varchar(32) NOT NULL ," 
				+ " FK_productInStationID INT NOT NULL ,"
				+ " fastFuelTime TIMESTAMP NOT NULL ," 
				+ " amountBought DOUBLE(32,2) NOT NULL ," 
				+ " finalPrice DOUBLE(32,2) NOT NULL ," 
				+ " PRIMARY KEY (fastFuelID) ,"
				//fk1
				+ " KEY fast_fuel_ibfk_1 (FK_productInStationID) ,"
				+ " CONSTRAINT fast_fuel_ibfk_1 FOREIGN KEY (FK_productInStationID) "
				+ " REFERENCES product_in_station (productInStationID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY fast_fuel_ibfk_2 (FK_customerID) ,"
				+ " CONSTRAINT fast_fuel_ibfk_2 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);		
	}
	
	public void generateFuelCompany(Connection con) {
		String tableName = "fuel_company";
		String values = "( " 
				+ " fuelCompanyName varchar(32) NOT NULL ,"
				+ " FK_employeeID int NOT NULL ," // supplier
				+ " PRIMARY KEY (fuelCompanyName) ,"
				//fk1
				+ " KEY fuel_company_ibfk_1 (FK_employeeID) ,"
				+ " CONSTRAINT fuel_company_ibfk_1 FOREIGN KEY (FK_employeeID) "
				+ " REFERENCES employee (employeeID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateFuelStation(Connection con) {
		String tableName = "fuel_station";
		String values = "( " 
				+ " fuelStationID INT NOT NULL AUTO_INCREMENT ," 
				+ " FK_fuelCompanyName varchar(32) NOT NULL ," 
				+ " FK_employeeID INT NOT NULL UNIQUE ,"
				+ " stationName varchar(32) NOT NULL UNIQUE ,"
				+ " address varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (fuelStationID) ,"	
				//fk1
				+ " KEY fuel_station_ibfk_1 (FK_fuelCompanyName) ,"
				+ " CONSTRAINT fuel_station_ibfk_1 FOREIGN KEY (FK_fuelCompanyName) "
				+ " REFERENCES fuel_company (fuelCompanyName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY fuel_station_ibfk_2 (FK_employeeID) ,"
				+ " CONSTRAINT fuel_station_ibfk_2 FOREIGN KEY (FK_employeeID) "
				+ " REFERENCES employee (employeeID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);		
	}
	
	public void generateFuelStationManager(Connection con) { 									    //to the fuel_station table and search there the FSmanagerID
		String tableName = "fuel_station_manager";
		String values = "( "
				+ " FK_employeeID int NOT NULL ," 
				+ " phoneNo varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (FK_employeeID) ,"
				//fk1
				+ " KEY fuel_station_manager_ibfk_1 (FK_employeeID) ,"
				+ " CONSTRAINT fuel_station_manager_ibfk_1 FOREIGN KEY (FK_employeeID) "
				+ " REFERENCES employee (employeeID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateFuelStationOrder(Connection con) {
		String tableName = "fuel_station_order";
		String values =	"( "
				+ "FK_ordersID INT NOT NULL ,"
				+ "FK_productInStationID INT NOT NULL ,"
				+ "assessed varchar(1) NOT NULL ,"
				+ "approved varchar(1) NOT NULL ,"
				+ "reasonDismissal varchar(64) NOT NULL ,"
				+ "supplied varchar(1) NOT NULL ,"
				+ "timeSupplied TIMESTAMP ,"
				+ " PRIMARY KEY (fk_ordersID) ,"
				//fk1
				+ " KEY fuel_station_order_ibfk_1 (FK_productInStationID) ,"
				+ " CONSTRAINT fuel_station_order_ibfk_1 FOREIGN KEY (FK_productInStationID) "
				+ " REFERENCES product_in_station (productInStationID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY fuel_station_order_ibfk_2 (FK_ordersID) ,"
				+ " CONSTRAINT fuel_station_order_ibfk_2 FOREIGN KEY (FK_ordersID) "
				+ " REFERENCES Orders (ordersID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateHomeFuelOrder(Connection con) {
		String tableName = "home_fuel_order";
		String values = "( "
				+ " FK_ordersID int NOT NULL ,"
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " FK_product_Name varchar(32) NOT NULL ,"
				+ " FK_shipmentType varchar(32) NOT NULL ,"	
				+ " duetime TIMESTAMP NOT NULL ,"
				+ " PRIMARY KEY (FK_ordersID) ,"
				//fk1
				+ " KEY home_fuel_order_ibfk_1 (FK_ordersID) ,"
				+ " CONSTRAINT home_fuel_order_ibfk_1 FOREIGN KEY (FK_ordersID) "
				+ " REFERENCES Orders (ordersID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY home_fuel_order_ibfk_2 (FK_customerID) ,"
				+ " CONSTRAINT home_fuel_order_ibfk_2 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk3
				+ " KEY home_fuel_order_ibfk_3 (FK_product_Name) ,"
				+ " CONSTRAINT home_fuel_order_ibfk_3 FOREIGN KEY (FK_product_Name) "
				+ " REFERENCES product (productName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk4
				+ " KEY home_fuel_order_ibfk_4 (FK_shipmentType) ,"
				+ " CONSTRAINT home_fuel_order_ibfk_4 FOREIGN KEY (FK_shipmentType) "
				+ " REFERENCES shipment_method (shipmentType) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateIncomeReport(Connection con) {
		String tableName = "income_report";
		String values =	"( "
				+ " FK_repQuarter INT NOT NULL,"
				+ " FK_repYear varchar(32) NOT NULL ,"
				+ " totalIncome DOUBLE(32,2) NOT NULL , " 		
				+ " PRIMARY KEY (FK_repQuarter,FK_repYear) ,"
				//fk1
				+ " KEY income_report_ibfk_1 (FK_repQuarter,FK_repYear),"
				+ " CONSTRAINT income_report_ibfk_1 FOREIGN KEY (FK_repQuarter,FK_repYear) "
				+ " REFERENCES quarterly_report (repQuarter,repYear) ON DELETE CASCADE ON UPDATE CASCADE)";
		generateTable(con, tableName, values);	
	}
	
	public void generateInventroyReport(Connection con) {
		String tableName = "inventory_report";
		String values =	"( "
					+ " FK_repQuarter INT NOT NULL ,"	
					+ " FK_repYear varchar(32) NOT NULL ,"
					+ " PRIMARY KEY (FK_repQuarter,FK_repYear) ,"
					//fk1
					+ " KEY inventory_report_ibfk_1 (FK_repQuarter,FK_repYear) ,"
					+ " CONSTRAINT inventory_report_ibfk_1 FOREIGN KEY (FK_repQuarter,FK_repYear) "
					+ " REFERENCES quarterly_report (repQuarter,repYear) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateNotification(Connection con) {
		String tableName = "notification";
		String values = "( "
				+ " notificationID int NOT NULL AUTO_INCREMENT ," 
				+ " FK_employeeID int NOT NULL ," 
				+ " message varchar(32) NOT NULL ,"
				+ " dismissed varchar(1) NOT NULL ,"
				+ " PRIMARY KEY (notificationID) ,"
				//fk1
				+ " KEY notification_ibfk_1 (FK_employeeID) ,"
				+ " CONSTRAINT notification_ibfk_1 FOREIGN KEY (FK_employeeID) "
				+ " REFERENCES fuel_station_manager (FK_employeeID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateOrders(Connection con) {
		String tableName = "orders";
		String values = "( "
				+ " ordersID int NOT NULL AUTO_INCREMENT ," 
				+ " orderTime TIMESTAMP NOT NULL ,"
				+ " amountBought DOUBLE(32,2) NOT NULL ,"
				+ " finalPrice DOUBLE(32,2) NOT NULL ," 
				+ " address varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (ordersID) )";
		generateTable(con, tableName, values);
	}
	
	public void generateOutcomeReport(Connection con) {
		String tableName = "outcome_report";
		String values =	"( "
				+ " FK_repQuarter INT NOT NULL ,"
				+ " FK_repYear varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (FK_repQuarter,FK_repYear) ,"
				//fk1
				+ " KEY outcome_report_ibfk_1 (FK_repQuarter,FK_repYear) ,"
				+ " CONSTRAINT outcome_report_ibfk_1 FOREIGN KEY (FK_repQuarter,FK_repYear) "
				+ " REFERENCES quarterly_report (repQuarter,repYear) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);		
	}
	
	public void generatePeriodicCustomersReport(Connection con) {
		String tableName = "periodic_customers_report";
		String values = "( "
				+ " dateFrom TIMESTAMP NOT NULL ,"
				+ " dateTo TIMESTAMP NOT NULL ,"
				+ " dateCreated TIMESTAMP NOT NULL ,"
				+ " PRIMARY KEY (dateFrom,dateTo) )";	
		generateTable(con, tableName, values);
	}
	
	public void generatePricingModel(Connection con) { 
		String tableName = "pricing_model";
		String values =	"( "
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " FK_pricingModelName varchar(32) NOT NULL ,"
				+ " currentDiscount DOUBLE(32,2) NOT NULL ,"
				+ " lastMonthUtillization DOUBLE(32,2) ,"
				+ " PRIMARY KEY (FK_customerID) ,"
				//fk1
				+ " KEY pricing_model_ibfk_1 (FK_customerID) ,"
				+ " CONSTRAINT pricing_model_ibfk_1 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY pricing_model_ibfk_2 (FK_pricingModelName) ,"
				+ " CONSTRAINT pricing_model_ibfk_2 FOREIGN KEY (FK_pricingModelName) "
				+ " REFERENCES pricing_model_type (pricingModelName) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generatePricingModelType(Connection con) {
		String tableName = "pricing_model_type";
		String values = "( "
				+ " pricingModelName varchar(32) NOT NULL ,"
		        + " description varchar(520) NOT NULL ,"
		        + " defaultDiscount DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (pricingModelName) )";	
		generateTable(con, tableName, values);
	}
	
	public void generateProduct(Connection con) {	
		String tableName = "product";
		String values =	"( "
				+ " productName varchar(32) NOT NULL ,"
				+ " maxPrice DOUBLE(32,2) NOT NULL ," 
				+ " currentPrice DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (productName) )";	
		generateTable(con, tableName, values);	
	}
	
	public void generateProductInIncomeReport(Connection con) {
		String tableName = "product_in_income_report";
		String values =	"( "
				+ " FK_productInStationID INT NOT NULL ,"
				+ " FK_repQuarter_IncomeReport INT NOT NULL ,"
				+ " FK_repYear_IncomeReport varchar(32) NOT NULL ,"
				+ " incomePerProduct DOUBLE(32,2) NOT NULL ,"				
				+ " avgPrice DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (FK_productInStationID, FK_repQuarter_IncomeReport,FK_repYear_IncomeReport) ,"	
				//fk1
				+ " KEY product_in_income_report_ibfk_1 (FK_productInStationID) ,"
				+ " CONSTRAINT product_in_income_report_ibfk_1 FOREIGN KEY (FK_productInStationID) "
				+ " REFERENCES product_in_station (productInStationID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_income_report_ibfk_2 (FK_repQuarter_IncomeReport,FK_repYear_IncomeReport) ,"
				+ " CONSTRAINT product_in_income_report_ibfk_2 FOREIGN KEY (FK_repQuarter_IncomeReport,FK_repYear_IncomeReport) "
				+ " REFERENCES income_report (FK_repQuarter,FK_repYear) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateProductInInventroyReport(Connection con) {
		String tableName = "product_in_inventory_report";
		String values =	"( "
				+ " FK_productInStationID INT NOT NULL ,"
				+ " FK_repQuarter_inventoryReport INT NOT NULL ,"
				+ " FK_repYear_inventoryReport varchar(32) NOT NULL ,"
				+ " amountSold DOUBLE(32,2) NOT NULL ,"
				+ " amountBegin DOUBLE(32,2) NOT NULL ,"
				+ " amountEnd DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (FK_productInStationID, FK_repQuarter_inventoryReport,FK_repYear_inventoryReport) ,"	
				//fk1
				+ " KEY product_in_inventory_report_ibfk_1 (FK_productInStationID) ,"
				+ " CONSTRAINT product_in_inventory_report_ibfk_1 FOREIGN KEY (FK_productInStationID) "
				+ " REFERENCES product_in_station (productInStationID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_inventory_report_ibfk_2 (FK_repQuarter_inventoryReport,FK_repYear_inventoryReport) ,"
				+ " CONSTRAINT product_in_inventory_report_ibfk_2 FOREIGN KEY (FK_repQuarter_inventoryReport,FK_repYear_inventoryReport) "
				+ " REFERENCES inventory_report (FK_repQuarter,FK_repYear) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateProductInOutcomeReport(Connection con) {
		String tableName = "product_in_outcome_report";
		String values =	"( "
				+ " FK_productInStationID INT NOT NULL ,"
				+ " FK_repQuarter_OutcomeReport INT NOT NULL ,"
				+ " FK_repYear_OutcomeReport varchar(32) NOT NULL ,"
				+ " amountBoughtFromSupplier DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (FK_productInStationID, FK_repQuarter_OutcomeReport,FK_repYear_OutcomeReport) ,"	
				//fk1
				+ " KEY product_in_outcome_report_ibfk_1 (FK_productInStationID) ,"
				+ " CONSTRAINT product_in_outcome_report_ibfk_1 FOREIGN KEY (FK_productInStationID) "
				+ " REFERENCES product_in_station (productInStationID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_outcome_report_ibfk_2 (FK_repQuarter_OutcomeReport,FK_repYear_OutcomeReport) ,"
				+ " CONSTRAINT product_in_outcome_report_ibfk_2 FOREIGN KEY (FK_repQuarter_OutcomeReport,FK_repYear_OutcomeReport) "
				+ " REFERENCES outcome_report (FK_repQuarter,FK_repYear) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateProductInRequest(Connection con) {	
		String tableName = "product_in_request";
		String values =	"( "
				+ " FK_updateRateRequestID INT NOT NULL ,"
				+ " FK_productName varchar(32) NOT NULL ,"
				+ " currentRate DOUBLE(32,2) NOT NULL ," 
				+ " requestedRate DOUBLE(32,2) NOT NULL ," 
				+ " PRIMARY KEY (FK_productName,FK_updateRateRequestID) ,"	
				//fk1
				+ " KEY product_in_request_ibfk_1 (FK_updateRateRequestID) ,"
				+ " CONSTRAINT product_in_request_ibfk_1 FOREIGN KEY (FK_updateRateRequestID) "
				+ " REFERENCES product_rates_update_request (updateRateRequestID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_request_ibfk_2 (FK_productName) ,"
				+ " CONSTRAINT product_in_request_ibfk_2 FOREIGN KEY (FK_productName) "
				+ " REFERENCES product (productName) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);		
	}
	
	public void generateProductInSalePattern(Connection con) {	
		String tableName = "product_in_sale_pattern";
		String values =	"( "
				+ " FK_salesPatternID INT NOT NULL ,"
				+ " FK_productName varchar(32) NOT NULL ,"
				+ " salesDiscount DOUBLE(32,2) NOT NULL ," 
				+ " PRIMARY KEY (FK_productName,FK_salesPatternID) ,"	
				//fk1
				+ " KEY product_in_sale_pattern_ibfk_1 (FK_salesPatternID) ,"
				+ " CONSTRAINT product_in_sale_pattern_ibfk_1 FOREIGN KEY (FK_salesPatternID) "
				+ " REFERENCES sales_pattern (salesPatternID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_sale_pattern_ibfk_2 (FK_productName) ,"
				+ " CONSTRAINT product_in_sale_pattern_ibfk_2 FOREIGN KEY (FK_productName) "
				+ " REFERENCES product (productName) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateProductInStation(Connection con) {
		String tableName = "product_in_station";
		String values =	"( "
				+ " productInStationID INT NOT NULL AUTO_INCREMENT ,"
				+ " FK_productName varchar(32) NOT NULL ,"
				+ " FK_fuelStationID INT NOT NULL ,"
				+ " capacity INT NOT NULL ," 
				+ " threshold INT NOT NULL ," 
				+ " PRIMARY KEY (productInStationID) ,"
				//fk1
				+ " KEY product_in_station_ibfk_1 (FK_productName) ,"
				+ " CONSTRAINT product_in_station_ibfk_1 FOREIGN KEY (FK_productName) "
				+ " REFERENCES product (productName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY product_in_station_ibfk_2 (FK_fuelStationID) ,"
				+ " CONSTRAINT product_in_station_ibfk_2 FOREIGN KEY (FK_fuelStationID) "
				+ " REFERENCES fuel_station (fuelStationID) ON DELETE CASCADE ON UPDATE CASCADE )";	
		generateTable(con, tableName, values);	
	}
	
	public void generateProductRatesUpdateRequest(Connection con) {	
		String tableName = "product_rates_update_request";
		String values = "( "
				+ " updateRateRequestID INT NOT NULL AUTO_INCREMENT ,"
				+ " assessed varchar(1) NOT NULL ," 
				+ " approved varchar(1) ,"
				+ " PRIMARY KEY (updateRateRequestID) )";
		generateTable(con, tableName, values);		
	}
	
	public void generatePurchasingProgram(Connection con) {
		String tableName = "purchasing_program";
		String values = "( "
				+ " FK_customerID varchar(32) NOT NULL ,"
				+ " FK_purchasingProgramName varchar(32) NOT NULL ," 
				+ " FK_fuelCompanyName1 varchar(32) NOT NULL ,"
				+ " FK_fuelCompanyName2 varchar(32) ,"
				+ " FK_fuelCompanyName3 varchar(32) ,"
				+ " PRIMARY KEY (FK_customerID) ,"
				//fk1
				+ " KEY purchasing_program_ibfk_1 (FK_customerID) ,"
				+ " CONSTRAINT purchasing_program_ibfk_1 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk2
				+ " KEY purchasing_program_ibfk_2 (FK_purchasingProgramName) ,"
				+ " CONSTRAINT purchasing_program_ibfk_2 FOREIGN KEY (FK_purchasingProgramName) "
				+ " REFERENCES Purchasing_program_type (purchasingProgramName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk3
				+ " KEY purchasing_program_ibfk_3 (FK_fuelCompanyName1) ,"
				+ " CONSTRAINT purchasing_program_ibfk_3 FOREIGN KEY (FK_fuelCompanyName1) "
				+ " REFERENCES fuel_company (fuelCompanyName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk4
				+ " KEY purchasing_program_ibfk_4 (FK_fuelCompanyName2) ,"
				+ " CONSTRAINT purchasing_program_ibfk_4 FOREIGN KEY (FK_fuelCompanyName2) "
				+ " REFERENCES fuel_company (fuelCompanyName) ON DELETE CASCADE ON UPDATE CASCADE ,"
				//fk5
				+ " KEY purchasing_program_ibfk_5 (FK_fuelCompanyName3) ,"
				+ " CONSTRAINT purchasing_program_ibfk_5 FOREIGN KEY (FK_fuelCompanyName3) "
				+ " REFERENCES fuel_company (fuelCompanyName) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generatePurchasingProgramType(Connection con) {
		String tableName = "purchasing_program_type";
		String values = "( "
				+ " purchasingProgramName varchar(32) NOT NULL ,"
				+ " description varchar(520) NOT NULL ," 
				+ " monthlyPrice DOUBLE(32,2) NOT NULL ,"
				+ " PRIMARY KEY (purchasingProgramName) )";
		generateTable(con, tableName, values);
	}
	
	public void generateQuarterlyReport(Connection con) {
		String tableName = "quarterly_report";
		String values =	"( "
				+ " repQuarter INT NOT NULL ,"
				+ " repYear varchar(32) NOT NULL ,"
				+ " FK_fuelStationID INT NOT NULL ,"
				+ " dateCreated TIMESTAMP NOT NULL ,"
				+ " PRIMARY KEY (repQuarter,repYear) ,"
				//fk1
				+ " KEY quarterly_report_ibfk_1 (FK_fuelStationID) ,"
				+ " CONSTRAINT quarterly_report_ibfk_1 FOREIGN KEY (FK_fuelStationID) "
				+ " REFERENCES fuel_station (fuelStationID) ON DELETE CASCADE ON UPDATE CASCADE )";	
		generateTable(con, tableName, values);	
	}
	
	public void generateRankingSheet(Connection con) {
		String tableName = "ranking_sheet";
		String values = "( "
				+ " FK_customerID varchar(32) NOT NULL ,"
		        + " customerTypeRank DOUBLE(32,2) NOT NULL ,"
		        + " fuelingHoursRank DOUBLE(32,2) NOT NULL ,"
				+ " fuelTypesRank DOUBLE(32,2) NOT NULL ,"
				+ " updatedForDate TIMESTAMP NOT NULL ,"
				+ " PRIMARY KEY (FK_customerID) ,"
				//fk1
				+ " KEY ranking_sheet_ibfk_1 (FK_customerID), "
				+ " CONSTRAINT ranking_sheet_ibfk_1 FOREIGN KEY (FK_customerID) "
				+ " REFERENCES customer (customerID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateSale(Connection con) {
		String tableName = "sale";
		String values = "( "
				+ " saleID INT NOT NULL AUTO_INCREMENT ," 
				+ " FK_salesPatternID int NOT NULL ," 	
				+ " active varchar(1)  NOT NULL ," 
				+ " startTime TIMESTAMP NOT NULL ,"
				+ " endTime TIMESTAMP NOT NULL ," 
				+ " PRIMARY KEY (saleID) ,"
				//fk1
				+ " KEY sale_ibfk_1 (FK_salesPatternID) ,"
				+ " CONSTRAINT sale_ibfk_1 FOREIGN KEY (FK_salesPatternID) "
				+ " REFERENCES sales_pattern (salesPatternID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);
	}
	
	public void generateSaleCommentsReport(Connection con) {
		String tableName = "sale_comments_report";
		String values =	"( "
				+ " FK_saleID INT NOT NULL ,"
				+ " numberOfCustomersBought INT NOT NULL,"
				+ " sumOfPurchases DOUBLE(32,2) NOT NULL,"
				+ " dateCreated TIMESTAMP NOT NULL,"
				+ " PRIMARY KEY (FK_saleID),"	
				//fk1
				+ " KEY sale_comments_report_ibfk_1 (FK_saleID) ,"
				+ " CONSTRAINT sale_comments_report_ibfk_1 FOREIGN KEY (FK_saleID) "
				+ " REFERENCES sale (saleID) ON DELETE CASCADE ON UPDATE CASCADE )";
		generateTable(con, tableName, values);	
	}
	
	public void generateSalePattern(Connection con) {	
		String tableName = "sales_pattern";
		String values = "( "
				+ " salesPatternID INT NOT NULL AUTO_INCREMENT ," 
				+ " durationInMinutes INT NOT NULL ,"
				+ " PRIMARY KEY (salesPatternID) )";
		generateTable(con, tableName, values);
	}
	
	public void generateShipmentMethod(Connection con) {
		String tableName = "shipment_method";
		String values = "( "
				+ " shipmentType varchar(32) NOT NULL ,"
				+ " shipmentPrice DOUBLE(32,2) NOT NULL ," 
				+ " shipmentMultiplier DOUBLE(32,2) NOT NULL ,"
				+ " deliveryTime varchar(32) NOT NULL ," 
				+ " PRIMARY KEY (shipmentType) )";
		generateTable(con, tableName, values);
	}

	public void generateUser(Connection con) {
		String tableName = "user";
		String values = "( "
				+ " username varchar(32) NOT NULL ,"
		        + " password varchar(32) NOT NULL ,"
				+ " connected varchar(1) NOT NULL ," 
		        + " email varchar(32) NOT NULL ," 
		        + " firstName varchar(32) NOT NULL ,"
				+ " surname varchar(32) NOT NULL ,"
				+ " PRIMARY KEY (username) )";
		generateTable(con, tableName, values);
	}
	
}
