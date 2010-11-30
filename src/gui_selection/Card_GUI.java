package gui_selection;

import javax.swing.*;
import model.Card;

public class Card_GUI extends JPanel {
	
	public Card_GUI(Card card){
		super();
		add(new JLabel(card.toString()));
	}
}
