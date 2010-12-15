package gui_schedule;

import gui.CardTransferHandler;
import gui.GUI_Propreties;

import java.awt.Color;
import java.awt.GridLayout;

import model.Teacher;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.StateFullSchedule;
import model.*;
import gui_selection.*;

public class MainViewSolo extends JPanel{


	StateFullSchedule state;
	TimeBox[] timeBoxes;
	Teacher selectedTeacher;
	Room selectedRoom;
	Section selectedSection;
	private DisplayPanel dp;
	private OptionPanelSolo ops;

	public MainViewSolo(StateFullSchedule state,DisplayPanel dp){
		this.state=state;
		this.dp=dp;


		this.setLayout(new GridLayout(8,7));
		timeBoxes=new TimeBox[56];

		//drawEmptySchedule();

	}

	public void drawEmptySchedule(){
		timeBoxes[0]=new TimeBox("");
		add(timeBoxes[0]);

		for(int i=1; i<7; i++){
			timeBoxes[i]=new TimeBox("jour "+i);
			add(timeBoxes[i]);
		}

		for(int i=7; i<56; i++){
			if(i%7==0)
				timeBoxes[i]=new TimeBox("periode "+i/7+".");

			else
				timeBoxes[i]=new TimeBox("",i,state,this);

			add(timeBoxes[i]);
		}
	}

	public DisplayPanel getDisplayPanel(){
		return dp;
	}
	
	public void setOptionPanelSolo(OptionPanelSolo ops){
		this.ops=ops;
	}
	
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}

	private void cleanSchedule(){
		for(int i=7; i<56; i++)
			if(i%7!=0){
				timeBoxes[i].setLabel("");
				timeBoxes[i].setCard(null);
				timeBoxes[i].setBackground(GUI_Propreties.card_default_background);
			}

	}
	public void setScheduleView(Teacher t){

		selectedRoom=null;
		selectedSection=null;
		selectedTeacher=t;

		//System.out.println(t.getCard());

		//let's clean first
		cleanSchedule();

		/* then let's get all the theacher's cards and see if theirs placed */
		for(Card c: t.getCard())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setLabel(c.getHtmlRepresentation());
				timeBoxes[c.getTimePeriod()].setCard(c);
			}


	}
	
	public void setScheduleView(Room r){


		selectedTeacher=null;
		selectedSection=null;
		selectedRoom=r;

		//let's clean first
		cleanSchedule();

		/* then let's get all the theacher's cards and see if theirs placed */
		for(Card c: r.getCards())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setLabel(c.getHtmlRepresentation());
				timeBoxes[c.getTimePeriod()].setCard(c);
			}


	}
	
	public void setScheduleView(Section s){

		selectedRoom=null;
		selectedTeacher=null;
		selectedSection=s;


		//let's clean first
		cleanSchedule();

		/* then let's get all the theacher's cards and see if theirs placed */
		for(Card c: s.getCards())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setLabel(c.getHtmlRepresentation());
				timeBoxes[c.getTimePeriod()].setCard(c);
			}


	}

	public void updateView(){
		if(selectedTeacher!=null)
			setScheduleView(selectedTeacher);
		else if(selectedRoom!=null)
			setScheduleView(selectedRoom);
		else if(selectedSection!=null)
			setScheduleView(selectedSection);
	}

}