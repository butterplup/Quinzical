package gamelogic;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;

public class Question implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	private int _reward;
	private String _question;
	private String[] _answer;
	private String _whatX;

	public Question(String question, String answer, String whatX) {
//		_reward = reward;
		
		String normalised = Normalizer.normalize(answer, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
		_question = question;
		_answer = normalised.split("/");

		_whatX = whatX;
	}
	
	public String ask() {
		say(_question);
		return _question;
		//TTS the question
		//tts.say(_question);
	}
	
	public boolean answer(String answer) {
		
		for (int i=0 ; i<_answer.length; i++)
		{
			if (answer.equalsIgnoreCase(_answer[i]))//Answer was correct
			{
				//winnings.add
			
				say("Your answer was correct!");
				
				return true;
			}
		}
		say("Sorry, but that was incorrect");
		//say("false, the correct answer was " + _answer[0]);
		return false;
	}

	private void say(String message) {
		TtsHandler tts = new TtsHandler();
		tts.loadSpeed();
		tts.say(message);
	}

	public String getPrompt() {
		return _whatX;
	}

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