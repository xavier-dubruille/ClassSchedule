package gui_schedule;

import gui.GUI_properties;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Room;
import model.Section;
import model.StateFullSchedule;
import model.Teacher;

import org.japura.gui.CheckComboBox;
import org.japura.gui.event.ListCheckListener;
import org.japura.gui.event.ListEvent;
import org.japura.gui.model.ListCheckModel;


/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
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
	private ImageIcon[] imagesTab;
	private JLabel icon;

	/**
	 * 
	 * @param state
	 * @param mvc
	 */
	public OptionPanelCompare(StateFullSchedule state,MainViewCompare mvc){
		this.state=state;
		this.mvc=mvc;
		ListCheckModel model;
		selectedDays=new ArrayList<String>();
		teachersToCompare = new ArrayList<Teacher>();
		roomsToCompare=new ArrayList<Room>();
		sectionsToCompare=new ArrayList<Section>();

		this.setPreferredSize(new Dimension(130,130));
		this.setBackground(GUI_properties.optionPanelCompare_color);
		
		imagesTab=new ImageIcon[4];
		imagesTab[0]=new ImageIcon("image/teacher_130_C.png");//getClass().getResource("/teacher_130.png"));
		imagesTab[1]=new ImageIcon("image/table_130_C.png");//getClass().getResource("/table_130.png"));
		imagesTab[2]=new ImageIcon("image/group2_130_C.png");//getClass().getResource("/group2_130.png"));	
		imagesTab[3]=new ImageIcon("image/blank_130_C.png");//getClass().getResource("/blank_130.png"));	
		icon=new JLabel(imagesTab[3]);
		add(icon);


		// days panel
		JPanel daysPanel=new JPanel();
		daysCombo=new CheckComboBox();
		daysCombo.setPreferredSize(GUI_properties.default_comboBox_size);

		daysPanel.setBorder(BorderFactory.createTitledBorder("jours"));
		daysPanel.add(daysCombo);
		daysPanel.setBackground(GUI_properties.optionPanelCompare_color);

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
		compareOn_Combo.setPreferredSize(GUI_properties.default_comboBox_size);

		compareOn_Panel.setBorder(BorderFactory.createTitledBorder("Option a comparer"));
		compareOn_Panel.add(compareOn_Combo);
		compareOn_Panel.setBackground(GUI_properties.optionPanelCompare_color);

		compareOn_Combo.setMultipleItemsText("* plusieurs selections *");

		model = compareOn_Combo.getModel();
		model.addElement(GUI_properties.option_teachers);
		model.addElement(GUI_properties.option_rooms);
		model.addElement(GUI_properties.option_sections);
		model.addListCheckListener(new MyListCheckListener(1));

		this.add(compareOn_Panel);

		// other stuff panel
		JPanel stuffPanel=new JPanel();
		stuffCombo=new CheckComboBox();
		stuffCombo.setPreferredSize(GUI_properties.default_comboBox_size);

		stuffPanel.setBorder(BorderFactory.createTitledBorder("Annee, selection, groupe"));
		stuffPanel.add(stuffCombo);
		stuffPanel.setBackground(GUI_properties.optionPanelCompare_color);
		stuffCombo.setMultipleItemsText("* plusieurs selections *");

		model = stuffCombo.getModel();
		model.addListCheckListener(new MyListCheckListener(2));

		this.add(stuffPanel);
	}

	/**
	 * not implemented yet
	 */
	public void update_from_state(){

	}

	/**
	 * 
	 * @author Dubruille Xavier
	 * @author Delange Jonas
	 *
	 */
	private class MyListCheckListener implements ListCheckListener{
		int category;
		java.util.List<Object> l;
		public MyListCheckListener(int category){
			this.category=category;
		}
		@Override
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

				if(value.equals(GUI_properties.option_teachers)){
					icon.setIcon(imagesTab[0]);
					for(Teacher t:state.getTeachers().values())
						model.addElement(t.getFirstName()+" "+t.getLastName());
				}
				else if(value.equals(GUI_properties.option_rooms)){
					icon.setIcon(imagesTab[1]);
					for(Room r:state.getClassRoom().values())
						model.addElement(r.getName());
				}
				else if(value.equals(GUI_properties.option_sections)){
					icon.setIcon(imagesTab[2]);
					for(Section s:state.getSections().values())
						model.addElement(s.getName()); 
				}
				break;
			case 2: //compare between these stuff
				//we update de mainView
				if(compareOn.equals(GUI_properties.option_teachers)){
					teachersToCompare.add(state.findTeacher(value));
				}
				else if(compareOn.equals(GUI_properties.option_rooms)){
					roomsToCompare.add(state.findRoom(value));
				}
				else if(compareOn.equals(GUI_properties.option_sections)){
					sectionsToCompare.add(state.findSection(value));
				}
				mvc.constructView();
				break;
			}

		}


		@Override
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
				if(compareOn.equals(GUI_properties.option_teachers)){
					teachersToCompare.remove(state.findTeacher(value));
				}
				else if(compareOn.equals(GUI_properties.option_rooms)){
					roomsToCompare.remove(state.findRoom(value));
				}
				else if(compareOn.equals(GUI_properties.option_sections)){
					sectionsToCompare.remove(state.findSection(value));
				}
				mvc.constructView();
				break;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getSelectedDays() {
		return selectedDays;
	}

	/**
	 * 
	 * @return
	 */
	public String getCompareOn() {
		return compareOn;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Teacher> getTeachersToCompare() {
		return teachersToCompare;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Room> getRoomsToCompare() {
		return roomsToCompare;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Section> getSectionsToCompare() {
		return sectionsToCompare;
	}

	/**
	 * 
	 * @return
	 */
	public CheckComboBox getDaysCombo() {
		return daysCombo;
	}


}
