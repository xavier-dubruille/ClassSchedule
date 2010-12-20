package gui_schedule;

import gui.GUI_Propreties;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * @author Dubruille Xavier
 * @author Delange Jonas
 */
public class GetFilesDialog extends JDialog implements ActionListener{

	JTextField jt_constrain, jt_lesson, jt_classRoom;
	String output[];
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param output
	 */
	public GetFilesDialog(String[] output){

		this.output=output;
		output[3]="first";

		setPreferredSize(GUI_Propreties.dialog_size);
		setMinimumSize(GUI_Propreties.dialog_size);
		setSize(GUI_Propreties.dialog_size);

		JPanel j=new JPanel();
		j.setLayout(new BoxLayout(j,BoxLayout.Y_AXIS));


		j.add(Box.createGlue());

		// 0th field: the semester checkbox
		JPanel semester=new JPanel();
		semester.setLayout(new BoxLayout(semester,BoxLayout.X_AXIS));
		JRadioButton sem1=new JRadioButton("semestre 1");
		sem1.setSelected(true);
		JRadioButton sem2=new JRadioButton("semestre 2");
		ButtonGroup group = new ButtonGroup();
		group.add(sem1);
		group.add(sem2);

		sem1.setActionCommand("sem1");
		sem1.addActionListener(this);
		sem2.setActionCommand("sem2");
		sem2.addActionListener(this);
		semester.add(sem1);
		semester.add(sem2);
	
		
		j.add(semester);

		//First field: the lessons
		JPanel lesson=new JPanel();
		lesson.setLayout(new BoxLayout(lesson,BoxLayout.X_AXIS));

		lesson.add(Box.createHorizontalGlue());
		JLabel jl=new JLabel("    Fichier contenant les cours");
		jl.setPreferredSize(GUI_Propreties.size_label_dialog);
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


		// second field : the classRooms
		JPanel classRoom=new JPanel();
		classRoom.setLayout(new BoxLayout(classRoom,BoxLayout.X_AXIS));

		classRoom.add(Box.createHorizontalGlue());

		JLabel classLabel=new JLabel("    Fichier contenant les locaux");
		classLabel.setPreferredSize(GUI_Propreties.size_label_dialog);
		classRoom.add(classLabel);

		classRoom.add(Box.createHorizontalGlue());
		jt_classRoom=new JTextField(20);
		classRoom.add(jt_classRoom);
		classRoom.add(Box.createHorizontalGlue());
		JButton jb_classRoom = new JButton("Browse");
		jt_classRoom.setMaximumSize(new Dimension(110,30));
		jb_classRoom.setActionCommand("classRoom");
		jb_classRoom.addActionListener(this);
		classRoom.add(jb_classRoom);
		classRoom.add(Box.createHorizontalGlue());
		j.add(classRoom);



		// third field : the constrains
		JPanel constrain=new JPanel();
		constrain.setLayout(new BoxLayout(constrain,BoxLayout.X_AXIS));

		constrain.add(Box.createHorizontalGlue());
		JLabel conLabel=new JLabel("    Fichier contenant les contraintes");
		conLabel.setPreferredSize(GUI_Propreties.size_label_dialog);

		constrain.add(conLabel);
		constrain.add(Box.createHorizontalGlue());
		jt_constrain=new JTextField(20);
		constrain.add(jt_constrain);
		constrain.add(Box.createHorizontalGlue());
		JButton jb_constrain = new JButton("Browse");
		jt_constrain.setMaximumSize(new Dimension(110,30));
		jb_constrain.setActionCommand("constrain");
		jb_constrain.addActionListener(this);
		constrain.add(jb_constrain);
		constrain.add(Box.createHorizontalGlue());
		j.add(constrain);


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
