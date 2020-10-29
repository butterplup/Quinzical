package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import application.Main;
import gamelogic.ldrboard.GameRecord;
import gamelogic.ldrboard.LeaderBoard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class LeaderboardController implements Initializable{
	
	@FXML
	private AnchorPane anchor;
	 
	@FXML
	private BorderPane leaderBoardPane;
	
	@FXML
	private TableView<GameRecord> ldrBoardTableView;
	
//	private ListView<String> ldrBoardListView;
	
	@FXML
	TableColumn nameColumn = new TableColumn("Name");
	@FXML
    TableColumn winningsColumn = new TableColumn("Score");
	@FXML
    TableColumn dateColumn = new TableColumn("Date");
    
    ObservableList<GameRecord> leaderBoardData = FXCollections.observableArrayList();
    LeaderBoard leaderboard = new LeaderBoard();
    
    public void handleBackBtnClick() {
        try {
        	// Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();
            anchor.getChildren().setAll(rootLayout);
            
            // Change layout from game menu to main menu

        } catch(Exception e) {
        	// Print in case of any errors
            e.printStackTrace();
        } 
    }

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		leaderBoardData = FXCollections.observableArrayList();
		leaderBoardData.addAll(leaderboard.getLeaderBoardList());
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("initials"));
	    winningsColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("winnings"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("dateString"));
	    
	    ldrBoardTableView.setItems(leaderBoardData);
	}
}
