package gui_schedule;

import gui.CardTransferHandler;

import java.awt.Color;
import java.awt.GridLayout;

import model.Teacher;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.StateFullSchedule;

public class MainViewSolo extends JPanel{


	StateFullSchedule state;
	TimeBox[] timeBoxes;

	public MainViewSolo(StateFullSchedule state){
		this.state=state;

		
		this.setLayout(new GridLayout(8,7));
		timeBoxes=new TimeBox[56];


		timeBoxes[0]=new TimeBox("");
		add(timeBoxes[0]);
		
		for(int i=1; i<7; i++){
			timeBoxes[i]=new TimeBox("jour "+i);
			add(timeBoxes[i]);
		}
		
		for(int i=7; i<56; i++){
			if(i%7==0)
				timeBoxes[i]=new TimeBox("periode "+i/7+".");
			
			else
				timeBoxes[i]=new TimeBox("");
			
			add(timeBoxes[i]);
		}
		
		
	}
	
	public void setScheduleView(Teacher t){
		System.out.println(t.getLastName());
		//this.removeAll();
		
		
		// faudrait parcourir les cartons du prof et voir si il y en a des placé (et les afficher)
		/*
		for(int i=7; i<56; i++)
			if(i%7!=0){}
		*/
		
		timeBoxes[37].setLabel("testeu");
		
		
		
	}
}