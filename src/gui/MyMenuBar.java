/**
 * 
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.StateFullSchedule;
/**
 * @author 
 *
 */
public class MyMenuBar extends JMenuBar {

	/**
	 * 
	 */
	public MyMenuBar(StateFullSchedule state, MainPanel mainPanel) {

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
		JMenuItem csv = new JMenuItem ("à partir de fichiers CSV");
		// *** end of declaration


		// System.out.println("name: "+this.getRootPane());
		
		csv.addActionListener(new CsvActionListener(state, mainPanel));
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
	
	/*
	 * Le listener qui s'occupera de l'item "créer un nouveau projet
	 * à partir de fichies CSV"
	 * 
	 */
	private class CsvActionListener implements ActionListener{
		StateFullSchedule state;
		MainPanel mainPanel;
		
		public CsvActionListener(StateFullSchedule state, MainPanel mainPanel){
			this.state=state;
			this.mainPanel=mainPanel;
		}
		public void actionPerformed(ActionEvent e){
			new GetCsvFilesDialog(state.getFilesPath());
			state.update();  //update the model; i.e. the internal data
			mainPanel.update(); //update the GUI
		}
	}

}
