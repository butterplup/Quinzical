package gamelogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * Call constructor every time you want to use tts:
 * usage: new TtsHandler(); obj.loadSpeed(); obj.say(message) loads speed and says message
 */
public class TtsHandler {
	double _speed = 1; 

	public boolean loadSpeed() {
		File f = new File("/tmp/ttsspeed.ser");
		if (f.exists() && !f.isDirectory()) {
			// do something
			try {
				FileInputStream fileIn = new FileInputStream("/tmp/ttsspeed.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				_speed = (double) in.readObject();
				in.close();
				fileIn.close();
//				System.out.println(_speed + " ttspeed.ser loaded");
				return true;
			} catch (IOException i) {
				i.printStackTrace();
				return false;
			} catch (ClassNotFoundException c) {
//				System.out.println("existing ttsspeed not found");
				c.printStackTrace();
				return false;
			}

		}
		else
			return false;
	}
	
	public void saveSpeed(double speed) {
		try {
			FileOutputStream fileOut = new FileOutputStream("/tmp/ttsspeed.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(speed);
			out.close();
			fileOut.close();

//			System.out.println("data saved to ttsspeed.ser");

		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public void say(String message) {
		new SpeakerThread(message, _speed).start();
	}
}
