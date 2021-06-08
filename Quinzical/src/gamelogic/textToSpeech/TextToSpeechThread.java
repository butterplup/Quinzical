package gamelogic.textToSpeech;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class TextToSpeechThread extends NotifyingThread{

	private String _message;
	private Process _process;
	private double _speed;
	
	public TextToSpeechThread() {
	}
	
	public TextToSpeechThread(String message) {
		_message = message;
	}
	
	@Override
	public void doRun() {
//		TtsHandler tts = new TtsHandler();
//		tts.say(_question);
		
		// Commands to get NZ accent, and setting the speed, and message
		try {
			buildScheme(_message, loadSpeed());
	     // Command reads out each the desired message
	     	speak();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double loadSpeed() {
		File f = new File("ttsspeed.ser");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				FileInputStream fileIn = new FileInputStream("ttsspeed.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				double speed = (double) in.readObject();
				in.close();
				fileIn.close();
				return speed;
			} catch (IOException i) {
				i.printStackTrace();
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
			}

		}
		return 1;
	}
	
	/**
	 * Saves info from settings menu to a file
	 * @param speed to be saved
	 */
	public void saveSpeed(double speed) {
		try {
			_speed = speed;
			FileOutputStream fileOut = new FileOutputStream("ttsspeed.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(_speed);
			out.close();
			fileOut.close();

		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	private void buildScheme(String message, double speed) {
		try {
			double audioStretch = 1/speed;
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("tts.scm"));
			bufferedWriter.write("(voice_akl_nz_jdt_diphone)");
	        bufferedWriter.newLine();
	        bufferedWriter.write("(Parameter.set 'Duration_Stretch " + audioStretch + ")");
	        bufferedWriter.newLine();
	        bufferedWriter.write("(SayText \"" + message + "\")");
	        bufferedWriter.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void speak() {
		String command = "festival -b tts.scm";
			// Bash command executives festival
		ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			// Executes command
		try {
			Process p = pb.start();
			p.waitFor();
			p.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
