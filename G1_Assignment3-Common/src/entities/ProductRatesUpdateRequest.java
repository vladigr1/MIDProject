package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProductRatesUpdateRequest implements Serializable {

	// primary keys
	private Integer updateRateRequestID;

	// fields
	private boolean assessed;
	private Boolean approved; // can be NULL

	public ProductRatesUpdateRequest(boolean assessed) {
		super();
		this.assessed = assessed;
	}

	public ProductRatesUpdateRequest(boolean assessed, boolean approved) {
		super();
		this.assessed = assessed;
		this.approved = approved;
	}

	public ProductRatesUpdateRequest(int updateRateRequestID, boolean assessed) {
		super();
		this.updateRateRequestID = updateRateRequestID;
		this.assessed = assessed;
	}

	public ProductRatesUpdateRequest(int updateRateRequestID, boolean assessed, boolean approved) {
		super();
		this.updateRateRequestID = updateRateRequestID;
		this.assessed = assessed;
		this.approved = approved;
	}

	public int getUpdateRateRequestID() {
		return updateRateRequestID;
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

	@Override
	public String toString() {
		String str = "ProductRatesUpdateRequest [";
		if (updateRateRequestID != null)
			str += "updateRateRequestID=" + updateRateRequestID + ", ";
		str += ", assessed=" + assessed;
		if (approved != null)
			str += ", approved=" + approved;
		return str + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ProductRatesUpdateRequest))
			return false;
		ProductRatesUpdateRequest other = (ProductRatesUpdateRequest) obj;
		if (this.updateRateRequestID == null || other.updateRateRequestID == null)
			return false;
		return this.updateRateRequestID.equals(other.updateRateRequestID);
	}

}
