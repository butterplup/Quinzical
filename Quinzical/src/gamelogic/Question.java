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
		
		say("false, the correct answer was " + _answer[0]);
		return false;
	}

	private void say(String message) {
		TtsHandler tts = new TtsHandler();
		tts.loadSpeed();
		tts.say(message);
	}
}