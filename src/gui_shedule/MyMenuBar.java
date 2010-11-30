/**
 * 
 */
package gui_shedule;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.StateFullSchedule;
import gui_selection.FrameSelection;

/**
 * @author 
 *
 */
public class MyMenuBar extends JMenuBar {

	FrameSchedule fSc;
	FrameSelection fSe;
	/**
	 * 
	 */
	public MyMenuBar(StateFullSchedule state) {

		//empty panel; has to be updated later
		fSc= new  FrameSchedule(); 
		fSe= new FrameSelection();

		// * top menus
		// * declarations
		JMenu file =new JMenu("Fichier");
		JMenu edit =new JMenu("Edition");
		JMenu help =new JMenu("Aide");
		// * end of declarations

		// ** file 
		// ** declarations
		JMenu new_project = new JMenu ("nouveau");
		JMenuItem open = new JMenuItem ("ouvrir");
		JMenuItem quit = new JMenuItem ("quiter");
		// ** end of declarations

		// *** new_project 
		// *** declarations
		JMenuItem csv = new JMenuItem ("A partir de fichiers CSV");
		// *** end of declaration


		// System.out.println("name: "+this.getRootPane());

		csv.addActionListener(new CsvActionListener(state));
		new_project.add(csv);
		// *** end of new_project

		// ** quit
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// il faudrait gerer la sauvegarde automatique ici
				System.exit(0);
			}
		});
		// ** end of quit

		file.add(new_project);
		file.add(open);
		file.add(quit);
		// ** end of file

		add(file);
		add(edit);
		add(help);
		// * end of top menus
	}
	
	public void setPanels(FrameSelection fSe,FrameSchedule fSc){
		this.fSc=fSc;
		this.fSe=fSe;
	}

	/*
	 * Le listener qui s'occupera de l'item "créer un nouveau projet
	 * à partir de fichies CSV"
	 * 
	 */
	private class CsvActionListener implements ActionListener{
		StateFullSchedule state;


		public CsvActionListener(StateFullSchedule state){
			this.state=state;
		}
		
		public void actionPerformed(ActionEvent e){
			new GetCsvFilesDialog(state.getFilesPath());
			state.update_from_files();  //update the model; i.e. the internal data
			fSc.update_from_state(); //update the GUI
			fSe.update_from_state(); //update the GUI
		}
	}

}
