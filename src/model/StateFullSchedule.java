package model;

import java.util.*;
import java.io.*;

import javax.swing.JOptionPane;
import org.apache.*;
import org.apache.poi.*;
import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;


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

	private boolean ready; //will be true when the object is usable


	/**
	 *  Construct a valid, but "empty" object
	 */
	public StateFullSchedule(){

		filesPath =new String[3];
		init();
	}


	private void init(){
		ready=false;
		lessons=new TreeMap<String, Lesson>();
		cards=new TreeMap<Integer, Card>();
		rooms=new TreeMap<String, Room>();
		teachers=new TreeMap<String, Teacher>();
		students=new TreeMap<String, Student>();


		csvDelemiter=";";
	}

	/*
	 * cette méthode, une fois écrite devrait mettre à jour
	 * tout l'état de l'objet à partir des infos dans les fichiers
	 */
	public boolean update_from_files(){

		// si ce n'est pas la tt première update; il faut fermer les fichiers,
		// et tout effacer (sauvegarder ?) 

		/* il faudrait p-e vérifier ici (du moins dans cette méthode) 
		 * si les fichiers on changé, (par ex ac une somme md5)
		 * si ils existent, sont dans un format valide, ect */
		init();


		try{
			createStateFromCardFile(filesPath[0]);

		}catch(NoFileException noFile){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du fichier csv/exel contenant les cours",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(NotSuportedFileException notSuported){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "seuls les fichiers csv et xls sont valide",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(Exception e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Impossible de crer nouveau projet",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}




		try{
			createStateFromClassRoomFile(filesPath[1]);
		}catch(NoFileException noFile){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du fichier csv contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(Exception e){

			e.printStackTrace();
			return false;
		}

		try{
			createStateFromConstrainDir(filesPath[2]);
		}catch(Exception e){
			e.printStackTrace();
			return false;

		}
		
		return true;

		/*********************************************
		 * Let's update all the maps
		 *********************************************/

	}

	private void createStateFromCardFile(String filePath) throws NoFileException,FileNotFoundException,IOException{
		if (filePath == null || filesPath.equals("") ) throw new NoFileException();
		if (filePath.endsWith("csv")) readCardFromCSV(filePath);
		else if(filePath.endsWith("xls")) readCardFromXLS(filePath);
		else throw new NotSuportedFileException();
	}
	private void createStateFromClassRoomFile(String filesPath) throws NoFileException{
		if (filesPath == null || filesPath.equals("") ) throw new NoFileException();
	}
	private void createStateFromConstrainDir(String filesPath){
		if (filesPath == null || filesPath.equals("") ) return;
	}


	/*
	 * Read the csv file containing the courses (the cards)
	 * 
	 * pre:filePath has to be valid
	 */
	private void readCardFromCSV(String filePath) throws FileNotFoundException{

		String[] line;
		int[] indexLine;

		Scanner sc=new Scanner(new File(filePath));
		String firstLine=sc.nextLine();
		setDelemiter(firstLine);
		line=firstLine.split(csvDelemiter);

		indexLine=new int[line.length];
		putRightIndex(line, indexLine);

		//faudrait enregister les champs restant à titre d'info..

		int cardId=0; //i.e. index
		while (sc.hasNext()) {

			line=sc.nextLine().split(csvDelemiter);
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

	private void readCardFromXLS(String filePath) throws FileNotFoundException,IOException{
		
		InputStream inp = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));
		
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			for (Cell cell : row) {
				CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
				System.out.print(cellRef.formatAsString());
				System.out.print(" - ");
				
				switch(cell.getCellType()) {
		      case Cell.CELL_TYPE_STRING:
		        System.out.println(cell.getRichStringCellValue().getString());
		        break;
		      case Cell.CELL_TYPE_NUMERIC:
		        if(DateUtil.isCellDateFormatted(cell)) {
		          System.out.println(cell.getDateCellValue());
		        } else {
		          System.out.println(cell.getNumericCellValue());
		        }
		        break;
		      case Cell.CELL_TYPE_BOOLEAN:
		        System.out.println(cell.getBooleanCellValue());
		        break;
		      case Cell.CELL_TYPE_FORMULA:
		        System.out.println(cell.getCellFormula());
		        break;
		      default:
		        System.out.println();
				}
			}
		}

	}

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
