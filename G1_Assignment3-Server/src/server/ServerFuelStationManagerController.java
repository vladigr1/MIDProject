package server;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import database.DatabaseController;
import entities.ActivityList;
import entities.FuelStationManager;
import entities.User;
import entities.myFuelStationManager;
import guiServer.ServerWindow;
import ocsf.server.ConnectionToClient;

public class ServerFuelStationManagerController {

	private static ServerFuelStationManagerController instance;
	private Object lock;
	private ServerWindow serverWindow;
	private DatabaseController databaseController;

	/**
	 * singleton class constructor
	 */
	private ServerFuelStationManagerController(ServerWindow serverWindow, DatabaseController databaseController,
			Object lock) {
		super();
		this.lock = lock;
		this.serverWindow = serverWindow;
		this.databaseController = databaseController;
	}

	/**
	 * @return instance of this class
	 */
	public static ServerFuelStationManagerController getInstance(ServerWindow serverWindow,
			DatabaseController databaseController, Object lock) {
		if (instance == null) {
			instance = new ServerFuelStationManagerController(serverWindow, databaseController, lock);
		}
		return instance;
	}

	/**
	 * handles client request and sends it to the database controller sends result
	 * got from database controller back to the client
	 * 
	 * @param user   entity or string
	 * @param client
	 */

	///////////////////////////////////// functions////////////////////////////////////////////////////////////
	/**
	 * handles client request and sends it to the database controller sends result
	 * got from database controller back to the client
	 * 
	 * @param user
	 * @param client
	 */
	public void handleMessageFromClient(Object object, ConnectionToClient client) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date = new Date();
			Object result = null;
			if (object instanceof myFuelStationManager) {
				myFuelStationManager fuelStationManager = (myFuelStationManager) object;
				String func = fuelStationManager.getFunction();
				///// my functions////
				// if(func.startWith()...)

				if (func.startsWith("get unassesedOrdersID")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction());
						this.lock.notifyAll();
					}
					result = this.databaseController.getUnassesdOrderIDbyUsername(fuelStationManager.getUserName());
				}

				else if (func.startsWith("get StationProductInOrder")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction() + " of order No. " + fuelStationManager.getParams());
						this.lock.notifyAll();
					}
					int orderID = Integer.parseInt(fuelStationManager.getParams());
					result = this.databaseController.getStationProductInOrderbyOrderID(orderID);
				}

				else if (func.startsWith("get ProductThreshold")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction());
						this.lock.notifyAll();
					}
					result = this.databaseController
							.getStationProductThresholdOrderbyUsername(fuelStationManager.getUserName());
				}

				else if (func.startsWith("update Threshold")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction() + "of: " + fuelStationManager.getParams());
						this.lock.notifyAll();
					}
					result = this.databaseController.updateProductInStationThresholdbyUsername(
							fuelStationManager.getUserName(), fuelStationManager.getParams());// (username,params)
				}

				else if (func.startsWith("update doneAssessmentOrder")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction() + " of order No. " + fuelStationManager.getParams());
						this.lock.notifyAll();
					}
					result = this.databaseController.updateOrderDoneAssesmentbyOrderID(fuelStationManager.getUserName(),
							fuelStationManager.getParams());
				}

//				else if (func.startsWith("get existQuarterlyReportYears")) {
//					synchronized (this.lock) {
//						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
//								+ fuelStationManager.getFunction() + " of user: " + fuelStationManager.getUserName());
//						this.lock.notifyAll();
//					}
//					result = this.databaseController
//							.getExistQuarterlyReportYearsbyUsername(fuelStationManager.getUserName());
//
//				}
				else if (func.startsWith("generate QuarterReport")) {
					synchronized (this.lock) {
						this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : request "
								+ fuelStationManager.getFunction() + " of user: " + fuelStationManager.getUserName());
						this.lock.notifyAll();
					}
					result = this.databaseController.getQuarterlyReportDataByUsernameYearQuarter(
							fuelStationManager.getUserName(), fuelStationManager.getParams());
				}

				synchronized (this.lock) {
					this.serverWindow.updateArea(formatter.format(date) + " : " + client + " : " + result);
					this.lock.notifyAll();
				}
			}
			client.sendToClient(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
