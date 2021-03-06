package entities;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Elroy, Vlad, Lior
 */
@SuppressWarnings("serial")
public class Sale implements Serializable {

	// primary keys
	private Integer saleID;

	// foreign keys
	private int salesPatternID;

	// fields
	private Date startTime;
	private Date endTime;

	/**
	 * w/o saleID auto-inc
	 * 
	 * @param salesPatternID
	 * @param startTime
	 * @param endTime
	 */
	public Sale(int salesPatternID, Date startTime, Date endTime) {
		super();
		this.salesPatternID = salesPatternID;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * 
	 * @param saleID
	 * @param salesPatternID
	 * @param startTime
	 * @param endTime
	 */
	public Sale(int saleID, int salesPatternID, Date startTime, Date endTime) {
		super();
		this.saleID = saleID;
		this.salesPatternID = salesPatternID;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public Integer getSaleID() {
		return saleID;
	}

	public int getSalesPatternID() {
		return salesPatternID;
	}

	public void setSalesPatternID(int salesPatternID) {
		this.salesPatternID = salesPatternID;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		String str = "Sale [";
		if (saleID != null)
			str += "saleID=" + saleID + ", ";
		str += "salesPatternID=" + salesPatternID + ", startTime=" + startTime + ", endTime=" + endTime + "]";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Sale))
			return false;
		Sale other = (Sale) obj;
		if (this.saleID == null || other.saleID == null)
			return false;
		return this.saleID.equals(other.saleID);
	}

}
