package model;

import main.Propreties;
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
import java.lang.reflect.Array;


/*
 * Contient de quoi creer un horraire et de 
 * concerver toutes les info le representant.
 */
public class StateFullSchedule {

	Map<String, Lesson> lessons;
	public Map<Integer, Card> cards;
	Map<String, Room> rooms;   
	Map<String, Section> sections; 
	Map<String, Teacher> teachers;
	Map<String, Student> students;
	Map<String,Integer> indexLine;

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

		indexLine=new HashMap<String,Integer>();
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
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les cours n'a pas pu �tre trouve",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(IOException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Probleme de lecture du fichier contenant les cours",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}




		try{
			createStateFromClassRoomFile(filesPath[2]);
		}catch(NoFileException noFile){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "veuillez renter le chemin du fichier csv/exel contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(NotSuportedFileException notSuported){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "seuls les fichiers csv et xls sont valide",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(FileNotFoundException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les locaux n'a pas pu �tre trouve",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(IOException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Probleme de lecture du fichier contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		System.out.println("rooms: "+this.rooms);
		try{
			createStateFromConstrainDir(filesPath[1]);
		}catch(Exception e){
			e.printStackTrace();
			return false;

		}


		ready=true;
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

	private void createStateFromClassRoomFile(String filePath) throws NoFileException,FileNotFoundException,NotSuportedFileException,IOException{
		if (filesPath == null || filesPath.equals("") ) throw new NoFileException();
		if (filePath.endsWith("csv")) readRoomFromCSV(filePath);
		else if(filePath.endsWith("xls")) readRoomFromXLS(filePath);
		else throw new NotSuportedFileException();
	}
	private void createStateFromConstrainDir(String filesPath){
		if (filesPath == null || filesPath.equals("") ) return;
	}

	private void readRoomFromCSV(String filePath){

	}
	private void  readRoomFromXLS(String filePath)throws FileNotFoundException,IOException{
		String[] line;

		InputStream inp = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));



		Sheet sheet1 = wb.getSheetAt(0);
		line=new String[sheet1.getRow(0).getPhysicalNumberOfCells()];


		for (Row row : sheet1) {
			for (Cell cell : row) {

				switch(cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					line[cell.getColumnIndex()]=cell.getRichStringCellValue().getString();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if(DateUtil.isCellDateFormatted(cell)) {
						line[cell.getColumnIndex()]=cell.getDateCellValue().toString();
					} else {

						line[cell.getColumnIndex()]=""+cell.getNumericCellValue();
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					line[cell.getColumnIndex()]=""+cell.getBooleanCellValue();

				default:
					line[cell.getColumnIndex()]="";
				}
			}



			constructRoomStateFromLine(line);

		}
		inp.close();



	}

	private void constructRoomStateFromLine(String []line){

		int seats;
		if(line[1].equalsIgnoreCase("{Indefini}")) {
			if (line[2].equalsIgnoreCase("classe"))
				seats=Propreties.default_class_number_seats;
			else if (line[2].equalsIgnoreCase("auditoire"))
				seats=Propreties.default_auditoir_number_seats;
			else if (line[2].equalsIgnoreCase("informatique"))
				seats=Propreties.default_informatic_number_seats;
			else if (line[2].equalsIgnoreCase("bureau"))
				seats=Propreties.default_bureau_number_seats;
			else if (line[2].equalsIgnoreCase("couloir"))
				seats=Propreties.default_couloir_number_seats;
			else if (line[2].equalsIgnoreCase("auditoire"))
				seats=Propreties.default_auditoir_number_seats;
			else if (line[2].equalsIgnoreCase("Laboratoire"))
				seats=Propreties.default_labo_number_seats;
			else 
				seats=0;

		}
		else{
			try {
				seats=(int)Double.parseDouble(line[1]);
			}catch(NumberFormatException na){
				return;
			}
		}
		if (seats==0){
			return;
		}



		rooms.put(line[0], new Room(line[0],seats,line[2]));
	}


	/*
	 * Read the csv file containing the courses (the cards)
	 * 
	 * pre:filePath has to be a valid String
	 */
	private void readCardFromCSV(String filePath) throws FileNotFoundException{

		String[] line;

		Scanner sc=new Scanner(new File(filePath));
		String firstLine=sc.nextLine();
		setDelemiter(firstLine);
		line=firstLine.split(csvDelemiter);

		putRightIndex(line);

		//faudrait enregister les champs restant à titre d'info..

		int cardId=0; //i.e. index
		while (sc.hasNext()) {

			line=sc.nextLine().split(csvDelemiter);
			constructCardStateFromLine(line,cardId);
			cardId++;
		}

	}



	private void setDelemiter(String line){
		if(line.split(";").length>2)
			this.csvDelemiter=";";
		else if(line.split(",").length>2)
			this.csvDelemiter=",";
	}

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
	 * index 9 = mod
	 */
	private void constructCardStateFromLine(String[] line, int cardId){

		//System.out.println("construct card state from line "+Arrays.toString(line));

		Teacher t; 
		Lesson l;
		Section s;
		

		//now, t,s and l HAVE TO point to a correct object, because we will use it
		if (!teachers.containsKey(line[indexLine.get("teacher_id")])){
			t=new Teacher(line[indexLine.get("teacher_firstName")],line[indexLine.get("teacher_lastName")]);
			teachers.put(line[indexLine.get("teacher_id")], t);

		}
		else{
			t=teachers.get(line[indexLine.get("teacher_id")]);
		}


		if (!lessons.containsKey(line[indexLine.get("courses_id")])){
			l=new Lesson(line[indexLine.get("course_name")]); //concatène 1 et L1 dans le group
			lessons.put(line[indexLine.get("courses_id")], l);
		}
		else {
			l=lessons.get(line[indexLine.get("courses_id")]);
		}

		// set section, teacher, periods , mode
		l.setOtherInfo( line[indexLine.get("year")]+line[indexLine.get("section")]+line[indexLine.get("group")], t, line[indexLine.get("period")],line[indexLine.get("mod")]);

		t.addCourse(line[indexLine.get("courses_id")],l);
		// p-e mettre une reference des prof dans l'objet course

		Card card=new Card(l,t,cardId,rooms);
		cards.put(cardId,card); 
		t.addCard(card);




		// il faut bien évidement rajouter ce qui manque..


	}

	private void readCardFromXLS(String filePath) throws FileNotFoundException,IOException{
		String[] line;

		InputStream inp = new FileInputStream(filePath);
		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(inp));



		Sheet sheet1 = wb.getSheetAt(0);
		line=new String[sheet1.getRow(0).getPhysicalNumberOfCells()];


		for (Row row : sheet1) {
			for (Cell cell : row) {

				switch(cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					line[cell.getColumnIndex()]=cell.getRichStringCellValue().getString();
					break;
				case Cell.CELL_TYPE_NUMERIC:
					if(DateUtil.isCellDateFormatted(cell)) {
						line[cell.getColumnIndex()]=cell.getDateCellValue().toString();
					} else {

						line[cell.getColumnIndex()]=""+cell.getNumericCellValue();
					}
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					line[cell.getColumnIndex()]=""+cell.getBooleanCellValue();

				default:
					line[cell.getColumnIndex()]="";
				}
			}



			if(row.getRowNum()==0)
				putRightIndex(line);
			else
				constructCardStateFromLine(line,row.getRowNum());

		}
		inp.close();



	}

	private void putRightIndex(String[] line){

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
		 * index 9 = mod
		 */

		int choix=1; // choix semestre
		String sem = "ORCO_NombrePeriodeSemaineSemestre" + choix; 

		for (int i=0; i<line.length; i++){

			if(line[i].equalsIgnoreCase("ann�e"))
				indexLine.put("year", i);

			else if(line[i].equalsIgnoreCase("Intitul� cours"))
				indexLine.put("course_name", i);

			else if(line[i].equalsIgnoreCase("Pr�nom"))
				indexLine.put("teacher_firstName", i);

			else if(line[i].equalsIgnoreCase("nom"))
				indexLine.put("teacher_lastName", i);

			else if(line[i].equalsIgnoreCase(sem))
				indexLine.put("period", i);

			else if(line[i].equalsIgnoreCase("CodeCours"))
				indexLine.put("courses_id", i);

			else if(line[i].equalsIgnoreCase("PERS_Id"))
				indexLine.put("teacher_id", i);


			else if(line[i].equalsIgnoreCase("groupe"))
				indexLine.put("group", i);


			else if(line[i].equalsIgnoreCase("Intitul� Section"))
				indexLine.put("section", i);

			else if(line[i].equalsIgnoreCase("Mode"))
				indexLine.put("mod", i);

		}
		//System.out.println("indexline :"+indexLine);
		//System.out.println("line      :"+Arrays.toString(line));
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
