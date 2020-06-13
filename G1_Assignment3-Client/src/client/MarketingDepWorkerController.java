package client;

import java.io.IOException;
import java.net.ConnectException;

import entities.MarketingManager;

/**
 * @version Basic
 * @author Lior, Elroy
 */
public abstract class MarketingDepWorkerController extends UserController {

	public void handleDepWorkerRequest(String message) {
		try {
			System.out.println("message from clientUI : " + message);
			this.openConnection();
			awaitResponse = true;

			MarketingManager manager = new MarketingManager();
			manager.setFunction(message);
			this.sendToServer(manager);

			/* wait for ack or data from the server */
			while (awaitResponse) {
				try {
					Thread.sleep(100);
					System.out.println("marketing dep worker is waiting");
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
