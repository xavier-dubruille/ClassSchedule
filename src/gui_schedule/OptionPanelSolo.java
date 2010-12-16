package gui_schedule;

import gui.GUI_Propreties;
import gui_selection.Card_GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import model.*;

public class OptionPanelSolo extends JPanel{
	private StateFullSchedule state;
	private JComboBox teacherCombo,roomCombo,sectionCombo;
	private MainViewSolo mvs;
	private String comb[];
	private Teacher selectedTeacher;
	private Room selectedRoom;
	private Section selectedSection;

	public OptionPanelSolo(){};
	public OptionPanelSolo(StateFullSchedule state,MainViewSolo mvs){
		this.state=state;
		this.mvs=mvs;
		this.setBackground(GUI_Propreties.optionPanelSolo_color); 

		
		// teacher panel
		JPanel teacherPanel=new JPanel();
		teacherCombo=new JComboBox(new String[]{" "});
		teacherCombo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		
		teacherPanel.setBorder(BorderFactory.createTitledBorder("Professeur"));
		teacherPanel.add(teacherCombo);
		teacherPanel.setBackground(GUI_Propreties.teacher_color);
		teacherCombo.addActionListener(new ComboListener(0));
		this.add(teacherPanel);

		
		// room panel
		JPanel roomPanel=new JPanel();
		roomCombo=new JComboBox(new String[]{" "});
		roomCombo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		
		roomPanel.setBorder(BorderFactory.createTitledBorder("Local"));
		roomPanel.add(roomCombo);
		roomPanel.setBackground(GUI_Propreties.room_color);
		roomCombo.addActionListener(new ComboListener(1));
		this.add(roomPanel);

		// section panel
		JPanel sectionPanel=new JPanel();
		sectionCombo=new JComboBox(new String[]{" "});
		sectionCombo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		
		sectionPanel.setBorder(BorderFactory.createTitledBorder("Section"));
		sectionPanel.add(sectionCombo);
		sectionPanel.setBackground(GUI_Propreties.section_color);
		sectionCombo.addActionListener(new ComboListener(2));
		this.add(sectionPanel);
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

		for(Room r:state.getClassRoom().values()){
			roomCombo.addItem(r.getName());
		}
		roomCombo.repaint();

		for(Section s:state.getSections().values()){
			sectionCombo.addItem(s.getName());
		}
		sectionCombo.repaint();

		this.repaint();		
		
	}
	
	

	private class ComboListener implements ActionListener{

		int option_type;

		public ComboListener(int option_type){
			this.option_type=option_type;
		}
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			String selectedItem = (String)cb.getSelectedItem();
			
			if(selectedItem.equals(" "))
				return;

			//let's set the main View Panel depending on the option_type
			switch (option_type){
			case 0: //teacher
				setBackground(GUI_Propreties.teacher_color);
				roomCombo.setSelectedItem(" ");
				sectionCombo.setSelectedItem(" ");
				selectedTeacher=state.findTeacher(selectedItem);
				mvs.setScheduleView(selectedTeacher);
				break;
			case 1: //room
				setBackground(GUI_Propreties.room_color);
				teacherCombo.setSelectedItem(" ");
				sectionCombo.setSelectedItem(" ");
				selectedRoom=state.findRoom(selectedItem);
				mvs.setScheduleView(selectedRoom);
				break;
			case 2: //section
				setBackground(GUI_Propreties.section_color);
				roomCombo.setSelectedItem(" ");
				teacherCombo.setSelectedItem(" ");
				selectedSection=state.findSection(selectedItem);
				mvs.setScheduleView(selectedSection);
				break;
			}

		}

		

	}

	public StateFullSchedule getState(){
		return state;
	}
	public Room getSelectedRoom() {
		return selectedRoom;
	}

	public Section getSelectedSection() {
		return selectedSection;
	}

	public Teacher getSelectedTeacher(){
		return selectedTeacher;
	}


}
