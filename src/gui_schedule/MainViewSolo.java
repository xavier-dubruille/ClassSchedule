package gui_schedule;

import gui.CardTransferHandler;

import java.awt.Color;
import java.awt.GridLayout;

import model.Teacher;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.StateFullSchedule;
import model.Card;
import gui_selection.*;

public class MainViewSolo extends JPanel{


	StateFullSchedule state;
	TimeBox[] timeBoxes;
	Teacher selectedTeacher;
	private DisplayPanel dp;
	
	public MainViewSolo(StateFullSchedule state,DisplayPanel dp){
		this.state=state;
		this.dp=dp;


		this.setLayout(new GridLayout(8,7));
		timeBoxes=new TimeBox[56];

		drawEmptySchedule();

	}

	private void drawEmptySchedule(){
		timeBoxes[0]=new TimeBox("",0,state,this);
		add(timeBoxes[0]);

		for(int i=1; i<7; i++){
			timeBoxes[i]=new TimeBox("jour "+i,i,state,this);
			add(timeBoxes[i]);
		}

		for(int i=7; i<56; i++){
			if(i%7==0)
				timeBoxes[i]=new TimeBox("periode "+i/7+".",i,state,this);

			else
				timeBoxes[i]=new TimeBox("",i,state,this);

			add(timeBoxes[i]);
		}
	}

	public DisplayPanel getDisplayPanel(){
		return dp;
	}
	public void setScheduleView(Teacher t){
		
		selectedTeacher=t;
		
		System.out.println(t.getCard());
		/* let's get all the theacher's cards and see if theirs placed */
		for(Card c: t.getCard())
			if(c.getTimePeriod()!=0)
				timeBoxes[c.getTimePeriod()].setLabel(c.getHtmlRepresentation());


	}
	
	public void updateView(){
		setScheduleView(selectedTeacher);
	}
	
}