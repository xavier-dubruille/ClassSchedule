/**
 * 
 */
package gui;

import javax.swing.*;

/**
 * @author xav
 *
 */
public class MainPanel extends JSplitPane {
	public MainPanel(){
		super(JSplitPane.VERTICAL_SPLIT);
		
		JScrollPane js=new JScrollPane(new CardPanel(),JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JTabbedPane jt=new JTabbedPane(JTabbedPane.TOP);
		jt.addTab("2TL1", new GenericSchedule());
		jt.addTab("2TL2", new GenericSchedule());
		
		add(js);
		add(jt);
		setDividerLocation(0.5);
		
	
	}

}
