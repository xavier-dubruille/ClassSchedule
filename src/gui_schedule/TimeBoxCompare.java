package gui_schedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import model.*;
import gui_selection.DisplayPanel;

public class TimeBoxCompare extends TimeBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainViewCompare mvc;
	//private OptionPanelCompare opc;
	private Section sectionConcerned;
	private Room roomConcerned;
	private Teacher teacherConcerned;

	private JPopupMenu popup;
	//private  DisplayPanel dp;

	/**
	 * only for the satic timeBox!
	 */
	public TimeBoxCompare(String s) {
		super(s);
	}
	public TimeBoxCompare(int day_period, boolean day) {
		super(day_period,day);
	}

	private TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, final MainViewCompare mvc, DisplayPanel dp) {

		super();

		
		this.mvc=mvc;
		//this.opc=opc;
		this.timePeriod=timePeriod;
		//this.dp=dp;


		myConstrainHandler.setType(2);
		
		if(c==null)
			clear();

		else {
			setCard(c);
			//reDraw(); // necessaire ??
		}

		popup = new JPopupMenu("Popup");
		JMenuItem menuItem;
		menuItem = new JMenuItem("Modifier");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,  "<html>Bient™t, Bient™t,.. ;-) </html>", 
								"Edition",JOptionPane.INFORMATION_MESSAGE); 
			}});
		popup.add(menuItem);
		menuItem = new JMenuItem("Faire autre chose..");
		//menuItem.addActionListener(this);
		popup.add(menuItem);
		
		
		this.setTransferHandler(new TimeBoxCompareTransferHandler(state,mvc,opc, dp));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {
				
				if (me.getButton()==MouseEvent.BUTTON3){

					if (me.isPopupTrigger()) {
						popup.show(me.getComponent(), me.getX(), me.getY());
					}
					return;
				}
				
				//System.out.println("mouse cliked!");
				
				TimeBox comp = (TimeBox) me.getSource();
				if (comp==null) return;
				TransferHandler handler = comp.getTransferHandler();
				if (handler==null) return;
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);

				if(!(comp.getCard()==null))
					mvc.showPossibilities(comp.getMyConstrainHandler());
					

			}
			
			public void mouseReleased(MouseEvent me){
				//System.out.println("mouse released!");
				if (me.isPopupTrigger()) {
					popup.show(me.getComponent(), me.getX(), me.getY());
				}
				/*
				mvc.constructView();
				mvc.revalidate();
				mvc.repaint();
				*/

			}

		});
	}

	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Section sectionConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=sectionConcerned;
		this.roomConcerned=null;
		this.teacherConcerned=null;
		myConstrainHandler.setViewParameters(teacherConcerned,sectionConcerned,roomConcerned);
	}
	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Teacher teacherConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=null;
		this.roomConcerned=null;
		this.teacherConcerned=teacherConcerned;
		myConstrainHandler.setViewParameters(teacherConcerned,sectionConcerned,roomConcerned);
	}
	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Room roomConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=null;
		this.roomConcerned=roomConcerned;
		this.teacherConcerned=null;
		myConstrainHandler.setViewParameters(teacherConcerned,sectionConcerned,roomConcerned);
	}

	public Section getSectionConcerned() {
		return sectionConcerned;
	}

	public Room getRoomConcerned() {
		return roomConcerned;
	}

	public Teacher getTeacherConcerned() {
		return teacherConcerned;
	}



}
