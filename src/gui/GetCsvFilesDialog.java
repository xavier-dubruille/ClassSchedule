package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GetCsvFilesDialog extends JDialog implements ActionListener{

	JTextField jt_constrain, jt_lesson, jt_classRoom;
	String output[];
	public GetCsvFilesDialog(String[] output){

		this.output=output;
		//setPreferredSize(new Dimension(900,800));
		setMinimumSize(new Dimension(550,190));
		JPanel j=new JPanel();
		j.setLayout(new BoxLayout(j,BoxLayout.Y_AXIS));


		j.add(Box.createGlue());

		//First field: the lessons
		JPanel lesson=new JPanel();
		lesson.setLayout(new BoxLayout(lesson,BoxLayout.X_AXIS));

		lesson.add(Box.createHorizontalGlue());
		lesson.add(new JLabel("Fichier contenant les cours"));

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

		JButton create=new JButton("créer");
		create.setActionCommand("create");
		create.addActionListener(this);
		j.add(create);
		getContentPane().add(j);
		setModal(true);
		setVisible(true);
	}



	public void actionPerformed(ActionEvent e){
		JFileChooser jf=new JFileChooser();
		if (e.getActionCommand().equals("lesson")){

			jf.showOpenDialog(this);
			jt_lesson.setText(jf.getSelectedFile().getName());
		}
		if (e.getActionCommand().equals("constrain")){

			jf.showOpenDialog(this);
			jt_constrain.setText(jf.getSelectedFile().getName());
		}
		if (e.getActionCommand().equals("classRoom")){

			jf.showOpenDialog(this);
			jt_classRoom.setText(jf.getSelectedFile().getName());
		}
		if (e.getActionCommand().equals("create")){

			output[0]=jt_lesson.getText();
			output[1]=jt_constrain.getText();
			output[2]=jt_classRoom.getText();
			
			this.setVisible(false);
		}
	}
}
