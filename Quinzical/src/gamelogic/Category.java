package gamelogic;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private List<Question> _questions = new ArrayList<Question>();
	
	public void ask(int qstnindex)
	{
		_questions.get(qstnindex).ask();
	}
	
	public boolean answer(int qstnindex, String answer)
	{
		boolean correct = _questions.get(qstnindex).answer(answer);
		return correct;
	}
}