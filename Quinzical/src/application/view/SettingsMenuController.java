package application.view;

import application.Main;
import gamelogic.TtsHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsMenuController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;
    @FXML
    private Slider voiceSlider;
    @FXML
    private Button defaultSpeedBtn;

    private TtsHandler _ttsHandler;

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

    public void handleVoiceSpeedChange() {
        System.out.println("You have chosen to set speed to " + voiceSlider.getValue() + "x");
        Double roundedValue = new BigDecimal(voiceSlider.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        _ttsHandler.saveSpeed(roundedValue);
        _ttsHandler.say("You have chosen to set voice speed to " + roundedValue + " times default");

    }

    public void handleDefaultSpeedBtn() {
        voiceSlider.setValue(1);
        System.out.println("Speed set to 1x");
        Double roundedValue = new BigDecimal(voiceSlider.getValue()).setScale(2, RoundingMode.HALF_UP).doubleValue();
        _ttsHandler.saveSpeed(roundedValue);
        _ttsHandler.say("You have reset the voice speed to default, which is 1 times speed");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        _ttsHandler = new TtsHandler();
    }
}
