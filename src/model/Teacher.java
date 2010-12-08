package model;

import java.util.TreeMap;
import java.util.Map;

public class Teacher {
	String firstName;
	String lastName;

	Map<String, Lesson> lessons;
	Map<Integer,Room> preferedClassRoom;
	// we have to had a "preferred schedule"
	
	public Teacher(){
		firstName="not set";
		lastName="not set";
		lessons=new TreeMap<String, Lesson>();
		preferedClassRoom =new TreeMap<Integer, Room>();
	}
	public Teacher(String firstName, String lastName){
		this();
		this.firstName=firstName;
		this.lastName=lastName;
	}
	
	public Teacher(String firstName, String lastName, String lesson_id){
		this();
		this.firstName=firstName;
		this.lastName=lastName;
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
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
