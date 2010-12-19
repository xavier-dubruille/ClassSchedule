package gui_selection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import model.StateFullSchedule;

public class FrameSelection extends JFrame {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DisplayPanel dp;
	OptionPanel op;
	JScrollPane scroll_display;
	JList jl;
	public FrameSelection(){
		//empty, not visible frame..

	}

	public FrameSelection(StateFullSchedule state) throws HeadlessException {
		super("Selection"); //faudrait p-e attraper la headLessException ici..
		
		
		setBounds( 50, 50, 210, 850);
		//this.setAlwaysOnTop(true); 
		//setDefaultCloseOperation(EXIT_ON_CLOSE); //Exit_on_close n'est sans doute pas ce qu'on veut ici..


		//p-e d'autre map, pour simplifier la recherche..
		SortedMap<Integer,Card_GUI> gui_cards=new TreeMap<Integer,Card_GUI>(); 
		
		dp=new DisplayPanel(state,gui_cards);

		
		dp.setVisible(true);
		op=new OptionPanel(dp, state,gui_cards);
		op.setMinimumSize(new Dimension(3,3));

		scroll_display=new JScrollPane(dp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	
		
		JSplitPane jSplit=new JSplitPane(JSplitPane.VERTICAL_SPLIT,op,scroll_display);
		jSplit.setAutoscrolls(true);
		jSplit.setMinimumSize(new Dimension(3,3));
		jSplit.setOneTouchExpandable(true);
		//jSplit.setMaximumSize(new Dimension(50,50));



		getContentPane().add(jSplit);


		setVisible(true);


	}

	public void update_from_state(){

		op.update_from_state(); // set the right options
		dp.update_default(); // display all the cards
		
		op.repaint();
		
		// dp.setAlignmentX(LEFT_ALIGNMENT);
		
		dp.repaint();
		//dp.setSize(50, 9900);
		dp.setPreferredSize(new Dimension(50,9900));
		//dp.setMinimumSize(new Dimension(500,9990));
		//dp.setMaximumSize(new Dimension(500,9990));
	


//		scroll_display.removeAll();

		System.out.println(scroll_display.getComponentCount());

		scroll_display.revalidate();
		scroll_display.validate();	
		scroll_display.repaint();

	}
	
	public DisplayPanel getDisplayPanel(){
		return dp;
	}


}
