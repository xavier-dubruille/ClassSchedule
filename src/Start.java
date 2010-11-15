import gui.MainFrame;
import model.StateFullSchedule;

public class Start {

	/**
	 * 
	 */
	public static void main(String[] args) {

		// faudrait p-e verifier ici que la JVM soie à une bonne version
		
		
		StateFullSchedule state=new StateFullSchedule();
		MainFrame mf=new MainFrame(state);

	}

}
