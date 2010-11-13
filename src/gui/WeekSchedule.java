package gui;

import javax.swing.*;

public class WeekSchedule extends JPanel {
	WeekSchedule(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		//JPanel j=new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new DaySchedule());
		add(new DaySchedule());
		add(new DaySchedule());
		
		add(Box.createVerticalGlue());
		

	}
}
