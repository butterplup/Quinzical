package gamelogic;

import java.io.Serializable;
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
		_question = question;
		_answer = answer.split("/");

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
		return false;
	}

	private void say(String message) {
		new SpeakerThread(_question);
	}
}