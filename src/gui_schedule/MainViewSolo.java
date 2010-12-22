package gui_schedule;

import gui.GUI_properties;
import gui_selection.DisplayPanel;

import java.awt.GridLayout;

import javax.swing.JPanel;
import main.Main_properties;
import model.Card;
import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;

/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
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

	/**
	 * 
	 * @param state
	 * @param dp
	 */
	public MainViewSolo(StateFullSchedule state,DisplayPanel dp){
		this.state=state;
		this.dp=dp;

		selectedRoom=null;
		selectedTeacher=null;
		selectedSection=null;

		this.setLayout(new GridLayout(Main_properties.period_per_day+1,Main_properties.day_per_week+1));
		size=(Main_properties.period_per_day+1)*(Main_properties.day_per_week+1);
		timeBoxes=new TimeBox[size];

		dp.setMainViewSolo(this);
		//drawEmptySchedule();

	}

	/**
	 * 
	 */
	public void drawEmptySchedule(){


		int d=Main_properties.day_per_week+1;

		timeBoxes[0]=new TimeBoxSolo(0,true);
		add(timeBoxes[0]);

		for(int i=1; i<d; i++){
			timeBoxes[i]=new TimeBoxSolo(i,true);
			add(timeBoxes[i]);
		}

		for(int i=d; i<size; i++){
			if(i%d==0)
				timeBoxes[i]=new TimeBoxSolo(i/d,false);

			else{

				timeBoxes[i]=new TimeBoxSolo("",i,state,this);
			}
			add(timeBoxes[i]);
		}
	}

	/**
	 * 
	 * @return
	 */
	public DisplayPanel getDisplayPanel(){
		return dp;
	}

	/**
	 * 
	 * @param ops
	 */
	public void setOptionPanelSolo(OptionPanelSolo ops){
		this.ops=ops;
	}

	/**
	 * 
	 * @return
	 */
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}

	/**
	 * 
	 */
	private void cleanSchedule(){

		int d=Main_properties.day_per_week+1;

		for(int i=d; i<size; i++)
			if(i%d!=0){
				timeBoxes[i].clear();
				timeBoxes[i].setBackground(GUI_properties.timeBox_color_empty);
			}

	}

	/**
	 * 
	 * @param t
	 */
	public void setScheduleView(Teacher t){

		selectedRoom=null;
		selectedSection=null;
		selectedTeacher=t;

		//System.out.print("set teacher view.. ");
		//System.out.println("mainVueSolo: setScheduleView(Teacher): teachers card's:"+t.getCards());

		//let's clean first
		cleanSchedule();

		/* then let's get all the theacher's cards and see if theirs placed */
		for(Card c: t.getCards())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setCard(c);
			}

		/* and then let's fix the preferance's Teacher */

		int pref[]=t.getPreferedTimeSlides();
		for(int i=0;i<pref.length;i++)
			if (pref[i]!=0)
				timeBoxes[i].setPref(pref[i]);


	}

	/**
	 * 
	 * @param r
	 */
	public void setScheduleView(Room r){
		selectedTeacher=null;
		selectedSection=null;
		selectedRoom=r;

		//let's clean first
		cleanSchedule();

		/* then let's get all the room's cards and see if theirs placed */
		for(Card c: r.getCards())
			if(c.getTimePeriod()!=0){
				timeBoxes[c.getTimePeriod()].setCard(c);
			}


	}

	/**
	 * 
	 * @param s
	 */
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

	/**
	 * 
	 */
	public void updateView(){
		if(selectedTeacher!=null)
			setScheduleView(selectedTeacher);
		else if(selectedRoom!=null)
			setScheduleView(selectedRoom);
		else if(selectedSection!=null)
			setScheduleView(selectedSection);

	}

	/**
	 * will update the view by showing where the card c can or can not be placed
	 * @param c the Card on witch we'll base the update showing
	 */
	public void showPossibilities(Card c){

		//System.out.println("showPossiblilities: card="+c);
		//System.out.println("options concerned:"+ ops.getSelectedRoom()+ops.getSelectedSection()+ops.getSelectedTeacher());
		
		// step 1: the card can no be placed at all on this selected view
		if(!(ops.getSelectedTeacher()==null) && c.getTeacher()!=ops.getSelectedTeacher()){
			disabiliseView(0);
			return;
		}

		if(!(ops.getSelectedSection()==null) && !(c.getCard_sections().contains(ops.getSelectedSection())) ) {
			disabiliseView(1);
			return;
		}
/*
		if(!(ops.getSelectedRoom()==null) && !(c.findAllRoom(atTimePeriod)ops.getSelectedRoom()) ) {
			disabiliseView(2);
			return;
		}
		*/

		
		// for all the timeBoxes..
		lab:for(int i=0; i<timeBoxes.length; i++){
			
			if (!(timeBoxes[i].getStaticLabel()==null)) continue;
			int currentTimePeriod=timeBoxes[i].getTimePeriod();

			// step 2: set the teacher (from the card) 's preferences
			int [] teacherPreferedMoments = c.getTeacher().getPreferedTimeSlides();
			if (teacherPreferedMoments[currentTimePeriod]!=0){
				if (teacherPreferedMoments[currentTimePeriod]<=5)
					timeBoxes[i].drawAdvised(0, 1);
				else
					timeBoxes[i].drawAdvised(0, 0 );
				continue lab;
			}

			// step 3: let's check if the teacher is not giving already some courses
			for (Card card :c.getTeacher().getCards() )
			{
				//System.out.println("showPossiblities() --> "+card.getTimePeriod()+" ("+currentTimePeriod+")");
				if (card.getTimePeriod()==currentTimePeriod){
					timeBoxes[i].drawAdvised(0, 0);
					continue lab;
				}
			}
			
			
			// step 4: let's see if one of the section is not busy

			for (Section section: c.getCard_sections())
				for (Card card: section.getCards()){
					if (card.getTimePeriod()==currentTimePeriod){
						timeBoxes[i].drawAdvised(1, 0);
						continue lab; 
					}
				}
			
			
			// step 5: let's see if the possible room are not busy ..
			//if (c.getTimePeriod()==0) {
				int nbRooms=c.findAllRoom(currentTimePeriod).size();
				if (nbRooms==0){
					timeBoxes[i].drawAdvised(2, 0);
					continue lab;
				}
				else if(nbRooms <2 ){
					timeBoxes[i].drawAdvised(2, 1);
					continue lab;
				}
			//}
				timeBoxes[i].drawAdvised(4, 2);
			
		}



	}

	private void disabiliseView(int reason){
		System.out.println("vue grisŽe! ");


		int d=Main_properties.day_per_week+1;

		for(int i=d; i<size; i++){
			if(i%d!=0)
				timeBoxes[i].drawAdvised(reason, 0);
		}
	}

}