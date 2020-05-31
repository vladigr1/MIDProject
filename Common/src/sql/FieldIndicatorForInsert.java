package sql;

public class FieldIndicatorForInsert {
	// elro addiotons

	public static String[] FullSingleMemberMonthly() {
		return new String[] { "FK_customerID", "lastMonthUtillization" };
	}

	public static String[] PricingModel() {
		return new String[] { "FK_pricingModelName", "FK_customerID", "currentDiscount" };
	}

	public static String[] PricingModelType() {
		return new String[] { "pricingModelName", "description", "defaultDiscount" };
	}

	public static String[] Car() {
		return new String[] { "registrationPlate", "ownerName", "FK_productName", "FK_customerID", "deleted" };
	}

	public static String[] RankingSheet() {
		return new String[] { "customerTypeRank", "fuelingHoursRank", "fuelTypesRank", "FK_customerID",
				"updatedForDate" };
	}

	public static String[] User() {
		return new String[] { "username", "password", "connected", "firstName", "surname", "email" };
	}

	public static String[] Employee() {
		return new String[] { "role", "affiliation", "FK_userName" };
	}

	public static String[] SalePattern() {
		return new String[] { "durationInMinutes" }; // add saleID?
	}

	public static String[] Customer() {
		return new String[] { "customerID", "FK_userName", "creditCard", "customerType", "deleted" };
	}

	public static String[] Activity() {
		return new String[] { "FK_employeeID", "action", "time" };
	}

	public static String[] Product() {
		return new String[] { "productName", "maxPrice", "currentPrice" };
	}

	public static String[] ProductInSalePattern() {
		return new String[] { "FK_productName", "FK_salesPatternID", "salesDiscount" };
	}

	public static String[] ProductRatesUpdateRequest() {
		return new String[] { "assessed", "approved" };
	}

	public static String[] ProductInRequest() {
		return new String[] { "FK_productName", "FK_updateRateRequestID", "currentRate", "requestedRate" };
	}

	public static String[] FuelStation() {
		return new String[] { "stationName", "FK_employeeID", "address", "FK_fuelCompanyName" }; // add here company
																									// name afterwards
	}

	public static String[] FastFuel() {
		return new String[] { "FK_customerID", "FK_productInStationID", "fastFuelTime", "amountBought", "finalPrice" };
	}

	public static String[] ProductInStation() {
		return new String[] { "FK_productName", "FK_fuelStationID", "capacity", "threshold" };
	}

	public static String[] QuarterlyReport() {
		return new String[] { "repQuarter", "repYear", "dateCreated", "FK_fuelStationID" };
	}

	public static String[] IncomeReport() {
		return new String[] { "FK_repQuarter", "FK_repYear", "totalIncome" };
	}

	public static String[] OutcomeReport() {
		return new String[] { "FK_repQuarter", "FK_repYear" };
	}

	public static String[] InventroyReport() {
		return new String[] { "FK_repQuarter", "FK_repYear" };
	}

	public static String[] ProductInIncomeReport() {
		return new String[] { "FK_productInStationID", "FK_repQuarter_IncomeReport", "FK_repYear_IncomeReport",
				"incomePerProduct","avgPrice" };
	}

	public static String[] ProductInOutcomeReport() {
		return new String[] { "FK_productInStationID", "FK_repQuarter_OutcomeReport", "FK_repYear_OutcomeReport",
				"amountBoughtFromSupplier" };
	}

	public static String[] ProductInInventroyReport() {
		return new String[] { "FK_productInStationID", "FK_repQuarter_inventoryReport", "FK_repYear_inventoryReport",
				"amountSold","amountBegin","amountEnd" };
	}

	public static String[] FuelStationManager() {
		return new String[] { "phoneNo", "FK_employeeID" };
	}

	public static String[] Sale() {
		return new String[] { "FK_salesPatternID", "active", "startTime", "endTime" };
	}

	public static String[] CustomerboughtInSale() {
		return new String[] { "FK_saleID", "FK_customerID", "amountPaid" };
	}

	public static String[] SaleCommentsReport() {
		return new String[] { "FK_saleID", "numberOfCustomersBought", "sumOfPurchases", "dateCreated" };
	}

	public static String[] FuelStationOrder() {
		return new String[] { "FK_ordersID", "FK_productInStationID", "assessed", "approved", "reasonDismissal","supplied" };
	}
	// vlad added

	public static String[] Notification() {
		return new String[] { "dismissed", "message", "FK_employeeID" };
	}

	public static String[] ShipmentMethod() {
		return new String[] { "shipmentPrice", "shipmentMultiplier", "shipmentType","deliveryTime" };
	}

	public static String[] Orders() {
		return new String[] { "orderTime", "amountBought", "finalPrice", "address" };
	}

	public static String[] HomeFuelOrder() {
		return new String[] { "duetime", "FK_customerID", "FK_shipmentType", "FK_ordersID", "FK_product_Name" };
	}

	public static String[] PurchasingProgramType() {
		return new String[] { "purchasingProgramName", "description", "monthlyPrice" };
	}

	public static String[] FuelCompany() {
		return new String[] { "fuelCompanyName", "FK_employeeID" };
	}

	
	public static String[] PurchasingProgram1() {
		return new String[] { "FK_customerID", "FK_purchasingProgramName", "FK_fuelCompanyName1" };
	}
	
	public static String[] PurchasingProgram2() {
		return new String[] { "FK_customerID", "FK_purchasingProgramName", "FK_fuelCompanyName1", "FK_fuelCompanyName2"  };
	}
	
	public static String[] PurchasingProgram3() {
		return new String[] { "FK_customerID", "FK_purchasingProgramName", "FK_fuelCompanyName1", "FK_fuelCompanyName2" , "FK_fuelCompanyName3"  };
	}


	public static String[] CustomerBoughtFromCompany() {
		return new String[] { "FK_customerID", "FK_fuelCompanyName", "amountBoughtFromCompany", "amountPaidCompany",
				"dateOfPurchase" };
	}

	public static String[] PeriodicCustomersReport() {
		return new String[] { "dateFrom", "dateTo", "dateCreated" };
	}

}
