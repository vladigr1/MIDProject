package entities;

import java.util.Date;

import enums.ProductName;
import enums.ShipmentType;

@SuppressWarnings("serial")
public class HomeFuelOrder extends Orders {

	// primary keys
	/* private Integer ordersID; */

	// foreign keys
	private String customerID;
	private ProductName productName;
	private ShipmentType shipmentMethod;

	// fields
	private Date dueTime;

	public HomeFuelOrder(Date orderTime, double amountBought, double finalPrice, String address, String customerID,
			ProductName productName, ShipmentType shipmentMethod, Date dueTime) {
		super(orderTime, amountBought, finalPrice, address);
		this.customerID = customerID;
		this.productName = productName;
		this.shipmentMethod = shipmentMethod;
		this.dueTime = dueTime;
	}

	public HomeFuelOrder(int ordersID, Date orderTime, double amountBought, double finalPrice, String address,
			String customerID, ProductName productName, ShipmentType shipmentMethod, Date dueTime) {
		super(ordersID, orderTime, amountBought, finalPrice, address);
		this.customerID = customerID;
		this.productName = productName;
		this.shipmentMethod = shipmentMethod;
		this.dueTime = dueTime;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public ProductName getProductName() {
		return productName;
	}

	public void setProductName(ProductName productName) {
		this.productName = productName;
	}

	public ShipmentType getShipmentMethod() {
		return shipmentMethod;
	}

	public void setShipmentMethod(ShipmentType shipmentMethod) {
		this.shipmentMethod = shipmentMethod;
	}

	public Date getDueTime() {
		return dueTime;
	}

	public void setDueTime(Date dueTime) {
		this.dueTime = dueTime;
	}

	@Override
	public String toString() {
		return "HomeFuelOrder [" + super.toString() + ", customerID=" + customerID + ", productName=" + productName
				+ ", shipmentMethod=" + shipmentMethod + ", dueTime=" + dueTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
