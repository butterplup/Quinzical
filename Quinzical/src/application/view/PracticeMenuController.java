package application.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class PracticeMenuController implements Initializable {

    @FXML
    private ComboBox selectCategory;

    public void handleCategorySelected() {
        System.out.println("Category selected: " + selectCategory.getValue());
        // Make category selector BorderPane opacity = 0
        // Make question presenter BorderPane opacity = 1
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectCategory.getItems().setAll("Animals","Countries");
    }
}
