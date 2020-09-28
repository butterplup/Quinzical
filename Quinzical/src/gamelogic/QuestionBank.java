package gamelogic;

import java.util.ArrayList;
import java.util.List;

//
public class QuestionBank {
	private List<Category> _categories = new ArrayList<Category>();
	public void saveState() {}
	public void loadState() {}
	
	public void ask(int CatIndex, int QstnIndex) {
		//access categorylist, ask question
		_categories.get(CatIndex).ask(QstnIndex);
	}
	
	public boolean answer(int CatIndex, int qstnindex, String answer)
	{
		boolean correct = _categories.get(CatIndex).answer(qstnindex, answer);
		return correct;
	}
	
	public int[][] getRandom(){
		//get 5 unique random category indices
		//for all random categories, get 5 unique character indices.
		//
		int[][] dummyOutput = {{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0},
				{0,0,0,0,0}};
		return dummyOutput;
	}
}

