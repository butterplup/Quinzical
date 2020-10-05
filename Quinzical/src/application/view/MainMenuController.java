package application.view;

import application.Main;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * The controller class for the main menu
 * @author jh and bs
 *
 */
public class MainMenuController {

	// Fields for JavaFX elements, loaded from fxml file
    @FXML
    private Button gamesBtn;
    @FXML
    private Button practiceBtn;
    @FXML
    private Button exitBtn;
    @FXML
    private AnchorPane anchor;

    /**
     * Changes menu to games module menu
     */
    @FXML
    public void handleGamesBtnClick() {
        this.loadFXML("GameMenu");
    }

    /**
     * Changes menu to practice module menu
     */
    @FXML
    public void handlePracticeBtnClick() {
        this.loadFXML("PracticeMenu");
    }
    
    /**
     * Changes menu to settings menu
     */
    @FXML
    public void handleSettingsBtnClick() {
        this.loadFXML("SettingsMenu");

    }

    /**
     * Takes the particular fxml file name as input and loads it to screen
     * @param file - name of fxml file
     */
    public void loadFXML(String file) {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/"+ file + ".fxml"));
            AnchorPane rootLayout = loader.load();
            anchor.getChildren().setAll(rootLayout);

        } catch(Exception e) {
            e.printStackTrace();
        }
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

