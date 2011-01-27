package gui_schedule;

import gui.GUI_properties;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;
import javax.swing.*;

/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class OptionPanelSolo extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private StateFullSchedule state;
	
	private JComboBox teacherCombo,roomCombo,sectionCombo;
	private MainViewSolo mvs;
	//private String comb[];
	private Teacher selectedTeacher;
	private Room selectedRoom;
	private Section selectedSection;
	private JLabel icon;
	private ImageIcon[] imagesTab;

	public OptionPanelSolo(){};
	public OptionPanelSolo(StateFullSchedule state,MainViewSolo mvs){
		this.state=state;
		this.mvs=mvs;
		init();
		

	}
	
	private void init(){
		
		this.removeAll();
		
		this.setBackground(GUI_properties.optionPanelSolo_color); 
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		// the image icon
		imagesTab=new ImageIcon[4];
		imagesTab[0]=new ImageIcon(getClass().getResource("/teacher_130.png"));
		imagesTab[1]=new ImageIcon(getClass().getResource("/table_130.png"));
		imagesTab[2]=new ImageIcon(getClass().getResource("/group2_130.png"));	
		imagesTab[3]=new ImageIcon(getClass().getResource("/blank_130.png"));	
		icon=new JLabel(imagesTab[3]);
		add(icon);
		this.add(Box.createVerticalGlue());

		// teacher panel
		JPanel teacherPanel=new JPanel();
		teacherCombo=new JComboBox(new String[]{" "});
		teacherCombo.setPreferredSize(GUI_properties.default_comboBox_size);

		teacherPanel.setBorder(BorderFactory.createTitledBorder("Professeur"));
		teacherPanel.add(teacherCombo);
		teacherPanel.setBackground(GUI_properties.teacher_color);
		teacherCombo.addActionListener(new ComboListener(0));
		this.add(teacherPanel);


		// room panel
		JPanel roomPanel=new JPanel();
		roomCombo=new JComboBox(new String[]{" "});
		roomCombo.setPreferredSize(GUI_properties.default_comboBox_size);

		roomPanel.setBorder(BorderFactory.createTitledBorder("Local"));
		roomPanel.add(roomCombo);
		roomPanel.setBackground(GUI_properties.room_color);
		roomCombo.addActionListener(new ComboListener(1));
		this.add(roomPanel);

		// section panel
		JPanel sectionPanel=new JPanel();
		sectionCombo=new JComboBox(new String[]{" "});
		sectionCombo.setPreferredSize(GUI_properties.default_comboBox_size);

		sectionPanel.setBorder(BorderFactory.createTitledBorder("Année, Section, Groupe"));
		sectionPanel.add(sectionCombo);
		sectionPanel.setBackground(GUI_properties.section_color);
		sectionCombo.addActionListener(new ComboListener(2));
		this.add(sectionPanel);
		this.add(Box.createVerticalGlue());
	}


	/**
	 * 
	 */
	public void update_from_state(){
		System.out.println("OptionPanelSolo: nombre de carton: "+state.cards_size());

		init();
		
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



	/**
	 * 
	 * @author Dubruille Xavier
	 * @author Delange Jonas
	 */
	private class ComboListener implements ActionListener{

		int option_type;

		public ComboListener(int option_type){
			this.option_type=option_type;
		}
		@Override
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
			String selectedItem = (String)cb.getSelectedItem();

			if(selectedItem.equals(" "))
				return;

			//let's set the main View Panel depending on the option_type
			switch (option_type){
			case 0: //teacher
				setBackground(GUI_properties.teacher_color);
				roomCombo.setSelectedItem(" ");
				sectionCombo.setSelectedItem(" ");
				icon.setIcon(imagesTab[0]);
				selectedRoom=null;
				selectedSection=null;
				selectedTeacher=state.findTeacher(selectedItem);
				mvs.setScheduleView(selectedTeacher);
				break;
			case 1: //room
				setBackground(GUI_properties.room_color);
				teacherCombo.setSelectedItem(" ");
				sectionCombo.setSelectedItem(" ");
				icon.setIcon(imagesTab[1]);
				selectedSection=null;
				selectedTeacher=null;
				selectedRoom=state.findRoom(selectedItem);
				mvs.setScheduleView(selectedRoom);
				break;
			case 2: //section
				setBackground(GUI_properties.section_color);
				roomCombo.setSelectedItem(" ");
				teacherCombo.setSelectedItem(" ");
				icon.setIcon(imagesTab[2]);
				selectedRoom=null;
				selectedTeacher=null;
				selectedSection=state.findSection(selectedItem);
				mvs.setScheduleView(selectedSection);
				break;
			}

		}
	}

	/**
	 * 
	 * @return
	 */
	public StateFullSchedule getState(){
		return state;
	}
	
	/**
	 * 
	 * @return
	 */
	public Room getSelectedRoom() {
		return selectedRoom;
	}

	/**
	 * 
	 * @return
	 */
	public Section getSelectedSection() {
		return selectedSection;
	}

	/**
	 * 
	 * @return
	 */
	public Teacher getSelectedTeacher(){
		return selectedTeacher;
	}
	public void clear() {
	

		
		this.removeAll();

		selectedTeacher=null;
		selectedRoom=null;
		selectedSection=null;
		
		init();
		
	}


}
