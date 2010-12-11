package model;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;




/*
 * Contient de quoi creer un horraire et de 
 * concerver toutes les info le representant.
 */
public class StateFullSchedule {

	Map<String, Lesson> lessons;
	public Map<Integer, Card> cards;
	Map<String, Room> rooms;
	Map<String, Teacher> teachers;
	Map<String, Student> students;

	String filesPath[];
	String csvDelemiter;
	File courses, classRooms, constrains;
	
	private boolean ready; //will be true when the object is usable


	/**
	 *  Construct a valid, but "empty" object
	 */
	public StateFullSchedule(){

		ready=false;
		lessons=new TreeMap<String, Lesson>();
		cards=new TreeMap<Integer, Card>();
		rooms=new TreeMap<String, Room>();
		teachers=new TreeMap<String, Teacher>();
		students=new TreeMap<String, Student>();


		filesPath =new String[3];
		csvDelemiter=";";
	}



	/*
	 * cette méthode, une fois écrite devrait mettre à jour
	 * tout l'état de l'objet à partir des infos dans les fichiers
	 */
	public void update_from_files(){

		// si ce n'est pas la tt première update; il faut fermer les fichiers,
		// et tout effacer (sauvegarder ?) 

		/* il faudrait p-e vérifier ici (du moins dans cette méthode) 
		 * si les fichiers on changé, (par ex ac une somme md5)
		 * si ils existent, sont dans un format valide, ect */


		if (filesPath[0] == null || filesPath[0].equals("") ){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du fichier csv contenant les cours",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return;  //faudrait p-e relancer la fenetre de dialogue en cas d'échec.. (de tt facon, faut pas gerer ca ici)
		}
		else {
			courses= new File(filesPath[0]);
		}

		if (filesPath[1] == null || filesPath[1].equals("") ){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du fichier csv contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			classRooms= new File(filesPath[1]);
		}

		if (filesPath[2] == null || filesPath[2].equals("") ){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du dossier contenant les fichiers de contraintes",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			constrains= new File(filesPath[2]);
		}
		try{

			// il y a encore beacoup de cas à prendre en compte..
			// comme si la première ligne n'est pas lisible, le fichier 
			// n'est pas du bon format, ne contien pas tous les champs 
			// necessaire.. 
			// Puis ca pourait être bien de suposer le séparateur, les ", ect

			//faut p-e déclarer ces trucs avant..

			//ici on verifie l'extention
			if(true){
				readCSV();
			}
			else
			{
				readXLS();
			}

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

			// vu que plusieur prof peuvent donner un cours, mais qu'il ne peut 
			// y avoir qu'un teacher par carton, on devra faire autrement ici..
	
			//faudrait remplacer par l.getId() 
		}
		
		// theacher map update
	}

	/*
	 * Read from CSV files
	 */
	private void readCSV() throws IOException{

		String[] line;
		int[] indexLine;


		/*************************************************
		 * first we start by scanning the file containing the courses
		 * 
		 * ***********************************************/

		Scanner sc=new Scanner(courses);
		String firstLine=sc.nextLine();
		setDelemiter(firstLine);
		line=firstLine.split(csvDelemiter);


		// Il faudrait initier ces int avec la première ligne, 
		// et l'idéal serait qu'il puisse le faire même avec un nom 
		// approximatif (e.g. sans accents ni majuscules)
		indexLine=new int[line.length];
		putRightIndex(line, indexLine);
	
		//faudrait enregister les champs restant à titre d'info..

		//System.out.println(""+line.length+" "+indexLine.length+" -->"+line[0]+":"+line[1]+":"+line[2]);

		int cardId=0; //i.e. index
		while (sc.hasNext()) {


			line=sc.nextLine().split(csvDelemiter);
		
			//System.out.println(""+line.length+" "+indexLine.length);
			//System.out.println("-----------------");
			//for (int i=0; i<9;i++)
			//	System.out.print(""+i+":"+line[indexLine[i]]+" ");
			
			
			
			Teacher t; 
			Lesson l; 

			//now, t and l HAVE TO point to a correct object, because we will use it
			if (!teachers.containsKey(line[indexLine[5]])){
				t=new Teacher(line[indexLine[2]],line[indexLine[7]]);
				teachers.put(line[indexLine[5]], t);
				
			}
			else{
				t=teachers.get(line[indexLine[5]]);
			}

			
			if (!lessons.containsKey(line[indexLine[4]])){
				l=new Lesson(line[indexLine[1]]); //concatène 1 et L1 dans le group
				lessons.put(line[indexLine[4]], l);
			}
			else {
				l=lessons.get(line[indexLine[4]]);
			}
			
			// set section, teacher, periods , mode
			l.setOtherInfo( line[indexLine[0]]+line[indexLine[3]]+line[indexLine[8]], t, line[indexLine[6]],line[indexLine[9]]);
			
			t.addCourse(line[indexLine[4]],l);
			// p-e mettre une reference des prof dans l'objet course
			
			Card card=new Card(l,t,cardId);
			cards.put(cardId,card); 
			t.addCard(card);
			cardId++;
			
		}

		// il faut bien évidement rajouter ce qui manque..


		ready=true;
	}



	private void setDelemiter(String line){
		if(line.split(";").length>2)
			this.csvDelemiter=";";
		else if(line.split(",").length>2)
			this.csvDelemiter=",";
	}
	
	private void readXLS(){}

	private void putRightIndex(String[] line, int[] indexLine){
		
		/*
		 * index 0 = year
		 * index 1 = course_name
		 * index 2 = teacher_firstName
		 * index 3 = section
		 * index 4 = courses_id
		 * index 5 = teacher_id
		 * index 6 = period
		 * index 7 = teacher_lastName
		 * index 8 = group
		 * index 9 = class/group
		 */

		int choix=1; // choix semestre
		String sem = "ORCO_NombrePeriodeSemaineSemestre" + choix; 
		
		for (int i=0; i<line.length; i++){

			if(line[i].equalsIgnoreCase("ann�e")){
				indexLine[0]=i;

				//System.out.println("anne "+i+": "+line[i]);
			}

			else if(line[i].equalsIgnoreCase("Intitul� cours")){
				indexLine[1]=i;
				//System.out.println("intitule cour: "+i+": "+line[i]);
			}

			else if(line[i].equalsIgnoreCase("Pr�nom")){
				indexLine[2]=i;

				//System.out.println("prenom: "+i+": "+line[i]);
			}

			else if(line[i].equalsIgnoreCase("nom")){
				indexLine[7]=i;
				

				//System.out.println("nom: "+i+": "+line[i]);
			}
			else if(line[i].equalsIgnoreCase(sem)){
				indexLine[6]=i;

				//System.out.println(sem+": "+i+": "+line[i]);
			}
			
			else if(line[i].equalsIgnoreCase("CodeCours")){
				indexLine[4]=i;

				//System.out.println("CodeCours: "+i+": "+line[i]);
			}

			else if(line[i].equalsIgnoreCase("PERS_Id"))
				indexLine[5]=i;


			else if(line[i].equalsIgnoreCase("groupe"))
				indexLine[8]=i;


			else if(line[i].equalsIgnoreCase("Intitul� Section"))
				indexLine[3]=i;
			
			else if(line[i].equalsIgnoreCase("Mode"))
				indexLine[9]=i;
			
		}
	}

	public Map<Integer,Card> getCards(){
		return cards;
	}
	
	public Map<String,Teacher> getTeachers(){
		return teachers;
	}

	public Map<String,Room> getClassRoom(){
		return rooms; 
	}

	
	
	public void setFilesPath(String filesPath[]){
		this.filesPath=filesPath;
	}

	public String[] getFilesPath(){
		return filesPath;
	}
	
	public boolean isReady(){
		return ready;
	}

}
