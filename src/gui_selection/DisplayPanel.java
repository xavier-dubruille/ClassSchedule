package gui_selection;

import javax.swing.*;
import java.util.*;

import model.*;
import java.awt.*;

public class DisplayPanel extends JPanel{

	SortedMap<Integer,Card_GUI> gui_cards;
	// l'idee, c'est d'en avoir plusieurs, pour optimiser le temps de recherche.. on verra si c'est necessaire..
	

	StateFullSchedule state;
	public DisplayPanel(StateFullSchedule state,SortedMap<Integer,Card_GUI> gui_cards){
		
		this.gui_cards=gui_cards;
		
		//this.setPreferredSize(new Dimension(30,9999));
		
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.state=state;
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
			gui_cards.put(e.getKey(), new Card_GUI(e.getValue()));
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
		System.out.println("done adding cards..");
		System.out.println("componant number: "+this.getComponentCount());
		
	}
	
	/*
	 * Will update the visible/non visible Card_GUI
	 * according to "options"
	 */
	public void update(String[] options){
		// p-e qu'il serait plus efficasse/plus propre/.. de mettre "option" en objet partage..

	}
}
