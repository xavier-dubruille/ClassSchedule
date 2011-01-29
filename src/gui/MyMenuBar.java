/**
 * 
 */
package gui;


import gui_schedule.FrameSchedule;
import gui_selection.FrameSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import model.NotSuportedFileException;
import model.StateFullSchedule;

/**
 * @author Dubruille Xavier
 * @author Delange Jonas 
 *
 */
public class MyMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private MenuBarFunctions menuBarFunctions;


	/**
	 * 
	 * @param state
	 */
	public MyMenuBar(final StateFullSchedule state) {

		menuBarFunctions=new MenuBarFunctions(state);

		//empty panel; has to be updated later
		//fSc= new  FrameSchedule(); 
		//fSe= new FrameSelection();

		// * top menus
		// * declarations
		JMenu file =new JMenu("Fichier");
		JMenu edit =new JMenu("Edition");
		JMenu help =new JMenu("Aide");
		// * end of declarations

		// ** file 
		// ** declarations
		JMenuItem new_project = new JMenuItem ("Nouveau");
		JMenuItem open = new JMenuItem ("Ouvrir");
		JMenuItem saveProject = new JMenuItem ("Enregistrer");
		JMenuItem export = new JMenuItem ("Exporter en csv");
		JMenuItem quit = new JMenuItem ("Quitter");
		JMenuItem about = new JMenuItem ("About");
		JMenuItem doc = new JMenuItem ("Documentation");
		// ** end of declarations
		
	

		// ** new_project
		new_project.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				menuBarFunctions.newProject();

			}
		});
		new_project.setMnemonic(KeyEvent.VK_N);
		new_project.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.ALT_MASK));
		// ** end of new_project

		
		// ** save
		saveProject.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(state==null || !state.isReady())
					return;
				JFileChooser jf=new JFileChooser();

				int ret=jf.showSaveDialog((JComponent)e.getSource());
				if(ret == JFileChooser.APPROVE_OPTION)
					menuBarFunctions.saveProject(jf.getSelectedFile().getPath());

			}
		});
		saveProject.setMnemonic(KeyEvent.VK_S);
		saveProject.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.ALT_MASK));
		// ** end of save

		// ** export
		export.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				if(state==null || !state.isReady())
					return;
				JFileChooser jf=new JFileChooser();

				int ret=jf.showSaveDialog((JComponent)e.getSource());
				if(ret == JFileChooser.APPROVE_OPTION)
					menuBarFunctions.export(jf.getSelectedFile().getPath());

			}
		});
		export.setMnemonic(KeyEvent.VK_D);
		export.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_D, ActionEvent.ALT_MASK));
		// ** end of export
		
		
		// ** quit
		quit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				// il faudrait gerer la sauvegarde automatique ici
				System.exit(0);
			}
		});
		quit.setMnemonic(KeyEvent.VK_Q);
		quit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Q, ActionEvent.ALT_MASK));
		// ** end of quit

		// ** open
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				JFileChooser jf=new JFileChooser();

				int ret=jf.showOpenDialog((JComponent)e.getSource());
				if(ret == JFileChooser.APPROVE_OPTION)
					try{
						menuBarFunctions.openProject(jf.getSelectedFile().getPath());
					}
				catch(NotSuportedFileException notSuportedE){
					JOptionPane.showMessageDialog(null,  "<html>Le projet n'a pu �tre ouvert <br>" +
							"Le projet doit �tre un fichier du type \"sx\" <br>" +
							"D�sol�.</html>", "Sorry",JOptionPane.ERROR_MESSAGE); 
				}
				catch(Exception exception){
					JOptionPane.showMessageDialog(null,  "<html>Le projet n'a pu �tre ouvert <br>" +
							"D�sol�.</html>", "Sorry",JOptionPane.ERROR_MESSAGE); 
				}

			}
		});
		open.setMnemonic(KeyEvent.VK_O);
		open.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.ALT_MASK));
		// ** end of open

		file.add(new_project);
		file.addSeparator();
		file.add(open);
		file.addSeparator();
		file.add(saveProject);
		file.add(export);
		file.addSeparator();
		file.add(quit);
		// ** end of file

		// * help
		// ** about
		about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){

				JOptionPane.showMessageDialog(null,  "<html>Programme de gestion et creation d'horaire. <br><br>" +
						"R�alis� dans le cadre du cour de <i>Langage avanc� de programmation</i> de Mm. Vroman, <br>" +
						"pour l'Ephec. <br><br>" +
						"Sous licence GPL2.<br><br>" +
						"Auteurs: xavier.dubruille@gmail.com et jonas.delange@gmail.com <br>" +
						"N'h�sitez pas � nous contacter! </html>", 
						"About",JOptionPane.INFORMATION_MESSAGE); 

			}
		});
		// ** end of about

		// ** doc
		doc.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				new DocumentationFrame();

			}
		});
		doc.setMnemonic(KeyEvent.VK_H);
		doc.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_H, ActionEvent.ALT_MASK));

		// ** end of doc
		
		help.add(about);
		help.add(doc);
		// * end of help

		add(file);
		add(edit);
		add(help);
		// * end of top menus
	}

	/**
	 * 
	 * @param fSe
	 * @param fSc
	 */
	public void setPanels(FrameSelection fSe,FrameSchedule fSc){
		menuBarFunctions.setPanels(fSe,fSc);
	}


}
