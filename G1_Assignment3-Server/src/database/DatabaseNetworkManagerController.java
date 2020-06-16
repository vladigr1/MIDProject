package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entities.MyNetManager;

/**
 * @version Basic
 * @author Lior
 *
 */
public class DatabaseNetworkManagerController {

	private static DatabaseNetworkManagerController instance;
	private Connection connection;

	/**
	 * singleton class constructor
	 */
	private DatabaseNetworkManagerController(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * @return instance of this class
	 */
	public static DatabaseNetworkManagerController getInstance(Connection connection) {
		if (instance == null) {
			instance = new DatabaseNetworkManagerController(connection);
		}
		return instance;
	}

	public MyNetManager getAllFuelStationIDs(MyNetManager netManager) {
		try {
			PreparedStatement pStmt = this.connection.prepareStatement("SELECT fuelStationID FROM fuel_station");
			ResultSet rs1 = pStmt.executeQuery();
			if (!rs1.next()) {
				netManager.setParams(null);
				return netManager;
			}

			ArrayList<Integer> fuelStationIDs = new ArrayList<>();
			do {
				fuelStationIDs.add(rs1.getInt(1));
			} while (rs1.next());
			rs1.close();

			netManager.setParams(new Object[] { fuelStationIDs });
			return netManager;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
