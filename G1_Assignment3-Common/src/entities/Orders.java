package entities;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public abstract class Orders implements Serializable {

	// primary keys
	private Integer ordersID;

	// fields
	private Date orderTime;
	private double amountBought;
	private double finalPrice;
	private String address;

	public Orders(Date orderTime, double amountBought, double finalPrice, String address) {
		super();
		this.orderTime = orderTime;
		this.amountBought = amountBought;
		this.finalPrice = finalPrice;
		this.address = address;
	}

	public Orders(int ordersID, Date orderTime, double amountBought, double finalPrice, String address) {
		super();
		this.ordersID = ordersID;
		this.orderTime = orderTime;
		this.amountBought = amountBought;
		this.finalPrice = finalPrice;
		this.address = address;
	}

	public int getOrdersID() {
		return ordersID;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		String str = "Orders [";
		if (ordersID != null)
			str += "ordersID=" + ordersID + ", ";
		str += "orderTime=" + orderTime + ", amountBought=" + amountBought + ", finalPrice=" + finalPrice + ", address="
				+ address + "]";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Orders))
			return false;
		Orders other = (Orders) obj;
		if (this.ordersID == null || other.ordersID == null)
			return false;
		return this.ordersID.equals(other.ordersID);
	}

}