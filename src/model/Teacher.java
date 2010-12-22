package model;

import java.util.*;
import main.Main_properties;

/**
 * 
 * This class represent a teacher, his informations, his preferences and all the course he teaches.
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class Teacher {
	String firstName;
	String lastName;

	Map<String, Lesson> lessons; //inutil ?
	ArrayList<Card> cards;
	Map<Integer,Room> preferedClassRoom;
	StateFullSchedule state;
	private int[] preferedTimeSlides;
	int testi;
	
	/**
	 * create an "empty" teacher
	 */
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
	 * This will set the teacher's preference for a particular day
	 * 
	 * @param timeSlide the moment (day and period) to witch we want to set the preference 
	 * @param pref an Interger between 0 and 10, representing the importance of the preference
	 *             0 if it's a perfect moment and 10 if it's an impossible one
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
	 * Get all the preferences of the teacher
	 * @return the int[] containing the teacher preference
	 * 			the indexes represent the moment, and the value the preference for that moment
	 */
	public int[] getPreferedTimeSlides() {
		return preferedTimeSlides;
	}
	
	/**
	 * Get all the cards the teacher has
	 * @return all the teacher's cards
	 */
	public ArrayList<Card> getCards(){
		return cards;
	}
	
	/**
	 * 
	 * If a teacher prefer a particular room for a course, 
	 * we can set it here.
	 * Not used yet.
	 * 
	 * @param priority the level of the preference
	 * @param room the room the teacher want
	 * @param lesson the lesson for witch he want this preference
	 */
	public void putLocal(int priority,Room room, Lesson lesson){
		preferedClassRoom.put(priority, room);
	}
	
	/**
	 * Set all the card of the teacher
	 * Should be used.
	 * 
	 * @param coll the list of all cards
	 */
	public void setCards(Collection<? extends Card> coll){
		cards.addAll(coll);
	}
	
	/**
	 * To add a card for that teacher
	 * @param ca the card to be added
	 */
	public void addCard(Card ca){
		cards.add(ca);
	}
	
	/**
	 * Add a course that this teacher give. 
	 * 
	 * @param id the ID of the course
	 * @param l the Lesson (course) to be added
	 */
	public void addCourse(String id, Lesson l){
		//if (lessons.size()==0)
		lessons.put(id,l);
		
	}
	
	/**
	 * Get the teacher first name
	 * @return the teacher first name
	 */
	public String getFirstName(){
		return firstName;
	}
	
	/**
	 * Get the teacher last name
	 * @return the teacher last name
	 */
	public String getLastName(){
		return lastName;
	}
	
}
