package gamelogic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Category represents an individual category of questions, as read in from file
 * Can be saved.
 * @author bs and jh
 *
 */
public class Category implements Serializable{
	
	// Fields
	private static final long serialVersionUID = 1L;
	private String _name;
	// All questions in this category
	private List<Question> _questions = new ArrayList<Question>();
	
	/**
	 * When constructed, the category file is read, and information for the questions
	 * is parsed out, constructing question objects
	 * @param file - a file found on the system in the categories folder
	 */
	public Category(File file) {
		try {
			_name = file.getName();
			// Reading the files for the clue/answer combo
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine())
            {
                String str = myReader.nextLine();
                // Splits between sections, and for multiple possible answers
                String questionLine[] = str.split("\\|");
                // Question constructor
                _questions.add(new Question(questionLine[0], questionLine[2], questionLine[1]));
                
            }
            // File safety
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Shuffles the order of the questions in the list
	 */
	public void shuffle() {
		Collections.shuffle(_questions);
	}
	
	/**
	 * Asks a question to the user, and shows the string representation of it
	 * @param qstnindex - the index position for the particular question
	 * @return a string of the clue
	 */
	public String ask(int qstnindex)
	{
		// A call to Question#ask, on one particular question
		return _questions.get(qstnindex).ask();
		
	}
	
	/**
	 * Checks if the input answer if correct or not
	 * @param qstnindex - index position for the desired question to answer
	 * @param answer - string of the input answer
	 * @return boolean correct that gives the status of the answer (right/wrong)
	 */
	public boolean answer(int qstnindex, String answer)
	{
		boolean correct = _questions.get(qstnindex).answer(answer);
		return correct;
	}
	
	// SECTION OF GETTERS
	
	/**
	 * Gets the name of the category as a string
	 * @return string representation of the category name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Gets the prompt (eg what is) of a particular clue
	 * @param questionIndex - desired clue's index
	 * @return string representation of the prompt
	 */
	public String getQPrompt(int questionIndex) {
		return _questions.get(questionIndex).getPrompt();
	}

	/**
	 * Gets the hint (eg first letters) of a particular clue
	 * @param questionIndex - desired clue's index
	 * @return string representation of the hint
	 */
	public String getQHint(int questionIndex) {
		return _questions.get(questionIndex).getHint();
	}

	/**
	 * Gets the answer (eg question) of a particular clue
	 * @param questionIndex - desired clue's index
	 * @return string representation of the answer
	 */
	public String getQAnswer(int questionIndex) {
		return _questions.get(questionIndex).getAnswer();
	}
}