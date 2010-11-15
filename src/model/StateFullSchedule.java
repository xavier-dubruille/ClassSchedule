package model;

import java.util.*;



/*
 * Contient de quoi créer un horraire et de 
 * concerver toutes les info le representan.
 */
public class StateFullSchedule {

	Map<String, Lesson> lessons;
	public Map<String, Card> cards;
	Map<String, Room> rooms;
	Map<String, Teacher> teachers;
	Map<String, Student> students;

	String filesPath[];


	/**
	 *  Construct a valid, but "empty" object
	 */
	public StateFullSchedule(){

		lessons=new TreeMap<String, Lesson>();
		cards=new TreeMap<String, Card>();
		rooms=new TreeMap<String, Room>();
		teachers=new TreeMap<String, Teacher>();
		students=new TreeMap<String, Student>();


		filesPath =new String[4];

	}



	/*
	 * cette méthode, une fois écrite devrait mettre à jour
	 * tout l'état de l'objet à partir des infos dans les fichiers
	 */
	public void update(){
		/* il faudrait p-e vérifier ici (du moins dans cette méthode)
		 * si les fichiers on changé,
		 * si ils existent, sont dans un format valide, ect */

		if (filesPath == null ||filesPath.length == 0){
			System.err.println("can't update"); //faudra mieu gérer ca..
			System.exit(-1); 
		}


		try{

			// 1) on parcourt le/les fichiers et on remplit les map.

			// 2) on va metre a jour toutes les Maps qui en ont besoin
			for (Card c: cards.values())
				c.update();
			// ect



		}catch(Exception e){
			System.err.println("can't update; bad/wrong files"); //faudra mieu gérer ca..
			System.exit(-1); 
		}
		
		
		// pour des fins de tests uniquement; voici qq valeurs ajoutées
		
		String name;
		name="réso";
		lessons.put(name, new Lesson(name));
		name="electronic";
		lessons.put(name, new Lesson(name));
		name="java";
		lessons.put(name, new Lesson(name));
		
		name="vroman";
		teachers.put(name, new Teacher(name));
		name="buterfa";
		teachers.put(name, new Teacher(name));
		
		cards.put("réso", new Card(lessons.get("réso"),teachers.get("vroman")));
		cards.put("electronic", new Card(lessons.get("electronic"),teachers.get("buterfa")));
		cards.put("java", new Card(lessons.get("java"),teachers.get("vroman")));
		
		//System.out.println(lessons+"\n"+teachers+"\n"+cards);
		//students
		//rooms
		

	}

	public Map<String,Card> getCards(){
		return cards;
	}
	
	public void setFilesPath(String filesPath[]){
		this.filesPath=filesPath;
	}

	public String[] getFilesPath(){
		return filesPath;
	}


}
