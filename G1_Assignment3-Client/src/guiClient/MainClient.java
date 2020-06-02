package guiClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * client's exe starts here
 * <p>
 * loads login window
 * 
 * @version Final
 * @author Elroy, Lior
 */
public class MainClient extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/windows/LoginWindow.fxml"));
			Scene scene = new Scene(loader.load());

			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setTitle("MyFuel Login");
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		launch(args);
	}

}
