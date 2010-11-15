/**
 * 
 */
package gui;


import java.awt.*;
import java.awt.dnd.*;
import javax.swing.JScrollPane;
import javax.swing.*;

import javax.swing.plaf.*;
import model.Card;

/**
 * @author 
 *
 */
public class CardPanel extends JPanel {



	public CardPanel() {

	}
	

	public CardPanel(Card card) {
		setMaximumSize(new Dimension(140,50));
		setPreferredSize(new Dimension(140,50));
		
		JLabel j=new JLabel(card.getHtmlRepresentation());
		j.setFont(new Font("Helvetica", Font.PLAIN, 12));
		
		add(j);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.lightGray);
		this.setDropTarget(new DropTarget());

	}

}
