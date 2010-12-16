package gui_schedule;

import javax.swing.JPanel;
import java.util.ArrayList;
import model.StateFullSchedule;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.*;
import main.Propreties;

public class MainViewCompare extends JPanel{


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