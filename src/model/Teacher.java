package model;

import java.util.*;
import main.Main_properties;

public class Teacher {
	String firstName;
	String lastName;

	Map<String, Lesson> lessons; //inutil ?
	ArrayList<Card> cards;
	Map<Integer,Room> preferedClassRoom;
	StateFullSchedule state;
	private int[] preferedTimeSlides;
	int testi;
	
	public Teacher(){
		preferedTimeSlides=new int[(Main_properties.day_per_week+1)*(Main_properties.period_per_day+1)];
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

	
	/**
	 * 
	 * @param timeSlide
	 * @param pref
	 */
	public void setPreferedSlide(int timeSlide,int pref){
	
		int day=timeSlide/10;
		int period=timeSlide%10;
		
		//we should make some cheks and throws exeption..
		
		int timeSlideConverted=(Main_properties.day_per_week+1)*period+day;
		
		System.out.println("setprefered time slide:"+timeSlide+"-->"+pref+" "+day+period+". and converted="+timeSlideConverted);
		
		preferedTimeSlides[timeSlideConverted]=pref;
	}
	
	/**
	 * 
	 * @return
	 */
	public int[] getPreferedTimeSlides() {
		return preferedTimeSlides;
	}
	public ArrayList<Card> getCards(){
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
