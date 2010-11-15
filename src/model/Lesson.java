package model;

import java.util.*;

public class Lesson {
	String name;
	Map<Integer,Room> preferedClassRoom;
	Teacher teacher;
	// Map<Integer,Teacher> preferedTeachers;
	
	public Lesson(){}
	
	public Lesson(String name){
		//preferedTeachers=new HashMap<Integer,Teacher>();
		preferedClassRoom =new HashMap<Integer, Room>();
		this.name=name;
	}
	
	/*
	 *  for tests purposes
	 */
	public Lesson(String name,Room preferedLocal, Teacher teacher){
	
		//preferedTeachers=new TreeMap<Integer,Teacher>();
		preferedClassRoom =new TreeMap<Integer, Room>();
		
		this.teacher=teacher;
		this.name=name;
		//preferedTeachers.put(100, preferedTeacher);
		preferedClassRoom.put(100, preferedLocal);
		
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	

	public Teacher getTeacher(){
		return teacher;
	}
	
}
