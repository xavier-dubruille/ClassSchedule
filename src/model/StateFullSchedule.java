package model;

import java.util.*;



/*
 * Contient de quoi créer un horraire et de 
 * concerver toutes les info le representan.
 */
public class StateFullSchedule {
	
	Map<String, Lesson> lessons;
	Map<String, Card> cards;
	Map<String, Room> rooms;
	Map<String, Teacher> teachers;
	Map<String, Student> students;
	

	/**
	 * utilisé à de fin de test
	 */
	public StateFullSchedule(){
		
		lessons=new HashMap<String, Lesson>();
		cards=new HashMap<String, Card>();
		rooms=new HashMap<String, Room>();
		teachers=new HashMap<String, Teacher>();
		students=new HashMap<String, Student>();
		
		
		
		
	}
	
	
	/*
	 * constructeur normal, a partir d'un/de fichier csv
	 */
	public StateFullSchedule(String path){
		
		lessons=new HashMap<String, Lesson>();
		cards=new HashMap<String, Card>();
		rooms=new HashMap<String, Room>();
		teachers=new HashMap<String, Teacher>();
		students=new HashMap<String, Student>();
	
		// 1) on parcourt le/les fichiers et on remplit les map.
	
		// 2) on va metre a jour toutes les Maps qui en ont besoin
		for (Card c: cards.values())
			c.update();
		// ect
		
	}
	
	

}
