package gamelogic;

/*
 * This class includes all the game-related methods that we need to
 * call from ui elements.
 */
public class GameBoard {
	int _winnings;
	QuestionBank _qBank= new QuestionBank();
	//randomly selected categories/questions
	//first row is categor indices
	//second row is question indices
	int[][] _indices = new int[6][5];
	
	public void ask(int catindex, int qIndex) {
		_qBank.ask(catindex, qIndex);
	}
	
	public boolean answer(int catIndex, int qIndex, String answer)
	{
		boolean correct = _qBank.answer(catIndex, qIndex, answer);
		return correct;
	}
	
	public int[][] randomise(){
		return _qBank.getRandom();
	}
}
