package guiClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FastFuelWindow extends AFXML {

	@FXML
	private Label lblFFPricePerLiter;

	@FXML
	private Button btnEmulate;

	@FXML
	private TextField tfRegPlate;

	@FXML
	private TextField tfFuelStation;

	@FXML
	private TextField tfAmount;

	@FXML
	void btnEmulatePressed(ActionEvent event) {

	}

	@FXML
	void closeTopBar(ActionEvent event) {

	}

	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		// TODO Auto-generated method stub

	}

}
