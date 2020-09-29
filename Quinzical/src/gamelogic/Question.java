package gamelogic;

import java.io.Serializable;

public class Question implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//	private int _reward;
	private String _question;
	private String _answer;
	private String _whatX;
	public Question(String question, String answer, String whatX) {
//		_reward = reward;
		_question = question;
		_answer = answer;
		_whatX = whatX;
	}
	
	public void ask() {
		say(_question);
		//TTS the question
		//tts.say(_question);
	}
	
	public boolean answer(String answer) {
		
		if (answer.equalsIgnoreCase(_answer))//Answer was correct
		{
			//winnings.add
		
			say("Your answer was correct!");
			
			return true;
		}
		else {//Answer was wrong			
		
			say("Wrong, the correct answer was " + _answer);
			return false;
		}
	}

	private void say(String message) {
		new SpeakerThread(_question);
	}
}