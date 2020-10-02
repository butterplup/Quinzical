package application.view;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsMenuController {

    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;

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

}
