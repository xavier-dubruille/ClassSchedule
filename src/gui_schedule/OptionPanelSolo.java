package gui_schedule;

import gui_selection.Card_GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.StateFullSchedule;
import model.Teacher;

public class OptionPanelSolo extends JPanel  implements ActionListener{
	private StateFullSchedule state;
	private JComboBox teacherCombo;
	private MainViewSolo mvs;
	private String comb[];
	private Teacher selectedTeacher;
	
	public OptionPanelSolo(StateFullSchedule state,MainViewSolo mvs){
		this.state=state;
		this.mvs=mvs;
		this.setBackground(Color.red); //test

		this.setPreferredSize(new Dimension(140,140));


		teacherCombo=new JComboBox();
		teacherCombo.setPreferredSize(new Dimension(160,27));
		teacherCombo.addActionListener(this);
		this.add(teacherCombo);
	}
	
	public Teacher getSelectedTeacher(){
		return selectedTeacher;
	}
	public void actionPerformed(ActionEvent e){
		JComboBox cb = (JComboBox)e.getSource();
		String SelectedItem = (String)cb.getSelectedItem();
		//System.out.println(SelectedItem);

		//let's set the main View Panel..
		selectedTeacher=findTeacher(SelectedItem);
		mvs.setScheduleView(selectedTeacher);
	}
	
	/*
	 * return the Teacher corresponding to the selectedItem
	 */
	private Teacher findTeacher(String selectedItem){
		
		// not very optimized yet.. but it won't make a big difference anyway.
		
		
		for (Teacher t: state.getTeachers().values())
			if (selectedItem.equalsIgnoreCase(t.getFirstName()+" "+t.getLastName()))
				return t;
		
		// if we're here, well, we haven't found it, and it's wrong..
		System.err.println("Etrange Etrange.. problem in OptionPanelSolo.findTeacher(..)");
		System.exit(-3);
		return new Teacher(); //stupid, but left for compiling reasons..
	}


	public void update_from_state(){
		if(!state.isReady()){
			System.err.println("Something wrong happened in optionPanelSolo.update_from_state(), call a developper");
			System.exit(-2);
		}

		for(Teacher t:state.getTeachers().values()){
			teacherCombo.addItem(t.getFirstName()+" "+t.getLastName());
		}
		teacherCombo.repaint();

		this.repaint();		
	}

	
}
