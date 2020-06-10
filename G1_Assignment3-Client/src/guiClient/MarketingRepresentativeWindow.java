package guiClient;

import java.util.ArrayList;
import java.util.Collection;

import client.MarketingRepresentativeController;
import entities.Car;
import entities.CarList;
import entities.Customer;
import entities.User;
import enums.ProductName;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 * boundary for marketing representative window
 * 
 * @version Basic
 * @author Elroy, Lior
 */
public class MarketingRepresentativeWindow extends MarketingDepWorkerWindow {

	@FXML
	private ToggleGroup one;
	@FXML
	private ToggleButton sidebar_btn0;
	@FXML
	private ToggleButton sidebar_btn1;
	@FXML
	private ToggleButton sidebar_btn2;
	@FXML
	private ToggleButton sidebar_btn3;
	@FXML
	private ToggleButton sidebar_btn4;
	@FXML
	private ToggleButton sidebar_btn5;

	@FXML
	private AnchorPane addEditCustomerPane;
	@FXML
	private Label step1;
	@FXML
	private TextField tfAECUCredit;
	@FXML
	private TextField tfAECUCustID;
	@FXML
	private TextField tfAECUFirstName;
	@FXML
	private TextField tfAECUSurname;
	@FXML
	private TextField tfAECUEmail;
	@FXML
	private ComboBox<String> cobAECUCustType;
	@FXML
	private Button btnAECUSave;
	@FXML
	private Button btnAECUEdit;

	@FXML
	private AnchorPane editCustomerPane;
	@FXML
	private GridPane gpECUCustomer;
	@FXML
	private AnchorPane apECUCustomer;
	@FXML
	private TextField tfACUCustID;
	@FXML
	private Button btnECUUpdate;
	@FXML
	private Button btnECUDelete;
	@FXML
	private Button btnECUClose;
	@FXML
	private TextField tfECUFirstName;
	@FXML
	private TextField tfECUSurname;
	@FXML
	private TextField tfECUEmail;
	@FXML
	private TextField tfECUCredit;
	@FXML
	private ComboBox<String> cobECUCustType;
	@FXML
	private Button btnECUShow;
	@FXML
	private Button btnECUClear;

	@FXML
	private AnchorPane addEditCarPane;
	@FXML
	private Label step2;
	@FXML
	private AnchorPane apAECACarDetails;
	@FXML
	private GridPane gpAECACarDetails;
	@FXML
	private TextField tfAECARegistration;
	@FXML
	private TextField tfAECAOwner;
	@FXML
	private Button btnAECASave;
	@FXML
	private TextField tfAECACustID;
	@FXML
	private Button btnAECACheck;
	@FXML
	private ComboBox<String> cobAECAFuelType;
	@FXML
	private Button btnAECAEdit;
	@FXML
	private Button btnAECAClear;

	@FXML
	private AnchorPane editCarPane;
	@FXML
	private AnchorPane apECACar;
	@FXML
	private AnchorPane apECACustomer;
	@FXML
	private TableView<Car> tvECACar;
	@FXML
	private TextField tfECACustID;
	@FXML
	private TextField tfECARegistration;
	@FXML
	private TextField tfECAOwner;
	@FXML
	private ComboBox<String> cobECAFuelType;
	@FXML
	private Button btnECAUpdate;
	@FXML
	private Button btnECADelete;
	@FXML
	private Button btnECAClose;
	@FXML
	private Button btnECAClear;
	@FXML
	private Button btnECAShow;

	@FXML
	private AnchorPane setPurchasingPane;
	@FXML
	private GridPane gpSPP;
	@FXML
	private AnchorPane apSPP;
	@FXML
	private Label step3;
	@FXML
	private VBox vbSPPMagicbox2;
	@FXML
	private ScrollPane purchProg_ExpenProgBox_SP;
	@FXML
	private TextArea taSPPExpensiveDetails;
	@FXML
	private ComboBox<String> cobSPPFuelCompany1;
	@FXML
	private ComboBox<String> cobSPPFuelCompany2;
	@FXML
	private ComboBox<String> cobSPPFuelCompany3;
	@FXML
	private Button btnSPPSave;
	@FXML
	private VBox vbSPPMagicbox1;
	@FXML
	private ScrollPane purchProg_ExpenProgBox_SP1;
	@FXML
	private TextArea taSPPSingleDetails;
	@FXML
	private ToggleGroup two;
	@FXML
	private RadioButton rbSPPStandard;
	@FXML
	private RadioButton rbSPPPremium;
	@FXML
	private Label lblSPPChooseCompany;
	@FXML
	private TextField tfSPPCustID;
	@FXML
	private Button btnSPPCheck;
	@FXML
	private Button btnSPPClear;

	@FXML
	private AnchorPane pricingModelPane;
	@FXML
	private Button btnSPMSet;
	@FXML
	private TextField tfSPMCustID;
	@FXML
	private Button btnSPMCheck;
	@FXML
	private Label lblSPMPriceModel1;
	@FXML
	private Text txSPMModel1Details;
	@FXML
	private ToggleButton btnSPMChoose1;
	@FXML
	private ToggleButton btnSPMChoose2;
	@FXML
	private ToggleButton btnSPMChoose3;
	@FXML
	private ToggleButton btnSPMChoose4;
	@FXML
	private ToggleGroup three;
	@FXML
	private Label lblSPMModel1Discount;
	@FXML
	private Label lblSPMPriceModel2;
	@FXML
	private Text txSPMModel2Details;
	@FXML
	private Label lblSPMModel2Discount;
	@FXML
	private Label lblSPMPriceModel3;
	@FXML
	private Text txSPMModel3Details;
	@FXML
	private Label lblSPMModel3Discount;
	@FXML
	private Label lblSPMPriceModel4;
	@FXML
	private Text txSPMModel4Details;
	@FXML
	private Label lblSPMModel4Discount;
	@FXML
	private Button btnSPMClear;

	@FXML
	void initialize() {
		this.homePane.setVisible(true);
		this.addEditCustomerPane.setVisible(false);
		this.addEditCarPane.setVisible(false);
		this.setPurchasingPane.setVisible(false);
		this.pricingModelPane.setVisible(false);
		this.createSalePatternPane.setVisible(false);
		this.editCustomerPane.setVisible(false);
		this.editCarPane.setVisible(false);
		this.visibleNow = this.homePane;
		this.controller = MarketingRepresentativeController.getInstance();
		this.controller.setCurrentWindow(this);
	}

	@Override
	public Window getWindow() {
		return this.addEditCustomerPane.getScene().getWindow();
	}

	/*********************** button listeners ***********************/

	@FXML
	void openAddEditCustomer(ActionEvent event) {
		this.visibleNow.setVisible(false);
		this.addEditCustomerPane.setVisible(true);
		this.visibleNow = this.addEditCustomerPane;
		this.topbar_window_label.setText("Add\\Edit Customer");
		clearFields();
	}

	@FXML
	void btnAECUSavePressed(ActionEvent event) {
		String customerID = this.tfAECUCustID.getText();
		String firstName = this.tfAECUFirstName.getText();
		String surname = this.tfAECUSurname.getText();
		String email = this.tfAECUEmail.getText();
		String creditCard = this.tfAECUCredit.getText();
		String customerType = this.cobAECUCustType.getValue();

		if (customerID.isEmpty() || firstName.isEmpty() || surname.isEmpty() || email.isEmpty()
				|| creditCard.isEmpty()) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (customerID.matches(".*[A-z].*") || firstName.matches(".*[0-9].*") || surname.matches(".*[0-9].*")
				|| creditCard.matches(".*[A-z].*") || creditCard.length() != 16 || customerID.length() != 9) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}

		this.controller.handleMessageFromClientUI("savecustomer " + customerID + " " + firstName + " " + surname + " "
				+ email + " " + creditCard + " " + customerType);
	}

	@FXML
	void btnAECUEditPressed(ActionEvent event) {
		mainBorderPane.setDisable(true);
		editCustomerPane.setVisible(true);
	}

	@FXML
	void btnECUShowPressed(ActionEvent event) {
		String customerID = this.tfACUCustID.getText();
		if (customerID.isEmpty() || customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}

		checkCustomerExists(customerID);
		this.controller.handleMessageFromClientUI("getcustomerdetails " + customerID);
	}

	@FXML
	void btnECUClearPressed(ActionEvent event) {
		clearEditCustomerPane();
	}

	@FXML
	void btnECUClosePressed(ActionEvent event) {
		this.mainBorderPane.setDisable(false);
		this.editCustomerPane.setVisible(false);
		clearEditCustomerPane();
	}

	@FXML
	void btnECUDeletePressed(ActionEvent event) {
		String customerID = this.tfACUCustID.getText();
		if (customerID.isEmpty() || customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		this.controller.handleMessageFromClientUI("deletecustomer " + customerID);
	}

	@FXML
	void btnECUUpdatePressed(ActionEvent event) {
		String customerID = this.tfACUCustID.getText();
		String firstName = this.tfECUFirstName.getText();
		String surname = this.tfECUSurname.getText();
		String email = this.tfECUEmail.getText();
		String creditCard = this.tfECUCredit.getText();
		String customerType = this.cobECUCustType.getValue();

		if (customerID.isEmpty() || firstName.isEmpty() || surname.isEmpty() || email.isEmpty()
				|| creditCard.isEmpty()) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (customerID.matches(".*[A-z].*") || firstName.matches(".*[0-9].*") || surname.matches(".*[0-9].*")
				|| creditCard.matches(".*[A-z].*") || creditCard.length() != 16 || customerID.length() != 9) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}

		this.controller.handleMessageFromClientUI("updatecustomer " + customerID + " " + firstName + " " + surname + " "
				+ email + " " + creditCard + " " + customerType);
	}

	@FXML
	void openAddEditCar(ActionEvent event) {
		this.visibleNow.setVisible(false);
		this.addEditCarPane.setVisible(true);
		this.visibleNow = this.addEditCarPane;
		this.topbar_window_label.setText("Add\\Edit Car");
		clearFields();
	}

	@FXML
	void btnAECACheckPressed(ActionEvent event) {
		String customerID = this.tfAECACustID.getText();
		if (customerID.isEmpty() || customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		checkCustomerExists(customerID);
	}

	@FXML
	void btnAECAClearPressed(ActionEvent event) {
		clearAddEditCarPane();
	}

	@FXML
	void btnAECASavePressed(ActionEvent event) {
		String customerID = this.tfAECACustID.getText();
		String regPlate = this.tfAECARegistration.getText();
		String owner = this.tfAECAOwner.getText();
		String fuelType = this.cobAECAFuelType.getValue().replaceAll("\\s", "");

		if (customerID.isEmpty() || regPlate.isEmpty() || owner.isEmpty() || fuelType.isEmpty()) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (customerID.length() != 9 || customerID.matches(".*[A-z].*") || owner.matches(".*[0-9].*")
				|| regPlate.matches(".*[A-z].*") || (regPlate.length() != 7 && regPlate.length() != 8)) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		this.controller
				.handleMessageFromClientUI("savecar " + customerID + " " + regPlate + " " + owner + " " + fuelType);
	}

	@FXML
	void btnAECAEditPressed(ActionEvent event) {
		this.mainBorderPane.setDisable(true);
		this.editCarPane.setVisible(true);
	}

	@FXML
	void btnECAClearPressed(ActionEvent event) {
		clearEditCarPane();
	}

	@FXML
	void btnECAClosePressed(ActionEvent event) {
		this.mainBorderPane.setDisable(false);
		this.editCarPane.setVisible(false);
		clearEditCarPane();
	}

	@FXML
	void btnECAShowPressed(ActionEvent event) {
		String customerID = this.tfECACustID.getText();
		if (customerID.isEmpty() || customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		checkCustomerExists(customerID);
	}

	@FXML
	void btnECADeletePressed(ActionEvent event) {
		String regPlate = this.tfECARegistration.getText();
		if (regPlate.isEmpty() || regPlate.matches(".*[A-z].*") || (regPlate.length() != 7 && regPlate.length() != 8)) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		this.controller.handleMessageFromClientUI("deletecar " + regPlate);
	}

	@FXML
	void btnECAUpdatePressed(ActionEvent event) {
		String customerID = this.tfECACustID.getText();
		String regPlate = this.tfECARegistration.getText();
		String owner = this.tfECAOwner.getText();
		String fuelType = this.cobECAFuelType.getValue().replaceAll("\\s", "");

		if (customerID.isEmpty() || regPlate.isEmpty() || owner.isEmpty() || fuelType.isEmpty()) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (customerID.matches(".*[A-z].*") || owner.matches(".*[0-9].*") || regPlate.matches(".*[A-z].*")
				|| (regPlate.length() != 7 && regPlate.length() != 8)) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		this.controller
				.handleMessageFromClientUI("updatecar " + customerID + " " + regPlate + " " + owner + " " + fuelType);
	}

	@FXML
	void openSetPurchasingProgram(ActionEvent event) {
		this.visibleNow.setVisible(false);
		this.setPurchasingPane.setVisible(true);
		this.visibleNow = this.setPurchasingPane;
		this.topbar_window_label.setText("Set Purchasing Program");
		clearFields();
	}

	@FXML
	void btnSPPClearPressed(ActionEvent event) {
		clearSetPurchasingPane();
	}

	@FXML
	void rbSPPStandardPressed(ActionEvent event) {
		this.purchProg_ExpenProgBox_SP1.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
		this.purchProg_ExpenProgBox_SP.setStyle("-fx-border-style: none;");
		this.cobSPPFuelCompany2.setDisable(true);
		this.cobSPPFuelCompany3.setDisable(true);
		this.cobSPPFuelCompany2.setValue("");
		this.cobSPPFuelCompany3.setValue("");
	}

	@FXML
	void rbSPPPremiumPressed(ActionEvent event) {
		this.purchProg_ExpenProgBox_SP.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
		this.purchProg_ExpenProgBox_SP1.setStyle("-fx-border-style: none;");
		this.cobSPPFuelCompany2.setDisable(false);
		this.cobSPPFuelCompany3.setDisable(false);
		this.cobSPPFuelCompany2.setValue("Paz");
	}

	@FXML
	void btnSPPCheckPressed(ActionEvent event) {
		String customerID = this.tfSPPCustID.getText();
		if (customerID.isEmpty() || customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		checkCustomerExists(customerID);
	}

	@FXML
	void btnSPPSavePressed(ActionEvent event) {
		String customerID = this.tfSPPCustID.getText();
		String program = null;
		if (rbSPPStandard.isSelected())
			program = "Standard";
		else
			program = "Premium";

		String company1 = this.cobSPPFuelCompany1.getValue();
		String company2 = "NaN";
		String company3 = "NaN";
		if (!this.cobSPPFuelCompany2.getValue().equals(""))
			company2 = this.cobSPPFuelCompany2.getValue();
		if (!this.cobSPPFuelCompany3.getValue().equals(""))
			company3 = this.cobSPPFuelCompany3.getValue();

		if (customerID.isEmpty() || program == null) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (customerID.length() != 9 || customerID.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}
		if (company1.equals(company2) || company1.equals(company3)
				|| (!company2.equals("NaN") && company2.equals(company3))) {
			openErrorAlert("Error", "Duplicate Fuel Companies");
			return;
		}

		this.controller.handleMessageFromClientUI(
				"setprogram " + customerID + " " + program + " " + company1 + " " + company2 + " " + company3);
	}

	/*********************************************************************************/

	@FXML
	void openSetPricingModel(ActionEvent event) {
		this.visibleNow.setVisible(false);
		this.pricingModelPane.setVisible(true);
		this.visibleNow = this.pricingModelPane;
		this.topbar_window_label.setText("Set Pricing Model");
		clearFields();
	}

	/*************** boundary "logic" - window changes ***************/

	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		super.callAfterMessage(lastMsgFromServer);

		if (lastMsgFromServer instanceof String) {
			String str = (String) lastMsgFromServer;
			if (str.equals("save car success")) {
				openErrorAlert("Success", "Car Saved");
				clearFields();

			} else if (str.equals("save car fail")) {
				openErrorAlert("Error", "Add Car Failed");

			} else if (str.equals("save car exist")) {
				openErrorAlert("Error", "Car Already Exists");

			} else if (str.equals("set purchasing program success")) {
				openErrorAlert("Success", "Purchasing Program Set Successfully");
				clearSetPurchasingPane();

			} else if (str.equals("set purchasing program fail")) {
				openErrorAlert("Error", "Purchasing Program Set Failed");

			} else if (str.equals("save customer success")) {
				openErrorAlert("Success",
						"Customer Saved\nUsername: " + this.tfAECUCustID.getText() + "\nPassword: 1234");
				clearFields();

			} else if (str.equals("save customer fail")) {
				openErrorAlert("Error", "Add Customer Failed");

			} else if (str.equals("save customer exist")) {
				openErrorAlert("Error", "Customer Already Exists");

			} else if (str.equals("update customer success")) {
				openErrorAlert("Success", "Customer Updated");
				clearEditCustomerPane();

			} else if (str.equals("update customer fail")) {
				openErrorAlert("Success", "Customer Update Failed");

			} else if (str.equals("update car success")) {
				openErrorAlert("Success", "Car Updated");
				clearEditCarPane();

			} else if (str.equals("update car fail")) {
				openErrorAlert("Success", "Car Update Failed");

			} else if (str.startsWith("Customer Delete")) {
				openErrorAlert("Delete", str);
				if (str.equals("Customer Deleted"))
					clearEditCustomerPane();

			} else if (str.startsWith("Car Delete")) {
				openErrorAlert("Delete", str);
				if (str.equals("Car Deleted"))
					clearEditCarPane();

			} else if (str.startsWith("Customer Check")) {
				openErrorAlert("Check", str);
				if (str.contains("Exists")) {
					if (this.visibleNow == this.addEditCarPane && editCarPane.isVisible() == false) {
						this.gpAECACarDetails.setDisable(true);
						this.apAECACarDetails.setDisable(false);

					} else if (this.visibleNow == this.addEditCarPane && editCarPane.isVisible() == true) {
						this.controller.handleMessageFromClientUI("getcustomercars " + this.tfECACustID.getText());

					} else if (this.visibleNow == this.setPurchasingPane) {
						this.gpSPP.setDisable(true);
						this.apSPP.setDisable(false);
						this.purchProg_ExpenProgBox_SP1.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
						this.purchProg_ExpenProgBox_SP.setStyle("-fx-border-style: none;");
						this.cobSPPFuelCompany2.setDisable(true);
						this.cobSPPFuelCompany3.setDisable(true);
					}
				}
			}

		} else if (lastMsgFromServer instanceof Object[]) {
			Object[] objArr = (Object[]) lastMsgFromServer;
			if (objArr.length == 2 && objArr[0] instanceof User && objArr[1] instanceof Customer) {
				User user = (User) objArr[0];
				Customer customer = (Customer) objArr[1];
				this.tfECUFirstName.setText(user.getFirstName());
				this.tfECUSurname.setText(user.getSurname());
				this.tfECUEmail.setText(user.getEmail());
				this.tfECUCredit.setText(customer.getCreditCard());
				this.cobECUCustType.setValue(customer.getCustomerType().toString());
				this.gpECUCustomer.setDisable(true);
				this.apECUCustomer.setDisable(false);
			}

		} else if (lastMsgFromServer instanceof CarList) {
			System.out.println("got car list from server");
			this.apECACustomer.setDisable(true);
			this.apECACar.setDisable(false);
			final ObservableList<Car> list = FXCollections.observableArrayList();
			for (int i = 0; i < this.tvECACar.getItems().size(); ++i)
				this.tvECACar.getItems().clear();
			ArrayList<Car> cars = ((CarList) lastMsgFromServer).getCars();
			for (Car car : cars)
				list.add(car);
			this.tvECACar.setItems(list);
		}
	}

	/**
	 * initialized tableview in home of marketing rep only
	 * 
	 * @param username
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
	@Override
	public void setUserComponents(String username) {
		super.setUserComponents(username);

		this.cobAECUCustType.getItems().removeAll((Collection<?>) this.cobAECUCustType.getItems());
		this.cobAECUCustType.getItems().addAll(new String[] { "Person", "Company" });
		this.cobAECUCustType.setValue("Person");

		this.cobECUCustType.getItems().removeAll((Collection<?>) this.cobECUCustType.getItems());
		this.cobECUCustType.getItems().addAll(new String[] { "Person", "Company" });
		this.cobECUCustType.setValue("Person");

		this.cobAECAFuelType.getItems().removeAll((Collection<?>) this.cobAECAFuelType.getItems());
		this.cobAECAFuelType.getItems().addAll(new String[] { "Gasoline", "Diesel", "Motorbike Fuel" });
		this.cobAECAFuelType.setValue("Gasoline");

		this.cobECAFuelType.getItems().removeAll((Collection<?>) this.cobAECAFuelType.getItems());
		this.cobECAFuelType.getItems().addAll(new String[] { "Gasoline", "Diesel", "Motorbike Fuel" });
		this.cobECAFuelType.setValue("Gasoline");
		final TableColumn<Car, String> regPlateColumn = (TableColumn<Car, String>) new TableColumn(
				"Registration Plate");
		regPlateColumn.setCellValueFactory((Callback) new PropertyValueFactory("registrationPlate"));
		regPlateColumn.impl_setWidth(160);
		this.tvECACar.getColumns().add(regPlateColumn);
		final TableColumn<Car, String> ownerColumn = (TableColumn<Car, String>) new TableColumn("Owner Name");
		ownerColumn.setCellValueFactory((Callback) new PropertyValueFactory("ownerName"));
		ownerColumn.impl_setWidth(180);
		this.tvECACar.getColumns().add(ownerColumn);
		final TableColumn<Car, ProductName> productColumn = (TableColumn<Car, ProductName>) new TableColumn(
				"Fuel Type");
		productColumn.setCellValueFactory((Callback) new PropertyValueFactory("productName"));
		productColumn.impl_setWidth(160);
		this.tvECACar.getColumns().add(productColumn);
		tvECACar.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				tvECACarPressed();
			}
		});

		this.cobSPPFuelCompany1.getItems().removeAll((Collection<?>) this.cobSPPFuelCompany1.getItems());
		this.cobSPPFuelCompany1.getItems().addAll(new String[] { "Sonol", "Paz", "Delek" });
		this.cobSPPFuelCompany1.setValue("Sonol");
		this.cobSPPFuelCompany2.getItems().removeAll((Collection<?>) this.cobSPPFuelCompany2.getItems());
		this.cobSPPFuelCompany2.getItems().addAll(new String[] { "Sonol", "Paz", "Delek" });
		this.cobSPPFuelCompany2.setValue("");
		this.cobSPPFuelCompany3.getItems().removeAll((Collection<?>) this.cobSPPFuelCompany3.getItems());
		this.cobSPPFuelCompany3.getItems().addAll(new String[] { "", "Sonol", "Paz", "Delek" });
		this.cobSPPFuelCompany3.setValue("");
		this.taSPPSingleDetails.setText("Fast fueling in fuel stations of only 1 fuel company\n\n10 dollars per month");
		this.taSPPExpensiveDetails
				.setText("Fast fueling in fuel stations of 2-3 fuel companies\n\n20 dollars per month");

		this.step2.setVisible(false);
		this.step3.setVisible(false);
	}

	@Override
	public void clearFields() {
		this.tfAECUCredit.clear();
		this.tfAECUCustID.clear();
		this.tfAECUFirstName.clear();
		this.tfAECUSurname.clear();
		this.tfAECUEmail.clear();
		this.cobAECUCustType.setValue("Person");
		clearEditCustomerPane();
		clearAddEditCarPane();
		clearEditCarPane();
		clearSetPurchasingPane();
		this.step2.setVisible(false);
		this.step3.setVisible(false);
	}

	private void clearEditCustomerPane() {
		this.tfACUCustID.clear();
		this.tfECUFirstName.clear();
		this.tfECUSurname.clear();
		this.tfECUEmail.clear();
		this.tfECUCredit.clear();
		this.cobECUCustType.setValue("Person");
		this.gpECUCustomer.setDisable(false);
		this.apECUCustomer.setDisable(true);
	}

	private void clearAddEditCarPane() {
		this.tfAECACustID.clear();
		this.tfAECARegistration.clear();
		this.tfAECAOwner.clear();
		this.cobAECAFuelType.setValue("Gasoline");
		this.step2.setVisible(false);
		this.gpAECACarDetails.setDisable(false);
		this.apAECACarDetails.setDisable(true);
	}

	private void clearEditCarPane() {
		this.tfECACustID.clear();
		this.tfECARegistration.clear();
		this.tfECAOwner.clear();
		this.cobECAFuelType.setValue("Gasoline");
		for (int i = 0; i < this.tvECACar.getItems().size(); ++i)
			this.tvECACar.getItems().clear();
		this.apECACustomer.setDisable(false);
		this.apECACar.setDisable(true);
	}

	private void clearSetPurchasingPane() {
		this.tfSPPCustID.clear();
		this.rbSPPStandard.setSelected(true);
		this.purchProg_ExpenProgBox_SP1.setStyle("-fx-border-color: green ; -fx-border-width: 2px ;");
		this.purchProg_ExpenProgBox_SP.setStyle("-fx-border-style: none;");
		this.cobSPPFuelCompany1.setValue("Sonol");
		this.cobSPPFuelCompany2.setValue("");
		this.cobSPPFuelCompany3.setValue("");
		this.step3.setVisible(false);
		this.gpSPP.setDisable(false);
		this.apSPP.setDisable(true);
	}

	private void checkCustomerExists(String customerID) {
		this.controller.handleMessageFromClientUI("checkcustomer " + customerID);
	}

	private void tvECACarPressed() {
		Car car = this.tvECACar.getSelectionModel().getSelectedItem();
		this.tfECARegistration.setText(car.getRegistrationPlate());
		this.tfECAOwner.setText(car.getOwnerName());
		this.cobECAFuelType.setValue(car.getProductName().toString());
	}

}
