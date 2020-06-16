package guiClient;

import java.util.ArrayList;
import java.util.Collection;

import client.NetworkManagerController;
import entities.MyNetManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

/**
 * 
 * @version Basic
 * @author Lior
 *
 */
public class NetworkManagerWindow extends QuarterlyReportWindow {

	@FXML
	private ToggleGroup one;
	@FXML
	private ToggleButton sidebar_btn0;
	@FXML
	private ToggleButton sidebar_btn1;
	@FXML
	private ToggleButton sidebar_btn2;

	@FXML
	private ComboBox<Integer> cobGQRFuelStationID;

	@FXML
	private AnchorPane apAPM;
	@FXML
	private TextField tfAPMPricingName;
	@FXML
	private TextField tfAPMTime;
	@FXML
	private TextField tfAPMcurDiscount;
	@FXML
	private TextField tfAPMnewDiscount;
	@FXML
	private TextArea taAPMdescription;
	@FXML
	private TextArea taAPMwarning;
	@FXML
	private ComboBox<Integer> cobAPMRequestID;

	@FXML
	void initialize() {
		this.visibleNow = this.homePane;
		this.controller = NetworkManagerController.getInstance();
		this.controller.setCurrentWindow(this);
		initiallizeIncomeReportTables();
		initiallizeOutcomeReportTables();
		initiallizeInventoryReportTables();
		fillQuarterlyReportComboBox();
		sidebar_btn0.setSelected(true);
		/*
		 * send to server to get fuelstationids and fill cb
		 */
		controller.handleMessageFromClientUI("getAllFuelStationIDs");
	}

	@Override
	public Window getWindow() {
		return this.assessPane.getScene().getWindow();
	}

	@Override
	public void clearFields() {
		super.clearFields();
		/*
		 * clear assess pane
		 */
		apAPM.setDisable(true);
		tfAPMPricingName.clear();
		tfAPMTime.clear();
		tfAPMcurDiscount.clear();
		tfAPMnewDiscount.clear();
		taAPMdescription.clear();
		taAPMwarning.clear();
//		controller.handleMessageFromClientUI("");
//		cobAPMRequestID.setValue("");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void callAfterMessage(Object lastMsgFromServer) {
		super.callAfterMessage(lastMsgFromServer);
		if (lastMsgFromServer instanceof Object[]) {
			Object[] objArr = (Object[]) lastMsgFromServer;
			if (objArr.length == 0) {
				System.out.println("callAfterMessage: object[] empty");

			} else if (objArr[0] instanceof String) {
				String str = (String) objArr[0];
				if (str.equals("Quarter Report")) {
					openQuarterlyReport2(objArr);
					requestToLogActivity("Viewed generated quarter report of Fuel Station: "
							+ cobGQRFuelStationID.getValue() + " Year: " + cobGQRYear.getValue() + " Quarter: "
							+ cobGQRQuarter.getValue() + ".");
				} else if (str.equals("NonExist Quarter Report")) {
					openErrorAlert("Error", "Quarterly Report Does not Exist");
				}
			}

		} else if (lastMsgFromServer instanceof MyNetManager) {
			Object[] objArr = ((MyNetManager) lastMsgFromServer).getParams();
			if (objArr[0] instanceof ArrayList<?>) {
				ArrayList<Integer> fuelStationIDs = (ArrayList<Integer>) objArr[0];
				this.cobGQRFuelStationID.getItems().removeAll((Collection<?>) this.cobGQRFuelStationID.getItems());
				this.cobGQRFuelStationID.getItems().addAll(fuelStationIDs);
				this.cobGQRFuelStationID.setValue(fuelStationIDs.get(0));
			}
		}
	}

	/*********************** button listeners ***********************/

	@FXML
	void openAssessPane(ActionEvent event) {
		clearFields();
		visibleNow.setVisible(false);
		assessPane.setVisible(true);
		visibleNow = assessPane;
		topbar_window_label.setText("Assess Pricing Model Update Request");
		sidebar_btn1.setSelected(true);

		/*
		 * // Decline order, secondary window pop up btnASODecline.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * mainBorderPane.setDisable(true); declineOrderPane.setVisible(true);
		 * visibleNow = declineOrderPane; taDOReasons.clear();
		 * btnDOCancel.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * declineOrderPane.setVisible(false); mainBorderPane.setDisable(false);
		 * visibleNow = assessPane; } }); btnDOOk.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { int orderID =
		 * cobASOOrderId.getValue(); String declineReson = taDOReasons.getText(); if
		 * (declineReson.isEmpty()) { openErrorAlert("Input incomplete",
		 * "Please write the reason you wish to decline your order."); return; } else {
		 * String message = "update doneAssessmentOrder" + "_" + username + "_" +
		 * orderID + " " + "false" + " " + declineReson;
		 * controller.handleMessageFromClientUI(message); } } }); } }); // Order
		 * approval,alert pop up btnASOConfirm.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * System.out.println("aprroved"); int orderID = cobASOOrderId.getValue();
		 * String message = "update doneAssessmentOrder" + "_" + username + "_" +
		 * orderID + " " + "true" + " Nan";
		 * controller.handleMessageFromClientUI(message); } });
		 * btnASOShowOrder.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { if
		 * (cobASOOrderId.getItems().size() <= 0) return; else {
		 * btnASOConfirm.setDisable(false); btnASODecline.setDisable(false); int orderID
		 * = cobASOOrderId.getValue(); String message = "get StationProductInOrder" +
		 * "_" + username + "_" + orderID;
		 * controller.handleMessageFromClientUI(message); } } });
		 */
	}

	@FXML
	void openGenerateQuarterlyReport(ActionEvent event) {
		clearFields();
		openPaneOfViewQuarterlyReport();
		sidebar_btn2.setSelected(true);
		topbar_window_label.setText("View Quarterly Report");
	}

	@FXML
	void openQuarterlyReport(ActionEvent event) {
		int selectedYear = Integer.parseInt(cobGQRYear.getValue());
		int selectedQuarter = cobGQRQuarter.getValue();
		int selectedFuelStationID = this.cobGQRFuelStationID.getValue();
		/*
		 * selectedFuelStationID checks
		 */
		if (openPaneOfQuarterlyReports(selectedYear, selectedQuarter) == true) {
			String message = "view QuarterReport" + "_" + selectedFuelStationID + "_" + selectedYear + " "
					+ selectedQuarter + " network";
			controller.handleMessageFromClientUI(message);
		}
	}

	/**
	 * click on side button Home
	 * 
	 * @param event
	 */
	void openHome(ActionEvent event) {
		super.openHome(event);
		sidebar_btn0.setSelected(true);
	}

}
