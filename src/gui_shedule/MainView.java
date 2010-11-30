package gui_shedule;

import javax.swing.JPanel;
import model.StateFullSchedule;

public class MainView extends JPanel{


	StateFullSchedule state;

	public MainView(StateFullSchedule state){
		this.state=state;
	}
}