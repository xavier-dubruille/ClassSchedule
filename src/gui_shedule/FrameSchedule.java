package gui_shedule;



import javax.swing.JFrame;
import model.StateFullSchedule;

public class FrameSchedule extends JFrame {

	public FrameSchedule(){
		//empty, not visible frame
	}
	public FrameSchedule(StateFullSchedule state,MyMenuBar bar){
		super("Gestion Horraires");

		setBounds( 100, 100, 800, 660);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		OptionView ov=new OptionView(state);
		MainView mv=new MainView(state);

		setJMenuBar(bar);

		getContentPane().add(ov);
		getContentPane().add(mv);



		setVisible(true);
	}
	
	public void update_from_state(){
		
	}
}
