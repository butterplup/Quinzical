package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Quinzical");
		
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(new Button("Exit"), new Button ("buttonReset"));
		vbox.setPadding(new Insets(10, 10, 10, 10));
		vbox.setSpacing(20);
		
		pane.setCenter(vbox);
		Scene scene = new Scene(pane, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
