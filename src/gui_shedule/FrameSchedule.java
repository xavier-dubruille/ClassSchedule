package gui_shedule;



import javax.swing.JFrame;
import model.StateFullSchedule;

public class FrameSchedule extends JFrame {

	public FrameSchedule(StateFullSchedule state){
		super("Gestion Horraires");

		setBounds( 100, 100, 800, 660);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		OptionView ov=new OptionView(state);
		MainView mv=new MainView(state);

		setJMenuBar(new MyMenuBar(state, mainPanel));

		getContentPane().add(ov);
		getContentPane().add(mv);



		setVisible(true);
	}
}
