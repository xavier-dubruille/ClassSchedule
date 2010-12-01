package gui_selection;

import javax.swing.*;
import java.util.*;
import model.*;

public class DisplayPanel extends JPanel{

	// l'idee, c'est d'en avoir plusieurs, pour optimiser le temps de recherche.. on verra si c'est necessaire..
	SortedMap<String,Card_GUI> content_by_courses;

	StateFullSchedule state;
	public DisplayPanel(StateFullSchedule state){
		
		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		this.state=state;
		
		// initiate all maps..
		content_by_courses=new TreeMap<String,Card_GUI>();
		
	}


	private void printCard(){
		for(Map.Entry<String,Card_GUI> e:content_by_courses.entrySet()){
			add(e.getValue());
			e.getValue().setVisible(true);
		}
	}
	
	/*
	 * default update:
	 * let's print all the cards..
	 */
	public void update_default(){
		
		// First we create ours sortedMap..

		for(Map.Entry<String,Card> e:state.cards.entrySet()){
			content_by_courses.put(e.getKey(), new Card_GUI(e.getValue()));
		}
		
		add(new JLabel("test"));
		
		
		// .. then we print it.
		printCard();
		repaint();
		this.repaint();
		this.setVisible(true);
		
	}
	
	/*
	 * Will update the visible/non visible Card_GUI
	 * according to "options"
	 */
	public void update(String[] options){
		// p-e qu'il serait plus efficasse/plus propre/.. de mettre "option" en objet partage..

	}
}
