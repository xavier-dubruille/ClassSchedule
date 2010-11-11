package model;

import java.util.*;

public class Lesson {
	String name;
	Map<Integer,Room> preferedClassRoom;
	Map<Integer,Teacher> preferedTeachers;
	
	public Lesson(String name){
		preferedTeachers=new HashMap<Integer,Teacher>();
		preferedClassRoom =new HashMap<Integer, Room>();
		this.name=name;
	}
	
	/*
	 *  for tests purposes
	 */
	public Lesson(String name,Room preferedLocal, Teacher preferedTeacher){
	
		preferedTeachers=new HashMap<Integer,Teacher>();
		preferedClassRoom =new HashMap<Integer, Room>();
		
		this.name=name;
		preferedTeachers.put(100, preferedTeacher);
		preferedClassRoom.put(100, preferedLocal);
		
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	
	public void putTeacher(int priority,Teacher t){
		preferedTeachers.put(priority, t);
	}
	
}
