package gamelogic;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class SpeakerThread extends Thread {

	private String _message;
	private double _audioStretch;
	
	public SpeakerThread(String message, double speed) {
		_message = message;
		_audioStretch = 1/speed;
		
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
	
	@Override
	public void run() {
		
		try {
			// Command reads out each the desired message
			
//			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tts.scm"));
//                bufferedWriter.write("(voice_akl_nz_jdt_diphone)");
//                bufferedWriter.newLine();
//                bufferedWriter.write("(Parameter.set 'Duration_Stretch " + _audioStretch + ")");
//                bufferedWriter.newLine();
//                bufferedWriter.write("(SayText \"" + _message + "\")");
//                bufferedWriter.close();
            
			String command = "festival -b tts.scm";
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			// Executes command
			pb.start();
			Process process = pb.start();
			process.waitFor();
			process.destroy();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("SpeakerThread running");
	}	
}