package model;

import java.util.HashMap;
import java.util.Map;

public class Card {
	
	
	// private String name;
	private Teacher teacher;
	private Lesson lesson;
	private int smiley; //se sera mieu de creer un enum pour cela
	int timeBox; // =dp (day,period)
	
	/**
	 * for tests only !
	 * 
	 */
	/*
	public Card(){
		//teacher=new Teacher("vroman");
		lesson = new Lesson("java");
	}
	*/
	
	/**
	 * for tests only !
	 * 
	 */
	/*
	public Card(Lesson l){
	//	teacher=new Teacher("not set");
		lesson = l;
	}
	*/
	
	public Card(Lesson lesson, Teacher teacher){
		this.lesson=lesson;
		this.teacher=teacher;
		//System.out.println(teacher.getFirstName());
		smiley=0;
	}
	

	public void update(){
		
	}
	

	public String getHtmlRepresentation(){
		return "<html>"+lesson.name+"<html>";
	}
	
	public Teacher getTeacher(){
		return teacher;
	}

	public String toString(){
		return lesson.name+" "+teacher.lastName;
	}
}
