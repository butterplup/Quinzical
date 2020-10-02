package application.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;

public class PracticeMenuController implements Initializable {

    @FXML
    private ChoiceBox<String> selectCategory;
    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;
    @FXML
    private BorderPane selectorPane;
    @FXML
    private BorderPane cluePane;

    public void handleCategorySelected() {
        System.out.println("Category selected: " + selectCategory.getValue());
        // Make category selector BorderPane opacity = 0
        selectorPane.setOpacity(0);
        // Make question presenter BorderPane opacity = 1
        cluePane.setOpacity(1);
        cluePane.toFront();
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

        } catch(Exception e) {
        	// Print in case of any errors
            e.printStackTrace();
        } 
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	// Make some call to the gameboard object to get all the actual categories
        selectCategory.getItems().addAll("Animals", "Countries", "Food");
    	
    }
    
}
