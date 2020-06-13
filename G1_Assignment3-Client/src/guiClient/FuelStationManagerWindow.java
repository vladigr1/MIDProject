package guiClient;

import javafx.stage.Window;
import javafx.util.Callback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;
import client.FuelStationManagerController;
import entities.Activity;
import entities.FuelStationOrder;
import entities.MyIncomeReport;
import entities.MyInventoryReport;
import entities.MyOutcomeReport;
import entities.ProductInStation;
import entities.QuarterlyReport;
import entities.UnassessedOrderIDList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class FuelStationManagerWindow extends EmployeeWindow {
	@FXML
	private ToggleButton sidebar_btn0;
	@FXML
	private ToggleGroup one;
	@FXML
	private ToggleButton sidebar_btn1;
	@FXML
	private ToggleButton sidebar_btn2;
	@FXML
	private ToggleButton sidebar_btn3;
	@FXML
	private AnchorPane thresholdPane;
	@FXML
	private CheckBox cbUTGasoline;
	@FXML
	private CheckBox cbUTDiesel;
	@FXML
	private CheckBox cbUTMotorbike;
	@FXML
	private TextField tfUTGasoline2;
	@FXML
	private TextField tfUTDiesel2;
	@FXML
	private TextField tfUTMotorbike2;
	@FXML
	private TextField tfUTGasoline1;
	@FXML
	private TextField tfUTMotorbike1;
	@FXML
	private TextField tfUTDiesel1;
	@FXML
	private Button btnUTUpdate;
	@FXML
	private AnchorPane assessPane;
	@FXML
	private Button btnASOConfirm;
	@FXML
	private Button btnASODecline;
	@FXML
	private ComboBox<Integer> cobASOOrderId;
	@FXML
	private TextField tfASOName;
	@FXML
	private TextField tfASOAmount;

	@FXML
	private TextField tfASOTime;

	@FXML
	private TextField tfASOAddress;

	@FXML
	private Button btnASOShowOrder;

	@FXML
	private TextField tfASOThreshold;

	@FXML
	private TextField tfASOInStock;

	@FXML
	private AnchorPane notificationPane;

	@FXML
	private Button btnNotifyClose;

	@FXML
	private TableView<?> tvNotifytable;

	@FXML
	private AnchorPane quarterlyReportPane;

	@FXML
	private ComboBox<String> cobGQRYear;

	@FXML
	private ComboBox<Integer> cobGQRQuarter;

	@FXML
	private Button btnGQRView;

	@FXML
	private AnchorPane declineOrderPane;

	@FXML
	private TextArea taDOResons;

	@FXML
	private Button btnDOOk;

	@FXML
	private Button btnDOCancel;

	@FXML
	private AnchorPane quarterReportPane;

	@FXML
	private Label lblQRDateCreated;

	@FXML
	private Label lblQRYear;

	@FXML
	private Label lblQRQuarter;

	@FXML
	private TableView<QuarterlyReport> tvQRDetails1;

	@FXML
	private TableView<QuarterlyReport> tvQRDetails2;

	@FXML
	private TableView<QuarterlyReport> tvQRDetails3;

	@FXML
	private Button btnQRClose;

	@FXML
	private Tab tabQRIncomeReport;

	@FXML
	private Tab tabQROutcomeReport;

	@FXML
	private Tab tabQRInventoryReport;

	@FXML
	private ImageView imgHomeNotify;
	@FXML
	private Button btnHomeNotification;
	@FXML
	private Label lblQRFuelStationID;
	@FXML
	private TextField tfQRTotalIncome;
	@FXML
	private TextField tfQRTotalOutcome;

	@FXML
	void initialize() {
		this.visibleNow = this.homePane;
		this.controller = FuelStationManagerController.getInstance();
		this.controller.setCurrentWindow(this);
		initiallizeQuarterReportTables();
		fillQuarterlyReportComboBox();
	}

	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		super.callAfterMessage(lastMsgFromServer);

		if (lastMsgFromServer instanceof Object[]) {
			Object[] objArr = (Object[]) lastMsgFromServer;
			if (objArr.length == 0) {
				System.out.println("callAfterMessage: object[] empty");
				return;
			} else if (objArr[0] instanceof String) {
				String str = (String) objArr[0];
				if (str.equals("Unassesd Order IDs")) {
					TreeSet<Integer> unassedOrderList = (TreeSet<Integer>) objArr[1];
					fillUnassessedOrderToComboBox(unassedOrderList);
				} else if (str.equals("Quarter Report")) {
					MyIncomeReport incomeReport = (MyIncomeReport) objArr[1];
					MyOutcomeReport outcomeReport = (MyOutcomeReport) objArr[2];
					MyInventoryReport inventoryReport = (MyInventoryReport) objArr[3];
					openQuarterReport(incomeReport, outcomeReport, inventoryReport);
				}
			}

			else if (objArr[0] instanceof ProductInStation && objArr[1] instanceof FuelStationOrder) {
				// תבדוק תקינות
				ProductInStation productInStation = (ProductInStation) objArr[0];
				FuelStationOrder fuelStationOrder = (FuelStationOrder) objArr[1];
				fillUnassessedOrderData(productInStation, fuelStationOrder);
			}
		}

		else if (lastMsgFromServer instanceof ArrayList<?>) {
			ArrayList<?> list = (ArrayList<?>) lastMsgFromServer;
			if (list.isEmpty()) {
				return;
			} else if (list.get(0) instanceof ProductInStation) {
				ArrayList<ProductInStation> productInStationList = (ArrayList<ProductInStation>) lastMsgFromServer;
				fillUpdateThreshold(productInStationList);
			}

		} else if (lastMsgFromServer instanceof Boolean) {
			if (visibleNow == thresholdPane) {
				requestToLogActivity("Updated Threshold");
				this.openCONFIRMATIONAlert("Success", "Threshold updated successfully");
				initializeThresholdPane();
			} else if (visibleNow == assessPane) {
				int orderID = cobASOOrderId.getValue();
				requestToLogActivity("Confirmed unassessed order No." + orderID);
				this.openCONFIRMATIONAlert("Success", "Order: " + orderID + " Approved, Assessment completed!");
				initializeAssessPane();
			} else if (visibleNow == declineOrderPane) {
				int orderID = cobASOOrderId.getValue();
				requestToLogActivity("Declined unassessed order No." + orderID);
				mainBorderPane.setDisable(false);
				declineOrderPane.setVisible(false);
				this.openCONFIRMATIONAlert("Success", "Order: " + orderID + " Declined, Assessment completed!");
				visibleNow = assessPane;
				initializeAssessPane();
			}
		}
	}

	@Override
	public Window getWindow() {
		return this.assessPane.getScene().getWindow();
	}

	@Override
	void openHome(ActionEvent event) {
		super.openHome(event);
		sidebar_btn0.setSelected(true);
	}

	@FXML
	void openNotification(ActionEvent event) {
		mainBorderPane.setDisable(true);
		notificationPane.setVisible(true);
		visibleNow = notificationPane;
		btnNotifyClose.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				mainBorderPane.setDisable(false);
				notificationPane.setVisible(false);
				visibleNow = homePane;
			}
		});
	}

	@FXML
	void openUpdateThreshold(ActionEvent event) {
		initializeThresholdPane();
		visibleNow.setVisible(false);
		thresholdPane.setVisible(true);
		visibleNow = thresholdPane;
		topbar_window_label.setText("Update Threshold");

		// click on check boxes
		cbUTGasoline.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean cbChecked = cbUTGasoline.isSelected();
				checkOtherCheckBoxes();
				if (!cbChecked)
					tfUTGasoline2.clear();
				tfUTGasoline2.setDisable(!cbChecked);
			}
		});
		cbUTDiesel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean cbChecked = cbUTDiesel.isSelected();
				checkOtherCheckBoxes();
				if (!cbChecked)
					tfUTDiesel2.clear();
				tfUTDiesel2.setDisable(!cbChecked);
			}
		});
		cbUTMotorbike.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean cbChecked = cbUTMotorbike.isSelected();
				checkOtherCheckBoxes();
				if (!cbChecked)
					tfUTMotorbike2.clear();
				tfUTMotorbike2.setDisable(!cbChecked);
			}
		});
	}

	@FXML
	void openAssessStationOrders(ActionEvent event) {
		initializeAssessPane();
		visibleNow.setVisible(false);
		assessPane.setVisible(true);
		visibleNow = assessPane;
		topbar_window_label.setText("Assess Station Orders");

		// Decline order,secondary window pop up
		btnASODecline.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mainBorderPane.setDisable(true);
				declineOrderPane.setVisible(true);
				visibleNow = declineOrderPane;
				taDOResons.clear();
				btnDOCancel.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						declineOrderPane.setVisible(false);
						mainBorderPane.setDisable(false);
						visibleNow = assessPane;
					}
				});
				btnDOOk.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						int orderID = cobASOOrderId.getValue();
						String declineReson = taDOResons.getText();
						if (declineReson.isEmpty()) {
							openErrorAlert("Input incomplete", "Please write reson for decline");
							return;
						} else {
							String message = "update doneAssessmentOrder" + "_" + username + "_" + orderID + " "
									+ "false" + " " + declineReson;
							controller.handleMessageFromClientUI(message);
						}
					}
				});
			}
		});

		// Order approval,alert pop up
		btnASOConfirm.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("aprroved");
				int orderID = cobASOOrderId.getValue();
				String message = "update doneAssessmentOrder" + "_" + username + "_" + orderID + " " + "true" + " Nan";
				controller.handleMessageFromClientUI(message);
			}
		});

		btnASOShowOrder.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (cobASOOrderId.getItems().size() <= 0)
					return;
				else {
					btnASOConfirm.setDisable(false);
					btnASODecline.setDisable(false);
					int orderID = cobASOOrderId.getValue();
					String message = "get StationProductInOrder" + "_" + username + "_" + orderID;
					controller.handleMessageFromClientUI(message);
				}
			}
		});
	}

	@FXML
	void openGenerateQuarterlyReport(ActionEvent event) {
		visibleNow.setVisible(false);
		quarterlyReportPane.setVisible(true);
		visibleNow = quarterlyReportPane;
		topbar_window_label.setText("Generate Quarterly Report");
		sidebar_btn3.setSelected(true);
	}

	void openQuarterReport(MyIncomeReport incomeReport, MyOutcomeReport outcomeReport,
			MyInventoryReport inventoryReport) {
		mainBorderPane.setDisable(true);
		quarterReportPane.setVisible(true);
		visibleNow = quarterReportPane;
		String[] dateCreated = converDateToFormattedString(incomeReport.getDateCreated()).split(" ");
		lblQRDateCreated.setText(dateCreated[0]);
		lblQRYear.setText(incomeReport.getRepYear());
		lblQRQuarter.setText(incomeReport.getRepQuarter() + "");
		lblQRFuelStationID.setText(incomeReport.getFuelStationID() + "");

		btnQRClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				visibleNow.setVisible(false);
				mainBorderPane.setDisable(false);
				visibleNow = quarterlyReportPane;
			}
		});
		// clear tables
		// fill tables with data
	}

	private void fillQuarterlyReportComboBox() {
		this.cobGQRYear.getItems().addAll(new String[] { "2019", "2020" });
		this.cobGQRQuarter.getItems().addAll(new Integer[] { 1, 2, 3, 4 });
		this.cobGQRYear.setValue("2020");
		this.cobGQRQuarter.setValue(1);
	}

	private void fillUnassessedOrderToComboBox(TreeSet<Integer> list) {
		this.cobASOOrderId.getItems().removeAll((Collection<?>) this.cobASOOrderId.getItems());
		if (list.isEmpty())
			return;
		this.cobASOOrderId.getItems().addAll(list);
		this.cobASOOrderId.setValue(list.pollFirst());
	}

	private void fillUnassessedOrderData(ProductInStation productInStation, FuelStationOrder fuelStationOrder) {
		tfASOName.setText(productInStation.getProductName().toString());
		tfASOThreshold.setText(String.valueOf(productInStation.getThreshold()));
		tfASOInStock.setText(String.valueOf(productInStation.getCapacity()));
		tfASOAmount.setText(String.valueOf(fuelStationOrder.getAmountBought()));
		tfASOAddress.setText(fuelStationOrder.getAddress());
		tfASOTime.setText(converDateToFormattedString(fuelStationOrder.getOrderTime()));
	}

	private void fillUpdateThreshold(ArrayList<ProductInStation> productList) {
		for (ProductInStation product : productList) {
			if (product.getProductName().toString().equals("Gasoline"))
				tfUTGasoline1.setText(product.getThreshold() + "");
			else if (product.getProductName().toString().equals("Diesel"))
				tfUTDiesel1.setText(product.getThreshold() + "");
			else if (product.getProductName().toString().equals("Motorbike Fuel"))
				tfUTMotorbike1.setText(product.getThreshold() + "");
		}
	}

	private void initializeThresholdPane() {
		sidebar_btn1.setSelected(true);
		tfUTGasoline1.clear();
		tfUTDiesel1.clear();
		tfUTMotorbike1.clear();

		tfUTGasoline2.clear();
		tfUTMotorbike2.clear();
		tfUTDiesel2.clear();
		tfUTGasoline2.setDisable(true);
		tfUTMotorbike2.setDisable(true);
		tfUTDiesel2.setDisable(true);

		cbUTGasoline.setSelected(false);
		cbUTDiesel.setSelected(false);
		cbUTMotorbike.setSelected(false);
		btnUTUpdate.setDisable(true);
		String message = "get ProductThreshold" + "_" + this.username;
		controller.handleMessageFromClientUI(message);
	}

	private void initializeAssessPane() {
		sidebar_btn2.setSelected(true);
		btnASOConfirm.setDisable(true);
		btnASODecline.setDisable(true);
		tfASOName.clear();
		tfASOAmount.clear();
		tfASOTime.clear();
		tfASOAddress.clear();
		tfASOThreshold.clear();
		tfASOInStock.clear();
		String message = "get unassesedOrdersID" + "_" + this.username;
		controller.handleMessageFromClientUI(message);
	}

	@SuppressWarnings("unchecked")
	private void initiallizeQuarterReportTables() {
		final TableColumn<QuarterlyReport, Integer> productIDColumn = (TableColumn<QuarterlyReport, Integer>) new TableColumn(
				"Product ID");
		productIDColumn.setCellValueFactory((Callback) new PropertyValueFactory("productID"));
		productIDColumn.setPrefWidth(80);
		this.tvQRDetails1.getColumns().add(productIDColumn);
		this.tvQRDetails2.getColumns().add(productIDColumn);
		this.tvQRDetails3.getColumns().add(productIDColumn);

		final TableColumn<QuarterlyReport, String> productNameColumn = (TableColumn<QuarterlyReport, String>) new TableColumn(
				"Product Name");
		productNameColumn.setCellValueFactory((Callback) new PropertyValueFactory("productName"));
		productNameColumn.setPrefWidth(150);
		this.tvQRDetails1.getColumns().add(productNameColumn);
		this.tvQRDetails2.getColumns().add(productNameColumn);
		this.tvQRDetails3.getColumns().add(productNameColumn);

		final TableColumn<QuarterlyReport, Double> incomePerProductColumn = (TableColumn<QuarterlyReport, Double>) new TableColumn(
				"Product Income");
		productNameColumn.setCellValueFactory((Callback) new PropertyValueFactory("income"));
		incomePerProductColumn.setPrefWidth(120);
		this.tvQRDetails1.getColumns().add(incomePerProductColumn);

		final TableColumn<QuarterlyReport, Double> outcomePerProductColumn = (TableColumn<QuarterlyReport, Double>) new TableColumn(
				"Product Outcome");
		productNameColumn.setCellValueFactory((Callback) new PropertyValueFactory("outcome"));
		outcomePerProductColumn.setPrefWidth(120);
		this.tvQRDetails2.getColumns().add(outcomePerProductColumn);

		final TableColumn<QuarterlyReport, Double> amountSoldColumn = (TableColumn<QuarterlyReport, Double>) new TableColumn(
				"Amount Sold");
		productNameColumn.setCellValueFactory((Callback) new PropertyValueFactory("amountSold"));
		amountSoldColumn.setPrefWidth(115);
		this.tvQRDetails3.getColumns().add(amountSoldColumn);

	}

	@FXML
	private void updateThresholdData(ActionEvent event) {
		Double newGasoline, newDiesel, newMotorbike;
		String func = "";
		try {
			if (!tfUTGasoline2.isDisable()) {
				newGasoline = Double.parseDouble(tfUTGasoline2.getText());
				if (newGasoline < 0)
					throw new java.lang.NumberFormatException();
				func += "Gasoline " + newGasoline + " ";
			}
			if (!tfUTDiesel2.isDisable()) {
				newDiesel = Double.parseDouble(tfUTDiesel2.getText());
				if (newDiesel < 0)
					throw new java.lang.NumberFormatException();
				func += "Diesel " + newDiesel + " ";

			}
			if (!tfUTMotorbike2.isDisable()) {
				newMotorbike = Double.parseDouble(tfUTMotorbike2.getText());
				if (newMotorbike < 0)
					throw new java.lang.NumberFormatException();
				func += "Motorbike-Fuel " + newMotorbike + " ";

			}
		} catch (java.lang.NumberFormatException e) {
			this.openErrorAlert("Worng input", "Please enter only positive number.");
		}
		if (func.isEmpty())
			return;
		String message = "update Threshold" + "_" + this.username + "_" + func;
		controller.handleMessageFromClientUI(message);
	}

	private void checkOtherCheckBoxes() {// linked to threshold
		if (!cbUTMotorbike.isSelected() && !cbUTDiesel.isSelected() && !cbUTGasoline.isSelected())
			btnUTUpdate.setDisable(true);
		else
			btnUTUpdate.setDisable(false);
	}

	@FXML
	void viewQuarterlyReport(ActionEvent event) {
		int currYear = Calendar.getInstance().get(Calendar.YEAR);
		int currMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int selectedYear = Integer.parseInt(cobGQRYear.getValue());
		int selectedQuarter = cobGQRQuarter.getValue();
		if (selectedYear == currYear) {

			if (selectedQuarter == 1 && currMonth < 3) {
				openErrorAlert("Wrong Input",
						"Year: " + selectedYear + "\nQuarter: " + selectedQuarter + "\nNot exsit yet!");
				return;
			} else if (selectedQuarter == 2 && currMonth < 6) {
				openErrorAlert("Wrong Input",
						"Year: " + selectedYear + "\nQuarter: " + selectedQuarter + "\nNot exsit yet!");
				return;
			} else if (selectedQuarter == 3 && currMonth < 9) {
				openErrorAlert("Wrong Input",
						"Year: " + selectedYear + "\nQuarter: " + selectedQuarter + "\nNot exsit yet!");
				return;
			}

			else if (selectedQuarter == 4 && currMonth < 12) {
				openErrorAlert("Wrong Input",
						"Year: " + selectedYear + "\nQuarter: " + selectedQuarter + "\nNot exsit yet!");
				return;
			}
		}
		String message = "generate QuarterReport" + "_" + this.username + "_" + selectedYear + " " + selectedQuarter;
		controller.handleMessageFromClientUI(message);

	}
	
	public String converDateToFormattedString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm");
		String dateFormatted = dateFormat.format(date);
		return dateFormatted;
	}

	@Override
	public void clearFields() {
		// לא יודע
	}

}
