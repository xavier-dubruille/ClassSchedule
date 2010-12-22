package main;

import gui.MyMenuBar;
import gui_schedule.*;
import gui_selection.*;
import model.StateFullSchedule;
import javax.swing.*;

public class Start {

    
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
			public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {

		
		StateFullSchedule state;
		MyMenuBar bar;
		FrameSchedule fSc;
		FrameSelection fSe;
		
		state=new StateFullSchedule();
		
		
		bar=new MyMenuBar(state);
		
		fSe=new FrameSelection(state);
		fSc=new FrameSchedule(state,bar,fSe);
		bar.setPanels(fSe,fSc);
		

		// we should start here a threak to folow the change in the files ?
	    // we also should star a thread to save the project if something wrong happen ( whith the handHookThread ?)

	}

}
