package gui;

import gui_schedule.FrameSchedule;
import gui_selection.FrameSelection;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import model.Card;
import model.NotSuportedFileException;
import model.StateFullSchedule;

public class MenuBarFunctions {
	private final StateFullSchedule state;
	private FrameSelection fSe;
	private FrameSchedule fSc;

	public MenuBarFunctions(final StateFullSchedule state){
		this.state=state;
	}

	/**
	 *  create a new project
	 */
	public void newProject(){


		boolean reset=state.isReady();

		if(reset){	
			state.filesPath=new String[4];

			System.out.println("et on recommence..");

			state.emptyMe();

		}

		//GetFilesDialog dialog=
		new GetFilesDialog(state.getFilesPath());

		if(reset){	

		}


		boolean IsUpdatedRight=state.update_from_files();
		if(!IsUpdatedRight && reset)
			System.out.println("Strange state? .. do we have to fix that ?..");

		if(IsUpdatedRight){ //update the model; i.e. the internal data

			fSc.update_from_state(); //update the GUI
			fSe.update_from_state(); //update the GUI

		}
	}


	/**
	 * Will export all the state in a csv file
	 * @param path the path where the project will be saved
	 */
	public void export(String path){
		String realPath= path+".csv";//path.split("\\.")[0]+".csv";
		//System.out.println(realPath);
		PrintWriter writer = null;

		try{
			writer =  new PrintWriter(new BufferedWriter
					(new FileWriter(realPath)));
		}catch (IOException io){
			System.err.println("the file can't be saved");
		}

		writer.println("Carton id; Nom du cour; Professeur; Local; Jour; Periode");
		for(Card c: state.getCards().values())
			if(c.getTimePeriod()!=0)
				writer.println(""+c.getCardId()+"; "+c.getLesson().getName()+"; "+c.getTeacher().getLastName()+"; "+c.getClassRoom().getName()+"; "+c.getDayName()+"; "+c.getPeriodName());

		writer.close();

	}

	/**
	 * save the project into "path"
	 * @param path
	 */
	public void saveProject(String path){
		try {
	

			FileOutputStream fos = new FileOutputStream(path+".sx");
			ObjectOutputStream oos= new ObjectOutputStream(fos);
			
			try {
				oos.writeObject(state); 
				oos.flush();
			} finally {
				try {
					oos.close();
				} finally {
					fos.close();
				}
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}


	/**
	 * 
	 */
	public void openProject(String path) throws NotSuportedFileException, IOException, ClassNotFoundException {

		if (!path.endsWith(".sx"))
			throw new NotSuportedFileException();

		StateFullSchedule savedState=null;

		FileInputStream fis = new FileInputStream(path);
		ObjectInputStream ois= new ObjectInputStream(fis);

		savedState = (StateFullSchedule) ois.readObject(); 

		ois.close();
		fis.close();

		System.out.println("Fichier ouvert coectement: nombre de cartons= "+savedState.cards_size());

		state.cloneSavedState(savedState);

		fSe.update_from_state();
		fSc.update_from_state();


	}


	/**
	 * 
	 */
	public void quit(){

	}

	public void setPanels(FrameSelection fSe, FrameSchedule fSc) {
		this.fSe=fSe;
		this.fSc=fSc;

	}

}
