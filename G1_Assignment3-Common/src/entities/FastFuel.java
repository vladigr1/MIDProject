package entities;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class FastFuel implements Serializable {

	// primary keys
	private Integer fastFuelID;

	// foreign keys
	private String customerID;
	private int productInStaionID;

	// fields
	private Date fastFuelTime;
	private double amountBought;
	private double finalPrice;

	public FastFuel(String customerID, int productInStaionID, Date fastFuelTime, double amountBought,
			double finalPrice) {
		super();
		this.customerID = customerID;
		this.productInStaionID = productInStaionID;
		this.fastFuelTime = fastFuelTime;
		this.amountBought = amountBought;
		this.finalPrice = finalPrice;
	}

	public FastFuel(int fastFuelID, String customerID, int productInStaionID, Date fastFuelTime, double amountBought,
			double finalPrice) {
		super();
		this.fastFuelID = fastFuelID;
		this.customerID = customerID;
		this.productInStaionID = productInStaionID;
		this.fastFuelTime = fastFuelTime;
		this.amountBought = amountBought;
		this.finalPrice = finalPrice;
	}

	public int getFastFuelID() {
		return fastFuelID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public int getProductInStaionID() {
		return productInStaionID;
	}

	public void setProductInStaionID(int productInStaionID) {
		this.productInStaionID = productInStaionID;
	}

	public Date getFastFuelTime() {
		return fastFuelTime;
	}

	public void setFastFuelTime(Date fastFuelTime) {
		this.fastFuelTime = fastFuelTime;
	}

	public double getAmountBought() {
		return amountBought;
	}

	public void setAmountBought(double amountBought) {
		this.amountBought = amountBought;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		String str = "FastFuel [";
		if (fastFuelID != null)
			str += "fastFuelID=" + fastFuelID + ", ";
		str += "customerID=" + customerID + ", productInStaionID=" + productInStaionID + ", fastFuelTime="
				+ fastFuelTime + ", amountBought=" + amountBought + ", finalPrice=" + finalPrice + "]";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FastFuel))
			return false;
		FastFuel other = (FastFuel) obj;
		if (this.fastFuelID == null || other.fastFuelID == null)
			return false;
		return this.fastFuelID.equals(other.fastFuelID);
	}

}
