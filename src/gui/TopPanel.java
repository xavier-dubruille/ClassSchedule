package gui;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class TopPanel extends JPanel {

	public TopPanel(){

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(new CardSelectionView());
		add(Box.createHorizontalStrut(11));
		add(new JScrollPane(new CardPanelView(),JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));


	}

}
