package jeopardy;


public class SpeakerThread extends Thread {

	private String _message;
	
	public SpeakerThread(String message) {
		_message = message;
	}
	
	@Override
	public void run() {
		try {
			// Command reads out each the desired message
			String command = "espeak \"" + _message + "\"";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			// Executes command
			pb.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
