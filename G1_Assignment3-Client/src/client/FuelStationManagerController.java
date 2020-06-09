package client;

import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entities.FuelStationOrder;
import entities.ProductInStation;
import entities.myFuelStationManager;

public class FuelStationManagerController extends UserController {

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
        }
		else {
			try {
                System.out.println("message from clientUI Fuel Station manager : " + message);
                this.openConnection();
                awaitResponse = true;

                myFuelStationManager manager = new myFuelStationManager();
                String[] splitMsg=message.split(" ");
              manager.setUserName(splitMsg[2]);
                manager.setFunction(message);
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
	
	
	public void askToSaveMinimumStorageThreshold(ProductInStation productInStationToUpdate,ArrayList<Double> newThresholdLevels) {
		
	}
	
	public List<ProductInStation> getProductInStationsByFuelStationManagerID(int employeeID) {
		return null;
	}
	
	public void askForProductInOrderID(int orderID,String username){
		handleMessageFromClientUI("get StationProductInOrder "+username+" "+orderID);
	}
	
	public void askToSaveFuelStationOrderAssessment(int orderID,boolean assessment) {
		
	}
	
//	public void askEmployeeID(String username) {
//		handleMessageFromClientUI("get EmployeeID "+username);
//	}


	
	

}
