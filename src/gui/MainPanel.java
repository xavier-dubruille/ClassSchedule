/**
 * 
 */
package gui;

import javax.swing.*;
import model.StateFullSchedule;

/**
 * @author 
 *
 */
public class MainPanel extends JSplitPane {
	
	private TopPanel top;
	private JTabbedPane jt;
	private StateFullSchedule state;
	
	public MainPanel(StateFullSchedule state){
	
		super(JSplitPane.VERTICAL_SPLIT);
		
		this.state=state;
		top = new TopPanel(state);
		jt=new JTabbedPane(JTabbedPane.TOP);
		
		// ne seront pas cree ainsi; c'est juste des tests
		// ils seront créé en fonction de l'objet state..
		jt.addTab("1TL2", new JScrollPane(new DaySchedule()));
		jt.addTab("2TL1", new JScrollPane(new DaySchedule()));
		jt.addTab("2TL2", new JScrollPane(new DaySchedule()));

		
		add(top);
		add(jt);
		setDividerLocation(150);
		
	
	}
	public TopPanel getTopPanel(){
		return top;
	}
	
	public void update(){
		// System.out.println("update de main panel: "+state.cards); //debug
		top.update();
		//faudrait aussi updater les onglets
		
		//p-e on peut aussi faire un updateUI() ...
	}
	

}
