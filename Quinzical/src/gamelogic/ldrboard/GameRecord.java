package gamelogic.ldrboard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GameRecord {
	private final SimpleStringProperty initials;
	private final SimpleIntegerProperty winnings;
	private final SimpleStringProperty dateString;
	
	public GameRecord(String init, int winnings) {
		initials= new SimpleStringProperty(init);
		this.winnings= new SimpleIntegerProperty(winnings);
		
		//Sets _dateString field to current date.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.now();
		dateString = new SimpleStringProperty(date.format(formatter));
		
	}
	
//	public String getString() {
//		System.out.println(_initials + " " + _winnings + " " + _dateString);
//		return _initials + " " + _winnings + " " + _dateString;
//	}
	
	public String getInitials() {
		return initials.get();
	}
	
	public void setInitials(String init) {
		initials.set(init);
	}
	
	public String getWinnings() {
		return "$" + Integer.toString(winnings.get());
	}
	
	public void setWinnings(int integer) {
		winnings.set(integer);
	}
	
	public String getDateString() {
		return dateString.get();
	}
	
	public void setDateString(String str) {
		dateString.set(str);
	}

}