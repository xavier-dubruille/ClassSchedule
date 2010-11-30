
import gui_shedule.*;
import gui_selection.*;
import model.StateFullSchedule;
import javax.swing.*;

public class Start {

	/**
	 *  
	 */
	public static void main(String[] args) {

		// faudrait p-e verifier ici que la JVM soie à une bonne version
		
		
		
		StateFullSchedule state;
		MyMenuBar bar;
		FrameSchedule fSc;
		FrameSelection fSe;
		
		state=new StateFullSchedule();
		
		//create empty,not visible Frame
		fSc=new FrameSchedule();
		fSe=new FrameSelection(); 
		
		bar=new MyMenuBar(state,fSc,fSe);
		fSc=new FrameSchedule(state,bar);
		fSe=new FrameSelection(state); 
		

		// faudrait lancer un thread de "fin d'application" (handHookThread ?)
		// pour sauvegarder dans un fichier caché l'état et vérifier au démarage
		// si un tel fichier existe.
		
	}

}
