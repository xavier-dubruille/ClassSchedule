package model;

import java.util.HashMap;
import java.util.Map;

public class Card {
	
	
	// private String name;
	private Teacher teacher;
	private Lesson lesson;
	private int smiley; //se sera mieu de creer un enum pour cela
	
	/**
	 * for tests only !
	 * 
	 */
	public Card(){
		//teacher=new Teacher("vroman");
		lesson = new Lesson("java");
	}
	
	/**
	 * for tests only !
	 * 
	 */
	public Card(Lesson l){
	//	teacher=new Teacher("not set");
		lesson = l;
	}
	
	
	public Card(Lesson lesson, Teacher teacher){
		this.lesson=lesson;
		this.teacher=teacher;
		smiley=0;
	}
	

	public void update(){
		
	}
	

	public String getHtmlRepresentation(){
		return "<html>"+lesson.name+"<br>"+teacher.lastname+"<html>";
	}

	public String toString(){
		return lesson.name+" "+teacher.lastname;
	}
}
