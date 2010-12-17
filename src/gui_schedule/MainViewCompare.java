package gui_schedule;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Propreties;
import model.*;

public class MainViewCompare extends JPanel{

	private static final long serialVersionUID = 1L;
	StateFullSchedule state;
	ArrayList<Teacher> teachers;
	ArrayList<Section> sections;
	ArrayList<Room> rooms;

	public MainViewCompare(StateFullSchedule state){
		this.state=state;
	}

	public void constructViewFromTeachers(ArrayList<String> days, ArrayList<Teacher> teachers){
		//	System.out.println("days: "+days+"--- teachers: "+teachers);

		this.teachers=teachers;
		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
		int cols=teachers.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		add(new TimeBoxCompare(""));
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(teachers.get(i).getLastName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(""+i));
			for (int j=0; j<cols-1;j++){
				timeBoxCompare=new TimeBoxCompare(getTeacherCardAtThatTime(teachers.get(j),i,days.get(0)));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();
	}
	private Card getTeacherCardAtThatTime(Teacher teacher, int i,String day) {
		// find the timePeriod --> 
		int timePeriod = (Propreties.day_per_week+1)*i+getDayNumber(day);

		for(Card c:teacher.getCard()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
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

	public void constructViewFromSections(ArrayList<String> days, ArrayList<Section> sections){
		//	System.out.println("days: "+days+"--- sections: "+sections);

		this.sections=sections;
		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
		int cols=sections.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		add(new TimeBoxCompare(""));
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(sections.get(i).getName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(""+i));
			for (int j=0; j<cols-1;j++){
				timeBoxCompare=new TimeBoxCompare(getSectionCardAtThatTime(sections.get(j),i,days.get(0)));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();

	}
	private Card getSectionCardAtThatTime(Section section, int i, String day) {

		// find the timePeriod --> 
		int timePeriod = (Propreties.day_per_week+1)*i+getDayNumber(day);

		for(Card c:section.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}

	public void constructViewFromRooms(ArrayList<String> days, ArrayList<Room> rooms){
		//	System.out.println("days: "+days+"--- rooms: "+rooms);
		this.rooms=rooms;
		TimeBoxCompare timeBoxCompare;
		int rows=days.size()*Propreties.period_per_day+1;
		int cols=rooms.size()+1;

		this.removeAll();
		setLayout(new GridLayout(rows,cols));

		//first line:
		add(new TimeBoxCompare(""));
		for (int i=0;i<cols-1;i++)
			add(new TimeBoxCompare(rooms.get(i).getName()));

		for (int i=1;i<rows;i++){
			add(new TimeBoxCompare(""+i));
			for (int j=0; j<cols-1;j++){
				timeBoxCompare=new TimeBoxCompare(getRoomCardAtThatTime(rooms.get(j),i,days.get(0)));
				add(timeBoxCompare);

			}
		}
		this.revalidate();
		this.repaint();

	}
	private Card getRoomCardAtThatTime(Room room, int i, String day) {

		// find the timePeriod --> 
		int timePeriod = (Propreties.day_per_week+1)*i+getDayNumber(day);

		for(Card c:room.getCards()){
			if (c.getTimePeriod()==timePeriod)
				return c;
		}

		return null;
	}
}