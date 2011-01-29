package main;


import java.io.File;
import java.io.InputStream;

import gui.MyMenuBar;
import gui.Player;
import gui_schedule.*;
import gui_selection.*;
import model.StateFullSchedule;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.*;

public class Start {
	public static FrameSchedule fSc;
	public static FrameSelection fSe;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}

	private static void createAndShowGUI() {


		final StateFullSchedule state;
		final MyMenuBar bar;

			
		Player.playPig();

		state=new StateFullSchedule();


		bar=new MyMenuBar(state);

		fSe=new FrameSelection(state);
		fSc=new FrameSchedule(state,bar,fSe);
		bar.setPanels(fSe,fSc);


		// we should start here a threak to folow the change in the files ?
		// we also should star a thread to save the project if something wrong happen ( whith the handHookThread ?)

	}

}
