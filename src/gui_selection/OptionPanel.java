package gui_selection;

import model.StateFullSchedule;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OptionPanel extends JPanel {

	StateFullSchedule state;
	String[][] chooses;
	JPanel choises_one_panel;
	JComboBox choises_one_Combo;
	
	public OptionPanel(DisplayPanel dp, StateFullSchedule state){
		this.state=state;
		

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(60,60));
		
		chooses=new String[4][];
		chooses[0]=new String[1];
		chooses[1]=new String[1];
		chooses[2]=new String[1];
		chooses[3]=new String[1];
		
		chooses[0][0]="all";
		chooses[1][0]="all";
		chooses[2][0]="all";
		chooses[3][0]="all";
		
		/*for(String[] s:chooses){
			s=new String[1];
		}*/
			

		//choises_one
		choises_one_panel=new JPanel();
		choises_one_panel.setLayout(new BoxLayout(choises_one_panel, BoxLayout.Y_AXIS));
		choises_one_panel.setSize(40, 40); //va faloir chipoter ici..
	    DefaultComboBoxModel d=new DefaultComboBoxModel(chooses[0]);
		choises_one_Combo = new JComboBox(d);
		choises_one_panel.add(new JLabel("Horizontal options"));
		choises_one_panel.add(choises_one_Combo);
		

		//choises_two
		JPanel choises_two_panel=new JPanel();
		choises_two_panel.setLayout(new BoxLayout(choises_two_panel, BoxLayout.Y_AXIS));
		choises_two_panel.setSize(40, 40); //va faloir chipoter ici..
		JComboBox choises_two_Combo = new JComboBox(chooses[1]);
		choises_two_panel.add(new JLabel("autres options"));
		choises_two_panel.add(choises_two_Combo);


		//choises_three
		JPanel choises_three_panel=new JPanel();
		choises_three_panel.setLayout(new BoxLayout(choises_three_panel, BoxLayout.Y_AXIS));
		choises_three_panel.setSize(40, 40); //va faloir chipoter ici..
		JComboBox choises_three_Combo = new JComboBox(chooses[2]);
		choises_three_panel.add(new JLabel("autres options"));
		choises_three_panel.add(choises_three_Combo);


		//choises_four
		JPanel choises_four_panel=new JPanel();
		choises_four_panel.setLayout(new BoxLayout(choises_four_panel, BoxLayout.Y_AXIS));
		choises_four_panel.setSize(40, 40); //va faloir chipoter ici..
		JComboBox choises_four_Combo = new JComboBox(chooses[3]);
		choises_four_panel.add(new JLabel("autres options"));
		choises_four_panel.add(choises_four_Combo);

		
		
		this.add(choises_one_panel);
		this.add(choises_two_panel);
		this.add(choises_three_panel);
		this.add(choises_four_panel);
		
		this.setVisible(true);
		// dans le listeneur on fera un dp.update(options);
		
	}
	
	/*
	 * the files(with all the options) are not know at creation, so we deal with it now
	 */
	public void update_from_state(){
		
	
		
		//chooses[0]=new String[3];
		chooses[0][0]="prof";
		
		
		choises_one_panel.repaint();
		choises_one_Combo.repaint();
		this.repaint();
		
		System.out.println("update optionPanel..");
	}
}
