package gui_selection;

import model.StateFullSchedule;

import gui.MainPanel;
import gui_shedule.MyMenuBar;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class FrameSelection extends JFrame {
	

	DisplayPanel dp;
	OptionPanel op;
	
	public FrameSelection(){
		//empty, not visible frame..
	
	}

	public FrameSelection(StateFullSchedule state) throws HeadlessException {
		super("Selection"); //faudrait p-e attraper la headLessException ici..

		
		setBounds( 100, 100, 260, 850);
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //Exit_on_close n'est sans doute pas ce qu'on veut ici..
		

		
		dp=new DisplayPanel(state);
		op=new OptionPanel(dp, state);
		
		JSplitPane js=new JSplitPane(JSplitPane.VERTICAL_SPLIT,op,dp);
		
		
		getContentPane().add(js);
		
		
		setVisible(true);

		
	}
	
	public void update_from_state(){

		op.update_from_state(); // set the right options
		dp.update_default(); // display all the cards
		op.repaint();
		dp.repaint();
		
	}


}
