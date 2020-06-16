package client;

import java.io.IOException;
import java.net.ConnectException;

/**
 * This Class is connection between the supplier gui and the server request and
 * acknowledge <br />
 * Sending message to serve the message of supplier always starts with
 * fuel_station_order. <br />
 * this is the way designed to recognize the requested approach
 * 
 * @version Final
 * @author Vlad
 * @see guiClient.SupplierWindow
 */
public class SupplierController extends UserController {

	private static SupplierController instance;

	/**
	 * singleton class constructor
	 */
	private SupplierController() {
		super();
	}

	/**
	 * @return instance of this class
	 */
	public static SupplierController getInstance() {
		if (instance == null) {
			instance = new SupplierController();
		}
		return instance;
	}

	@Override
	public void handleMessageFromClientUI(String message) {
		super.handleMessageFromClientUI(message);
	}

	public void getFuelStationWithOrder(String username) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer("fuel_station_order_getfs " + username);

			/* wait for ack or data from the server */
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			this.currentWindow.callAfterMessage(this.lastMsgFromServer);

		} catch (ConnectException ce) {
			this.currentWindow.openErrorAlert("Server Error", "Error - No connection to server");
			ce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void getSupplierItemInTable(int fuelStationIDs) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer("fuel_station_order_getsiit " + fuelStationIDs);

			/* wait for ack or data from the server */
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			this.currentWindow.callAfterMessage(this.lastMsgFromServer);

		} catch (ConnectException ce) {
			this.currentWindow.openErrorAlert("Server Error", "Error - No connection to server");
			ce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void approveFuelStationOrder(int ordersID, double amount) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer("fuel_station_order_approve " + ordersID + " " + amount);

			/* wait for ack or data from the server */
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
			this.currentWindow.callAfterMessage(this.lastMsgFromServer);

		} catch (ConnectException ce) {
			this.currentWindow.openErrorAlert("Server Error", "Error - No connection to server");
			ce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
