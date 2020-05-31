package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Notification implements Serializable {

	// primary keys
	private Integer notificationID;

	// foreign keys
	private int employeeID;

	// fields
	private String message;
	private boolean dismissed;

	public Notification(int employeeID, String message, boolean dismissed) {
		super();
		this.employeeID = employeeID;
		this.message = message;
		this.dismissed = dismissed;
	}

	public Notification(int notificationID, int employeeID, String message, boolean dismissed) {
		super();
		this.notificationID = notificationID;
		this.employeeID = employeeID;
		this.message = message;
		this.dismissed = dismissed;
	}

	public Integer getNotificationID() {
		return notificationID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDismissed() {
		return dismissed;
	}

	public void setDismissed(boolean dismissed) {
		this.dismissed = dismissed;
	}

	@Override
	public String toString() {
		String str = "Notification [";
		if (notificationID != null)
			str += "notificationID=" + notificationID + ", ";
		str += "employeeID=" + employeeID + ", message=" + message + ", dismissed=" + dismissed + "]";
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Notification))
			return false;
		Notification other = (Notification) obj;
		if (this.notificationID == null || other.notificationID == null)
			return false;
		return this.notificationID.equals(other.notificationID);
	}

}