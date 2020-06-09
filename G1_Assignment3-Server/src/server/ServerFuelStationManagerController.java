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
				myFuelStationManager fuelStationManager=(myFuelStationManager)object;
				String func=fuelStationManager.getFunction();
				/////my functions////
				//if(func.startWith()...)
				if(func.startsWith("get unassesedOrderID ")) {
					String[] str=func.split(" ");
					
					synchronized (this.lock) {
                        this.serverWindow.updateArea(formatter.format(date) + " : " + client
                                + " : request get unassesed order ID of: "+str[2]);
                        this.lock.notifyAll();
                    }

                    result = this.databaseController.getUnassesdOrderID(str[2]);
				}
				
				if(func.startsWith("get StationProductInOrder ")) {
					String[] str=func.split(" ");
					
					synchronized (this.lock) {
                        this.serverWindow.updateArea(formatter.format(date) + " : " + client
                                + " : request get stationProduct in order No. "+str[3]);
                        this.lock.notifyAll();
                    }
					int orderID=Integer.parseInt(str[3]);
                    result = this.databaseController.getStationProductInOrderbyOrderID(orderID);
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
