package gui_schedule;

import gui.GUI_Propreties;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Propreties;
import model.*;
import gui_selection.DisplayPanel;

public class MainViewCompare extends JPanel{

	private static final long serialVersionUID = 1L;
	private StateFullSchedule state;
	private OptionPanelCompare opc;
	private DisplayPanel dp;
	private TimeBoxCompare firstTimeBox;
	
	public MainViewCompare(StateFullSchedule state, DisplayPanel dp){
		this.state=state;
		this.dp=dp;
	}


	public void constructView(){
		String compareOn=opc.getCompareOn();
		ArrayList<String> selectedDays=opc.getSelectedDays();
		if(compareOn==null) return;
		if(compareOn.equals(GUI_Propreties.option_teachers)){
			constructViewFromTeachers(selectedDays, opc.getTeachersToCompare());
		}
		else if(compareOn.equals(GUI_Propreties.option_rooms)){

			constructViewFromRooms(selectedDays, opc.getRoomsToCompare());
		}
		else if(compareOn.equals(GUI_Propreties.option_sections)){
			constructViewFromSections(selectedDays, opc.getSectionsToCompare());
		}
	}
	
	private void constructViewFromTeachers(ArrayList<String> days, ArrayList<Teacher> teachers){
		//	System.out.println("days: "+days+"--- teachers: "+teachers);

		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
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
	private Card getTeacherCardAtThatTime(Teacher teacher, int timePeriod) {
		// find the timePeriod --> 
		

		//System.out.println("getTeacherAtTime: teacherName:"+teacher.getLastName()+". day="+day);
		for(Card c:teacher.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}

	private int calculateTimePeriod(int i, String day){
		return (Propreties.day_per_week+1)*i+getDayNumber(day);
	}
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


	
	private void constructViewFromSections(ArrayList<String> days, ArrayList<Section> sections){
		//	System.out.println("days: "+days+"--- sections: "+sections);

		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
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
	private Card getSectionCardAtThatTime(Section section, int timePeriod) {

		for(Card c:section.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}

	private void constructViewFromRooms(ArrayList<String> days, ArrayList<Room> rooms){
		//	System.out.println("days: "+days+"--- rooms: "+rooms);
		
		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
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
}