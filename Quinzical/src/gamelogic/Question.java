package gamelogic;

import java.io.Serializable;
import java.text.Normalizer;

/**
 * Class to represent an individual question, and all the components of it
 * @author jh and bs
 *
 */
public class Question implements Serializable{

	private static final long serialVersionUID = 1L;
	private String _question;
	private String[] _answer;
	// The prompt (e.g what is X)
	private String _whatX;

	/**
	 * Takes in data read in from file and parses into usable form. For all params refer to fields
	 * @param question
	 * @param answer
	 * @param whatX
	 */
	public Question(String question, String answer, String whatX) {
		
		String normalised = Normalizer.normalize(answer, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
		_question = question;
		_answer = normalised.split("/");
		_whatX = whatX;
	}
	
	/**
	 * Says the question using tts, and returns the string value
	 * @return string of the clue
	 */
	public String ask() {
		say(_question);
		return _question;
	}
	
	/**
	 * Checks to see if the supplied answer matches the one on record. 
	 * Speaks the result of this to user
	 * @param answer - user input answer
	 * @return boolean, true if user correct
	 */
	public boolean answer(String answer) {
		
		for (int i=0 ; i<_answer.length; i++) {
			if (answer.equalsIgnoreCase(_answer[i])) {//Answer was correct			
				say("Your answer was correct!");
				return true;
			}
		}
		say("Sorry, but that was incorrect");
		return false;
	}

	/**
	 *  Create tts intermediate which creates new Thread to speak a message
	 * @param message - what needs to be said
	 */
	private void say(String message) {
		TtsHandler tts = new TtsHandler();
		tts.loadSpeed();
		tts.say(message);
	}

	/**
	 * getter method to get the prompt to show user
	 * @return
	 */
	public String getPrompt() {
		return _whatX;
	}

	/**
	 * Checks how many answers are acceptable and gets the first letter of each
	 * to show as a hint to the user
	 * @return a hint, the first letter of the answer/s
	 */
	public String getHint() {
		String hint = "Hint: First letter of answer";

		if (_answer.length > 1) {
			for (int i = 0; i < _answer.length - 1; i++) {
				hint = hint + "s: " + _answer[i].charAt(0) + " or ";
			}
		} else {
			hint = hint + ": ";
		}
		return hint + _answer[_answer.length - 1].charAt(0);
	}

	/**
	 * Gets 1 or more answers properly formatted to be able to show the user
	 * @return a string of formatted answers for when there is more than one
	 */
	public String getAnswer() {
		String answer = "";

		if (_answer.length > 1) {
			for (int i = 0; i < _answer.length - 1; i++) {
				answer = answer + _answer[i] + " or ";
			}

		}
		return answer + _answer[_answer.length - 1];
	}
}