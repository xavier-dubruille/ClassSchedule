package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author Dubruille Xavier
 * @author Delange Jonas
 */

/**
 * Not used i think.  If so, i think it may be deleted..
 */
public class SaveFileDialog extends JDialog implements  ActionListener{

	JTextField jt_constrain, jt_lesson, jt_classRoom;
	String output[];
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * Create a dialog 'frame' to save the project 
	 * 
	 * @param output the string tab where this dialog will put the files
	 */
	public SaveFileDialog(String[] output){

		this.output=output;

		setPreferredSize(GUI_properties.dialog_size);
		setMinimumSize(GUI_properties.dialog_size);
		setSize(GUI_properties.dialog_size);

		JPanel j=new JPanel();
		j.setLayout(new BoxLayout(j,BoxLayout.Y_AXIS));


		j.add(Box.createGlue());


		//First field: the lessons
		JPanel lesson=new JPanel();
		lesson.setLayout(new BoxLayout(lesson,BoxLayout.X_AXIS));

		lesson.add(Box.createHorizontalGlue());
		JLabel jl=new JLabel("    Fichier contenant les cours");
		jl.setPreferredSize(GUI_properties.size_label_dialog);
		lesson.add(jl);

		lesson.add(Box.createHorizontalGlue());
		jt_lesson=new JTextField(20);
		jt_lesson.setMaximumSize(new Dimension(110,30));
		lesson.add(jt_lesson);

		lesson.add(Box.createHorizontalGlue());
		JButton jb_lesson = new JButton("Browse");
		jb_lesson.setActionCommand("lesson");
		jb_lesson.addActionListener(this);
		lesson.add(jb_lesson);

		lesson.add(Box.createHorizontalGlue());
		j.add(lesson);



		j.add(Box.createGlue());

		JButton create=new JButton("creer");
		create.setActionCommand("create");
		create.addActionListener(this);
		j.add(create);
		getContentPane().add(j);

		this.pack();
		setModal(true);
		setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e){
		JFileChooser jf=new JFileChooser();
		int returnVal;


		//System.out.println(e.getActionCommand());
		
		javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter(){
			@Override
			public boolean accept(File f){
				return f.getName().endsWith(".csv")||f.isDirectory()||f.getName().endsWith(".xls");}
			@Override
			public String getDescription(){return "fichiers csv et xls";}
		};



		if (e.getActionCommand().equals("sem1")){
			output[3]="first";
		}
		if (e.getActionCommand().equals("sem2")){
			output[3]="second";
		}
		
		if (e.getActionCommand().equals("lesson")){

			jf.setFileFilter(filter);
			jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
			returnVal=jf.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				jt_lesson.setText(jf.getSelectedFile().getPath());
		}
		if (e.getActionCommand().equals("constrain")){

			jf.resetChoosableFileFilters();
			jf.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			returnVal =jf.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				jt_constrain.setText(jf.getSelectedFile().getPath());

		}
		if (e.getActionCommand().equals("classRoom")){

			jf.setFileFilter(filter);
			jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
			returnVal =jf.showOpenDialog(this);
			if(returnVal == JFileChooser.APPROVE_OPTION)
				jt_classRoom.setText(jf.getSelectedFile().getPath());
		}
		if (e.getActionCommand().equals("create")){

			output[0]=jt_lesson.getText();
			output[1]=jt_constrain.getText();
			output[2]=jt_classRoom.getText();
			//output[3]=sem;
			//System.out.println("actionPerfomed du Dialog; sem= "+sem);

			this.setVisible(false);
		}
	}

}
