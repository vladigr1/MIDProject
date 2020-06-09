package guiClient;

import javafx.stage.Window;

import java.util.Collection;
import java.util.List;
import client.FuelStationManagerController;
import entities.FuelStationOrder;
import entities.ProductInStation;
import entities.UnassedOrderIDList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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
	private TextField tfASOFinalPrice;

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
	private AnchorPane quarterlyReportPane;

	@FXML
	private ComboBox<?> cobGQRYear;

	@FXML
	private ComboBox<?> cobGQRQuarter;

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
	private Button btnQRNext;

	@FXML
	private Button btnQRPrevious;

	@FXML
	private AnchorPane paneQRPart1;

	@FXML
	private Label lblQRTitle1;

	@FXML
	private TableView<?> tvQRDetails1;

	@FXML
	private AnchorPane paneQRPart2;

	@FXML
	private Label lblQRTitle2;

	@FXML
	private TableView<?> tvQRDetails2;

	@FXML
	private AnchorPane paneQRPart3;

	@FXML
	private Label lblQRTitle3;

	@FXML
	private TableView<?> tvQRDetails3;

	@FXML
	private Button btnQRClose;

	@FXML
	private Label lblQRPageNum;

	private int pageNum = 1;

	@FXML
	void initialize() {
		this.visibleNow = this.homePane;
		this.controller = FuelStationManagerController.getInstance();
		this.controller.setCurrentWindow(this);

	}

	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		super.callAfterMessage(lastMsgFromServer);
		/**
		 * 
		 */
		if (lastMsgFromServer instanceof UnassedOrderIDList) {
			UnassedOrderIDList unassedOrderList = (UnassedOrderIDList) lastMsgFromServer;
			this.fillUnassessedOrderToComboBox(unassedOrderList.getList());
		}

		if (lastMsgFromServer instanceof Object[]) {
			Object[] objArr=(Object[]) lastMsgFromServer;
			if(objArr[0] instanceof ProductInStation && objArr[1] instanceof FuelStationOrder ) {
				System.out.println((ProductInStation)objArr[0]);
				System.out.println((FuelStationOrder)objArr[1]);
			}
				
		}

	}

	@Override
	public Window getWindow() {
		return this.assessPane.getScene().getWindow();
	}

	@FXML
	void openUpdateThreshold(ActionEvent event) {
		visibleNow.setVisible(false);
		thresholdPane.setVisible(true);
		visibleNow = thresholdPane;
		topbar_window_label.setText("Update Threshold");
	}

	@FXML
	void openAssessStationOrders(ActionEvent event) {
		visibleNow.setVisible(false);
		assessPane.setVisible(true);
		visibleNow = assessPane;
		topbar_window_label.setText("Assess Station Orders");
		String msg = "get unassesedOrderID " + this.username;
		this.sendToClientController(msg);
	}

	@FXML
	void showUnassessedOrderData(ActionEvent event) {
		int orderID = cobASOOrderId.getValue();
		FuelStationManagerController.getInstance().askForProductInOrderID(orderID, this.username);
	}

	@FXML
	void openDeclineOrder(ActionEvent event) {
		mainBorderPane.setDisable(true);
		declineOrderPane.setVisible(true);
	}

	@FXML
	void cancelDeclineOrder(ActionEvent event) {
		declineOrderPane.setVisible(false);
		mainBorderPane.setDisable(false);
	}

	@FXML
	void okDeclineOrder(ActionEvent event) {
		declineOrderPane.setVisible(false);
		mainBorderPane.setDisable(false);
	}

	@FXML
	void openGenerateQuarterlyReport(ActionEvent event) {
		visibleNow.setVisible(false);
		quarterlyReportPane.setVisible(true);
		visibleNow = quarterlyReportPane;
		topbar_window_label.setText("Generate Quarterly Report");
	}

	@FXML
	void openViewQuarterReport(ActionEvent event) {
		mainBorderPane.setDisable(true);
		quarterReportPane.setVisible(true);
	}

	@FXML
	void closeQuarterReport(ActionEvent event) {
		quarterReportPane.setVisible(false);
		mainBorderPane.setDisable(false);
	}

	@FXML
	void nextQuarterReport(ActionEvent event) {
		if (pageNum < 3) {// pageNum initialize to 1
			pageNum++;
			lblQRPageNum.setText(pageNum + "/3");
			switch (pageNum) {
			case 2: // came from page 1 to 2
				paneQRPart1.setVisible(false);
				paneQRPart2.setVisible(true);
				btnQRPrevious.setDisable(false);
				break;
			case 3: // came from page 2 to 3
				paneQRPart2.setVisible(false);
				paneQRPart3.setVisible(true);
				btnQRNext.setDisable(true);
				break;
			default:
				break;
			}
		}

	}

	@FXML
	void previousQuarterReport(ActionEvent event) {
		if (pageNum > 1) {
			pageNum--;
			lblQRPageNum.setText(pageNum + "/3");
			switch (pageNum) {
			case 2: // came from page 3 to 2
				paneQRPart3.setVisible(false);
				paneQRPart2.setVisible(true);
				btnQRNext.setDisable(false);
				break;
			case 1: // came from page 2 to 1
				paneQRPart2.setVisible(false);
				paneQRPart1.setVisible(true);
				btnQRPrevious.setDisable(true);
				break;
			default:
				break;
			}
		}
	}

	private void fillUnassessedOrderToComboBox(List<Integer> list) {
		this.cobASOOrderId.getItems().removeAll((Collection<?>) this.cobASOOrderId.getItems());
		this.cobASOOrderId.getItems().addAll(list);
		this.cobASOOrderId.setValue(list.get(0));
	}

	/**
	 * method that will sent to the client controller the required message
	 * 
	 * @param message
	 */
	private void sendToClientController(String message) {
		this.controller.handleMessageFromClientUI(message);
	}

}
