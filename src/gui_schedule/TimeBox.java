package gui_schedule;

import gui.CardTransferHandler;
import gui.GUI_Propreties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.*;

public class TimeBox extends JPanel {

	JLabel jl;
	String name;
	private int timePeriod;
	private StateFullSchedule state;
	private MainViewSolo view;
	
	TimeBox(String s, int timePeriod,StateFullSchedule state,MainViewSolo view){
		super();

		this.timePeriod=timePeriod;
		this.state=state;
		this.view=view;
		name=s;
		setMaximumSize(GUI_Propreties.card_dimension);
		setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_Propreties.card_dimension);
		

		jl=new JLabel(name);
		jl.setFont(GUI_Propreties.card_default_font);

		add(jl);
		this.setBorder(GUI_Propreties.card_default_border);
		this.setBackground(Color.lightGray);
		//this.setDropTarget(new DropTarget());
		//this.setTransferHandler(new CardTransferHandler());

		//il faudrait faire cela seleument sur les bonne cases..
		this.setTransferHandler(new CardTransferHandler(state,view.getDisplayPanel()));

	}
	
	public MainViewSolo getView(){
		return view;
	}
	public void setLabel(String s){
		jl.setText(s);
	}
	
	public String toString(){
		return "timebox: "+name;
	}

	public int getTimePeriod(){
		return timePeriod;
	}
	
	public void setTimePeriod(int timePeriod){
		this.timePeriod=timePeriod;
	}
}
