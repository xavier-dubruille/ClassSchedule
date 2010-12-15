package gui_selection;

import model.*;
import java.awt.*;
import java.awt.event.*;
import java.util.SortedMap;

import javax.swing.*;

public class OptionPanel extends JPanel {

	StateFullSchedule state;
	DisplayPanel dp;
	String[] Option_general;
	String[] Option_teacher;
	String[] Option_classRoom;
	JPanel choises_one_panel;
	SortedMap<Integer,Card_GUI> gui_cards;
	
	JComboBox choises_one_Combo,choises_two_Combo,choises_three_Combo;
	//DefaultComboBoxModel def;
	
	public OptionPanel(DisplayPanel dp, StateFullSchedule state, SortedMap<Integer,Card_GUI> gui_cards){
		this.dp=dp;
		this.state=state;
		this.gui_cards=gui_cards;

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(60,60));
		

		Option_general=new String[]{"place","non place","place posant probleme","tous"};
		Option_teacher=new String[]{"tous"};
		Option_classRoom=new String[]{"tous"};
		
		//general option
		choises_one_panel=new JPanel();
		choises_one_panel.setLayout(new BoxLayout(choises_one_panel, BoxLayout.X_AXIS));
		choises_one_panel.setPreferredSize(new Dimension(33,33));
		choises_one_panel.setSize(40, 40); //va faloir chipoter ici..
		choises_one_Combo = new JComboBox(Option_general);
		choises_one_Combo.addActionListener(new MyItemListener(0));
		choises_one_panel.add(new JLabel("Options general"));
		choises_one_panel.add(choises_one_Combo);
		

		//teacher option
		JPanel choises_two_panel=new JPanel();
		choises_two_panel.setLayout(new BoxLayout(choises_two_panel, BoxLayout.X_AXIS));
		choises_two_panel.setPreferredSize(new Dimension(33,33));
		choises_two_panel.setSize(40, 40); //va faloir chipoter ici..
		choises_two_Combo = new JComboBox(Option_teacher);
		choises_two_Combo.addActionListener(new MyItemListener(1));
		choises_two_panel.add(new JLabel("professeur"));
		choises_two_panel.add(choises_two_Combo);


		//class room option
		JPanel choises_three_panel=new JPanel();
		choises_three_panel.setLayout(new BoxLayout(choises_three_panel, BoxLayout.X_AXIS));
		choises_three_panel.setPreferredSize(new Dimension(33,33));
		choises_three_panel.setSize(40, 40); //va faloir chipoter ici..
		choises_three_Combo = new JComboBox(Option_classRoom);
		choises_three_Combo.addActionListener(new MyItemListener(2));
		choises_three_panel.add(new JLabel("local"));
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
		// dans le listeneur on fera un dp.update(options);
		
	}
	
	/*
	 * the files(with all the options) are not know at creation, so we deal with it now
	 */
	public void update_from_state(){
		
		if(!state.isReady()){
			System.err.println("Something wrong happened in optionPanel.update_from_state(), call a developper");
			System.exit(-2);
		}
		
		
		//def.addElement("def moddel");
		
		//teacher option update:
		//choises_two_Combo.removeAllItems();
		for(Teacher t:state.getTeachers().values())
			choises_two_Combo.addItem(t.getFirstName()+" "+t.getLastName());
		choises_two_Combo.repaint();
		

		//classroom option update:
		/*
		for(Room r:state.getClassRoom().values())
			choises_three_Combo.addItem(r.getName());
		choises_three_Combo.repaint();
		*/
		this.repaint();
		
		// System.out.println("update optionPanel..");
	}
	
	private class MyItemListener implements ActionListener{


		int category; 
		/*
		 * 0 --> general option
		 * 1 --> teacher
		 * 2 --> classroom
		 */
		MyItemListener(int category){
			this.category=category;
		}
		public void actionPerformed(ActionEvent e){
			JComboBox cb = (JComboBox)e.getSource();
	        String selectedItem = (String)cb.getSelectedItem();
	        //System.out.println(item);
	        
	        // foundAndSetvisible(category,SelectedItem,true);
			if (category==1)
				dp.updateByTeacherItem(selectedItem);
			else if(category==0)
				dp.updateByGeneralItem(selectedItem);
					
		}
	}
}
