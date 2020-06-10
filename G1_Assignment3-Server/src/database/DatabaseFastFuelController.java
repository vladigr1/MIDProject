package database;

import java.sql.Connection;

/**
 * controller for fast fuel
 * 
 * @version Basic
 * @author Lior
 */
public class DatabaseFastFuelController {

	private static DatabaseFastFuelController instance;
	private Connection connection;

	/**
	 * singleton class constructor
	 */
	private DatabaseFastFuelController(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * @return instance of this class
	 */
	public static DatabaseFastFuelController getInstance(Connection connection) {
		if (instance == null) {
			instance = new DatabaseFastFuelController(connection);
		}
		return instance;
	}
	
}
