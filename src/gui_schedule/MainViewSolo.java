package gui_schedule;

import gui.ConstrainHandler;
import gui.GUI_properties;
import gui_selection.Card_GUI;
import gui_selection.DisplayPanel;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
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

	private StateFullSchedule state;
	
	private TimeBox[] timeBoxes;
	private Teacher selectedTeacher;
	private Room selectedRoom;
	private Section selectedSection;
	private DisplayPanel dp;
	private OptionPanelSolo ops;
	private int size;
	public static final ImageIcon[] problem_level_images=new ImageIcon[3];
	public static final ImageIcon[] problem_category_image=new ImageIcon[3];

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
		

		problem_level_images[0]=new ImageIcon(getClass().getResource("/cross_30.png"));
		problem_level_images[1]=new ImageIcon(getClass().getResource("/carefull_30.png"));
		problem_level_images[2]=new ImageIcon(getClass().getResource("/check_30.png"));

		problem_category_image[0]=new ImageIcon(getClass().getResource("/teacher_30.png"));
		problem_category_image[1]=new ImageIcon(getClass().getResource("/group_30.png"));
		problem_category_image[2]=new ImageIcon(getClass().getResource("/table_30.png"));


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
		/*
		int pref[]=t.getPreferedTimeSlides();
		for(int i=0;i<pref.length;i++)
			if (pref[i]!=0)
				timeBoxes[i].setPref(pref[i]);
		*/

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
	 * @param 
	 */
	public void showPossibilities(ConstrainHandler consHand){

		//System.out.println("showPossiblilities: card="+c);
		//System.out.println("options concerned:"+ ops.getSelectedRoom()+ops.getSelectedSection()+ops.getSelectedTeacher());
		
		consHand.setViewParameters(ops.getSelectedTeacher(),ops.getSelectedSection(),ops.getSelectedRoom());

		// for all the timeBoxes..
		for(TimeBox t:timeBoxes){
			if (!(t.getStaticLabel()==null)) continue;

			if (!consHand.canI(t))
				t.drawAdvised(consHand.getReasonOfImpossibility(), consHand.getLevelOfImpossibility());
			else
				t.drawAdvised(4, 2);
			
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

	/**
	 * not used.. and should not be (because it create new "TimeBoxes")
	 */
	public void clear() {
		this.removeAll();
		drawEmptySchedule();
		
	}

	public void update_from_state() {
		System.out.println("MainVueSolo1: nombre de cartons: "+state.cards_size());
		cleanSchedule();
		
		selectedTeacher=null;
		selectedRoom=null;
		selectedSection=null;
		
		updateView(); // complitely useless.. but won't change a thing..
		
		System.out.println("MainVueSolo2: nombre de cartons: "+state.cards_size());

		
		
	}

}