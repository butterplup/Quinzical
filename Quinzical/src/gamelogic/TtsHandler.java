package gamelogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *  This class offers methods for the execution of text to speech commands,
 *  and the adjustment of their playback speed. ttsspeed.ser stores the 
 *  user's preferred playback speed.
 *  Call constructor every time you want to use tts:
 * 	usage: new TtsHandler(); obj.loadSpeed(); obj.say(message) loads speed and says message
 *  @author bs and jh
 */
public class TtsHandler {
	double _speed = 1; 

	/**
	 * Reads a file that takes the desired speed that has been saved from settings menu
	 * @return true if all goes well
	 */
	public boolean loadSpeed() {
		File f = new File("ttsspeed.ser");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				FileInputStream fileIn = new FileInputStream("ttsspeed.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				_speed = (double) in.readObject();
				in.close();
				fileIn.close();
				return true;
			} catch (IOException i) {
				i.printStackTrace();
				return false;
			} catch (ClassNotFoundException c) {
				c.printStackTrace();
				return false;
			}

		}
		else
			return false;
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
	
	/**
	 * Plays the message on a background thread.
	 * @param message to be read
	 */
	public void say(String message) {
		new SpeakerThread(message, _speed).start();
	}
}
