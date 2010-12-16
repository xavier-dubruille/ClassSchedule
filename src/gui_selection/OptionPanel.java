package gui_selection;

import model.*;
import gui.GUI_Propreties;

import java.awt.*;
import java.awt.event.*;
import java.util.SortedMap;

import javax.swing.*;
import javax.swing.border.*;
import org.japura.gui.*;
import org.japura.*;
import org.japura.gui.model.*;
import org.japura.gui.renderer.*;
import org.japura.gui.event.*;
import org.japura.controller.*;

public class OptionPanel extends JPanel {

	StateFullSchedule state;
	DisplayPanel dp;
	String[] Option_general;
	String[] Option_teacher;
	String[] Option_classRoom;
	JPanel choises_one_panel;
	SortedMap<Integer,Card_GUI> gui_cards;
	
	public CheckComboBox choises_one_Combo,choises_two_Combo,choises_three_Combo;

	
	//DefaultComboBoxModel def;
	
	public OptionPanel(DisplayPanel dp, StateFullSchedule state, SortedMap<Integer,Card_GUI> gui_cards){
		this.dp=dp;
		this.state=state;
		this.gui_cards=gui_cards;
		 ListCheckModel model;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setPreferredSize(new Dimension(60,60));
		

		//Option_general=new String[]{"place","non place","place posant probleme","tous"};

		
		//general option
		choises_one_panel=new JPanel();
		choises_one_panel.setBorder(BorderFactory.createTitledBorder("Options generales"));

		choises_one_Combo = new CheckComboBox();
		choises_one_Combo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		choises_one_Combo.setMultipleItemsText("* plusieurs selections *");

		model = choises_one_Combo.getModel();
		model.addElement("tous");
		model.addElement("place");
		model.addElement("non place");
		model.addElement("posant probleme");
		model.addListCheckListener(new MyListCheckListener(0));
		choises_one_panel.add(choises_one_Combo);
		  

		//teacher option
		JPanel choises_two_panel=new JPanel();
		choises_two_panel.setBorder(BorderFactory.createTitledBorder("Professeur"));
		
		choises_two_Combo = new CheckComboBox();
		choises_two_Combo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		choises_two_Combo.setMultipleItemsText("* plusieurs selections *");
		
		model = choises_two_Combo.getModel();
		model.addElement("tous");
		model.addListCheckListener(new MyListCheckListener(1));
	
		choises_two_panel.add(choises_two_Combo);


		//class room option
		JPanel choises_three_panel=new JPanel();
		choises_three_panel.setBorder(BorderFactory.createTitledBorder("Local"));
		
		choises_three_Combo = new CheckComboBox();
		choises_three_Combo.setPreferredSize(GUI_Propreties.default_comboBox_size);
		choises_three_Combo.setMultipleItemsText("* plusieurs selections *");
	
		model = choises_three_Combo.getModel();
		model.addElement("tous");
		model.addListCheckListener(new MyListCheckListener(2));
		
		choises_three_panel.add(choises_three_Combo);


		// on rajoute d'autre options ?
		/*
		JPanel choises_four_panel=new JPanel();
		choises_four_panel.setLayout(new BoxLayout(choises_four_panel, BoxLayout.Y_AXIS));
		choises_four_panel.setSize(40, 40); //va faloir chipoter ici..
		JComboBox choises_four_Combo = new JComboBox(chooses[3]);
		choises_four_panel.add(new JLabel("autres options"));
		choises_four_panel.add(choises_four_Combo);
		*/
		
		
		this.add(choises_one_panel);
		this.add(choises_two_panel);
		this.add(choises_three_panel);
		
		this.setVisible(true);
	}
	
	/*
	 * the files(with all the options) are not know at creation, so we deal with it now
	 */
	public void update_from_state(){
		ListCheckModel model;
		
		if(!state.isReady()){
			System.err.println("Something wrong happened in optionPanel.update_from_state(), call a developper");
			System.exit(-2);
		}



		//teacher option update:
		//choises_two_Combo.removeAllItems();
		model=choises_two_Combo.getModel();
		for(Teacher t:state.getTeachers().values())
			model.addElement(t.getFirstName()+" "+t.getLastName());
		choises_two_Combo.repaint();
		

		//classroom option update:
		model=choises_three_Combo.getModel();
		for(Room r:state.getClassRoom().values())
			model.addElement(r.getName());
		choises_three_Combo.repaint();
		
		this.repaint();
		
		
		// System.out.println("update optionPanel..");
	}
	
	private class MyListCheckListener implements ListCheckListener{


		int category; 
		/*
		 * 0 --> general option
		 * 1 --> teacher
		 * 2 --> classroom
		 */
		MyListCheckListener(int category){
			this.category=category;
		}
		public void addCheck(ListEvent e){
			String value=(String)e.getValues().get(0);
			switch(category){
			case 1: //teachers
				dp.showTeacherCards(value);
				break;
			case 0: //general option
				java.util.List<Object> l=choises_one_Combo.getModel().getCheckeds();
				for(int i=0;i<l.size();i++){
					String s=(String)l.get(i);
					if(!s.equals(value))
						e.getSource().removeCheck(s);
				}
					
				dp.updateByGeneralItem(value);
				break;
			case 2: //rooms
				showRoom(value);
				//dp.showRoomCards((String)e.getValues().get(0));
				break;
			}
		}
		public void removeCheck(ListEvent e){
			switch(category){
			case 1:	//teachers
				dp.hideTeacherCards((String)e.getValues().get(0));
				break;
			case 0: //general option
				//choises_one_Combo.getModel().removeChecks();
				dp.updateByGeneralItem("tous");
				break;
			case 2: //rooms
				hideRoom((String)e.getValues().get(0));
				//dp.hideRoomCards((String)e.getValues().get(0));
				break;
			}
		}

		private void showRoom(String room){}
		private void hideRoom(String room){}
		
	}
}
