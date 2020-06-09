package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Customer;
import entities.User;
import enums.CustomerType;

/**
 * controller for marketing representative
 * 
 * @version Basic
 * @author Lior
 */
public class DatabaseMarketingRepresentativeController {

	private static DatabaseMarketingRepresentativeController instance;
	private Connection connection;

	/**
	 * singleton class constructor
	 */
	private DatabaseMarketingRepresentativeController(Connection connection) {
		super();
		this.connection = connection;
	}

	/**
	 * @return instance of this class
	 */
	public static DatabaseMarketingRepresentativeController getInstance(Connection connection) {
		if (instance == null) {
			instance = new DatabaseMarketingRepresentativeController(connection);
		}
		return instance;
	}

	/**
	 * check if customerID exists, 0 if exists, 1 if deleted, 2 if doesnt exist
	 * 
	 * @param customerID
	 */
	public Integer checkCustomerExists(String customerID) {
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT deleted FROM customer WHERE customerID = ?");
			pStmt.setString(1, customerID);
			ResultSet rs1 = pStmt.executeQuery();

			if (!rs1.next())
				return 2;
			int deleted = rs1.getInt(1);
			rs1.close();

			if (deleted == 1)
				return 1;

			return 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * delete customer and its username from db
	 * 
	 * @param customerID
	 * @return false if failed
	 */
	public boolean deleteCustomer(String customerID) {
		try {
			int exists = checkCustomerExists(customerID);
			if (exists != 0) {
				System.out.println("deleteCustomer exists!=0");
				return false;
			}

			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT FK_username FROM customer WHERE customerID = ?");
			pStmt.setString(1, customerID);
			ResultSet rs1 = pStmt.executeQuery();

			if (!rs1.next()) {
				System.out.println("deleteCustomer !rs1.next()");
				return false;
			}
			String username = rs1.getString(1);
			rs1.close();

			pStmt = this.connection.prepareStatement("UPDATE customer SET deleted = 1 WHERE customerID = ?");
			pStmt.setString(1, customerID);
			pStmt.executeUpdate();

			pStmt = this.connection.prepareStatement("DELETE FROM user WHERE username = ?");
			pStmt.setString(1, username);
			pStmt.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param user
	 * @param customer
	 * @return string of success or fail
	 */
	public String saveNewCustomerSequence(User user, Customer customer) {
		try {
			String customerID = customer.getCustomerID();
			int exists = checkCustomerExists(customerID);
			if (exists == 0) {
				return "save customer exist";

			} else if (checkCustomerExists(customerID) == null) {
				return "save customer fail";

			} else if (exists == 1) { // deleted customer
				if (updateCustomer(user, customer).equals("update customer success"))
					return "save customer success";
				else
					return "save customer fail";
			}

			// "username", "password", "connected", "email", "firstName", "surname"
			Object[] values1 = { user.getUsername(), "1234", false, user.getEmail(), user.getFirstName(),
					user.getSurname() };
			TableInserts.insertUser(connection, values1);

			// "customerID", "FK_userName", "creditCard", "customerType", "deleted"
			Object[] values2 = { customerID, user.getUsername(), customer.getCreditCard(),
					customer.getCustomerType().toString(), false };
			TableInserts.insertCustomer(connection, values2);

			return "save customer success";

		} catch (SQLException e) {
			e.printStackTrace();
			return "save customer fail";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "save customer fail";
		}
	}

	/**
	 * 
	 * @param user
	 * @param customer
	 * @return string of success or fail
	 */
	public String updateCustomer(User user, Customer customer) {
		try {
			String customerID = customer.getCustomerID();

			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT FK_username FROM customer WHERE customerID = ?");
			pStmt.setString(1, customerID);
			ResultSet rs1 = pStmt.executeQuery();
			if (!rs1.next())
				return "update customer fail";
			String username = rs1.getString(1);
			rs1.close();

			pStmt = this.connection.prepareStatement("SELECT * FROM user WHERE username = ?");
			pStmt.setString(1, username);
			ResultSet rs2 = pStmt.executeQuery();

			if (!rs2.next()) { // username doesn't exist
				// "username", "password", "connected", "email", "firstName", "surname"
				Object[] values1 = { user.getUsername(), "1234", false, user.getEmail(), user.getFirstName(),
						user.getSurname() };
				TableInserts.insertUser(connection, values1);

			} else { // username does exist

				pStmt = this.connection
						.prepareStatement("UPDATE user SET email = ?, firstName = ?, surname = ? WHERE username = ?");
				pStmt.setString(1, user.getEmail());
				pStmt.setString(2, user.getFirstName());
				pStmt.setString(3, user.getSurname());
				pStmt.setString(4, username);
				pStmt.executeUpdate();
			}
			rs2.close();

			pStmt = this.connection.prepareStatement(
					"UPDATE customer SET creditCard = ?, customerType = ?, deleted = 0 WHERE customerID = ?");
			pStmt.setString(1, customer.getCreditCard());
			pStmt.setString(2, customer.getCustomerType().toString());
			pStmt.setString(3, customerID);
			pStmt.executeUpdate();
			return "update customer success";

		} catch (SQLException e) {
			e.printStackTrace();
			return "update customer fail";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "update customer fail";
		}
	}

	public Object[] getCustomerDetails(String customerID) {
		try {
			int exists = checkCustomerExists(customerID);
			if (exists != 0) {
				System.out.println("exists != 0");
				return null;
			}

			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT creditCard, customerType, FK_username FROM customer WHERE customerID = ?");
			pStmt.setString(1, customerID);
			ResultSet rs1 = pStmt.executeQuery();
			if (!rs1.next()) {
				System.out.println("!rs1.next()");
				return null;
			}

			String creditCard = rs1.getString(1);
			CustomerType customerType = CustomerType.valueOf(rs1.getString(2));
			String username = rs1.getString(3);
			rs1.close();
			Customer customer = new Customer(customerID, creditCard, customerType);

			pStmt = this.connection.prepareStatement("SELECT firstName, surname, email FROM user WHERE username = ?");
			pStmt.setString(1, username);
			ResultSet rs2 = pStmt.executeQuery();
			if (!rs2.next()) {
				System.out.println("!rs2.next()");
				return null;
			}

			String firstName = rs2.getString(1);
			String surname = rs2.getString(2);
			String email = rs2.getString(3);
			rs2.close();
			User user = new User(username, email, firstName, surname);

			return new Object[] { user, customer };

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

}
