package gui_selection;


import gui.GUI_Propreties;

import javax.swing.*;


import java.util.*;

import model.*;

import java.awt.*;


public class DisplayPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	SortedMap<Integer,Card_GUI> gui_cards;
	// l'idee, c'est d'en avoir plusieurs, pour optimiser le temps de recherche.. on verra si c'est necessaire..

	String generalOptionChoosed;
	ArrayList<String> teachersChoosed;
	ArrayList<String> sectionsChoosed;
	String roomTypeChoosed;

	StateFullSchedule state;
	private boolean teacherFull;
	public DisplayPanel(StateFullSchedule state,SortedMap<Integer,Card_GUI> gui_cards){

		this.gui_cards=gui_cards;

		//this.setPreferredSize(new Dimension(30,9999));

		//this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		this.state=state;

		this.setTransferHandler(new PanelTransferHandler(state,this));
		this.setBackground(GUI_Propreties.displayPanel);

		teachersChoosed= new ArrayList<String>();
		sectionsChoosed= new ArrayList<String>();
		roomTypeChoosed= "tous";
		generalOptionChoosed="tous";

		setLayout(new FlowLayout());

		// initiate all maps..


	}


	private void printCard(){
		this.removeAll();
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

		allTeachersChoosed(false);
		allSectionsChoosed(false);


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

			if(
					(teacherFull || teachersChoosed.contains(ca.getTeacher().getFirstName()+" "+ca.getTeacher().getLastName())) && 
					generalOptionCheck(ca.getTimePeriod()) && 
					roomOptionCheck(ca) &&
					sectionOptionCheck(ca)
			)
			{
				c.setVisible(true);
				//	System.out.println(generalOptionChoosed+"+ (tp)"+ca.getTimePeriod()+" = "+generalOptionCheck(ca.getTimePeriod()));

			}
			else
				c.setVisible(false);

		}
		// this.revalidate(); //we should revalidate the scroolPane instead

	}
	private boolean sectionOptionCheck(Card ca) {
		for(Section s: ca.getCard_sections())
			if (sectionsChoosed.contains(s.getName())) return true;
		return false;
	}


	private boolean roomOptionCheck(Card c) {

		if(roomTypeChoosed.equalsIgnoreCase("tous"))
			return true;

		if(roomTypeChoosed.equalsIgnoreCase("Informatique") && c.isInfo())
			return true;

		if(roomTypeChoosed.equalsIgnoreCase(c.getMod()))
			return true;

		if(roomTypeChoosed.equalsIgnoreCase("autre") && !(c.getMod().equalsIgnoreCase("classe") || c.getMod().equalsIgnoreCase("groupe")))
			return true;

		return false;
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

	public void updateByRoomItem(String selectedItem){
		this.roomTypeChoosed=selectedItem;
		this.updateViewedCards();

	}

	public void showTeacherCards(String teacher){
		//System.out.println("showTeacher:"+teacher);
		this.teachersChoosed.add(teacher);
		teacherFull=false; // useless !
		this.updateViewedCards();
	}

	public void hideTeacherCards(String teacher){
		//System.out.println("hideTeacher:"+teacher);
		this.teachersChoosed.remove(teacher);
		teacherFull=false;
		this.updateViewedCards();
	}

	public void hideSectionCards(String section) {
		sectionsChoosed.remove(section);
		this.updateViewedCards();
	}

	public void showSectionCards(String section) {
		sectionsChoosed.add(section);
		this.updateViewedCards();
	}


	/*
	 * use it if the card may have been placed/unplaced
	 */
	public void updateStatusCard(){
		for (Card_GUI cg:gui_cards.values())
			cg.reDraw();

		updateViewedCards();

	}


	public void allTeachersChoosed(boolean clear) {
		teachersChoosed.clear();
		teacherFull=!clear;
		if(!clear)
			for(Teacher t: state.getTeachers().values())
				teachersChoosed.add(t.getFirstName()+" "+t.getLastName());
		updateViewedCards();
	}


	public void allSectionsChoosed(boolean clear) {
		sectionsChoosed.clear();
		if(!clear)
			for(Section s: state.getSections().values())
				sectionsChoosed.add(s.getName());
		updateViewedCards();
	}



}
