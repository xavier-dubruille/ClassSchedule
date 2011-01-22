package gui_schedule;

import gui.GUI_properties;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Main_properties;
import model.*;
import gui_selection.DisplayPanel;

/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas 
 */
public class MainViewCompare extends JPanel{

	private static final long serialVersionUID = 1L;
	private StateFullSchedule state;
	private OptionPanelCompare opc;
	private DisplayPanel dp;
	private TimeBoxCompare firstTimeBox;
	
	public MainViewCompare(StateFullSchedule state, DisplayPanel dp){
		this.state=state;
		this.dp=dp;
	    constructDefaultView();
	}


	/**
	 * for now, it's ugly..
	 * but it's supose to be the nice, default view, when nothing is selected..
	 */
	private void constructDefaultView() {
		//TimeBoxCompare timeBoxCompare;
		int rows=7;
		int cols=2;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		firstTimeBox=new TimeBoxCompare("*** Selectionnez un jour ***");
		add(firstTimeBox);
		add(new TimeBoxCompare("Selectionnez une option"));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(i,false));
			add(new TimeBoxCompare(" "));
		}
		this.revalidate();
		this.repaint();
		
	}


	/**
	 * 
	 */
	public void constructView(){
		String compareOn=opc.getCompareOn();
		ArrayList<String> selectedDays=opc.getSelectedDays();
		if(compareOn==null) return;
		if(compareOn.equals(GUI_properties.option_teachers)){
			constructViewFromTeachers(selectedDays, opc.getTeachersToCompare());
		}
		else if(compareOn.equals(GUI_properties.option_rooms)){

			constructViewFromRooms(selectedDays, opc.getRoomsToCompare());
		}
		else if(compareOn.equals(GUI_properties.option_sections)){
			constructViewFromSections(selectedDays, opc.getSectionsToCompare());
		}
	}
	
	/**
	 * 
	 * @param days
	 * @param teachers
	 */
	private void constructViewFromTeachers(ArrayList<String> days, ArrayList<Teacher> teachers){
		//	System.out.println("days: "+days+"--- teachers: "+teachers);

		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Main_properties.period_per_day+1;
		int cols=teachers.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		firstTimeBox=new TimeBoxCompare("*** "+days.get(0)+" ***");
		add(firstTimeBox);
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(teachers.get(i).getLastName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(i,false));
			for (int j=0; j<cols-1;j++){
				int timePeriod = calculateTimePeriod(i,days.get(0));
				Card c=getTeacherCardAtThatTime(teachers.get(j),timePeriod);
				timeBoxCompare=new TimeBoxCompare(c,state,timePeriod,opc,this,dp,teachers.get(j));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * 
	 * @param teacher
	 * @param timePeriod
	 * @return
	 */
	private Card getTeacherCardAtThatTime(Teacher teacher, int timePeriod) {
		// find the timePeriod --> 
		

		//System.out.println("getTeacherAtTime: teacherName:"+teacher.getLastName()+". day="+day);
		for(Card c:teacher.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}

	/**
	 * 
	 * @param i
	 * @param day
	 * @return
	 */
	private int calculateTimePeriod(int i, String day){
		return (Main_properties.day_per_week+1)*i+getDayNumber(day);
	}
	
	/**
	 * 
	 * @param day
	 * @return
	 */
	private int getDayNumber(String day) {
		if(day.equalsIgnoreCase("lundi"))
			return 1;
		else if(day.equalsIgnoreCase("mardi"))
			return 2;
		else if(day.equalsIgnoreCase("mercredi"))
			return 3;
		else if(day.equalsIgnoreCase("vendredi"))
			return 5;
		else if(day.equalsIgnoreCase("samdi"))
			return 6;
		else if(day.equalsIgnoreCase("dimanche"))
			return 7;
		return 0;
	}


	/**
	 * 
	 * @param days
	 * @param sections
	 */
	private void constructViewFromSections(ArrayList<String> days, ArrayList<Section> sections){
		//	System.out.println("days: "+days+"--- sections: "+sections);

		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Main_properties.period_per_day+1;
		int cols=sections.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		firstTimeBox=new TimeBoxCompare("*** "+days.get(0)+" ***");
		add(firstTimeBox);
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(sections.get(i).getName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(i,false));
			for (int j=0; j<cols-1;j++){
				int timePeriod = calculateTimePeriod(i,days.get(0));
				Card c=getSectionCardAtThatTime(sections.get(j),timePeriod);
				timeBoxCompare=new TimeBoxCompare(c,state,timePeriod,opc,this,dp,sections.get(j));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();

	}
	
	/**
	 * 
	 * @param section
	 * @param timePeriod
	 * @return
	 */
	private Card getSectionCardAtThatTime(Section section, int timePeriod) {

		for(Card c:section.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}

	/**
	 * 
	 * @param days
	 * @param rooms
	 */
	private void constructViewFromRooms(ArrayList<String> days, ArrayList<Room> rooms){
		//	System.out.println("days: "+days+"--- rooms: "+rooms);
		
		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Main_properties.period_per_day+1;
		int cols=rooms.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:		
		firstTimeBox=new TimeBoxCompare("*** "+days.get(0)+" ***");
		add(firstTimeBox);
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(rooms.get(i).getName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(i,false));
			for (int j=0; j<cols-1;j++){

				int timePeriod = calculateTimePeriod(i,days.get(0));
				Card c=getRoomCardAtThatTime(rooms.get(j),timePeriod);
				timeBoxCompare=new TimeBoxCompare(c,state,timePeriod,opc,this,dp,rooms.get(j));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();

	}
	
	/**
	 * 
	 * @param room
	 * @param timePeriod
	 * @return
	 */
	private Card getRoomCardAtThatTime(Room room, int timePeriod) {

		for(Card c:room.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}
	
	public void setOptionPanelCompare(OptionPanelCompare opc){
		this.opc=opc;
	}


	public void clear() {
		constructDefaultView();
		
		
	}
}