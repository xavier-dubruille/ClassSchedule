package gui_selection;


import gui.GUI_Propreties;

import javax.swing.*;
import java.util.*;

import model.*;
import java.awt.*;


public class DisplayPanel extends JPanel{

	SortedMap<Integer,Card_GUI> gui_cards;
	// l'idee, c'est d'en avoir plusieurs, pour optimiser le temps de recherche.. on verra si c'est necessaire..
	
	String generalOptionChoosed;
	ArrayList<String> teachersChoosed;
	ArrayList<String> classRoomsChoosed;

	StateFullSchedule state;
	public DisplayPanel(StateFullSchedule state,SortedMap<Integer,Card_GUI> gui_cards){
		
		this.gui_cards=gui_cards;
		
		//this.setPreferredSize(new Dimension(30,9999));
		
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.state=state;
		
		teachersChoosed= new ArrayList<String>();
		classRoomsChoosed= new ArrayList<String>();
		generalOptionChoosed="tous";
		
		setLayout(new FlowLayout());
		
		// initiate all maps..
		
		
	}


	private void printCard(){
		for(Map.Entry<Integer,Card_GUI> e:gui_cards.entrySet()){
			//setLayout(new FlowLayout());
			add((Card_GUI)e.getValue());
			((Card_GUI)e.getValue()).setVisible(true);
		}
		
	}
	
	/*
	 * default update:
	 * let's print all the cards..
	 */
	public void update_default(){
		
		//this.setLayout(new GridLayout());
		// First we create ours sortedMap..

		for(Map.Entry<Integer,Card> e:state.cards.entrySet()){
			gui_cards.put(e.getKey(), new Card_GUI(e.getValue(),this));
		}
		
		//add(new JLabel("test"));
		
		System.out.println("componant number: "+this.getComponentCount());
		// .. then we print it.
		printCard();
		repaint();
		this.repaint();
		this.setVisible(true);
		this.revalidate();
		this.validate();
		
		System.out.println("added cards= "+this.getComponentCount());
		
	}
	
	/*
	 * Will update the visible/non visible Card_GUI
	 * according to the different options
	 */
	private void updateViewedCards(){
		Card ca;
		for (Card_GUI c:gui_cards.values())
		{
			ca=c.getCard();
			
			if(teachersChoosed.contains(ca.getTeacher().getFirstName()+" "+ca.getTeacher().getLastName())
			   && generalOptionCheck(ca.getTimePeriod()))
			{
				c.setVisible(true);
				System.out.println(generalOptionChoosed+"+ (tp)"+ca.getTimePeriod()+" = "+generalOptionCheck(ca.getTimePeriod()));
				
			}
			else
				c.setVisible(false);
			
		}

	}
	private boolean generalOptionCheck(int timePeriod){
		if (generalOptionChoosed.equalsIgnoreCase("tous") || generalOptionChoosed.equalsIgnoreCase("place posant probleme"))
				return true;
		
		if(generalOptionChoosed.equalsIgnoreCase("place") && timePeriod!=0)
			return true;
		
		if(generalOptionChoosed.equalsIgnoreCase("non place") && timePeriod==0)
			return true;
		
		return false;
	}
	public void updateByTeacherItem(String selectedItem){

		this.teachersChoosed.add(0, selectedItem); //sera fait autrement.
		this.updateViewedCards();
		
	}
	public void updateByGeneralItem(String selectedItem){
		this.generalOptionChoosed=selectedItem;
		this.updateViewedCards();
		
	}
	public void updateStatusCard(){
		for (Card_GUI c:gui_cards.values())
		{
			if(c.getCard().getTimePeriod()!=0)
				c.setBackground(GUI_Propreties.card_color_placed);
			else
				c.setBackground(GUI_Propreties.card_default_background);
		}
		 updateViewedCards();
		
	}
}
