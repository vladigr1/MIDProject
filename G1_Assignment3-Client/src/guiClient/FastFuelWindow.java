package guiClient;

import client.FastFuelController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * @version Basic
 * @author Lior
 */
public class FastFuelWindow extends AFXML {

	@FXML
	private TextField tfRegPlate;
	@FXML
	private TextField tfFuelStation;
	@FXML
	private TextField tfAmount;
	@FXML
	private Button btnEmulate;
	@FXML
	private Label lblPrice;
	@FXML
	private Label lblFuelType;
	@FXML
	private Label lblContinue;

	@FXML
	void initialize() {
		this.lblContinue.setVisible(false);
		this.controller = FastFuelController.getInstance();
		this.controller.setCurrentWindow(this);
	}

	private Window getWindow() {
		return this.lblFuelType.getScene().getWindow();
	}

	/*********************** button listeners ***********************/

	@FXML
	void btnEmulatePressed(ActionEvent event) {
		String regPlate = this.tfRegPlate.getText();
		String fuelStationName = this.tfFuelStation.getText();
		String amount = this.tfAmount.getText();
		if (regPlate.isEmpty() || fuelStationName.isEmpty() || amount.isEmpty()) {
			openErrorAlert("Error", "Missing Required Fields");
			return;
		}
		if (regPlate.matches(".*[A-z].*") || fuelStationName.matches(".*[0-9].*") || amount.matches(".*[A-z].*")) {
			openErrorAlert("Error", "Field Not Valid");
			return;
		}

		this.controller.handleMessageFromClientUI("getdiscount " + regPlate + " " + fuelStationName + " " + amount);
	}

	@FXML
	public void closeTopBar(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/windows/LoginWindow.fxml"));
			Scene newScene = new Scene(loader.load());
			Stage newStage = new Stage();

			LoginWindow loginWindow = (LoginWindow) loader.getController();
			loginWindow.setVisibleNow(true);

			newStage.setResizable(false);
			newStage.setScene(newScene);
			newStage.setTitle("MyFuel Login");
			newStage.initStyle(StageStyle.UNDECORATED);
			newStage.show();
			newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					System.exit(0);
				}
			});
			getWindow().hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*************** boundary "logic" - window changes ***************/

	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		// TODO Auto-generated method stub

	}

}
