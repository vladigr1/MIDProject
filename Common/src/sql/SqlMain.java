package sql;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlMain {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/myfuel?serverTimezone=Asia/Jerusalem", "root",
					Pass.pass);
			// Connection con =
			// DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
			System.out.println("SQL connection succeed\n");

			// -------------------------------------------------------------------------
			new GenerateTables(con);
			new InsertDefaultTables(con);
			// ----------------------------------------------------------------------------
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
