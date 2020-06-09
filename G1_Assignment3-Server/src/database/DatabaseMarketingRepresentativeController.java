package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entities.Car;
import entities.CarList;
import entities.Customer;
import entities.User;
import enums.CustomerType;
import enums.ProductName;

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

			CarList carList = getCustomerCars(customerID);
			if (carList == null) {
				System.out.println("deleteCustomer carList == null true");
				return false;
			}
			for (Car car : carList.getCars()) {
				this.deleteCar(car.getRegistrationPlate());
			}

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
					"UPDATE customer SET FK_username = ?, creditCard = ?, customerType = ?, deleted = 0 WHERE customerID = ?");
			pStmt.setString(1, user.getUsername());
			pStmt.setString(2, customer.getCreditCard());
			pStmt.setString(3, customer.getCustomerType().toString());
			pStmt.setString(4, customerID);
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

	/**
	 * 
	 * @param car
	 * @return string of success or fail
	 */
	public String saveNewCarSequence(Car car) {
		try {
			String regPlate = car.getRegistrationPlate();
			int exists = checkCarExists(regPlate);
			if (exists == 0) {
				return "save car exist";

			} else if (checkCarExists(regPlate) == null) {
				return "save car fail";

			} else if (exists == 1) { // deleted car
				if (updateCar(car).equals("update car success"))
					return "save car success";
				else
					return "save car fail";
			}

			// "registrationPlate", "FK_customerID", "FK_productName", "ownerName",
			// "deleted"
			Object[] values1 = { regPlate, car.getCustomerID(), car.getProductName().toString(), car.getOwnerName(),
					false };
			TableInserts.insertCar(connection, values1);

			return "save car success";

		} catch (SQLException e) {
			e.printStackTrace();
			return "save car fail";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "save car fail";
		}
	}

	/**
	 * 
	 * @param car
	 * @return string of success or fail
	 */
	public String updateCar(Car car) {
		try {
			String regPlate = car.getRegistrationPlate();
			int exists = checkCarExists(regPlate);
			if (exists != 0 && exists != 1)
				return "update car fail";

			PreparedStatement pStmt = this.connection.prepareStatement(
					"UPDATE car SET FK_customerID = ?, FK_productName = ?, ownerName = ?, deleted = 0 WHERE registrationPlate = ?");
			pStmt.setString(1, car.getCustomerID());
			pStmt.setString(2, car.getProductName().toString());
			pStmt.setString(3, car.getOwnerName());
			pStmt.setString(4, regPlate);
			pStmt.executeUpdate();

			return "update car success";

		} catch (SQLException e) {
			e.printStackTrace();
			return "update car fail";
		} catch (Exception ex) {
			ex.printStackTrace();
			return "update car fail";
		}
	}

	/**
	 * check if regPlate exists, 0 if exists, 1 if deleted, 2 if doesnt exist
	 * 
	 * @param regPlate
	 */
	private Integer checkCarExists(String regPlate) {
		try {
			PreparedStatement pStmt = this.connection
					.prepareStatement("SELECT deleted FROM car WHERE registrationPlate = ?");
			pStmt.setString(1, regPlate);
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
	 * 
	 * @param customerID
	 * @return list of cars of customer
	 */
	public CarList getCustomerCars(String customerID) {
		try {
			int exists = checkCustomerExists(customerID);
			if (exists != 0) {
				System.out.println("getCustomerCars exists!=0");
				return null;
			}

			CarList carList = new CarList();

			PreparedStatement pStmt = this.connection.prepareStatement(
					"SELECT registrationPlate, FK_productName, ownerName FROM car WHERE FK_customerID = ? AND deleted = 0");
			pStmt.setString(1, customerID);
			ResultSet rs2 = pStmt.executeQuery();

			if (!rs2.next()) {
				System.out.println("getCustomerCars nocars");
				return carList;
			}

			do {
				String registrationPlate = rs2.getString(1);
				String productName = rs2.getString(2).replaceAll("\\s", "");
				ProductName fuelType = ProductName.valueOf(productName);
				String ownerName = rs2.getString(3);

				Car car = new Car(registrationPlate, customerID, fuelType, ownerName);
				carList.add(car);

			} while (rs2.next());
			rs2.close();

			return carList;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param regPlate
	 * @return string of success or fail
	 */
	public Boolean deleteCar(String regPlate) {
		try {
			int exists = checkCarExists(regPlate);
			if (exists != 0) {
				System.out.println("deleteCar exists!=0");
				return false;
			}

			PreparedStatement pStmt = this.connection
					.prepareStatement("UPDATE car SET deleted = 1 WHERE registrationPlate = ?");
			pStmt.setString(1, regPlate);
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

}
