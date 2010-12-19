package gui_schedule;

import gui.GUI_Propreties;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;

import org.japura.gui.CheckComboBox;
import org.japura.gui.event.ListCheckListener;
import org.japura.gui.event.ListEvent;
import org.japura.gui.model.ListCheckModel;


public class OptionPanelCompare extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private StateFullSchedule state;
	private ArrayList<String> selectedDays;
	private String compareOn;
	private ArrayList<Teacher> teachersToCompare;
	private ArrayList<Room> roomsToCompare;
	private ArrayList<Section> sectionsToCompare;
	private CheckComboBox daysCombo,compareOn_Combo,stuffCombo;
	private MainViewCompare mvc;

	public OptionPanelCompare(StateFullSchedule state,MainViewCompare mvc){
		this.state=state;
		this.mvc=mvc;
		ListCheckModel model;
		selectedDays=new ArrayList<String>();
		teachersToCompare = new ArrayList<Teacher>();
		roomsToCompare=new ArrayList<Room>();
		sectionsToCompare=new ArrayList<Section>();
		
		this.setPreferredSize(new Dimension(130,130));
		this.setBackground(GUI_Propreties.optionPanelCompare_color);


		// days panel
		JPanel daysPanel=new JPanel();
		daysCombo=new CheckComboBox();
		daysCombo.setPreferredSize(GUI_Propreties.default_comboBox_size);

		daysPanel.setBorder(BorderFactory.createTitledBorder("jours"));
		daysPanel.add(daysCombo);
		daysPanel.setBackground(GUI_Propreties.optionPanelCompare_color);

		daysCombo.setMultipleItemsText("* plusieurs selections *");

		model = daysCombo.getModel();
		model.addElement("Lundi");
		model.addElement("Mardi");
		model.addElement("Mercredi");
		model.addElement("Jeudi");
		model.addElement("Vendredi");
		model.addElement("Samdi");
		model.addListCheckListener(new MyListCheckListener(0));

		this.add(daysPanel);


		// main option/option_to compare on panel
		JPanel compareOn_Panel=new JPanel();
		compareOn_Combo=new CheckComboBox();
		compareOn_Combo.setPreferredSize(GUI_Propreties.default_comboBox_size);

		compareOn_Panel.setBorder(BorderFactory.createTitledBorder("Option a comparer"));
		compareOn_Panel.add(compareOn_Combo);
		compareOn_Panel.setBackground(GUI_Propreties.optionPanelCompare_color);

		compareOn_Combo.setMultipleItemsText("* plusieurs selections *");

		model = compareOn_Combo.getModel();
		model.addElement(GUI_Propreties.option_teachers);
		model.addElement(GUI_Propreties.option_rooms);
		model.addElement(GUI_Propreties.option_sections);
		model.addListCheckListener(new MyListCheckListener(1));

		this.add(compareOn_Panel);

		// other stuff panel
		JPanel stuffPanel=new JPanel();
		stuffCombo=new CheckComboBox();
		stuffCombo.setPreferredSize(GUI_Propreties.default_comboBox_size);

		stuffPanel.setBorder(BorderFactory.createTitledBorder("selection"));
		stuffPanel.add(stuffCombo);
		stuffPanel.setBackground(GUI_Propreties.optionPanelCompare_color);
		stuffCombo.setMultipleItemsText("* plusieurs selections *");

		model = stuffCombo.getModel();
		model.addListCheckListener(new MyListCheckListener(2));

		this.add(stuffPanel);



	}

	public void update_from_state(){

	}

	private class MyListCheckListener implements ListCheckListener{
		int category;
		java.util.List<Object> l;
		public MyListCheckListener(int category){
			this.category=category;
		}
		public void addCheck(ListEvent e){
			//System.out.println("addCheck: catego="+category+", selected="+e.getValues().get(0));

			String value=(String)e.getValues().get(0);
			switch(category){
			case 0: //days
				selectedDays.clear();
				selectedDays.add(value);
				
				l=daysCombo.getModel().getCheckeds();
				for(int i=0;i<l.size();i++){
					String s=(String)l.get(i);
					if(!s.equals(value))
						e.getSource().removeCheck(s);
				}
				mvc.constructView();

 
				break;
			case 1: //compare on
				l=compareOn_Combo.getModel().getCheckeds();
				for(int i=0;i<l.size();i++){
					String s=(String)l.get(i);
					if(!s.equals(value))
						e.getSource().removeCheck(s);
				}
				compareOn=value;
				// then we fill the CheckComboBox "otherStuffToCompareBetween"
				ListCheckModel model=stuffCombo.getModel();
				model.clear();
				teachersToCompare.clear();
				roomsToCompare.clear();
				sectionsToCompare.clear();

				if(value.equals(GUI_Propreties.option_teachers))
					for(Teacher t:state.getTeachers().values())
						model.addElement(t.getFirstName()+" "+t.getLastName());
				else if(value.equals(GUI_Propreties.option_rooms))
					for(Room r:state.getClassRoom().values())
						model.addElement(r.getName());
				else if(value.equals(GUI_Propreties.option_sections))
					for(Section s:state.getSections().values())
						model.addElement(s.getName()); 
				break;
			case 2: //compare between these stuff
				//we update de mainView
				if(compareOn.equals(GUI_Propreties.option_teachers)){
					teachersToCompare.add(state.findTeacher(value));
				}
				else if(compareOn.equals(GUI_Propreties.option_rooms)){
					roomsToCompare.add(state.findRoom(value));
				}
				else if(compareOn.equals(GUI_Propreties.option_sections)){
					sectionsToCompare.add(state.findSection(value));
				}
				mvc.constructView();
				break;
			}

		}


		public void removeCheck(ListEvent e){
			//System.out.println("removeCheck: catego="+category+", selected="+e.getValues().get(0));

			String value=(String)e.getValues().get(0);
			switch(category){
			case 0: //days
				//selectedDays.remove(value);
				//constructMainView();
				break;
			case 1: //compare on
				//e.getSource().addCheck(value);
				break;
			case 2: //compare between these stuff
				if(compareOn.equals(GUI_Propreties.option_teachers)){
					teachersToCompare.remove(state.findTeacher(value));
				}
				else if(compareOn.equals(GUI_Propreties.option_rooms)){
					roomsToCompare.remove(state.findRoom(value));
				}
				else if(compareOn.equals(GUI_Propreties.option_sections)){
					sectionsToCompare.remove(state.findSection(value));
				}
				mvc.constructView();
				break;
			}
		}
		

		
		
	}

	public ArrayList<String> getSelectedDays() {
		return selectedDays;
	}

	public String getCompareOn() {
		return compareOn;
	}

	public ArrayList<Teacher> getTeachersToCompare() {
		return teachersToCompare;
	}

	public ArrayList<Room> getRoomsToCompare() {
		return roomsToCompare;
	}

	public ArrayList<Section> getSectionsToCompare() {
		return sectionsToCompare;
	}

	public CheckComboBox getDaysCombo() {
		return daysCombo;
	}
	
	
}
