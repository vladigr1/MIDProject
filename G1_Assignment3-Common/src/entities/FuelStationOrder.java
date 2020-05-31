package entities;

import java.util.Date;

@SuppressWarnings("serial")
public class FuelStationOrder extends Orders {

	// primary keys
	/* private Integer ordersID; */

	// foreign keys
	private int productInStaionID;

	// fields
	private boolean assessed;
	private boolean approved;
	private String reasonDismissal;
	private boolean supplied;
	private Date timeSupplied; // can be NULL

	public FuelStationOrder(Date orderTime, double amountBought, double finalPrice, String address,
			int productInStaionID, boolean assessed, boolean approved, String reasonDismissal, boolean supplied) {
		super(orderTime, amountBought, finalPrice, address);
		this.productInStaionID = productInStaionID;
		this.assessed = assessed;
		this.approved = approved;
		this.reasonDismissal = reasonDismissal;
		this.supplied = supplied;
	}

	public FuelStationOrder(int ordersID, Date orderTime, double amountBought, double finalPrice, String address,
			int productInStaionID, boolean assessed, boolean approved, String reasonDismissal, boolean supplied) {
		super(ordersID, orderTime, amountBought, finalPrice, address);
		this.productInStaionID = productInStaionID;
		this.assessed = assessed;
		this.approved = approved;
		this.reasonDismissal = reasonDismissal;
		this.supplied = supplied;
	}

	public FuelStationOrder(Date orderTime, double amountBought, double finalPrice, String address,
			int productInStaionID, boolean assessed, boolean approved, String reasonDismissal, boolean supplied,
			Date timeSupplied) {
		super(orderTime, amountBought, finalPrice, address);
		this.productInStaionID = productInStaionID;
		this.assessed = assessed;
		this.approved = approved;
		this.reasonDismissal = reasonDismissal;
		this.supplied = supplied;
		this.timeSupplied = timeSupplied;
	}

	public FuelStationOrder(int ordersID, Date orderTime, double amountBought, double finalPrice, String address,
			int productInStaionID, boolean assessed, boolean approved, String reasonDismissal, boolean supplied,
			Date timeSupplied) {
		super(ordersID, orderTime, amountBought, finalPrice, address);
		this.productInStaionID = productInStaionID;
		this.assessed = assessed;
		this.approved = approved;
		this.reasonDismissal = reasonDismissal;
		this.supplied = supplied;
		this.timeSupplied = timeSupplied;
	}

	public int getProductInStaionID() {
		return productInStaionID;
	}

	public void setProductInStaionID(int productInStaionID) {
		this.productInStaionID = productInStaionID;
	}

	public boolean isAssessed() {
		return assessed;
	}

	public void setAssessed(boolean assessed) {
		this.assessed = assessed;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getReasonDismissal() {
		return reasonDismissal;
	}

	public void setReasonDismissal(String reasonDismissal) {
		this.reasonDismissal = reasonDismissal;
	}

	public boolean isSupplied() {
		return supplied;
	}

	public void setSupplied(boolean supplied) {
		this.supplied = supplied;
	}

	public Date getTimeSupplied() {
		return timeSupplied;
	}

	public void setTimeSupplied(Date timeSupplied) {
		this.timeSupplied = timeSupplied;
	}

	@Override
	public String toString() {
		String str = "FuelStationOrder [" + super.toString() + ", productInStaionID=" + productInStaionID
				+ ", assessed=" + assessed + ", approved=" + approved + ", reasonDismissal=" + reasonDismissal
				+ ", supplied=" + supplied;
		if (timeSupplied != null)
			str += ", timeSupplied=" + timeSupplied;
		str += "]";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

}
