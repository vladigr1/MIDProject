package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTables { // insert to tables

	private static int insertRow(Connection con, String tableName, String[] fields, Object[] values) {
		int res = -1;
		try {
			// Get result test metadata
//			System.out.println("inserting row to table -> " + tableName);
			StringBuilder valueString = new StringBuilder();
			for (int i = 0; i < values.length; i++) {
				valueString.append(values[i].toString() + " , ");
			}
//			System.out.println("values -> " + valueString.toString());

			if (values.length != fields.length)
				throw new Exception("ERROR: there are more values than coloms in table->" + tableName);

			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO " + tableName + " (");
			for (int i = 0; i < fields.length; i++) {
				if (i == fields.length - 1) {
					sb.append(fields[i] + ") VALUES (");
				} else {
					sb.append(fields[i] + ", ");
				}
			}
//			System.out.println("String builder->"+sb.toString());
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					sb.append("?)");
				} else {
					sb.append("?, ");
				}
			}
//			System.out.println("String builder->"+sb.toString());
			PreparedStatement ps = con.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);
			for (int i = 1; i <= fields.length; ++i) {
				ps.setObject(i, values[i - 1]);
			}
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next() != false) {
		    	res = rs.getInt(1);	
		    }	   
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("in table "+tableName);
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return res;
	}
	
	// only add inserts method!
	public static int insertUser(Connection con, Object[] values) {
		return insertRow(con, "user", FieldIndicatorForInsert.User(), values);
	}

	public static void insertCustomer(Connection con, Object[] values) {
		insertRow(con, "customer", FieldIndicatorForInsert.Customer(), values);
	}

	public static int insertEmployee(Connection con, Object[] values) {
		return insertRow(con, "employee", FieldIndicatorForInsert.Employee(), values);

	}

	public static int insertSalesPattern(Connection con, Object[] values) {
		return insertRow(con, "sales_pattern", FieldIndicatorForInsert.SalePattern(), values);
	}
	
	public static int insertFuelStationManager(Connection con, Object[] values) {
		return insertRow(con, "fuel_station_manager", FieldIndicatorForInsert.FuelStationManager(), values);
	}

	public static void insertProduct(Connection con, Object[] values) {
		insertRow(con, "product", FieldIndicatorForInsert.Product(), values);

	}

	public static void insertProductInSalePattern(Connection con, Object[] values) {
		insertRow(con, "product_in_sale_pattern", FieldIndicatorForInsert.ProductInSalePattern(), values);
	}

	public static int insertProductRatesUpdateRequest(Connection con, Object[] values) {
		return insertRow(con, "product_rates_update_request", FieldIndicatorForInsert.ProductRatesUpdateRequest(), values);
	}

	public static void insertProductInRequest(Connection con, Object[] values) {
		insertRow(con, "product_in_request", FieldIndicatorForInsert.ProductInRequest(), values);
	}

	public static int insertFuelStation(Connection con, Object[] values) {
		return insertRow(con, "fuel_station", FieldIndicatorForInsert.FuelStation(), values);
	}

	public static void insertFastFuel(Connection con, Object[] values) {
		insertRow(con, "fast_fuel", FieldIndicatorForInsert.FastFuel(), values);
	}

	public static void insertProductInStation(Connection con, Object[] values) {
		insertRow(con, "product_in_station", FieldIndicatorForInsert.ProductInStation(), values);
		
	}

	public static void insertQuarterlyReport(Connection con, Object[] values) {
		 insertRow(con, "quarterly_report", FieldIndicatorForInsert.QuarterlyReport(), values);
		
	}

	public static void insertIncomeReport(Connection con, Object[] values) {
		insertRow(con, "income_report", FieldIndicatorForInsert.IncomeReport(), values);
	}

	public static void insertOutcomeReport(Connection con, Object[] values) {
		insertRow(con, "outcome_report", FieldIndicatorForInsert.OutcomeReport(), values);
	}

	public static void insertInventroyReport(Connection con, Object[] values) {
		insertRow(con, "inventory_report", FieldIndicatorForInsert.InventroyReport(), values);
	}

	public static void insertProductInIncomeReport(Connection con, Object[] values) {
		insertRow(con, "product_in_income_report", FieldIndicatorForInsert.ProductInIncomeReport(), values);
	}

	public static void insertProductInOutcomeReport(Connection con, Object[] values) {
		insertRow(con, "product_in_outcome_report", FieldIndicatorForInsert.ProductInOutcomeReport(), values);
	}

	public static void insertProductInInventroyReport(Connection con, Object[] values) {
		insertRow(con, "product_in_inventory_report", FieldIndicatorForInsert.ProductInInventroyReport(), values);
	}

	public static int insertSale(Connection con, Object[] values) {
		return insertRow(con, "sale", FieldIndicatorForInsert.Sale(), values);		
	}

	public static void insertCustomerboughtInSale(Connection con, Object[] values) {
		insertRow(con, "customer_bought_in_sale", FieldIndicatorForInsert.CustomerboughtInSale(), values);	
	}

	public static void insertSaleCommentsReport(Connection con, Object[] values) {
		insertRow(con, "sale_comments_report", FieldIndicatorForInsert.SaleCommentsReport(), values);
		
	}

	public static void insertFuelStationOrder(Connection con, Object[] values) {
		insertRow(con, "fuel_station_order", FieldIndicatorForInsert.FuelStationOrder(), values);
		
	}
	
	public static void insertNotification(Connection con, Object[] values) {
		insertRow(con, "notification", FieldIndicatorForInsert.Notification(), values);
	}
	
	
	public static void insertShipmentMethod(Connection con, Object[] values) {
		insertRow(con, "shipment_method", FieldIndicatorForInsert.ShipmentMethod(), values);
	}
	
	public static int insertOrders(Connection con, Object[] values) {
		return insertRow(con, "Orders", FieldIndicatorForInsert.Orders(), values);
	}
	
	public static void insertHomeFuelOrder(Connection con, Object[] values) {
		insertRow(con, "home_fuel_order", FieldIndicatorForInsert.HomeFuelOrder(), values);
	}
	
	public static void insertPurchasingProgramType(Connection con, Object[] values) {
		insertRow(con, "Purchasing_program_type", FieldIndicatorForInsert.PurchasingProgramType(), values);
	}
	
	public static void insertFuelCompany(Connection con, Object[] values) {
		insertRow(con, "fuel_company", FieldIndicatorForInsert.FuelCompany(), values);
	}
	
	public static void insertPurchasingProgram(Connection con, Object[] values) {
		switch(values.length)
		{
		case 3:
			insertRow(con, "purchasing_program", FieldIndicatorForInsert.PurchasingProgram1(), values);
			break;
		case 4:
			insertRow(con, "purchasing_program", FieldIndicatorForInsert.PurchasingProgram2(), values);
			break;
		case 5:
			insertRow(con, "purchasing_program", FieldIndicatorForInsert.PurchasingProgram3(), values);	
			break;
		}
	}
	public static void insertCustomerBoughtFromCompany(Connection con, Object[] values) {
		insertRow(con, "customer_bought_from_company", FieldIndicatorForInsert.CustomerBoughtFromCompany(), values);
	}
	
	//vlad add

	public static void insertCar(Connection con, Object[] values) {
		 insertRow(con, "car", FieldIndicatorForInsert.Car(), values);
		
	}

	public static void insertRankingSheet(Connection con, Object[] values) {
		  insertRow(con, "ranking_sheet", FieldIndicatorForInsert.RankingSheet(), values);
		
	}

	public static void insertPricingModelType(Connection con, Object[] values) {
		insertRow(con, "pricing_model_type", FieldIndicatorForInsert.PricingModelType(), values);
		
	}

	public static void insertPricingModel(Connection con, Object[] values) {
		insertRow(con, "pricing_model", FieldIndicatorForInsert.PricingModel(), values);
		
	}
	
	public static void insertPeriodicCustomersReport(Connection con, Object[] values) {
		insertRow(con, "periodic_customers_report", FieldIndicatorForInsert.PeriodicCustomersReport(), values);
	}

	public static void insertActivity(Connection con, Object[] values) {
		insertRow(con, "activity", FieldIndicatorForInsert.Activity(), values);
		
		
	}
}
