package client;

import entities.FastFuel;

/**
 * @version Basic
 * @author Lior
 */
public class FastFuelController extends ClientController {

	private static FastFuelController instance;

	/**
	 * singleton class constructor
	 */
	private FastFuelController() {
		super();
	}

	/**
	 * @return instance of this class
	 */
	public static FastFuelController getInstance() {
		if (instance == null) {
			instance = new FastFuelController();
		}
		return instance;
	}

	@Override
	public void handleMessageFromClientUI(String message) {
		String[] splitMsg = message.split(" ");
		if (splitMsg[0].equals("getdiscount")) {
			FastFuel fastFuel = new FastFuel(splitMsg[1]);
		}
	}

}
