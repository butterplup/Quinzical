package application.view;

import gamelogic.Category;
import gamelogic.GameBoard;
import gamelogic.QuestionBank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.scene.layout.VBox;

public class PracticeMenuController implements Initializable {
    // For choosing the first index of the category
    private final int Q_INDEX = 1;
    // FXML specific fields loaded in from file
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
    private VBox incorrectLabels;
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
    // Fields for use as model
    private QuestionBank _practiceQBank;
    private Category _selectedCategory;
    private String _questionStr;
    private int _wrongCount = 0;

    /**
     * Gets the chosen category and selects a random question from this category to ask the user
     */
    public void handleCategorySelected() {
        // Get the String representing the name of selected category
        String categoryName = selectCategory.getValue();
        // Get the Category object for the corresponding category
        _selectedCategory = _practiceQBank.getCategory(categoryName);
        // Shuffle to ensure first question is random
        _selectedCategory.shuffle();

        // Reads out the clue and returns the String representation
        _questionStr = _selectedCategory.ask(Q_INDEX);
        // For longer strings that would otherwise be clipped, a newline char is added to split it
        if (_questionStr.length() > 50) {
            for (int i = 38; i < _questionStr.length(); i++) {
            	// Finds a space between two words to split
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
    
    /**
     * Returns user to main menu
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
     * Repeats the clue as tts for user to listen
     */
    public void handleRepeatBtnClick() {
        _selectedCategory.ask(1);
    }

    /**
     * Adds the vowel with macron of the corresponding button to the textfield
     * @param event - event of the button clicked
     */
    public void handleLetterAdd(ActionEvent event) {
        Button clickedBtn = (Button) event.getSource();
        String letter = clickedBtn.getText();
        answerField.setText(answerField.getText() + letter);
    }

    /**
     * Checks to see if user hit the enter key as another option for submitting
     * @param keyEvent - fired when a key has been pressed
     */
    public void handleSubmitKey(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            this.handleSubmitBtnClick();
        }
    }

    /**
     * Reads the user input and checks if the answer is correct,
     * implements a three strikes rule
     */
    public void handleSubmitBtnClick() {
        String answer = answerField.getText();
        answerField.clear();
        cluePane.setOpacity(0);

        if (_selectedCategory.answer(Q_INDEX, answer)) {
            // Show correct answer screen
            correctPane.setOpacity(1);
            correctPane.toFront();
        } else {
            // Add to number of attempts
            _wrongCount++;
            // After two wrong tries
            if (_wrongCount == 2) {
                // Give first letter/s as hint for retry
                hintLabel.setText(_selectedCategory.getQHint(Q_INDEX));
            // After three wrong attempts, no more attempts remain
            } else if (_wrongCount == 3) {
                // No more attempts, show full answer with clue
                hintLabel.setText("No more attempts.");
                
                Label clueLabel = new Label("The clue was: " + _questionStr);
                Label answerLabel = new Label("The correct answer was: " + _selectedCategory.getQAnswer(Q_INDEX));
                
                clueLabel.setStyle("-fx-font-size: 20px;");
                answerLabel.setStyle("-fx-font-size: 20px;");
                
                incorrectLabels.getChildren().add(2,clueLabel);
                incorrectLabels.getChildren().add(3,answerLabel);
                // Cannot attempt again
                retryBtn.setDisable(true);
                // Offer the option to return to menu
                Button returnBtn = new Button("Select new category");
                returnBtn.setOnAction(e -> handleResetBtnClick());
                incorrectBtns.getChildren().add(returnBtn);

            }
            // Display wrong answer screen
            incorrectPane.setOpacity(1);
            incorrectPane.toFront();

        }
    }

    /**
     * Resets the page back to a new category selector, for a new question
     */
    public void handleResetBtnClick() {
        try {
            // Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/PracticeMenu.fxml"));
            AnchorPane rootLayout = loader.load();

            // Change layout from practice menu to main menu
            anchor.getChildren().setAll(rootLayout);

        } catch(Exception e) {
            // Print in case of any errors
            e.printStackTrace();
        }
    }

    /**
     * Changes from the hint screen to the answer input screen,
     * gives the user another attempt
     */
    public void handleRetryBtnClick() {
        incorrectPane.setOpacity(0);
        cluePane.setOpacity(1);
        cluePane.toFront();
    }

    /**
     * Called when FXML file is loaded. Initialises the objects to be used as the model.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Initialise QuestionBank object for use of PracticeMenu
        _practiceQBank = new QuestionBank();
        
        GameBoard _gameBoard = new GameBoard();
    	if (_gameBoard.intSectionEnabled()) {
    		_practiceQBank.addIntSection();
    	}
    	
    	// Make call to the QuestionBank object to get all the names of categories
        List<String> allCategories = _practiceQBank.getAllCategories();
        // Adds all the category names to the ChoiceBox for selection
        selectCategory.getItems().addAll(allCategories);
        
    }
}
