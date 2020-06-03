package client;

import java.io.IOException;
import java.net.ConnectException;

import entities.User;

/**
 * all logic controllers extend this
 * 
 * @version Almost Final
 * @author Elroy, Lior
 */
public abstract class UserController extends ClientController {

	public UserController() {
		super();
	}

	/**
	 * handle signout process requested by client and route to server
	 */
	@Override
	public void handleMessageFromClientUI(String message) {
		try {
			System.out.println("message from clientUI : " + message);
			this.openConnection();
			awaitResponse = true;

			if (!message.startsWith("signout"))
				return;

			String[] splitMsg = message.split(" ");

			/* construct a new user */
			/* determine what the server will do with it */
			User user = new User(splitMsg[1]);
			user.setFunction("sign out");

			/* announce and send the user to the server */
			System.out.println("sending to server : " + user);
			this.sendToServer(user);

			/* wait for ack or data from the server */
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}

			this.currentWindow.callAfterMessage(this.lastMsgFromServer);

		} catch (ConnectException ce) {
			this.currentWindow.openErrorAlert("Server Error", "Error - No connection to server");
			ce.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
