package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * The main class from where the application is launched.
 * @author jh and bs
 *
 */
public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * This method sets up the UI elements for the stage, and loads the main menu
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Quinzical");
		
		try {
			// Loading in the main menu elements from fxml file
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout, 1280, 720);
            // Add css stylings to the GUI
            scene.getStylesheets().add(getClass().getResource("css/quinzicalStyles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            
            
        } catch(Exception e) {
            e.printStackTrace();
        } 

	}
}