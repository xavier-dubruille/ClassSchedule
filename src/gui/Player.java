package gui;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;



public class Player {

	private static Clip clip;

	static {
		try{
			AudioInputStream  stream = AudioSystem.getAudioInputStream(new File("./pig.wav"));
			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	public static void playPig(){

		new Thread ( new Runnable(){
			public void run(){
				clip.start();
				clip.drain();
				clip.stop();
			}
		}).start();


	}
}