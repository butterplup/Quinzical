package gamelogic;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * This class makes another thread to read out a message tts
 * @author jh and bs
 *
 */
public class SpeakerThread extends Thread {

	private String _message;
	private double _audioStretch;
	
	/**
	 * Constructor sets up fields before run() is called
	 * @param message
	 * @param speed - playback speed.
	 */
	public SpeakerThread(String message, double speed) {
		_message = message;
		_audioStretch = 1/speed;
		// Commands to get NZ accent, and setting the speed, and message
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tts.scm"));
			bufferedWriter.write("(voice_akl_nz_jdt_diphone)");
	        bufferedWriter.newLine();
	        bufferedWriter.write("(Parameter.set 'Duration_Stretch " + _audioStretch + ")");
	        bufferedWriter.newLine();
	        bufferedWriter.write("(SayText \"" + _message + "\")");
	        bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}
	
	/**
	 * Passes the tts.scm file as an argument for the "festival -b" command.
	 * This plays the specified text string using the text-to-speech tool
	 * festival.
	 */
	@Override
	public void run() {
		
		try {
			// Command reads out each the desired message
			String command = "festival -b tts.scm";
			// Bash command executives festival
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			// Executes command
			pb.start();
			Process process = pb.start();
			process.waitFor();
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}