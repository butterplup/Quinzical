package application;

import java.util.Arrays;

import gamelogic.GameBoard;
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
            AnchorPane rootLayout = (AnchorPane) loader.load();

            Scene scene = new Scene(rootLayout, 300, 200);
            scene.getStylesheets().add(getClass().getResource("css/quinzicalStyles.css").toExternalForm());

            primaryStage.setMinHeight(300.0);
            primaryStage.setMinWidth(200.0);
            primaryStage.setScene(scene);
            primaryStage.show();
            
            
            
        } catch(Exception e) {
            e.printStackTrace();
        } 

	}
}