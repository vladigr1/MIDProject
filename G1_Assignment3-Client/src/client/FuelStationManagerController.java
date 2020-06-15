package client;

import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;
import entities.myFuelStationManager;

public class FuelStationManagerController extends UserController {

	/**
	 * @author LiadVax 
	 */
	
	private static FuelStationManagerController instance;
	private boolean logged = false;

	/**
	 * singleton class constructor
	 */
	private FuelStationManagerController() {
		super();
	}

	/**
	 * @return instance of this class
	 */
	public static FuelStationManagerController getInstance() {
		if (instance == null) {
			instance = new FuelStationManagerController();
		}
		return instance;
	}

	@Override
	public void handleMessageFromClientUI(String message) {
		if (logged == false || message.startsWith("signout") || message.startsWith("activity")) {
			if (message.startsWith("signout"))
				logged = false;
			else
				logged = true;
			super.handleMessageFromClientUI(message);
		} else {
			try {
				System.out.println("message from clientUI, [Fuel Station manager] : " + message);
				this.openConnection();
				awaitResponse = true;

				myFuelStationManager manager = new myFuelStationManager();
				String[] splitMsg = message.split("_");//message: <command> <mathodName>_<username>_<params>{param1 param2 ...}
				manager.setFunction(splitMsg[0]);
				manager.setUserName(splitMsg[1]);
				if(splitMsg.length>2)
					manager.setParams(splitMsg[2]);
				this.sendToServer(manager);
				while (awaitResponse) {
					try {
						Thread.sleep(100);
						System.out.println("Fuel station manager is waiting");
					} catch (InterruptedException ie) {
						ie.printStackTrace();
					}
				}

			} catch (ConnectException ce) {
				this.currentWindow.openErrorAlert("Server Error", "Error - No connection to server");
				ce.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
			this.currentWindow.callAfterMessage(this.lastMsgFromServer);
		}

	}

	/**
	 * 
	 */






}
