package gui_schedule;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GetCsvFilesDialog extends JDialog implements ActionListener{

	JTextField jt_constrain, jt_lesson, jt_classRoom;
	String output[];
	public GetCsvFilesDialog(String[] output){

		this.output=output;
		//setPreferredSize(new Dimension(900,800));
		setMinimumSize(new Dimension(550,190));
		setSize(new Dimension(550,190));
		
		JPanel j=new JPanel();
		j.setLayout(new BoxLayout(j,BoxLayout.Y_AXIS));


		j.add(Box.createGlue());

		//First field: the lessons
		JPanel lesson=new JPanel();
		lesson.setLayout(new BoxLayout(lesson,BoxLayout.X_AXIS));

		lesson.add(Box.createHorizontalGlue());
		JLabel jl=new JLabel("Fichier contenant les cours");
		//jl.setPreferredSize(12)
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
		classRoom.add(new JLabel("Fichier contenant les locaux"));
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
		constrain.add(new JLabel("Fichier contenant les contraites"));
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
		setModal(true);
		setVisible(true);
	}



	public void actionPerformed(ActionEvent e){
		JFileChooser jf=new JFileChooser();
		int returnVal;
		
		javax.swing.filechooser.FileFilter filter = new javax.swing.filechooser.FileFilter(){
			public boolean accept(File f){
				return f.getName().endsWith(".csv")||f.isDirectory()||f.getName().endsWith(".xls");}
			public String getDescription(){return "fichiers csv et xls";}
		};
		
		

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

				this.setVisible(false);
			}
		}

}
