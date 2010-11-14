/**
 * 
 */
package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author 
 *
 */
public class MyMenuBar extends JMenuBar {

	/**
	 * 
	 */
	public MyMenuBar() {

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


		csv.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				// on va ouvrir un jdialog qui permetra de choisir 
				// les fichier csv.
				
				//il ne sera sans doute pas cree ici, mais d'ici la..
				String path[]=new String[3];
				
				new GetCsvFilesDialog(path);
				
				
			}

		});
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

}
