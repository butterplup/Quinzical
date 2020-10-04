package gamelogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String _name;
	private List<Question> _questions = new ArrayList<Question>();
	
	public Category(File file) {
		try {
			_name = file.getName();
			
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                String str = myReader.nextLine();
                String questionLine[] = str.split("\\|");

                _questions.add(new Question(questionLine[0], questionLine[2], questionLine[1]));
                
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public void shuffle() {
		Collections.shuffle(_questions);
	}
	
	public String ask(int qstnindex)
	{
		return _questions.get(qstnindex).ask();
		
	}
	
	public boolean answer(int qstnindex, String answer)
	{
		boolean correct = _questions.get(qstnindex).answer(answer);
		return correct;
	}
	
	public String getName() {
		return _name;
	}

	public String getQPrompt(int questionIndex) {
		return _questions.get(questionIndex).getPrompt();
	}

	public String getQHint(int questionIndex) {
		return _questions.get(questionIndex).getHint();
	}

	public String getQAnswer(int questionIndex) {
		return _questions.get(questionIndex).getAnswer();
	}
}