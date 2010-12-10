package gui_schedule;

import gui.CardTransferHandler;
import gui.GUI_Propreties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeBox extends JPanel {

	JLabel jl;
	String name;
	
	TimeBox(String s){
		super();

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

		this.setTransferHandler(new CardTransferHandler());

	}
	
	public void setLabel(String s){
		jl.setText(s);
	}
	
	public String toString(){
		return "timebox: "+name;
	}

}
