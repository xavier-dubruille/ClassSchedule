package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import main.Main_properties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


/**
 * This class is the most important of the wall program.
 * It contains all the data, it is really the model in a model-view-controler pattern
 * 
 * This class has also everything to construct himself from a file, or save him to a file
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
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
	private 
	int choice_sem; // semester choice

	private boolean ready; //will be true when the object is usable


	/**
	 *  Construct a valid, but "empty" object
	 */
	public StateFullSchedule(){

		filesPath =new String[4];
		init();
	}


	/**
	 * initiation procedure, it's more like a extension of the constructor
	 */
	private void init(){
		ready=false;

		indexLine=new HashMap<String,Integer>();
		lessons=new TreeMap<String, Lesson>();
		cards=new TreeMap<Integer, Card>();
		rooms=new TreeMap<String, Room>();
		teachers=new TreeMap<String, Teacher>();
		students=new TreeMap<String, Student>();
		sections=new TreeMap<String,Section>();
		choice_sem=2;

		csvDelemiter=";";
	}

	/**
	 * This is everything is construct from the files.
	 * Pre: the path files must be valid before calling this method
	 * the path files must be in filePath[]
	 * 
	 * @return true if it was a success
	 */
	public boolean update_from_files(){

		
		
		init(); // maybe there is more to (re)initiate ?


		if(filesPath[3]!=null && filesPath[3].equalsIgnoreCase("first"))
			choice_sem=1;
		else
			choice_sem=2;

		System.out.println("et donc le choix de semestre est: "+choice_sem);
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
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les cours n'a pas pu être trouve",
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
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les locaux n'a pas pu être trouve",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(IOException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Probleme de lecture du fichier contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}


		try{
			createStateFromConstrainDir(filesPath[1]);
		}catch(IOException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Impossible de lire les fichiers contenenant les contraintes",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;

		}


		// System.out.println("choix semestre :"+choice_sem+" ("+filesPath[3]+")");
		// System.out.println("sections: "+this.sections);
		// System.out.println("rooms: "+this.rooms);
		// 		for (Teacher t: this.teachers.values())
		// System.out.println(t.getLastName()+": "+t.getCard());


		ready=true;
		return true;

	}

	/**
	 * Construct the state based on the first/main file containing all the courses/teachers/cards
	 * @param filePath the file path of the file to be read
	 * @throws NoFileException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void createStateFromCardFile(String filePath) throws NoFileException,FileNotFoundException,IOException{
		if (filePath == null || filesPath.equals("") ) throw new NoFileException();
		if (filePath.endsWith("csv")) readCardFromCSV(filePath);
		else if(filePath.endsWith("xls")) readCardFromXLS(filePath);
		else throw new NotSuportedFileException();
	}

	/**
	 * Construct the state based on the second file. Containing all room specifications.
	 * @param filePath the file path of the file to be read
	 * @throws NoFileException
	 * @throws FileNotFoundException
	 * @throws NotSuportedFileException
	 * @throws IOException
	 */
	private void createStateFromClassRoomFile(String filePath) throws NoFileException,FileNotFoundException,NotSuportedFileException,IOException{
		if (filesPath == null || filesPath.equals("") ) throw new NoFileException();
		if (filePath.endsWith("csv")) readRoomFromCSV(filePath);
		else if(filePath.endsWith("xls")) readRoomFromXLS(filePath);
		else throw new NotSuportedFileException();
	}
	
	/**
	 * construct the state based on the directory containing all the constrains of the teachers, classRooms, sections..
	 * @param filesPath the path of the directory
	 * @throws IOException
	 */
	private void createStateFromConstrainDir(String filesPath) throws IOException{
		if (filesPath == null || filesPath.equals("") ) return;

		/*****************************/

		File rep = new File(filesPath);
		File[] constraints = rep.listFiles();
		for(File f:constraints)
			treatConstraintFile(f);

	}


	/**
	 * 
	 * called for each file in the constraints directory.. 
	 * will build the state accordingly
	 * @param file the File to be treated
	 * @throws IOException
	 */
	private void treatConstraintFile(File file) throws IOException{
		if (!file.getName().endsWith(".txt")) return;

		String name[]=file.getName().split("\\.");

		System.out.println(Arrays.toString(name));

		Teacher t=findTeacherFromLastName(name[0]);
		System.out.println(name[0]);
		if(t!=null)
			updateTeacherConstraints(t,file);

	}


	/**
	 * 
	 * Called for each teacher's file's constrains.
	 * Will build the state accordingly
	 * 
	 * @param teacher the teacher concerned by this changes
	 * @param file the File containing this theacher's preferrences (=the constraints)
	 * @throws IOException
	 */
	private void updateTeacherConstraints (Teacher teacher,File file) throws IOException{
		System.out.println("update teacher constraints"+teacher.getFirstName()+" "+teacher.getLastName());
		String[] lineTab;

		String line;
		Scanner sc=new Scanner(file);


		while(sc.hasNext()){

			line=sc.nextLine();

			if(line.length()<3) continue; 
			if(line.substring(0, 1).equals("#")) continue;

			lineTab=line.split("=");
			if(lineTab.length!=2) continue;
			teacher.setPreferedSlide(Integer.parseInt(lineTab[0]), Integer.parseInt(lineTab[1]));

		}

	}


	/**
	 * 
	 * construct the state room from a csv file
	 * @param filePath the file path of the csv file containing the rooms spec
	 */
	private void readRoomFromCSV(String filePath){

	}
	
	/**
	 * 
	 * Construct the state room from a xls file
	 * @param filePath the file path of the xls file containing the rooms spec
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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

	/**
	 * Called for each line in a room file specifcation. no matter if it's a csv file, a xls or orther
	 * @param line the table represanting the line to be treated.
	 */
	private void constructRoomStateFromLine(String []line){

		int seats;
		if(line[1].equalsIgnoreCase("{Indefini}")) {
			if (line[2].equalsIgnoreCase("classe"))
				seats=Main_properties.default_class_number_seats;
			else if (line[2].equalsIgnoreCase("auditoire"))
				seats=Main_properties.default_auditoir_number_seats;
			else if (line[2].equalsIgnoreCase("informatique"))
				seats=Main_properties.default_informatic_number_seats;
			else if (line[2].equalsIgnoreCase("bureau"))
				seats=Main_properties.default_bureau_number_seats;
			else if (line[2].equalsIgnoreCase("couloir"))
				seats=Main_properties.default_couloir_number_seats;
			else if (line[2].equalsIgnoreCase("auditoire"))
				seats=Main_properties.default_auditoir_number_seats;
			else if (line[2].equalsIgnoreCase("Laboratoire"))
				seats=Main_properties.default_labo_number_seats;
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


	/**
	 * Read the csv file containing the courses (the cards) 
	 * pre:filePath has to be a valid String
	 *
	 * @param filePath the file path of the csv file containing the course. has to be a valid path.
	 * @throws FileNotFoundException
	 */
	private void readCardFromCSV(String filePath) throws FileNotFoundException{

		String[] line;

		Scanner sc=new Scanner(new File(filePath));
		String firstLine=sc.nextLine();
		setDelemiter(firstLine);
		line=firstLine.split(csvDelemiter);

		putRightIndex(line);

		//should we save the rest of the info ?

		int cardId=0; //i.e. index
		while (sc.hasNext()) {

			line=sc.nextLine().split(csvDelemiter);
			constructCardStateFromLine(line,cardId);
			cardId++;
		}

	}



	/**
	 * will set the most probable delimiter for a csv file
	 * @param line a line of the file
	 */
	private void setDelemiter(String line){
		if(line.split(";").length>2)
			this.csvDelemiter=";";
		else if(line.split(",").length>2)
			this.csvDelemiter=",";
	}


	/**
	 * 
	 *  Called for each lines of the file containing the courses,teachers, ect
	 *  It's separeted from the rest because it can be called from any 
	 *  type of file (in our case: csv and xls)
	 *   
	 * @param line the String table representing a line of the file
	 * @param cardId the card id, must be different each time the method is called.
	 */
	private void constructCardStateFromLine(String[] line, int cardId){

		int periods=Integer.parseInt(line[indexLine.get("period")]);
		//System.out.println("construct card state from line "+Arrays.toString(line));
		if (periods==0){
			//System.out.println(line[indexLine.get("course_name")]+" "+line[indexLine.get("period")]);
			//System.out.println("index line pour les periods: "+indexLine.get("period"));
			return; //this course doesn't take place this semester
		}
		Teacher t; 
		Lesson l;
		//Room r;
		Section s;
		String section_name=line[indexLine.get("year")]+line[indexLine.get("section")]+line[indexLine.get("group")];
		String teacher_lastName=line[indexLine.get("teacher_lastName")];
		if(teacher_lastName.equalsIgnoreCase("{N}"))
			teacher_lastName="Indefini";


		//now, t,s,r and l HAVE TO point to a correct object, because we will use it

		//teachers
		if (!teachers.containsKey(teacher_lastName+line[indexLine.get("teacher_firstName")])){
			t=new Teacher(line[indexLine.get("teacher_firstName")],teacher_lastName, this);
			teachers.put(line[indexLine.get("teacher_lastName")]+line[indexLine.get("teacher_firstName")], t);

		}
		else{
			t=teachers.get(teacher_lastName+line[indexLine.get("teacher_firstName")]);
		}

		//lessons
		if (!lessons.containsKey(line[indexLine.get("courses_id")])){
			l=new Lesson(line[indexLine.get("course_name")]); 
			lessons.put(line[indexLine.get("courses_id")], l);
		}
		else {
			l=lessons.get(line[indexLine.get("courses_id")]);
		}

		//sections
		if (!sections.containsKey(section_name)){
			s=new Section(line[indexLine.get("year")],line[indexLine.get("section")],line[indexLine.get("group")]);
			sections.put(section_name, s);
		}
		else {
			s=sections.get(section_name);
		}


		// set section, teacher, periods , mode
		l.setOtherInfo( line[indexLine.get("year")]+line[indexLine.get("section_name")]+line[indexLine.get("group")], t, line[indexLine.get("period")],line[indexLine.get("mod")]);

		t.addCourse(line[indexLine.get("courses_id")],l);
		// should we put a reference to the teacher in the Lesson object ?

		ArrayList<Card> matchingCards=findMachingCards(l,line[indexLine.get("mod")],s);
		Card card;

		if(matchingCards.size()==0){
			for(int i=0; i<periods; i++){
				card=new Card(l,t,cardId*10+i,s,this,line[indexLine.get("info")],line[indexLine.get("mod")]);
				cards.put(cardId*10+i,card); 	
				t.addCard(card);
				s.addCard(card);
			}
			//if(line[indexLine.get("teacher_lastName")].equalsIgnoreCase("Batugowski"))
			//System.out.println("Batu, nvll carte: "+line[indexLine.get("course_name")]);
		}
		else{
			for(Card c: matchingCards)
				c.addSection(s);
		}

	}

	/**
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
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

						line[cell.getColumnIndex()]=""+(int)cell.getNumericCellValue();
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
		 * The different keys of "indexLine" are:
		 *  year
		 *  course_name
		 *  teacher_firstName
		 *  section
		 *  courses_id
		 *  teacher_id
		 *  period
		 *  teacher_lastName
		 *  group
		 *  mod
		 *  section_name
		 */

		String sem = ("ORCO_NombrePeriodeSemaineSemestre" + choice_sem); 
		//System.out.println("choice sem: "+choice_sem+" "+sem);

		for (int i=0; i<line.length; i++){

			if(line[i].equalsIgnoreCase("année"))
				indexLine.put("year", i);

			else if(line[i].equalsIgnoreCase("Intitulé cours"))
				indexLine.put("course_name", i);

			else if(line[i].equalsIgnoreCase("Prénom"))
				indexLine.put("teacher_firstName", i);

			else if(line[i].equalsIgnoreCase("nom"))
				indexLine.put("teacher_lastName", i);

			else if(line[i].equalsIgnoreCase(sem)){
				indexLine.put("period", i);
				//System.out.println("on a fixé l'index des periodes à: "+i);
			}

			else if(line[i].equalsIgnoreCase("CodeCours"))
				indexLine.put("courses_id", i);

			else if(line[i].equalsIgnoreCase("PERS_Id"))
				indexLine.put("teacher_id", i);


			else if(line[i].equalsIgnoreCase("groupe"))
				indexLine.put("group", i);


			else if(line[i].equalsIgnoreCase("Intitulé Section"))
				indexLine.put("section_name", i);

			else if(line[i].equalsIgnoreCase("Section"))
				indexLine.put("section", i);

			else if(line[i].equalsIgnoreCase("Mode"))
				indexLine.put("mod", i);
			
			else if(line[i].equalsIgnoreCase("ORCO_SalleInformatique"))
				indexLine.put("info", i);
			
			

		}
		//System.out.println("indexline :"+indexLine);
		//System.out.println("line      :"+Arrays.toString(line));
	}

	private ArrayList<Card> findMachingCards(Lesson l,String mod,Section s){
		//System.out.println("secType="+s.getSectionType());
		
		ArrayList<Card> toReturn=new ArrayList<Card>();
		String groupLetters=s.getGroupNumber().substring(0, (s.getGroupNumber().length())-1);
		String cardGroupLetters, cardSecGroupN;

		for (Card c:cards.values()){
			cardSecGroupN=c.getCard_sections().get(0).getGroupNumber();
			cardGroupLetters=cardSecGroupN.substring(0, (cardSecGroupN.length())-1);

			if(l==c.getLesson() && 
					mod.equalsIgnoreCase("classe") &&
					s.getYear().equalsIgnoreCase(c.getCard_sections().get(0).getYear()) &&
					s.getSectionType().equalsIgnoreCase(c.getCard_sections().get(0).getSectionType()) &&
					groupLetters.equalsIgnoreCase(cardGroupLetters)) 
			{
				//System.out.println("cardGroupLetter="+cardGroupLetters+", groupLetter="+groupLetters);
				toReturn.add(c);
			}
		}

		return toReturn;
	}

	/*
	 * return the Teacher corresponding to the "LastName"
	 */
	public Teacher findTeacherFromLastName(String string){

		for (Teacher t: getTeachers().values())
			if (string.equalsIgnoreCase(t.getLastName()))
				return t;

		return null; 
	}

	/*
	 * return the Teacher corresponding to the "firstName LastName"
	 */
	public Teacher findTeacher(String selectedItem){

		for (Teacher t: getTeachers().values())
			if (selectedItem.equalsIgnoreCase(t.getFirstName()+" "+t.getLastName()))
				return t;

		return null; 
	}


	/*
	 * return the Room corresponding to the selectedItem
	 */
	public Room findRoom(String selectedItem){

		for (Room r: getClassRoom().values())
			if (selectedItem.equalsIgnoreCase(r.getName()))
				return r;

		return null; //shoulden't happen..

	}

	/**
	 * return the Section corresponding to the selectedItem
	 * 
	 * @param selectedItem
	 * @return
	 */
	public Section findSection(String selectedItem){

		for (Section s: getSections().values())
			if (selectedItem.equalsIgnoreCase(s.getName()))
				return s;

		return null; //shoulden't happen..

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

	public Map<String, Section> getSections(){
		return sections;
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
