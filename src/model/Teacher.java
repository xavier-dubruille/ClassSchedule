package model;

import java.util.TreeMap;
import java.util.Map;

public class Teacher {
	String firstname;
	String lastname;

	Map<String, Lesson> lessons;
	Map<Integer,Room> preferedClassRoom;
	// we have to had a "prefered schedule"
	
	public Teacher(){
		lessons=new TreeMap<String, Lesson>();
		preferedClassRoom =new TreeMap<Integer, Room>();
	}
	public Teacher(String firstname, String lastname){
		this();
		this.firstname=firstname;
		this.lastname=lastname;
	}
	
	public Teacher(String firstname, String lastname, String lesson_id){
		this();
		this.firstname=firstname;
		this.lastname=lastname;
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	
	public void addCourse(String id, Lesson l){
		//if (lessons.size()==0)
		lessons.put(id,l);
		
	}
	
}
