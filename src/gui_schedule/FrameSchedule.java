package gui_schedule;



import javax.swing.*;
import java.awt.*;
import model.StateFullSchedule;
import gui_selection.*;

public class FrameSchedule extends JFrame {
	
	private static final long serialVersionUID = 1L;
	OptionPanelSolo ops;
	MainViewSolo mvs;
	OptionPanelCompare opc;
	
	public FrameSchedule(){
		//empty, not visible frame
	}
	public FrameSchedule(StateFullSchedule state,MyMenuBar bar,FrameSelection fs){
		super("Gestion Horraires");

		setBounds( 100, 100, 800, 660);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		setJMenuBar(bar);


		// first Tab: the solo/normal selection
		JPanel soloTab=new JPanel(new BorderLayout());
		mvs=new MainViewSolo(state,fs.getDisplayPanel());
		ops=new OptionPanelSolo(state,mvs);
		mvs.setOptionPanelSolo(ops);
		mvs.drawEmptySchedule();
		soloTab.add(ops,BorderLayout.NORTH);
		soloTab.add(mvs,BorderLayout.SOUTH);
		
		// second Tab: the compare selection
		JPanel compareTab=new JPanel(new BorderLayout());
		
		MainViewCompare mvc=new MainViewCompare(state,fs.getDisplayPanel());
		opc = new OptionPanelCompare(state,mvc);
		mvc.setOptionPanelCompare(opc);
		
		compareTab.add(opc,BorderLayout.NORTH);
		compareTab.add(mvc,BorderLayout.SOUTH);

		// ... and the tabbedPane
		JTabbedPane jt=new JTabbedPane();
		jt.addTab("mode normal", soloTab); //faudrait prendre l'autre methode addTab(...), avec le parametre tip
		jt.addTab("mode comparaison", compareTab);

		this.getContentPane().add(jt);

		this.pack();
		setVisible(true);
	}
	
	public void update_from_state(){
		ops.update_from_state();
		opc.update_from_state(); //pas encore implementé
	}
}
