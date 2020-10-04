package application.view;

import gamelogic.Category;
import gamelogic.QuestionBank;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;

public class PracticeMenuController implements Initializable {
    // For choosing the first index of the category
    private final int Q_INDEX = 1;

    @FXML
    private ChoiceBox<String> selectCategory;
    @FXML
    private AnchorPane anchor;
    @FXML
    private BorderPane selectorPane;
    @FXML
    private BorderPane cluePane;
    @FXML
    private BorderPane correctPane;
    @FXML
    private BorderPane incorrectPane;
    @FXML
    private HBox incorrectBtns;
    @FXML
    private Label clueText;
    @FXML
    private Label promptLabel;
    @FXML
    private Label hintLabel;
    @FXML
    private TextField answerField;
    @FXML
    private Button retryBtn;

    private QuestionBank _practiceQBank;
    private Category _selectedCategory;
    private String _questionStr;

    private int _wrongCount = 0;

    public void handleCategorySelected() {
        // Get the String representing the name of selected category
        String categoryName = selectCategory.getValue();
        System.out.println("Category selected: " + categoryName);
        // Get the Category object for the corresponding category
        _selectedCategory = _practiceQBank.getCategory(categoryName);
        // Shuffle to ensure first question is random
        _selectedCategory.shuffle();

        // Reads out the clue and returns the String representation
        _questionStr = _selectedCategory.ask(Q_INDEX);

        if (_questionStr.length() > 50) {
        	System.out.println("Long clue, huh?");
            for (int i = 40; i < _questionStr.length(); i++) {
                if (_questionStr.charAt(i) == ' ') {
                    _questionStr = _questionStr.substring(0, i) + "...\n" + _questionStr.substring(i);
                    break;
                }
            }
        }

        // Set the clue label to display to user, and the corresponding prompt (e.g "what is: ")
        clueText.setText(_questionStr);
        promptLabel.setText(_selectedCategory.getQPrompt(Q_INDEX) + ": ");

        // Make category selector BorderPane opacity = 0
        selectorPane.setOpacity(0);
        // Make question presenter BorderPane opacity = 1, and move to front
        cluePane.setOpacity(1);
        cluePane.toFront();
    }
    
    public void handleBackBtnClick() {
    	System.out.println("Back");
    	
    	// Get the primaryStage object
    	//Stage mainStage = (Stage) backBtn.getScene().getWindow();
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

    public void handleRepeatBtnClick() {
        _selectedCategory.ask(1);
    }

    public void handleSubmitBtnClick() {
        String answer = answerField.getText();
        cluePane.setOpacity(0);

        if (_selectedCategory.answer(Q_INDEX, answer)) {
            // Show correct answer screen
            correctPane.setOpacity(1);
            correctPane.toFront();
        } else {
            // Add to number of attempts
            _wrongCount++;

            if (_wrongCount == 2) {
                // Give first letter/s as hint for retry
                hintLabel.setText(_selectedCategory.getQHint(Q_INDEX));

            } else if (_wrongCount == 3) {
                // No more attempts, show full answer with clue
                hintLabel.setText("No more attempts.\nThe clue was: \n" + _questionStr + "\nThe correct answer was: \n" + _selectedCategory.getQAnswer(Q_INDEX));
                retryBtn.setDisable(true);
                Button returnBtn = new Button("Return to menu");
                returnBtn.setOnAction(e -> handleBackBtnClick());
                incorrectBtns.getChildren().add(returnBtn);

            }
            // Display wrong answer screen
            incorrectPane.setOpacity(1);
            incorrectPane.toFront();

        }
    }

    public void handleRetryBtnClick() {
        incorrectPane.setOpacity(0);
        cluePane.setOpacity(1);
        cluePane.toFront();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialise QuestionBank object for use of PracticeMenu
        _practiceQBank = new QuestionBank();
    	// Make call to the QuestionBank object to get all the names of categories
        List<String> allCategories = _practiceQBank.getAllCategories();
        // Adds all the category names to the ChoiceBox for selection
        selectCategory.getItems().addAll(allCategories);
    	
    }
    
}
