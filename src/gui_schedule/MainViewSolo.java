package gui_schedule;

import gui.GUI_Propreties;
import gui_selection.DisplayPanel;

import java.awt.GridLayout;

import javax.swing.JPanel;
import main.Propreties;
import model.Card;
import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;

public class MainViewSolo extends JPanel{
	private static final long serialVersionUID = 1L;

	StateFullSchedule state;
	TimeBox[] timeBoxes;
	Teacher selectedTeacher;
	Room selectedRoom;
	Section selectedSection;
	private DisplayPanel dp;
	private OptionPanelSolo ops;
	private int size;

	public MainViewSolo(StateFullSchedule state,DisplayPanel dp){
		this.state=state;
		this.dp=dp;
		
		selectedRoom=null;
		selectedTeacher=null;
		selectedSection=null;

		this.setLayout(new GridLayout(Propreties.period_per_day+1,Propreties.day_per_week+1));
		size=(Propreties.period_per_day+1)*(Propreties.day_per_week+1);
		timeBoxes=new TimeBox[size];

		//drawEmptySchedule();

	}

	public void drawEmptySchedule(){
	
		
		int d=Propreties.day_per_week+1;
		
		timeBoxes[0]=new TimeBoxSolo("");
		add(timeBoxes[0]);

		for(int i=1; i<d; i++){
			timeBoxes[i]=new TimeBoxSolo("jour "+i);
			add(timeBoxes[i]);
		}

		for(int i=d; i<size; i++){
			if(i%d==0)
				timeBoxes[i]=new TimeBoxSolo("periode "+i/d+".");

			else{
				
				timeBoxes[i]=new TimeBoxSolo("",i,state,this);
			}
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
		
		int d=Propreties.day_per_week+1;
		
		for(int i=d; i<size; i++)
			if(i%d!=0){
				timeBoxes[i].clear();
				timeBoxes[i].setBackground(GUI_Propreties.card_default_background);
			}

	}
	public void setScheduleView(Teacher t){

		selectedRoom=null;
		selectedSection=null;
		selectedTeacher=t;

		//System.out.print("set teacher view..");
		//System.out.println(t.getCard());

		//let's clean first
		cleanSchedule();

		/* then let's get all the theacher's cards and see if theirs placed */
		for(Card c: t.getCard())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setCard(c);
			}
		
		/* and then let's fix the preferance's Teacher */
		int pref[]=t.getPreferedTimeSlides();
		for(int i=0;i<pref.length;i++)
			if (pref[i]!=0)
				timeBoxes[i].setPref(pref[i]);


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