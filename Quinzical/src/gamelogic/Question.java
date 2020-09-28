package gamelogic;

public class Question {
	private int _reward;
	private String _question;
	private String _answer;
	private boolean _completed;
	public Question(int reward, String question, String answer) {
		_reward = reward;
		_question = question;
		_answer = answer;
		_completed = false;

	}
	
	public boolean isCompleted() {
		return _completed;
	}
	
	public int getReward() {
		return _reward;
	}
	
	public void ask() {
		_completed=true;
		//TTS the question
		//tts.say(_question);
	
	}
	
	public boolean answer(String answer) {
		
		if (answer.equalsIgnoreCase(_answer))//Answer was correct
		{
			//winnings.add
			//tts.say();
			return true;
		}
		else {//Answer was wrong			
		
				//tts.say();
			return false;
		}
	}
	
	public void reset() {
		_completed = false;
	}

}