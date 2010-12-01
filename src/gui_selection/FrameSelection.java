package gui_selection;

import model.StateFullSchedule;

import gui.MainPanel;
import gui_shedule.MyMenuBar;

import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
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

		//JScrollPane scroll_display=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	//	scroll_display.add(dp);

		JSplitPane jSplit=new JSplitPane(JSplitPane.VERTICAL_SPLIT,op,dp);


		getContentPane().add(jSplit);


		setVisible(true);


	}

	public void update_from_state(){

		op.update_from_state(); // set the right options
		dp.update_default(); // display all the cards
		op.repaint();
		dp.repaint();

	}


}
