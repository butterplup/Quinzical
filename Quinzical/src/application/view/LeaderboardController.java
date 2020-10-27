package application.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import application.Main;
import gamelogic.ldrboard.GameRecord;
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
    
    ObservableList<GameRecord> leaderBoardData = FXCollections.observableArrayList(
    		new GameRecord ("aosuh", 1937),
    		new GameRecord ("HI hi Bill song", 9582)
    		);
    
//    public void setListView() {
//    	stringSet.add("String 1");
//        stringSet.add("String 2");
//        stringSet.add("String 3");
//        stringSet.add("String 4");
////        leaderBoardData.setAll(stringSet);
//	    ldrBoardListView.setCellFactory(new Callback<ListView<String>, javafx.scene.control.ListCell<String>>(){
//	    	@Override
//	        public ListCell<String> call(ListView<String> listView)
//	        {
//	            return new ListCell<String>();
//	        }
//	    });
//    }
    
    public void handleBackBtnClick() {

        try {
        	
        	// Load the main menu layout
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("view/FXML/MainMenu.fxml"));
            AnchorPane rootLayout = loader.load();
            
            // Change layout from game menu to main menu

        } catch(Exception e) {
        	// Print in case of any errors
            e.printStackTrace();
        } 
    }
    
    public void handleResetBtnClick() {}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("initials"));
	    winningsColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("winnings"));
	    dateColumn.setCellValueFactory(new PropertyValueFactory<GameRecord,String>("dateString"));
	    ldrBoardTableView.setItems(leaderBoardData);
	}
}
