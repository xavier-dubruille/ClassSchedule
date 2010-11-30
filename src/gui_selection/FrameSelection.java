package gui_selection;

import model.StateFullSchedule;

import gui.MainPanel;
import gui_shedule.MyMenuBar;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

public class FrameSelection extends JFrame {


	public FrameSelection(StateFullSchedule state) throws HeadlessException {
		super("Selection"); //faudrait p-e attraper la headLessException ici..
		
		
		setBounds( 100, 100, 800, 660);
		setDefaultCloseOperation(EXIT_ON_CLOSE); //Exit_on_close n'est sans doute pas ce qu'on veut ici..
		

		JSplitPane js=new JSplitPane();
		DisplayPanel dp=new DisplayPanel(state);
		OptionPanel op=new OptionPanel(dp);
		
		js.add(dp);
		js.add(op);
		
		getContentPane().add(js);
		
		
		setVisible(true);

		
	}


}
