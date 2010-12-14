package gui_schedule;



import javax.swing.*;
import java.awt.*;
import model.StateFullSchedule;
import gui_selection.*;

public class FrameSchedule extends JFrame {

	OptionPanelSolo ops;
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
		MainViewSolo mvs=new MainViewSolo(state,fs.getDisplayPanel());
		ops=new OptionPanelSolo(state,mvs);
		soloTab.add(ops,BorderLayout.NORTH);
		soloTab.add(mvs,BorderLayout.SOUTH);
		
		// second Tab: the compare selection
		JPanel compareTab=new JPanel(new BorderLayout());
		opc = new OptionPanelCompare(state);
		MainViewCompare mvc=new MainViewCompare(state);
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
