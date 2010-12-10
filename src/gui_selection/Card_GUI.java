package gui_selection;

import gui.CardTransferHandler;

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

		setMaximumSize(new Dimension(140,50));
		setPreferredSize(new Dimension(140,50));

		JLabel j=new JLabel(card.getHtmlRepresentation());
		j.setFont(new Font("Helvetica", Font.PLAIN, 12));

		add(j);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.lightGray);
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
