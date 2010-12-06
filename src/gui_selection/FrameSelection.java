package gui_selection;

import model.StateFullSchedule;

import gui.MainPanel;
import gui_shedule.MyMenuBar;

import java.awt.HeadlessException;

import javax.swing.*;
import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class FrameSelection extends JFrame {


	DisplayPanel dp;
	OptionPanel op;
	JScrollPane scroll_display;
	JList jl;
	public FrameSelection(){
		//empty, not visible frame..

	}

	public FrameSelection(StateFullSchedule state) throws HeadlessException {
		super("Selection"); //faudrait p-e attraper la headLessException ici..


		jl=new JList(); //tests
		jl.add(new JLabel("testeuu"));
		
		setBounds( 100, 100, 260, 850);
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //Exit_on_close n'est sans doute pas ce qu'on veut ici..


		dp=new DisplayPanel(state);

		dp.setBackground(Color.orange);
		dp.setVisible(true);
		op=new OptionPanel(dp, state);

		scroll_display=new JScrollPane(dp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		//scroll_display.add(dp);
		dp.setBackground(Color.blue);
		
		JSplitPane jSplit=new JSplitPane(JSplitPane.VERTICAL_SPLIT,op,scroll_display);


		getContentPane().add(jSplit);


		setVisible(true);


	}

	public void update_from_state(){

		op.update_from_state(); // set the right options
		dp.update_default(); // display all the cards
		dp.add(new JLabel("TESTTTTTTTTTTTTTTTTTT"));
		op.repaint();
		
		dp.setAlignmentX(LEFT_ALIGNMENT);
		//dp.setComponentZOrder(scroll_display, 9);
		dp.repaint();
		dp.setSize(1000, 800);
		dp.setPreferredSize(new Dimension(500,500));
		dp.setMinimumSize(new Dimension(500,500));
		dp.setMaximumSize(new Dimension(500,500));
		//dp.resize(200, 600);
	


//		scroll_display.removeAll();

		
		//jl.add(new JLabel("testeuu 2"));
		System.out.println(scroll_display.getComponentCount());

		scroll_display.revalidate();
		scroll_display.validate();	
		scroll_display.repaint();

	}


}
