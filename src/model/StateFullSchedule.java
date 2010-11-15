package model;

import java.util.*;
import java.io.*;




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
			System.err.println("can't update from files"); //faudra mieu gérer ca..
			System.exit(-1); 
		}


		try{

			// il y a encore beacoup de cas à prendre en compte..
			// comme si la première ligne n'est pas lisible, le fichier 
			// n'est pas du bon format, ne contien pas tous les champs 
			// necessaire.. 
			// Puis ca pourait être bien de suposer le séparateur, les ", ect

			//faut p-e déclarer ces trucs avant..
			String[] line;
			int course_name, course_year, course_section, course_mode, course_id,
			coursePeriodFirstSemester,teacher_id,teacher_name; //à continuer..

			/*************************************************
			* first we start by scanning the file containing the courses
			* 
			* ***********************************************/
		
			Scanner sc=new Scanner(new File(filesPath[0]));
			line=sc.next().split(",");

			// Il faudrait initier ces int avec la première ligne, 
			// et l'idéal serait qu'il puisse le faire même avec un nom 
			// approximatif (e.g. sans accents ni majuscules)
			course_name=4;
			course_year=1;
			course_section=0;
			course_mode=5;
			course_id=3;
			coursePeriodFirstSemester=15;
			teacher_id=7;
			teacher_name=8;
			//faudrait enregister les champs restant à titre d'info..



			while (sc.hasNext()) {
				

				line=sc.nextLine().split(";");
								
				Teacher t; 
				Lesson l=new Lesson(); // je suis obligé, mais j'aime pas..
				
				//now, t and l HAVE TO point to the right object !
				if (!teachers.containsKey(line[teacher_id])){
					t=new Teacher(line[teacher_name],line[course_id],l);
					teachers.put(line[teacher_id], t);
				}
				else{
					t=teachers.get(line[teacher_id]);
				}
					
				
				if (!lessons.containsKey(line[course_id])){
					l=new Lesson(line[course_name]); //ne sera evidement pas sufisant
					lessons.put(line[course_id], l);
				}
				else {
					l=lessons.get(line[course_name]);
				}
				
			}

			// il faut bien évidement rajouter ce qui manque..



		}catch(IOException e){
			System.err.println("can't update; bad/wrong files"); //faudra mieu gérer ca..
			System.exit(-1); 
		}


		/*********************************************
		 * Let's update all the maps
		 *********************************************/
		
		// card map update
		for (Lesson l: lessons.values()){
			// faudra en creer n, avec n en fonction du type de cour que c'est.
			// faire des vérif, ne peut pas etr une mauvaise chose non plus.
			
			cards.put(l.name, new Card(l)); 
			//faudrait remplacer par l.getId() et l.getTeacher()
		}
		
		
		
		
		// pour des fins de tests uniquement; voici qq valeurs ajoutées manuelment
		/**
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
		*/
		
		//System.out.println(lessons+"\n"+teachers+"\n"+cards); //debug
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
