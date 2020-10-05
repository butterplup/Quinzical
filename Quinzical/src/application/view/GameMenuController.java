package application.view;

import gamelogic.GameBoard;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;

public class GameMenuController implements Initializable {

    @FXML
    private Button backBtn;
    @FXML
    private AnchorPane anchor;
    @FXML
    private BorderPane selectionPane;
    @FXML
    private BorderPane cluePane;
    @FXML
    private BorderPane resultPane;
    @FXML
    private BorderPane completedPane;
    @FXML
    private GridPane clueGrid;
    @FXML
    private Label promptLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label winningsLabel;
    @FXML
    private TextField answerField;

    private GameBoard _gameBoard;
    private List<String> _categories;
    private String _questionStr;
    private int _questionIndex;
    private int _categoryIndex;
    private boolean _remaining = true;

    public void handleClueSelected(ActionEvent event) {
//    	System.out.println("Clue selected");
        Button clickedBtn = (Button) event.getSource();
        clickedBtn.setDisable(true);

    	_questionIndex = GridPane.getRowIndex(clickedBtn).intValue() - 1;
    	
    	if (GridPane.getColumnIndex(clickedBtn) == null) { 
    		_categoryIndex = 0;
    	} else {
    		_categoryIndex = GridPane.getColumnIndex(clickedBtn).intValue();
    	}
    	_gameBoard.makeCompleted(_questionIndex, _categoryIndex);
        // Checks whether any are remaining since one more has been attempted
        checkRemaining();
        // Updates button state to reflect question attempts
        updateBoardState();

        _questionStr = _gameBoard.ask(_questionIndex, _categoryIndex);
        promptLabel.setText(_gameBoard.getPrompt(_questionIndex, _categoryIndex));

        // Make category selector BorderPane opacity = 0
        selectionPane.setOpacity(0);
        // Make question presenter BorderPane opacity = 1
        cluePane.setOpacity(1);
        cluePane.toFront();

    }

    public void handleRepeatBtnClick() {
        _gameBoard.say(_questionStr);
    }

    public void handleSubmitBtnClick() {
        // Get entered field, or empty if didn't know
        String answer = answerField.getText();
        // Check if correct
        if (_gameBoard.answer(_questionIndex, _categoryIndex, answer)) {
            resultLabel.setText("Your answer was correct! Good job!");
        } else {
            resultLabel.setText("You were not correct.");
        }

        cluePane.setOpacity(0);
        resultPane.setOpacity(1);
        resultPane.toFront();

    }

    public void handleOkBtnClick() {
        resultPane.setOpacity(0);

        if (_remaining) {
            selectionPane.setOpacity(1);
            selectionPane.toFront();
        } else {
        	int winnings = _gameBoard.getWinnings();
            winningsLabel.setText("Your final winnings are $" + Integer.toString(winnings));
            
            completedPane.setOpacity(1);
            completedPane.toFront();
        }
    }
    
    public void handleBackBtnClick() {
//    	System.out.println("Back");

        try {
        	_gameBoard.saveState();
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

    public void handleResetBtnClick() {

        _categories = _gameBoard.getCategoryNames();
        _remaining = true;

        _gameBoard.reset();
        updateBoardState();

        completedPane.setOpacity(0);
        selectionPane.setOpacity(1);
        selectionPane.toFront();
    }

    private void updateBoardState() {
    	// Record any changes to file
    	_gameBoard.saveState();

        ObservableList<Node> gridNodes = clueGrid.getChildren();

    	int catVal;
    	int clueVal;

        for (Node node: gridNodes) {
        	
        	if (GridPane.getRowIndex(node) == null) { 
        		clueVal = 0;
        	} else {
        		clueVal = GridPane.getRowIndex(node).intValue();
        	}
        	if (GridPane.getColumnIndex(node) == null) { 
        		catVal = 0; 
        	} else {
        		catVal = GridPane.getColumnIndex(node).intValue();
        	}
        	// Enable all top clue buttons
        	if (clueVal == 1) {
        		Button clueBtn = (Button) node;
                clueBtn.setDisable(false);
        	}
        	// Check whether it is a button or not
            if (clueVal == 0) {
                for (int i = 0; i < 6 ; i++) {
                    if (catVal == i) {
                        Label catLabel = (Label) node;
                        catLabel.setText(_categories.get(i));
                    }
                }
                // Check whether a question has been completed
            } else if (_gameBoard.isCompleted(clueVal-1,catVal)) {
            	// Disable if it has been completed
                Button clueBtn = (Button) node;
                clueBtn.setDisable(true);
                // Check if it is non-first row button that has no higher un-attempted clues
            } else if (clueVal != 1) {
                if (_gameBoard.isCompleted(clueVal-2,catVal)) {
                    Button clueBtn = (Button) node;
                    clueBtn.setDisable(false);
                }
            }
        }
    }

    public boolean checkRemaining() {
        boolean[][] completedArray = _gameBoard.getCompleted();
        for (int x = 0; x < completedArray.length; x++) {
            for (int y = 0; y < completedArray[0].length; y++) {
                if (! completedArray[x][y]) {
                        _remaining = true;
                        return true;
                }
            }
        }
        _remaining = false;
        return false;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	// Initialise and load GameBoard object
        _gameBoard = new GameBoard();
        // Make call to the GameBoard object to get all the actual categories
        _categories = _gameBoard.getCategoryNames();
        // Reflect any loaded state in GUI
        updateBoardState();

    }
}
