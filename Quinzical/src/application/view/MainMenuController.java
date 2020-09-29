package application.view;

import application.Main;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainMenuController {

    @FXML
    private Button gamesBtn;
    @FXML
    private Button practiceBtn;
    @FXML
    private Button exitBtn;
    
    @FXML
    private AnchorPane anchor;

    @FXML
    public void handleGamesBtnClick() {
        System.out.println("Game Button clicked");
    }

    @FXML
    public void handlePracticeBtnClick() {
        System.out.println("Prac Button clicked");
        Stage mainStage = (Stage) practiceBtn.getScene().getWindow();
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/PracticeMenu.fxml"));
            AnchorPane rootLayout = (AnchorPane) loader.load();
            anchor.getChildren().setAll(rootLayout);

            mainStage.setHeight(400.0);
            mainStage.setWidth(600.0);

        } catch(Exception e) {
            e.printStackTrace();
        } 
    }
    
    @FXML
    public void handleSettingsBtnClick() {
        System.out.println("Settings Button clicked");

    }
    
    /**
     * This handles when the exit button is clicked, closing the stage
     * and saving the current game states.
     */
    @FXML
    public void handleExitBtnClick() {
    	// Save game
        System.out.println("Call gamelogic.saveGame()");
    	// Get the current stage
        Scene scene = exitBtn.getScene();
        Window window = scene.getWindow();
    	Stage mainStage = (Stage) window;
    	// Close the current stage
    	mainStage.close();
    }

}

