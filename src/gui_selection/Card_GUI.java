package gui_selection;

import gui.CardTransferHandler;
import gui.GUI_Propreties;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.dnd.DropTarget;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;

import model.Card;

public class Card_GUI extends JPanel {

	private Card card;
	public Card_GUI(Card card){

		super();
		this.card=card;

		setMaximumSize(GUI_Propreties.card_dimension);
		setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_Propreties.card_dimension);
		setSize(GUI_Propreties.card_dimension);
		

		JLabel j=new JLabel(card.getHtmlRepresentation());
		j.setFont(GUI_Propreties.card_default_font);

		add(j);
		this.setBorder(GUI_Propreties.card_default_border);
		this.setBackground(GUI_Propreties.card_default_background);
		//this.setDropTarget(new DropTarget());
		this.setTransferHandler(new CardTransferHandler());
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {
				System.out.println("mouse pressed");
				JComponent comp = (JComponent) me.getSource();
				TransferHandler handler = comp.getTransferHandler();
				//	System.out.println("class name: "+handler.getClass());
				//	System.out.println("handeler tostring: "+handler);

				Toolkit toolkit=Toolkit.getDefaultToolkit();
				Clipboard clip=toolkit.getSystemClipboard();

				handler.exportToClipboard(comp, clip, TransferHandler.MOVE);
				
				Transferable clipData = clip.getContents(clip);
				if (clipData != null) {
					//if (clipData.isDataFlavorSupported(DataFlavor.imageFlavor)) {

						try{
							System.out.println(clipData.getTransferData(DataFlavor.stringFlavor));

						}catch(Exception e){
							System.err.println("raté");
						}
					//}
				}
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);
			}
		});

		//System.out.println("la carte "+card.getHtmlRepresentation()+" a ete cree");
		//System.out.println("-------------------------");

	}

	public Card getCard(){
		return card;
	}

	public String toSrring(){
		return card.toString();
	}
}
