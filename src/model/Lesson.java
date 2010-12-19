package model;

import java.util.*;

/**
 * 
 * This class represent a Lesson
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class Lesson {
	String name;
	String group; //next version, we should make a object of this
	int period; //for the current semester
	
	
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
	
	public void  setOtherInfo(String group, Teacher t, String periods, String mode){
		this.teacher=t;
	}
	
	/**
	 * 
	 * Allow to set a room to this Lesson, with a priority
	 * (not currently used)
	 * 
	 * @param priority	the priority of the room (for this lesson)
	 * @param room 	the room to be added
	 */
	public void putRoom(int priority,Room room){
		preferedClassRoom.put(priority, room);
	}
	

	/**
	 * 
	 * @return the teacher giving this course
	 */
	public Teacher getTeacher(){
		return teacher;
	}
	
}
