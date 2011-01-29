package gui;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;



public class Player {

	private static Clip pig,basic,intro;
	private static Thread pigThread;
	private static AudioInputStream  stream;
	
	private static AudioInputStream streamB;
	private static DataLine.Info info;
	
	

	static {
		try{
			streamB = AudioSystem.getAudioInputStream(new File("./basic.wav"));
			info = new DataLine.Info(Clip.class, streamB.getFormat());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	
	static {
		try{
			AudioInputStream streamC = AudioSystem.getAudioInputStream(new File("./intro.wav"));
			DataLine.Info info = new DataLine.Info(Clip.class, streamC.getFormat());
			intro = (Clip) AudioSystem.getLine(info);
			intro.open(streamC);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	
	static {
		try{
			stream = AudioSystem.getAudioInputStream(new File("./pig.wav"));
			DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
			pig = (Clip) AudioSystem.getLine(info);
			pig.open(stream);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 */
	public static void playPig(){
		pigThread = new Thread ( new Runnable(){
			public void run(){
				pig.stop();
				pig.flush();
				
				/*
				pig.close();
				try{
					
					stream = AudioSystem.getAudioInputStream(new File("./pig.wav"));
					DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat());
					pig = (Clip) AudioSystem.getLine(info);
					pig.open(stream);
					
				}catch (Exception e){
					e.printStackTrace();
				}
				*/
				
				pig.start();
				
			}
		});
		pigThread.start();
	}
	
	
	/**
	 * 
	 */
	public static void playNormal(){
	
		new Thread ( new Runnable(){
			public void run(){
			

				try{
					
					streamB = AudioSystem.getAudioInputStream(new File("./basic.wav"));
					info = new DataLine.Info(Clip.class, streamB.getFormat());
					
					basic = (Clip) AudioSystem.getLine(info);
					basic.open(streamB);
					basic.start();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				} 
				
				
				
				
			}
		}).start();
	
	}
	
	
	
	/**
	 * 
	 */
	public static void playIntro(){
	
		new Thread ( new Runnable(){
			public void run(){
				intro.stop();
				intro.flush();
				intro.start();
				
			}
		}).start();
	
	}
	
	
	
	
}