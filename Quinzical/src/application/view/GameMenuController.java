package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

public class GameMenuController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;

    public void handleClueSelected() {
        // Make category selector BorderPane opacity = 0
        // Make question presenter BorderPane opacity = 1
    }
    
    public void handleBackBtnClick() {
    	System.out.println("Back");
    	
    	// Get the primaryStage object
    	Stage mainStage = (Stage) backBtn.getScene().getWindow();
        try {
        	// Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();
            
            // Change layout from practice menu to main menu
            anchor.getChildren().setAll(rootLayout);
            
            // Adjust bounds of stage
            mainStage.setHeight(400.0);
            mainStage.setWidth(600.0);

        } catch(Exception e) {
        	// Print in case of any errors
            e.printStackTrace();
        } 
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	// Make some call to the gameboard object to get all the actual categories
    	
    }
    
}
