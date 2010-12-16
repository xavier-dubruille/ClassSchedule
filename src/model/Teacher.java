package model;

import java.util.*;

public class Teacher {
	String firstName;
	String lastName;

	Map<String, Lesson> lessons; //inutil ?
	ArrayList<Card> cards;
	Map<Integer,Room> preferedClassRoom;
	StateFullSchedule state;
	// we have to had a "preferred schedule"
	
	public Teacher(){
		firstName="not set";
		lastName="not set";
		cards=new ArrayList<Card>();
		lessons=new TreeMap<String, Lesson>();
		preferedClassRoom =new TreeMap<Integer, Room>();
	}
	public Teacher(String firstName, String lastName, StateFullSchedule state){
		this();
		this.firstName=firstName;
		this.lastName=lastName;
		this.state=state;
	}


	
	public ArrayList<Card> getCard(){
		return cards;
	}
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	
	public void setCards(Collection<? extends Card> coll){
		cards.addAll(coll);
	}
	public void addCard(Card ca){
		cards.add(ca);
	}
	
	public void addCourse(String id, Lesson l){
		//if (lessons.size()==0)
		lessons.put(id,l);
		
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
}
