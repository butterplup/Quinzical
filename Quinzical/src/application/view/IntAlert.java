package application.view;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class IntAlert {
	private Alert alert = new Alert(AlertType.INFORMATION, "Go to the practice modules to learn more about the world!");
	
	public IntAlert() {
	   	 alert.setTitle("Congratulations!");
	     alert.setHeaderText("You've just unlocked the International category");
	}
	
	public void showAndWait() {
		alert.showAndWait();
	}
}
