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
    private GridPane clueGrid;

    private GameBoard _gameBoard;
    private List<String> _categories;

    public void handleClueSelected(ActionEvent event) {
    	System.out.println("Clue selected");
        Button clickedBtn = (Button) event.getSource();
        
        int questionIndex;
        int categoryIndex;
        
    	questionIndex = GridPane.getRowIndex(clickedBtn).intValue() - 1;
    	
    	if (GridPane.getColumnIndex(clickedBtn) == null) { 
    		categoryIndex = 0; 
    	} else {
    		categoryIndex = GridPane.getColumnIndex(clickedBtn).intValue();
    	}

        // Make category selector BorderPane opacity = 0
        selectionPane.setOpacity(0);
        // Make question presenter BorderPane opacity = 1
        cluePane.setOpacity(1);
        cluePane.toFront();

    }
    
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

    private void loadBoardState() {
        // Make call to the GameBoard object to get all the actual categories
        _categories = _gameBoard.getCategoryNames();
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
        	
            if (clueVal == 0) {
                for (int i = 0; i < 6 ; i++) {
                    if (catVal == i) {
                        Label catLabel = (Label) node;
                        catLabel.setText(_categories.get(i));
                    }
                }
            } else if (_gameBoard.isCompleted(clueVal-1,catVal)) {
                Button clueBtn = (Button) node;
                clueBtn.setDisable(true);
            } else if (clueVal != 1) {
                if (_gameBoard.isCompleted(clueVal-2,catVal)) {
                    Button clueBtn = (Button) node;
                    clueBtn.setDisable(false);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	// Initialise and load GameBoard object
        _gameBoard = new GameBoard();
        // Reflect any loaded state in GUI
        loadBoardState();


    }
}
