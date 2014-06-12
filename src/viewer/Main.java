package viewer;

import java.io.File;
import java.io.IOException;

import model.configFile.ConfGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application
{
	public Scene scene;

	@Override
	public void start(Stage primaryStage) 
	{
		try {

			Parent root = FXMLLoader.load(getClass().getResource("WordMasterUI.fxml"));
	        scene = new Scene(root, 600, 400);
	        primaryStage.initStyle(StageStyle.DECORATED);
	        primaryStage.setScene(scene);
	        primaryStage.setTitle("WordMaster");
	        primaryStage.show();
			/*scene = new Scene(grid,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*controller，主要是界面的linstener，然后调用observer和observation*/
	public static void main(String[] args) throws IOException 
	{
		//word.setState(2);
		//word.setWord(3);
		File confFile = new File("user.conf");
 
		if(!confFile.exists())
		{
			ConfGenerator.generateConfig();
		}
		launch(args);
	}
}
