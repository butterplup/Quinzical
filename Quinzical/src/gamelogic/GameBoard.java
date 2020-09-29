package gamelogic;

import java.util.List;

/*
 * This class includes all the game-related methods that we need to
 * call from ui elements.
 * HOW TO USE:
 * 	shuffle shuffles entire questionbank, so that first 5 categories are random,
 * 	and first 5 questions from each category is random.
 * 
 * 	Then buttons will call ask(0-4, 0,4) and answer(0-4,0-4)
 * 
 * Here's some basic code i was using to test
 *  //TESTING
            GameBoard gBoard = new GameBoard();
            System.out.println(gBoard.getCategoryNames());
            gBoard.ask(0,0);
            
            if (gBoard.answer(0,0,"bumbum")) {
            	System.out.println("GOOD");
            }
            else System.out.println("BAD");
            
 */													
public class GameBoard {
	int _winnings=0;
	QuestionBank _qBank;
	//randomly selected categories/questions
	//first row is category indices
	//second-6th row is question indices
	boolean[][] _completed = new boolean[5][5]; //0=incomplete.1=completed.
	
	public GameBoard() {
		loadState();
		_qBank = new QuestionBank();
		_qBank.shuffle();
	}
	
	public void ask(int qIndex,int catindex) {
		_qBank.ask(catindex, qIndex);
	}
	
	public boolean answer(int qIndex, int catIndex, String answer)
	{
		boolean wasCorrect = _qBank.answer(catIndex, qIndex, answer);
		if (wasCorrect)
		{
			_winnings+=qIndex*100;
		}
		_completed[qIndex][catIndex]=true;
		return wasCorrect;
	}
	
	public int getWinnings() {
		return _winnings;
	}
	
	/*
	 * returns 2d boolean matrix, where 1=completed and 0=incomplete
	 */
	public boolean[][] getCompleted(){
		return _completed;
	}
	
	public List<String> getCategoryNames(){
		return _qBank.getFirst5Cat();
	}
	
	/*
	 * if fresh instance, do nothing.
	 * if saved state exists, update _winnings, update _completed,
	 */
	private void loadState() {
		
	}
}
