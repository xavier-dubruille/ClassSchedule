package gui_schedule;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import model.StateFullSchedule;
import gui.MyMenuBar;
import gui_selection.*;

/**
 *
 * @author Dubruille Xavier
 * @author Delange Jonas
 */
public class FrameSchedule extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private OptionPanelSolo ops;
	private OptionPanelCompare opc;
	
	
	final MainViewCompare mvc;
	final MainViewSolo mvs;
	
	private StateFullSchedule state;
	
	/*
	public FrameSchedule(){
		//empty, not visible frame
	}
	*/
	
	/**
	 * 
	 * @param state
	 * @param bar
	 * @param fs
	 */
	public FrameSchedule(StateFullSchedule state,MyMenuBar bar,FrameSelection fs){
		super("Gestion Horraires");

		this.state=state;
		
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
		soloTab.add(mvs,BorderLayout.CENTER);
		soloTab.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {}

			@Override
			public void componentShown(ComponentEvent arg0) {
				//System.out.println("youpie Component");
				mvs.updateView();
				
			}});
		
		// second Tab: the compare selection
		JPanel compareTab=new JPanel(new BorderLayout());
		mvc=new MainViewCompare(state,fs.getDisplayPanel());
		opc = new OptionPanelCompare(state,mvc);
		mvc.setOptionPanelCompare(opc);
		compareTab.add(opc,BorderLayout.NORTH);
		compareTab.add(mvc,BorderLayout.CENTER);
		compareTab.addComponentListener(new ComponentListener(){
			@Override
			public void componentHidden(ComponentEvent arg0) {}

			@Override
			public void componentMoved(ComponentEvent arg0) {}

			@Override
			public void componentResized(ComponentEvent arg0) {}

			@Override
			public void componentShown(ComponentEvent arg0) {
				System.out.println("youpie Component");
				mvc.constructView();
				
			}});

		// ... and the tabbedPane
		JTabbedPane jt=new JTabbedPane();
		jt.addTab("Mode Standard", soloTab); 
		jt.addTab("Mode Comparaison", compareTab);
		//TODO : faudrait prendre l'autre methode addTab(...), avec le parametre tip
		
		this.getContentPane().add(jt);

		this.pack();
		setVisible(true);
	}
	
	/**
	 * Update the whole frame GUI ..
	 * 
	 *  used when we (re)create a new project
	 */
	public void update_from_state(){
		System.out.println("FrameSchedule: nombre de carton: "+state.cards_size());
		ops.update_from_state();
		opc.update_from_state(); // not implemented yet.
		
		mvc.update_from_state();
		mvs.update_from_state();
		
	}

	/**
	 * not used
	 */
	public void clear() {

		mvc.clear();
		mvs.clear();
		ops.clear();
		opc.clear();
		
		
	}


}
