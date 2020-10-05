package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Quinzical");
		
		try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();

            Scene scene = new Scene(rootLayout, 600, 400);
            scene.getStylesheets().add(getClass().getResource("css/quinzicalStyles.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
            
            
        } catch(Exception e) {
            e.printStackTrace();
        } 

	}
}