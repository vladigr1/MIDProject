package entities;

import java.io.Serializable;

import enums.ProductName;

@SuppressWarnings("serial")
public class ProductInSalePattern implements Serializable {

	// primary keys
	private int salesPatternID;
	private ProductName productName;

	// fields
	private double salesDiscount;

	public ProductInSalePattern(int salesPatternID, ProductName productName, double salesDiscount) {
		super();
		this.salesPatternID = salesPatternID;
		this.productName = productName;
		this.salesDiscount = salesDiscount;
	}

	public int getSalesPatternID() {
		return salesPatternID;
	}

	public ProductName getProductName() {
		return productName;
	}

	public double getSalesDiscount() {
		return salesDiscount;
	}

	public void setSalesDiscount(double salesDiscount) {
		this.salesDiscount = salesDiscount;
	}

	@Override
	public String toString() {
		return "ProductInSalePattern [salesPatternID=" + salesPatternID + ", productName=" + productName
				+ ", salesDiscount=" + salesDiscount + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProductInSalePattern))
			return false;
		ProductInSalePattern other = (ProductInSalePattern) obj;
		return this.productName.equals(other.productName) && this.salesPatternID == other.salesPatternID;
	}

}
