package gui_schedule;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import main.Propreties;
import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;

public class MainViewCompare extends JPanel{

	private static final long serialVersionUID = 1L;
	StateFullSchedule state;

	public MainViewCompare(StateFullSchedule state){
		this.state=state;
	}
	
	public void constructViewFromTeachers(ArrayList<String> days, ArrayList<Teacher> teachers){
	//	System.out.println("days: "+days+"--- teachers: "+teachers);
		TimeBox timeBox;
		int rows=days.size()*Propreties.period_per_day+1;
		int cols=teachers.size()+1;
		
		this.removeAll();
		setLayout(new GridLayout(rows,cols));
		
		for (int i=0;i<rows;i++)
			for (int j=0; j<cols;j++){
				timeBox=new TimeBox("");
				add(timeBox);
			}
		this.revalidate();
		this.repaint();
	}
	public void constructViewFromSections(ArrayList<String> days, ArrayList<Section> sections){
	//	System.out.println("days: "+days+"--- sections: "+sections);
	}
	public void constructViewFromRooms(ArrayList<String> days, ArrayList<Room> rooms){
	//	System.out.println("days: "+days+"--- rooms: "+rooms);
	
	}
}