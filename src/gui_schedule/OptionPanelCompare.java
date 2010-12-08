package gui_schedule;

import java.awt.*;

import javax.swing.JPanel;
import model.StateFullSchedule;

public class OptionPanelCompare extends JPanel {
	StateFullSchedule state;

	public OptionPanelCompare(StateFullSchedule state){
		this.state=state;
		this.setBackground(Color.blue); //test

		this.setPreferredSize(new Dimension(200,200));

	}
	
	public void update_from_state(){}
}
