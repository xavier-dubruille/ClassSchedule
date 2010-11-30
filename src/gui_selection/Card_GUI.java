package gui_selection;

import gui.CardTransferHandler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DropTarget;

import javax.swing.*;

import model.Card;

public class Card_GUI extends JPanel {

	public Card_GUI(Card card){
		super();
		setMaximumSize(new Dimension(140,50));
		setPreferredSize(new Dimension(140,50));

		JLabel j=new JLabel(card.getHtmlRepresentation());
		j.setFont(new Font("Helvetica", Font.PLAIN, 12));

		add(j);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.lightGray);
		this.setDropTarget(new DropTarget());
		this.setTransferHandler(new CardTransferHandler());


	}
}
