package guiClient;

import java.io.IOException;

import client.LoginController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * boundary for login window
 * 
 * @version 1 Method To Final
 * @see successLogin()
 * @author Elroy, Lior
 */
public class LoginWindow implements IFXML {

	@FXML
	private TextField tfLoginUserName;

	@FXML
	private PasswordField tfLoginPassword;

	@FXML
	private Label lblError;

	@FXML
	private Button btnSignIn;

	@FXML
	private ToggleGroup rb1;

	@FXML
	private RadioButton rbEmployee;

	@FXML
	private RadioButton rbCustomer;

	private LoginController loginController;

	@FXML
	void initialize() throws IOException {
		this.loginController = LoginController.getInstance();
		this.loginController.setCurrentWindow(this);
	}

	/**
	 * if login details valid, send <code>Role</Code> to <Code>successLogin()</Code>
	 * so another window will open accordingly
	 * 
	 * @param lastMsgFromServer
	 */
	@Override
	public void callAfterMessage(Object lastMsgFromServer) {
		if (lastMsgFromServer instanceof String) {
			String message = lastMsgFromServer.toString();
			if (message.startsWith("login succeeded")) {
				String[] splitMsg = message.split(" ");
				this.successLogin(splitMsg[2]);
			}

			if (message.startsWith("login failed"))
				this.failedLogin();

			if (message.startsWith("login already connected"))
				this.alreadyConnectedLogin();
		}
	}

	/**
	 * user clicked on sign in button
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void signIn(ActionEvent event) throws Exception {
		this.mySignIn();
	}

	/**
	 * handles sign in request by:
	 * <p>
	 * checking no information is missing and prompting for errors
	 * <p>
	 * sending all information to <Code>handleMessageFromClientUI</Code> as a string
	 */
	private void mySignIn() {
		String username = tfLoginUserName.getText();
		String password = tfLoginPassword.getText();
		String userType;

		if (username.isEmpty() || password.isEmpty()) {
			this.lblError.setText("Missing Required Fields");
			this.lblError.setVisible(true);
			this.tfLoginUserName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			this.tfLoginPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
			return;
		}

		this.lblError.setVisible(false);
		this.tfLoginUserName.setStyle("-fx-border-style: none;");
		this.tfLoginPassword.setStyle("-fx-border-style: none;");

		if (this.rbEmployee.isSelected())
			userType = this.rbEmployee.getText();
		else
			userType = this.rbCustomer.getText();

		this.loginController.setCurrentWindow(this);
		this.loginController.handleMessageFromClientUI(("login" + " " + username + " " + password + " " + userType));
	}

	/**
	 * called when sign in is valid
	 * <p>
	 * determines path of fxml file and window title according to <Code>Role</Code>
	 * <p>
	 * loads new window accordingly and sends username to its boundary
	 * 
	 * @param role
	 */
	private void successLogin(String role) {
		this.lblError.setVisible(false);

		String newWindowPath = "";
		String newWindowTitle = "";

		if (role.equals("MarketingRepresentative")) {
			newWindowPath = "/marketing/MarketingRepresentativeWindow.fxml";
			newWindowTitle = "MyFuel Marketing Representative";
		}

		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource(newWindowPath));
			Scene newScene = new Scene(loader.load());
			Stage newStage = new Stage();

			UserWindow newWindow = loader.getController();
			newWindow.setUsername(this.tfLoginUserName.getText());

			newStage.setResizable(false);
			newStage.setScene(newScene);
			newStage.setTitle(newWindowTitle);
			newStage.show();
			newStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					if (!newWindow.signOutClicked(newWindow.getWindow()))
						we.consume();
				}
			});
			this.btnSignIn.getScene().getWindow().hide();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * called when sign in failed
	 */
	private void failedLogin() {
		this.lblError.setText("wrong username or password");
		this.lblError.setVisible(true);
		this.tfLoginUserName.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
		this.tfLoginPassword.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
	}

	/**
	 * called when user is already connected in another client
	 */
	private void alreadyConnectedLogin() {
		this.lblError.setText("user already connected");
		this.lblError.setVisible(true);
	}

	public void btnSignInHover() {
		this.btnSignIn.setStyle("-fx-background-color: #FFA07A ; -fx-background-radius: 7");
	}

	public void btnSignInExit() {
		this.btnSignIn.setStyle("-fx-background-color:  #F56B2C ; -fx-background-radius: 7");
	}

	@FXML
	void enterKeyPressed(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER:
			this.mySignIn();
			break;
		default:
			break;
		}
	}

	@FXML
	void tabEmployeePressed(KeyEvent event) {
		switch (event.getCode()) {
		case TAB:
			this.rbCustomer.setSelected(true);
			break;
		default:
			break;
		}
	}

	@FXML
	void tabCustomerPressed(KeyEvent event) {
		switch (event.getCode()) {
		case TAB:
			this.rbEmployee.setSelected(true);
			break;
		default:
			break;
		}
	}

}
