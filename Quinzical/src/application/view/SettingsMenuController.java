package application.view;

import application.Main;
import gamelogic.TtsHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class deals with the settings menu GUI
 * @author jh and bs
 *
 */
public class SettingsMenuController implements Initializable {

	//FXML fields loaded in from file
    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Slider voiceSlider;
    @FXML
    private Button defaultSpeedBtn;
    // Used to demo tts
    private TtsHandler _ttsHandler;

    /**
     * Takes user back to main menu
     */
    public void handleBackBtnClick() {

        try {
            // Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();

            // Change layout from practice menu to main menu
            anchor.getChildren().setAll(rootLayout);

        } catch(Exception e) {
            // Print in case of any errors
            e.printStackTrace();
        }
    }

    /**
     * Takes the set value on the slider and saves as the desired speed for tts, also reads out demo at chosen speed
     */
    public void handleVoiceSpeedChange() {
    	// Rounding double value to 2 decimal places
        Double roundedValue = new BigDecimal(voiceSlider.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        // Saves value of speed to be used elsewhere
        _ttsHandler.saveSpeed(roundedValue);
        // Demo reads out string to listen to speed in action
        _ttsHandler.say("You have chosen to set voice speed to " + roundedValue + " times default");

    }

    /**
     * See SettingsMenuController#handleVoiceSpeedChange()
     * Instead just resets to default speed of 1x
     */
    public void handleDefaultSpeedBtn() {
        voiceSlider.setValue(1);
        System.out.println("Speed set to 1x");
        Double roundedValue = new BigDecimal(voiceSlider.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        _ttsHandler.saveSpeed(roundedValue);
        _ttsHandler.say("You have reset the voice speed to default, which is 1 times speed");
    }

    /**
     * Called when FXML file loaded, initialising any objects to be used by this menu
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _ttsHandler = new TtsHandler();
    }
}
