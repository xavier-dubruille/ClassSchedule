package model;

import java.util.HashMap;
import java.util.Map;

public class Card {
	
	
	// private String name;
	private Teacher teacher;
	private Lesson lesson;
	private int smiley; //se sera mieu de creer un enum pour cela
	private int timePeriod; // =DP (Day,Period)
	private int cardId;
	private Map<String,Room> rooms;
	
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
	
	public Card(Lesson lesson, Teacher teacher,int cardId,Map<String,Room> rooms){
		this.lesson=lesson;
		this.teacher=teacher;
		this.cardId=cardId;
		this.rooms=rooms;
		smiley=0;
		timePeriod=0;
	}
	public int getCardId(){
		return cardId;
	}
	
	public void setTimePeriod_and_pickARoom(int time){
		timePeriod=time;

		for(Room r:rooms.values()){
			//if()//si il est du mm type et que la class n'est pas prise ˆ ce moment
		}
	}
	
	public int getTimePeriod(){
		return timePeriod;
	}
	
	public void update(){
		System.out.println("attention la methode Card.update() est vide! ");
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
