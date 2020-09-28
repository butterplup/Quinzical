package application.view;

import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public void handleGamesBtnClick() {
        System.out.println("Game Button clicked");
    }

    @FXML
    public void handlePracticeBtnClick() {
        System.out.println("Prac Button clicked");
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

