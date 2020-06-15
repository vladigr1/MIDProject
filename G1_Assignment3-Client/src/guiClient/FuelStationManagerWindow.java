package guiClient;

import javafx.stage.Window;
import javafx.util.Callback;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import client.FuelStationManagerController;
import entities.FuelStationOrder;
import entities.MyIncomeReport;
import entities.MyInventoryReport;
import entities.MyOutcomeReport;
import entities.Notification;
import entities.ProductInStation;
import entities.QuarterlyReport;
import entities.RowForQuarterlyReports;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * @author LiadVax
 */
public class FuelStationManagerWindow extends EmployeeWindow {
	@FXML
	private ToggleButton sidebar_btn0;
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
	private TableView<Notification> tvHomeNotifytable;
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
	private TableView<RowForQuarterlyReports> tvQRDetails1;
	@FXML
	private TableView<RowForQuarterlyReports> tvQRDetails2;
	@FXML
	private TableView<RowForQuarterlyReports> tvQRDetails3;
	@FXML
	private Button btnQRClose;
	@FXML
	private Button btnHomeNotification;
	@FXML
	private TextField tfQRTotalIncome;
	@FXML
	private TextField tfQRTotalAmountSold;
	@FXML
	private TextField tfQRTotalAmountBought;
	@FXML
	private TextField tflQRDateCreated;
	@FXML
	private TextField tflQRQuarter;
	@FXML
	private TextField tflQRYear;
	@FXML
	private TextField tflQRFuelStationID;
	@FXML
	private Button btnNotifyDissmiss;

	@SuppressWarnings("unchecked")
	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		super.callAfterMessage(lastMsgFromServer);

		if (lastMsgFromServer instanceof Object[]) {
			Object[] objArr = (Object[]) lastMsgFromServer;
			if (objArr.length == 0) {
				System.out.println("callAfterMessage: object[] empty");
			} else if (objArr[0] instanceof String) {
				String str = (String) objArr[0];
				if (str.equals("Unassesd Order IDs")) {
					TreeSet<Integer> unassedOrderList = (TreeSet<Integer>) objArr[1];
					fillUnassessedOrderToComboBox(unassedOrderList);
				} else if (str.equals("No Unassessd Orders")) {
					this.cobASOOrderId.getItems().removeAll((Collection<?>) this.cobASOOrderId.getItems());
					this.openCONFIRMATIONAlert("Information",
							"There are no orders waiting for assessment in your station.");
				} else if (str.equals("Quarter Report")) {
					openQuarterlyReport2(objArr);
					requestToLogActivity("Genarated generated quarter report Year: " + cobGQRYear.getValue()
							+ " Quarter: " + cobGQRQuarter.getValue() + ".");
				} else if (str.equals("Undismissed Notifications")) {
					ArrayList<Notification> notificationsList = (ArrayList<Notification>) objArr[1];
					fillNotificationsTable(notificationsList);
				} else if (str.equals("No Undismissed Notifications")) {
					for (int i = 0; i < tvHomeNotifytable.getItems().size(); i++) {
						tvHomeNotifytable.getItems().clear();
					}
					this.openCONFIRMATIONAlert("Information", "There are no new notifications.");
				} else if (str.equals("Station Products In Orders")) {
					ProductInStation productInStation = (ProductInStation) objArr[1];
					FuelStationOrder fuelStationOrder = (FuelStationOrder) objArr[2];
					fillUnassessedOrderData(productInStation, fuelStationOrder);
				} else if (str.equals("products In Station")) {
					ArrayList<ProductInStation> productInStationList = (ArrayList<ProductInStation>) objArr[1];
					fillUpdateThreshold(productInStationList);
				} else if (str.equals("No Product In Station")) {
					this.openCONFIRMATIONAlert("Information", "There is no products in your station.");
				}
			}
		}

		else if (lastMsgFromServer instanceof Boolean) {
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
			} else if (visibleNow == notificationPane) {
				requestToLogActivity("Notification Dismissed");
				this.openCONFIRMATIONAlert("Success", "Notification Dismissed");
				getNotifications();
			}
		}
	}

	@Override
	public Window getWindow() {
		return this.assessPane.getScene().getWindow();
	}

	/**********************************************************************
	 * open methods
	 **********************************************************************/

	/**
	 * click on side button Home
	 * 
	 * @param event
	 */
	@Override
	void openHome(ActionEvent event) {
		super.openHome(event);
		sidebar_btn0.setSelected(true);
	}

	/**
	 * click notifications in Home
	 * 
	 * @param event
	 */
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
		getNotifications();
	}

	/**
	 * click on side button Update Threshold
	 * 
	 * @param event
	 */
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

	/**
	 * click on side button Assess Station Orders
	 * 
	 * @param event
	 */
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
							openErrorAlert("Input incomplete",
									"Please write the reason you wish to decline your order.");
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

	/**
	 * click on side button Generate Quarterly Report
	 * 
	 * @param event
	 */
	@FXML
	void openGenerateQuarterlyReport(ActionEvent event) {
		visibleNow.setVisible(false);
		quarterlyReportPane.setVisible(true);
		visibleNow = quarterlyReportPane;
		topbar_window_label.setText("Generate Quarterly Report");
		sidebar_btn3.setSelected(true);
	}

	/**
	 * click on View button in Generate Quarterly Report
	 * 
	 * @param event
	 */
	@FXML
	void openQuarterlyReport(ActionEvent event) { // click on view report
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

	/**
	 * continue of openQuarterlyReport after handleMessageFromClientUI
	 * 
	 * @param event
	 */
	void openQuarterlyReport2(Object[] reports) {
		// reports[0] have string
		MyIncomeReport incomeReport = (MyIncomeReport) reports[1];
		MyOutcomeReport outcomeReport = (MyOutcomeReport) reports[2];
		MyInventoryReport inventoryReport = (MyInventoryReport) reports[3];
		fillQuarterReport(incomeReport, outcomeReport, inventoryReport);
		mainBorderPane.setDisable(true);
		quarterReportPane.setVisible(true);
		visibleNow = quarterReportPane;

		btnQRClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				visibleNow.setVisible(false);
				mainBorderPane.setDisable(false);
				visibleNow = quarterlyReportPane;
			}
		});
	}

	/***************************************************
	 * fill data methods
	 ****************************************************/

	/**
	 * fill Generate Quarterly Report comobo boxes
	 */
	private void fillQuarterlyReportComboBox() {
		this.cobGQRYear.getItems().addAll(new String[] { "2019", "2020" });
		this.cobGQRQuarter.getItems().addAll(new Integer[] { 1, 2, 3, 4 });
		this.cobGQRYear.setValue("2020");
		this.cobGQRQuarter.setValue(1);
	}

	/**
	 * fill Assess Station Orders combo box with orderID
	 * 
	 * @param list
	 */
	private void fillUnassessedOrderToComboBox(TreeSet<Integer> list) {
		this.cobASOOrderId.getItems().removeAll((Collection<?>) this.cobASOOrderId.getItems());
		this.cobASOOrderId.getItems().addAll(list);
		this.cobASOOrderId.setValue(list.pollFirst());
	}

	/**
	 * when click on View Order button fill Assess Station Orders textfields with
	 * orderID data
	 * 
	 * @param list
	 */
	private void fillUnassessedOrderData(ProductInStation productInStation, FuelStationOrder fuelStationOrder) {
		tfASOName.setText(productInStation.getProductName().toString());
		tfASOThreshold.setText(String.valueOf(productInStation.getThreshold()));
		tfASOInStock.setText(String.valueOf(productInStation.getCapacity()));
		tfASOAmount.setText(String.valueOf(fuelStationOrder.getAmountBought()));
		tfASOAddress.setText(fuelStationOrder.getAddress());
		tfASOTime.setText(converDateToFormattedString(fuelStationOrder.getOrderTime()));
	}

	/**
	 * fill UpdateThreshold textfields with threshold data
	 * 
	 * @param list
	 */
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

	/**
	 * fill Notification tableview with notifications data
	 * 
	 * @param list
	 */
	private void fillNotificationsTable(ArrayList<Notification> list) {
		for (int i = 0; i < tvHomeNotifytable.getItems().size(); i++) {
			tvHomeNotifytable.getItems().clear();
		}
		ObservableList<Notification> rowsList = FXCollections.observableArrayList();
		for (Notification notif : list) {
			rowsList.add(notif);
		}
		tvHomeNotifytable.setItems(rowsList);
	}

	/**
	 * fill QuarterReport tableviews with reports data
	 * 
	 * @param list
	 */
	private void fillQuarterReport(MyIncomeReport incomeReport, MyOutcomeReport outcomeReport,
			MyInventoryReport inventoryReport) {
		String[] dateCreated = converDateToFormattedString(incomeReport.getDateCreated()).split(" ");
		tflQRDateCreated.setText(dateCreated[0]);
		tflQRYear.setText(incomeReport.getRepYear());
		tflQRQuarter.setText(incomeReport.getRepQuarter() + "");
		tflQRFuelStationID.setText(incomeReport.getFuelStationID() + "");
		for (int i = 0; i < tvQRDetails1.getItems().size(); i++) {
			tvQRDetails1.getItems().clear();
		}
		for (int i = 0; i < tvQRDetails2.getItems().size(); i++) {
			tvQRDetails2.getItems().clear();
		}
		for (int i = 0; i < tvQRDetails3.getItems().size(); i++) {
			tvQRDetails3.getItems().clear();
		}
		ObservableList<RowForQuarterlyReports> rowsList = FXCollections.observableArrayList();
		for (Entry<ProductInStation, Double> entry : incomeReport.getIncomePerProduct().entrySet()) {
			ProductInStation p = entry.getKey();
			int pID = p.getProductInStationID();
			String pName = p.getProductName().toString();
			Double incomePer = entry.getValue();
			RowForQuarterlyReports row = new RowForQuarterlyReports(pID, pName, incomePer);
			rowsList.add(row);

		}
		tvQRDetails1.setItems(rowsList);

		ObservableList<RowForQuarterlyReports> rowsList2 = FXCollections.observableArrayList();
		for (Entry<ProductInStation, Double> entry : outcomeReport.getAmountBoughtPerProduct().entrySet()) {
			ProductInStation p = entry.getKey();
			int pID = p.getProductInStationID();
			String pName = p.getProductName().toString();
			Double outputPer = entry.getValue();
			RowForQuarterlyReports row = new RowForQuarterlyReports(pID, pName, outputPer);
			rowsList2.add(row);
		}
		tvQRDetails2.setItems(rowsList2);

		ObservableList<RowForQuarterlyReports> rowsList3 = FXCollections.observableArrayList();
		for (Entry<ProductInStation, Double> entry : inventoryReport.getAmountsPerProduct().entrySet()) {
			ProductInStation p = entry.getKey();
			int pID = p.getProductInStationID();
			String pName = p.getProductName().toString();
			Double invPer = entry.getValue();
			RowForQuarterlyReports row = new RowForQuarterlyReports(pID, pName, invPer);
			rowsList3.add(row);
		}
		tvQRDetails3.setItems(rowsList3);

		tvQRDetails1.refresh();
		tvQRDetails2.refresh();
		tvQRDetails3.refresh();
		tfQRTotalIncome.setText(incomeReport.getTotalIncome() + "");
		tfQRTotalAmountBought.setText(outcomeReport.getTotalAmountBoughtFromSupplier() + "");
		tfQRTotalAmountSold.setText(inventoryReport.getTotalAmountSold() + "");
	}

	/**********************************************************************
	 * initialize methods
	 **********************************************************************/
	@FXML
	void initialize() {
		this.visibleNow = this.homePane;
		this.controller = FuelStationManagerController.getInstance();
		this.controller.setCurrentWindow(this);
		initiallizeNotificationTable();
		initiallizeIncomeReportTables();
		initiallizeOutcomeReportTables();
		initiallizeInventoryReportTables();
		fillQuarterlyReportComboBox();
		sidebar_btn0.setSelected(true);
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

	/**
	 * Initialize table view columns
	 */
	@SuppressWarnings("unchecked")
	private void initiallizeIncomeReportTables() {
		tvQRDetails1.getColumns().clear();
		TableColumn<RowForQuarterlyReports, Integer> productIDColumn = new TableColumn<RowForQuarterlyReports, Integer>(
				"Product ID");
		productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
		productIDColumn.setPrefWidth(120);
		this.tvQRDetails1.getColumns().add(productIDColumn);
		TableColumn<RowForQuarterlyReports, String> productNameColumn = new TableColumn<RowForQuarterlyReports, String>(
				"Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productNameColumn.setPrefWidth(250);
		this.tvQRDetails1.getColumns().add(productNameColumn);
		TableColumn<RowForQuarterlyReports, Double> incomePerProductColumn = new TableColumn<RowForQuarterlyReports, Double>(
				"Product Income");
		incomePerProductColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		incomePerProductColumn.setPrefWidth(200);
		this.tvQRDetails1.getColumns().add(incomePerProductColumn);
	}

	/**
	 * Initialize table view columns
	 */
	private void initiallizeOutcomeReportTables() {
		tvQRDetails2.getColumns().clear();
		TableColumn<RowForQuarterlyReports, Integer> productIDColumn = new TableColumn<RowForQuarterlyReports, Integer>(
				"Product ID");
		productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
		productIDColumn.setPrefWidth(120);
		this.tvQRDetails2.getColumns().add(productIDColumn);
		TableColumn<RowForQuarterlyReports, String> productNameColumn = new TableColumn<RowForQuarterlyReports, String>(
				"Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productNameColumn.setPrefWidth(250);
		this.tvQRDetails2.getColumns().add(productNameColumn);
		TableColumn<RowForQuarterlyReports, Double> outcomePerProductColumn = new TableColumn<RowForQuarterlyReports, Double>(
				"Product Outcome");
		outcomePerProductColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		outcomePerProductColumn.setPrefWidth(200);
		this.tvQRDetails2.getColumns().add(outcomePerProductColumn);
	}

	/**
	 * Initialize table view columns
	 */
	private void initiallizeInventoryReportTables() {
		tvQRDetails3.getColumns().clear();
		TableColumn<RowForQuarterlyReports, Integer> productIDColumn = new TableColumn<RowForQuarterlyReports, Integer>(
				"Product ID");
		productIDColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
		productIDColumn.setPrefWidth(120);
		this.tvQRDetails3.getColumns().add(productIDColumn);
		TableColumn<RowForQuarterlyReports, String> productNameColumn = new TableColumn<RowForQuarterlyReports, String>(
				"Product Name");
		productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
		productNameColumn.setPrefWidth(250);
		this.tvQRDetails3.getColumns().add(productNameColumn);
		TableColumn<RowForQuarterlyReports, Double> amountSoldColumn = new TableColumn<RowForQuarterlyReports, Double>(
				"Amount Sold");
		amountSoldColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
		amountSoldColumn.setPrefWidth(200);
		this.tvQRDetails3.getColumns().add(amountSoldColumn);
	}

	/**
	 * Initialize table view columns
	 */
	@SuppressWarnings({ "unchecked" })
	private void initiallizeNotificationTable() {
		final TableColumn<Notification, Integer> notificationIDColumn = (TableColumn<Notification, Integer>) new TableColumn(
				"Notification ID");
		notificationIDColumn.setCellValueFactory((Callback) new PropertyValueFactory("notificationID"));
		notificationIDColumn.setPrefWidth(0);
		final TableColumn<Notification, Date> timeColumn = (TableColumn<Notification, Date>) new TableColumn("Time");
		timeColumn.setCellValueFactory((Callback) new PropertyValueFactory("dateCreated"));
		timeColumn.setPrefWidth(170);
		this.tvHomeNotifytable.getColumns().add(timeColumn);
		final TableColumn<Notification, String> messageColumn = (TableColumn<Notification, String>) new TableColumn(
				"Message");
		messageColumn.setCellValueFactory((Callback) new PropertyValueFactory("message"));
		messageColumn.setPrefWidth(250);
		this.tvHomeNotifytable.getColumns().add(messageColumn);
	}

	/**********************************************************************
	 * update/refresh methods
	 **********************************************************************/

	/**
	 * method check if the numbers in updateThreshold textfield is ok and send
	 * update to server
	 * 
	 * @param event
	 */
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
			return;
		}
		if (func.isEmpty())
			return;
		String message = "update Threshold" + "_" + this.username + "_" + func;
		controller.handleMessageFromClientUI(message);
	}

	/**
	 * get notification id from the selected row in the table and dismiss it
	 * 
	 * @param event
	 */
	@FXML
	private void updateNotificationData(ActionEvent event) {
		Notification notification = tvHomeNotifytable.getSelectionModel().getSelectedItem();
		if (notification == null)
			return;
		int notificationID = notification.getNotificationID();
		// get notificationID from selected line in notification table
		String message = "updated dismissNotifications" + "_" + this.username + "_" + notificationID;
		controller.handleMessageFromClientUI(message);
	}

	/**********************************************************************
	 * others/helpers methods
	 **********************************************************************/

	/**
	 * if all of the checkboxes is not selected disable update button
	 */
	private void checkOtherCheckBoxes() {// linked to threshold
		if (!cbUTMotorbike.isSelected() && !cbUTDiesel.isSelected() && !cbUTGasoline.isSelected())
			btnUTUpdate.setDisable(true);
		else
			btnUTUpdate.setDisable(false);
	}

	/**
	 * convert date to string
	 */
	private String converDateToFormattedString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy  HH:mm");
		String dateFormatted = dateFormat.format(date);
		return dateFormatted;
	}

	/**
	 * send to server to get notifications
	 */
	private void getNotifications() {
		String message = "get undismissedNotifications" + "_" + username;
		controller.handleMessageFromClientUI(message);
	}

	@Override
	public void clearFields() {
		cbUTGasoline.setSelected(false);
		cbUTDiesel.setSelected(false);
		cbUTMotorbike.setSelected(false);
		tfUTGasoline2.clear();
		tfUTDiesel2.clear();
		tfUTMotorbike2.clear();
		tfASOName.clear();
		tfUTGasoline1.clear();
		tfUTDiesel1.clear();
		tfUTMotorbike1.clear();
		cobASOOrderId.getItems().removeAll((Collection<?>) this.cobASOOrderId.getItems());
		tfASOTime.clear();
		tfASOAmount.clear();
		tfASOAddress.clear();
		tfASOInStock.clear();
		tfASOThreshold.clear();
		cobGQRYear.getItems().removeAll((Collection<?>) this.cobGQRYear.getItems());
		cobGQRQuarter.getItems().removeAll((Collection<?>) this.cobGQRQuarter.getItems());
		for (int i = 0; i < tvHomeNotifytable.getItems().size(); i++)
			tvHomeNotifytable.getItems().clear();
		for (int j = 0; j < tvQRDetails1.getItems().size(); j++)
			tvQRDetails1.getItems().clear();
		for (int k = 0; k < tvQRDetails2.getItems().size(); k++)
			tvQRDetails2.getItems().clear();
		for (int p = 0; p < tvQRDetails3.getItems().size(); p++)
			tvQRDetails3.getItems().clear();
		taDOResons.clear();
		tfQRTotalIncome.clear();
		tfQRTotalAmountSold.clear();
		tfQRTotalAmountBought.clear();
		tflQRDateCreated.clear();
		tflQRQuarter.clear();
		tflQRYear.clear();
		tflQRFuelStationID.clear();
	}

}
