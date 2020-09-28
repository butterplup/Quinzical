package application.view;

import javafx.fxml.*;
import javafx.scene.control.*;

public class MainMenuController {

    @FXML
    Button gamesBtn;

    @FXML
    Button practiceBtn;

    @FXML
    public void handleGamesBtnClick() {
        System.out.println("Game Button clicked");
    }

    @FXML
    public void handlePracticeBtnClick() {
        System.out.println("Prac Button clicked");
    }

}

