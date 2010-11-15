package model;

import java.util.TreeMap;
import java.util.Map;

public class Teacher {
	String name;

	Map<String, Lesson> lessons;
	Map<Integer,Room> preferedClassRoom;
	// we have to had a "prefered schedule"
	
	public Teacher(){
		lessons=new TreeMap<String, Lesson>();
		preferedClassRoom =new TreeMap<Integer, Room>();
	}
	public Teacher(String name){
		this();
		this.name=name;
	}
	
	public Teacher(String name, String lesson_id,Lesson lesson){
		this();
		this.name=name;
		lessons.put(lesson_id, lesson); 
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	
}
