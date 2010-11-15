package gui;

import java.awt.*;

import javax.swing.*;

public class CardSelectionView extends JPanel {

	
	
	public CardSelectionView() {
		
		setPreferredSize(new Dimension(130,90));
		add(new JLabel("Selection des cartons"));
		JComboBox jcb =new JComboBox();
		jcb.addItem("tous");
		jcb.addItem("Mm. Vroman");
		jcb.addItem("M. Buterfa");
		
		add(new JLabel("<html>Afficher les cours <br> des professeurs suivant: "));
		add(jcb);
		
	}
	
	/**
	 * sans doute qu'elle ne sera pas utile, et 
	 * que repaint() et updateUI() suffiront..
	 */
	public void update(){}

}
