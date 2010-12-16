package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.JOptionPane;

import main.Propreties;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


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
	private 
	int choice_sem; // choix semestre

	private boolean ready; //will be true when the object is usable


	/**
	 *  Construct a valid, but "empty" object
	 */
	public StateFullSchedule(){

		filesPath =new String[4];
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
		sections=new TreeMap<String,Section>();
		choice_sem=2;

		csvDelemiter=";";
	}

	/*
	 * cette mÃ©thode, une fois Ã©crite devrait mettre Ã  jour
	 * tout l'Ã©tat de l'objet Ã  partir des infos dans les fichiers
	 */
	public boolean update_from_files(){

		// si ce n'est pas la tt premiÃ¨re update; il faut fermer les fichiers,
		// et tout effacer (sauvegarder ?) 

		/* il faudrait p-e vÃ©rifier ici (du moins dans cette mÃ©thode) 
		 * si les fichiers on changÃ©, (par ex ac une somme md5)
		 * si ils existent, sont dans un format valide, ect */
		init();
		
		if(filesPath[3]!=null && filesPath[3].equalsIgnoreCase("first"))
			choice_sem=1;
		else
			choice_sem=2;
		

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
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les cours n'a pas pu tre trouve",
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
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Le fichier contenant les locaux n'a pas pu tre trouve",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}catch(IOException e){
			JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "Probleme de lecture du fichier contenant les locaux",
					"Impossible de crer nouveau projet", JOptionPane.ERROR_MESSAGE);
			return false;
		}
  
		// System.out.println("sections: "+this.sections);
		// System.out.println("rooms: "+this.rooms);
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

		//faudrait enregister les champs restant Ã  titre d'info..

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
		if (Integer.parseInt(line[indexLine.get("period")])==0){
			//System.out.println(line[indexLine.get("course_name")]+" "+line[indexLine.get("period")]);
			return; //this course doesn't take place this semester
		}
		Teacher t; 
		Lesson l;
		Room r;
		Section s;
		String section_name=line[indexLine.get("year")]+line[indexLine.get("section")]+line[indexLine.get("group")];
		String teacher_lastName=line[indexLine.get("teacher_lastName")];
		if(teacher_lastName.equalsIgnoreCase("{N}"))
			teacher_lastName="Indefini";
		

		//now, t,s,r and l HAVE TO point to a correct object, because we will use it
		
		//teachers
		if (!teachers.containsKey(line[indexLine.get("teacher_id")])){
			t=new Teacher(line[indexLine.get("teacher_firstName")],teacher_lastName, this);
			teachers.put(line[indexLine.get("teacher_id")], t);

		}
		else{
			t=teachers.get(line[indexLine.get("teacher_id")]);
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
			s=new Section(section_name);
			sections.put(section_name, s);
		}
		else {
			s=sections.get(section_name);
		}
		
		
		// set section, teacher, periods , mode
		l.setOtherInfo( line[indexLine.get("year")]+line[indexLine.get("section_name")]+line[indexLine.get("group")], t, line[indexLine.get("period")],line[indexLine.get("mod")]);

		t.addCourse(line[indexLine.get("courses_id")],l);
		// p-e mettre une reference des prof dans l'objet course

		Card card=findMachingCard(l,line[indexLine.get("mod")],s);
		if(card==null){
			card=new Card(l,t,cardId,s,this);
			cards.put(cardId,card); 		
		}
		else{
			card.addSection(s);
		}
		
		t.addCard(card);
		s.addCard(card);



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
		 * index  = year
		 * index  = course_name
		 * index  = teacher_firstName
		 * index  = section
		 * index  = courses_id
		 * index  = teacher_id
		 * index  = period
		 * index  = teacher_lastName
		 * index  = group
		 * index  = mod
		 * section_name
		 */

		String sem = "ORCO_NombrePeriodeSemaineSemestre" + choice_sem; 

		for (int i=0; i<line.length; i++){

			if(line[i].equalsIgnoreCase("annŽe"))
				indexLine.put("year", i);

			else if(line[i].equalsIgnoreCase("IntitulŽ cours"))
				indexLine.put("course_name", i);

			else if(line[i].equalsIgnoreCase("PrŽnom"))
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


			else if(line[i].equalsIgnoreCase("IntitulŽ Section"))
				indexLine.put("section_name", i);

			else if(line[i].equalsIgnoreCase("Section"))
				indexLine.put("section", i);
			
			else if(line[i].equalsIgnoreCase("Mode"))
				indexLine.put("mod", i);

		}
		//System.out.println("indexline :"+indexLine);
		//System.out.println("line      :"+Arrays.toString(line));
	}
	
	private Card findMachingCard(Lesson l,String mod,Section s){
		
		for (Card c:cards.values()){
			if(l==c.getLesson() && 
					mod.equalsIgnoreCase("classe") &&
					s.getName().substring(0, 3).equalsIgnoreCase(c.getCard_sections().get(0).getName().substring(0,3)))
			{
				return c;
			}
		}
		
		return null;
	}

	/*
	 * return the Teacher corresponding to the "firstName LastName"
	 */
	public Teacher findTeacher(String selectedItem){
		
		for (Teacher t: getTeachers().values())
			if (selectedItem.equalsIgnoreCase(t.getFirstName()+" "+t.getLastName()))
				return t;

		return null; //shoulden't happen..
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
	
	/*
	 * return the Section corresponding to the selectedItem
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
