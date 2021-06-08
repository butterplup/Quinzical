package application.view;

import gamelogic.GameBoard;
import gamelogic.ldrboard.LeaderBoard;
import gamelogic.textToSpeech.TextToSpeechThread;
import gamelogic.textToSpeech.ThreadCompleteListener;
import gamelogic.textToSpeech.NotifyingThread;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
//import javafx.scene.control;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;

/**
 * The controller class for the game module GUI
 * @author jh
 *
 */
public class GameMenuController implements Initializable, ThreadCompleteListener{

	// FXML Fields
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
    private Label finalWinningsLabel;
    @FXML
    private TextField answerField;
    @FXML
    private TextField inputNameField;
    @FXML
    private Button subGmRcrdBtn;
    @FXML
    private Label timerLabel;
    @FXML
    private ProgressBar timerBar;
    @FXML
    private Button submitBtn;
    @FXML
    private Button repeatBtn;
    
    // Fields for model
    private GameBoard _gameBoard;
    private List<String> _categories;
    private String _questionStr;
    private int _questionIndex;
    private int _categoryIndex;
    private boolean _remaining = true;
    private static final Integer STARTTIME = 8;
    private IntegerProperty timerSeconds = new SimpleIntegerProperty(STARTTIME*100);
    private Timeline timeline = new Timeline();

    /**
     * Handles events caused by any of the clue buttons.
     * Gets the corresponding question info
     * Changes scene to display question entry scene
     * @param event
     */
    public void handleClueSelected(ActionEvent event) {

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
        
        // Make category selector BorderPane opacity = 0
        selectionPane.setOpacity(0);
        // Make question presenter BorderPane opacity = 1
        cluePane.setOpacity(1);
        cluePane.toFront();
        
        submitBtn.setDisable(true);
        repeatBtn.setDisable(true);

        //say the clue
        _questionStr = _gameBoard.ask(_questionIndex, _categoryIndex);
        
        //freeze timer progressbar and label at maximum.
        timerSeconds.set((STARTTIME)*100);
        
        NotifyingThread ttsThread = new TextToSpeechThread(_questionStr);
        ttsThread.addListener(this); // add ourselves as a listener
        ttsThread.start();           // Start the Thread

        promptLabel.setText(_gameBoard.getPrompt(_questionIndex, _categoryIndex));


    }
    
	@Override
	public void notifyOfThreadComplete(Thread thread) {
		Platform.runLater(new Runnable() {
			@Override
			public void run(){
				startTimer();
			}
		});
        submitBtn.setDisable(false);
        repeatBtn.setDisable(false);
	}
	
    private void handleOutOfTime() {
    	System.out.println("OUT OF TIME!");
    	
    }

    private void startTimer() {
    	
    	if (timeline == null || timeline.getStatus()!= Timeline.Status.RUNNING) {	//dont touch timer if its still going
    		timerBar.progressProperty().bind(
        	        timerSeconds.divide(STARTTIME*100.0));
        	timerLabel.textProperty().bind(timerSeconds.divide(100).asString());
    		
        	timeline.setOnFinished((new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent actionEvent) {
                    handleOutOfTime();
                    }
    		}));
        	
    		timeline.getKeyFrames().add(
    				new KeyFrame(Duration.seconds(STARTTIME+1),
    				new KeyValue(timerSeconds, 0)));
    		timeline.playFromStart();
    	}
	}

	/**
     * Repeats the clue message through tts
     */
	public void handleRepeatBtnClick() {
		repeatBtn.setDisable(true);
		NotifyingThread ttsThread = new TextToSpeechThread(_questionStr);
		ttsThread.addListener(this); // add ourselves as a listener
		ttsThread.start(); // Start the Thread
	}

    /**
     * Gets the user's input for the answer and checks whether it is correct.
     * Then changes scene to display the result to the user.
     */
    public void handleSubmitBtnClick() {
        // Get entered field, or empty if didn't know
        String answer = answerField.getText();
        answerField.clear();
        // Check if correct
        if (_gameBoard.answer(_questionIndex, _categoryIndex, answer)) {
            resultLabel.setText("Your answer was correct! Good job!");
            NotifyingThread ttsThread = new TextToSpeechThread("Your answer was correct!");
            ttsThread.start();
            
        } else {
            resultLabel.setText("You were not correct.");
            NotifyingThread ttsThread = new TextToSpeechThread("Sorry, but that was incorrect!");
            ttsThread.start();
        }
        // Change the value of the winnings appropriately
        int winnings = _gameBoard.getWinnings();
        winningsLabel.setText("Current Winnings: $" + Integer.toString(winnings));
        // Makes the results BorderPane visible, while making the clue Pane transparent
        cluePane.setOpacity(0);
        resultPane.setOpacity(1);
        // Bring to front so user can interact with its nodes
        resultPane.toFront();
        
        timeline.stop();
    }

    /**
     * Handles events caused by user recognition of confirmation screens.
     * Progresses user on to appropriate next screen, either to select a new clue,
     * or the end game screen
     */
    public void handleOkBtnClick() {
        resultPane.setOpacity(0);

        //If any questions remain then
        if (_remaining) {
        	// Show user the clue selection screen
            selectionPane.setOpacity(1);
            selectionPane.toFront();
        } else {
        	// Otherwise display the final score, and tell them they've finished, add this game to leaderboard
        	int winnings = _gameBoard.getWinnings();
            winningsLabel.setText("Final Winnings: $" + Integer.toString(winnings));
            finalWinningsLabel.setText("Your final winnings are $" + Integer.toString(winnings));

            completedPane.setOpacity(1);
            completedPane.toFront();
        }
    }
    /**
     * Meant to take user back to main menu screen.
     * Handles the back button events.
     */
    public void handleBackBtnClick() {

        try {
        	_gameBoard.saveState();
        	// Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();
            
            // Change layout from game menu to main menu
            anchor.getChildren().setAll(rootLayout);

        } catch(Exception e) {
        	// Print in case of any errors
            e.printStackTrace();
        } 
    }

    /**
     * Resets all fields and the game board to new values,
     * such that the user can play again
     */
    public void handleResetBtnClick() {
        // Selects new questions and categories, removes any save files
        _gameBoard.reset();
        // Creates new save files for the reset game
        _gameBoard.saveState();

        try {
            // Reload the game menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/GameMenu.fxml"));
            AnchorPane rootLayout = loader.load();

            // Change layout from game menu to a new instance of game menu
            anchor.getChildren().setAll(rootLayout);

        } catch(Exception e) {
            // Print in case of any errors
            e.printStackTrace();
        }

    }

    public void handleSubGmRcrdBtnClick() {
    	String name = inputNameField.getText();
    	int w = _gameBoard.getWinnings();
    	
    	LeaderBoard ldrbrd = new LeaderBoard();
    	ldrbrd.addGameRecordNow(name, w);
    	ldrbrd.saveState();
    	
    	inputNameField.clear();
    	subGmRcrdBtn.setDisable(true);
    }
    /**
     * Sets the buttons to reflect their state as stored in GameBoard
     */
    private void updateBoardState() {
    	// Record any changes to file
    	_gameBoard.saveState();
    	// Get all of the nodes in the grid
        ObservableList<Node> gridNodes = clueGrid.getChildren();

    	int catVal;
    	int clueVal;
    	// Loop through each node
        for (Node node: gridNodes) {
        	// For nodes with either a row or col of 0, method gives null
        	if (GridPane.getRowIndex(node) == null) { 
        		// So need to adjust to avoid exceptions
        		clueVal = 0;
        	} else {
        		// Otherwise the method returns the right value
        		clueVal = GridPane.getRowIndex(node).intValue();
        	}
        	// Same as above
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

    /**
     * A method that checks if there are any un-attempted questions remaining
     * @return true if there are some questions left to answer
     */
    public boolean checkRemaining() {
        boolean[][] completedArray = _gameBoard.getCompleted();
        // Loop through whole array to check the state of every question
        for (int x = 0; x < completedArray.length; x++) {
            for (int y = 0; y < completedArray[0].length; y++) {
                if (! completedArray[x][y]) {
                	// It is sufficient that at least one non-attempted question remains
                        _remaining = true;
                        return true;
                }
            }
        }
        _remaining = false;
        return false;
    }

    /**
     * Run when the FXML file is loaded to initialise model objects to be utilised for the view
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	// Initialise and load GameBoard object
        _gameBoard = new GameBoard();
        // Make call to the GameBoard object to get any saved winnings
        int winnings = _gameBoard.getWinnings();
        winningsLabel.setText("Current Winnings: $" + Integer.toString(winnings));
        // Make call to the GameBoard object to get all the actual categories
        _categories = _gameBoard.getCategoryNames();
        // Reflect any loaded state in GUI
        updateBoardState();

    }


}
