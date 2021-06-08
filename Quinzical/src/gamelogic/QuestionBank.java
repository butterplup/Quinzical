package gamelogic;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Randomisation--> shuffling qBank, we can mess with qBank any way we want
 * since practice module will use different qBank instance.
 */
public class QuestionBank implements Serializable{

	private static final long serialVersionUID = 1L;
	private List<Category> _categories = new ArrayList<Category>();
	// Left empty on purpose, needed for Serializable
	public void saveState() {}
	public void loadState() {}
	
	/**
	 * Gets all of the categories in file and makes Category objects
	 */
	public QuestionBank() {
		File directory = new File("categories");
		   File categoryList[] = directory.listFiles();
		   // Loops through all of the files names
		   for (File category: categoryList)
		   {
			   _categories.add(new Category(category));
		   }
	}
	
	/**
	 * Asks a particular question, reading out the clue with text-to-speech.
	 * @param CatIndex - desired index
	 * @param QstnIndex - desired index
	 * @return a string of the clue
	 */
	public String ask(int CatIndex, int QstnIndex) {
		//access category list, ask question
		return _categories.get(CatIndex).ask(QstnIndex);
	}
	
	/**
	 * Answers a question, checking if correct
	 * @param CatIndex - specific index
	 * @param qstnindex - specific index
	 * @param answer - user input answer
	 * @return true if correct
	 */
	public boolean answer(int CatIndex, int qstnindex, String answer)
	{
		boolean correct = _categories.get(CatIndex).answer(qstnindex, answer);
		return correct;
	}
	
	/**
	 * Shuffles the order of all the categories, and for each category shuffles the questions
	 */
	public void shuffle() {
		Collections.shuffle(_categories);
		for (Category cat: _categories) {
			cat.shuffle();
		}
	}
	
	/**
	 * Shuffles just the questions of the categories
	 * @param categoryName - desired category to shuffle
	 */
	public void shuffleCat(String categoryName) {
		for (Category category: _categories) {
			if (category.getName().equals(categoryName))
			{
				category.shuffle();
			}
		}
	}

	/**
	 * Gets the specific prompt text for this question
	 * @param question - desired index
	 * @param category - desired index
	 * @return - a string of the prompt
	 */
	public String getPrompt(int question, int category) {
		return _categories.get(category).getQPrompt(question);
	}
	
	public List<String> getFirst5Cat(){
		List<String> output = new ArrayList<String>();
		for (int i=0; i<=4;i++) {
			output.add(_categories.get(i).getName());
		}
		return output;
	}

	/**
	 * A list of every category to give user options
	 * @return list of category names
	 */
	public List<String> getAllCategories() {
		List<String> output = new ArrayList<String>();

		for (Category category : _categories) {
			output.add(category.getName());
		}

		return output;
	}

	/**
	 * Gets a Category object for a particular category name
	 * @param categoryName - name of desired category
	 * @return category - actual object with info
	 */
	public Category getCategory(String categoryName) {
		for (Category category: _categories) {
			if (category.getName().equals(categoryName))
			{
				return category;
			}
		}
		return null;
	}
}