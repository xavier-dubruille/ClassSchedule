package model;

import java.util.HashMap;
import java.util.Map;

public class Card {
	
	
	private String name;
	private Lesson lesson;
	private int smiley; //se sera mieu de creer un enum pour cela
	
	
	public Card(Lesson lesson){
		this.lesson=lesson;
		smiley=0;
	}
	

	public void update(){
		
	}

}
