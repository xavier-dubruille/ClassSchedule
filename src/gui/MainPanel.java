/**
 * 
 */
package gui;

import javax.swing.*;

/**
 * @author 
 *
 */
public class MainPanel extends JSplitPane {
	public MainPanel(){
		super(JSplitPane.VERTICAL_SPLIT);
		
		TopPanel top = new TopPanel();
		
		JTabbedPane jt=new JTabbedPane(JTabbedPane.TOP);
		
		// ne seront pas cree ainsi; c'est juste des tests
		jt.addTab("1TL2", new JScrollPane(new DaySchedule()));
		jt.addTab("2TL1", new JScrollPane(new DaySchedule()));
		jt.addTab("2TL2", new JScrollPane(new DaySchedule()));

		
		add(top);
		add(jt);
		setDividerLocation(150);
		
	
	}

}
