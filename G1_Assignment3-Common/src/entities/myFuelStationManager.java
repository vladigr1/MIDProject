package entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class myFuelStationManager implements Serializable {
	private String func;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public myFuelStationManager() {
		super();
	}

	public String getFunction() {
		return func;
	}

	public void setFunction(String func) {
		this.func = func;
	}

}
