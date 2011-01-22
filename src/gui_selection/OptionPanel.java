package gui_selection;

import gui.GUI_properties;

import java.util.SortedMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import model.Section;
import model.StateFullSchedule;
import model.Teacher;

import org.japura.gui.CheckComboBox;
import org.japura.gui.event.ListCheckListener;
import org.japura.gui.event.ListEvent;
import org.japura.gui.model.ListCheckModel;

public class OptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StateFullSchedule state;
	private DisplayPanel dp;
	private JPanel choices_one_panel;
//	private SortedMap<Integer,Card_GUI> gui_cards;
	private boolean started;

	public CheckComboBox choices_one_combo,choices_two_combo,choices_three_combo,choices_four_combo;


	//DefaultComboBoxModel def;

	public OptionPanel(DisplayPanel dp, StateFullSchedule state, SortedMap<Integer,Card_GUI> gui_cards){
		this.dp=dp;
		this.state=state;
//		this.gui_cards=gui_cards;
		ListCheckModel model;
		started=false;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		//this.setPreferredSize(new Dimension(60,60));


		//Option_general=new String[]{"place","non place","place posant probleme","tous"};


		//general option
		choices_one_panel=new JPanel();
		choices_one_panel.setBorder(BorderFactory.createTitledBorder("Options G始erales"));

		choices_one_combo = new CheckComboBox();
		choices_one_combo.setPreferredSize(GUI_properties.default_comboBox_size);
		choices_one_combo.setMultipleItemsText("* plusieurs s四ections *");

		model = choices_one_combo.getModel();
		model.addElement("tous");
		model.addElement("place");
		model.addElement("non place");
		model.addElement("posant probl塾e");
		model.addListCheckListener(new MyListCheckListener(0));
		model.setCheck("tous");
		choices_one_panel.add(choices_one_combo);


		//teacher option
		JPanel choices_two_panel=new JPanel();
		choices_two_panel.setBorder(BorderFactory.createTitledBorder("Professeur"));

		choices_two_combo = new CheckComboBox();
		choices_two_combo.setPreferredSize(GUI_properties.default_comboBox_size);
		choices_two_combo.setMultipleItemsText("* plusieurs s四ections *");

		model = choices_two_combo.getModel();
		model.addElement("tous");
		//model.setCheck("tous");
		model.addListCheckListener(new MyListCheckListener(1));

		choices_two_panel.add(choices_two_combo);


		//class room option
		JPanel choices_three_panel=new JPanel();
		choices_three_panel.setBorder(BorderFactory.createTitledBorder("Local"));

		choices_three_combo = new CheckComboBox();
		choices_three_combo.setPreferredSize(GUI_properties.default_comboBox_size);
		choices_three_combo.setMultipleItemsText("* plusieurs s四ections *");

		model = choices_three_combo.getModel();
		model.addElement("tous");
		model.addElement("Classe");
		model.addElement("Groupe");
		model.addElement("Informatique");
		model.addElement("autre");
		model.setCheck("tous");
		model.addListCheckListener(new MyListCheckListener(2));

		choices_three_panel.add(choices_three_combo);

		//section option
		JPanel choices_four_panel=new JPanel();
		choices_four_panel=new JPanel();
		choices_four_panel.setBorder(BorderFactory.createTitledBorder("Ann仔, Section, Groupe"));

		choices_four_combo = new CheckComboBox();
		choices_four_combo.setPreferredSize(GUI_properties.default_comboBox_size);
		choices_four_combo.setMultipleItemsText("* plusieurs s四ections *");

		model = choices_four_combo.getModel();
		model.addElement("tous");
		model.addListCheckListener(new MyListCheckListener(3));
		model.setCheck("tous");
		choices_four_panel.add(choices_four_combo);

		// do we add more options?


		this.add(choices_one_panel);
		this.add(choices_two_panel);
		this.add(choices_three_panel);
		this.add(choices_four_panel);

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
		//choices_two_combo.removeAllItems();
		model=choices_two_combo.getModel();
		for(Teacher t:state.getTeachers().values())
			model.addElement(t.getFirstName()+" "+t.getLastName());
		model.checkAll();
		choices_two_combo.repaint();

		//section option update
		model=choices_four_combo.getModel();
		for(Section s:state.getSections().values())
			model.addElement(s.getName());
		model.checkAll();
		choices_four_combo.repaint();

		//classroom option update:
		/*
		model=choices_three_combo.getModel();
		for(Room r:state.getClassRoom().values())
			model.addElement(r.getName());
		choices_three_combo.repaint();
		 */

		this.repaint();


		// System.out.println("update optionPanel..");
		started=true;
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
		@Override
		public void addCheck(ListEvent e){
			
			if(e.getValues().size()>1) return;
			String value=(String)e.getValues().get(0);
			
			//System.out.println(e.getValues());
			java.util.List<Object> l;
			switch(category){
			case 1: //teachers
				if(value.equalsIgnoreCase("tous")){
					e.getSource().checkAll();
					dp.allTeachersChoosed(false);
				}
				else
					dp.showTeacherCards(value);
				break;
			case 0: //general option
				l=choices_one_combo.getModel().getCheckeds();
				for(int i=0;i<l.size();i++){
					String s=(String)l.get(i);
					if(!s.equals(value))
						e.getSource().removeCheck(s);
				}

				dp.updateByGeneralItem(value);
				break;
			case 2: //rooms
				l=choices_three_combo.getModel().getCheckeds();
				for(int i=0;i<l.size();i++){
					String s=(String)l.get(i);
					if(!s.equals(value))
						e.getSource().removeCheck(s);
				}

				dp.updateByRoomItem(value);
				break;
			case 3: //sections
				if(started && value.equalsIgnoreCase("tous")){
					choices_four_combo.getModel().checkAll();
					dp.allSectionsChoosed(false);
				}
				else
					dp.showSectionCards(value);
				break;
			}
		}
		@Override
		public void removeCheck(ListEvent e){
			String value;
			if (e == null || e.getValues()==null)
				return;
			value=(String)e.getValues().get(0);
			switch(category){
			case 1:	//teachers
				if(value.equalsIgnoreCase("tous")){
					choices_two_combo.getModel().removeChecks();
					dp.allTeachersChoosed(true);
				}
				else
					dp.hideTeacherCards(value);
				break;
			case 0: //general option
				//choices_one_combo.getModel().removeChecks();
				dp.updateByGeneralItem("tous");
				break;
			case 2: //rooms
				dp.updateByRoomItem("tous");
				break;
			case 3:	//sections
				if(value.equalsIgnoreCase("tous")){
					choices_four_combo.getModel().removeChecks();
					dp.allSectionsChoosed(true);
				}
				else
					dp.hideSectionCards(value);
				break;
			}
		}

		/*
		private void showRoom(String room){}
		private void hideRoom(String room){}
		*/
	}

	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
