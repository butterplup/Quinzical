package gamelogic.ldrboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LeaderBoard {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<GameRecord> _leaderboard = new ArrayList<GameRecord>();
	
	public LeaderBoard() {
		addGameRecordNow("1asdas", 5);
		addGameRecordNow("134543sdfs", 500);
		addGameRecordNow("8dfgdasd", 1000);
		addGameRecordNow("1asdas", 5);
		saveState();
		loadState();
	}
	
	public ArrayList<GameRecord> getLeaderBoardList() {
		return _leaderboard;
	}
	
	public ObservableList<GameRecord> getObservableList(){
		ObservableList<GameRecord>list = FXCollections.observableArrayList();
		list.addAll(_leaderboard);
		return list;
	}
	
	/**
	 * adds game record instance to leaderboard.
	 * example usage: leaderBoard.add(new GameRecord("initials", winnings))
	 * @param gmRcrd
	 */
	public void addGameRecord(String init, int winnings, String dateString) {
		_leaderboard.add(new GameRecord(init, winnings, dateString));
	}
	
	public void addGameRecordNow(String init, int winnings) {
		_leaderboard.add(new GameRecord(init, winnings));
	}
	
	public void loadState() {
		File f = new File("leaderboard.txt");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				_leaderboard = new ArrayList<GameRecord>();
				Scanner scanner = new Scanner(f);
				
				 while(scanner.hasNextLine()){
			            String line[] = scanner.nextLine().split("\\|");
//			            System.out.println(scanner.nextLine());
//			            System.out.println(line[0]);
			            addGameRecord(line[0], Integer.parseInt(line[1]), line[2]);
			     }
				 scanner.close();
			} catch (IOException i) {
				i.printStackTrace();
				return;
			}
		}
	}
	
	public void saveState() {
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("leaderboard.txt"));
			for (GameRecord g: _leaderboard) {
				bufferedWriter.write(g.getInitials() + "|" + Integer.toString(g.getWinningsInt()) + "|" + g.getDateString());
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
}