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
import model.Section;;

public class Card_GUI extends JPanel {

	private Card card;
	private DisplayPanel dp;
	public Card_GUI(Card card, DisplayPanel dp){

		super();
		this.card=card;
		this.dp=dp;

		this.drawMe();

		//this.setDropTarget(new DropTarget());
		this.setTransferHandler(new CardTransferHandler(card.getState()));
		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {

				JComponent comp = (JComponent) me.getSource();
				TransferHandler handler = comp.getTransferHandler();
				//	System.out.println("class name: "+handler.getClass());
				//	System.out.println("handeler tostring: "+handler);


				/*
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
				}*/
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);

			}
		});

		//System.out.println("la carte "+card.getHtmlRepresentation()+" a ete cree");
		//
	}

	private void drawMe(){

		setMaximumSize(GUI_Propreties.card_dimension);
		setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_Propreties.card_dimension);
		setSize(GUI_Propreties.card_dimension);
		this.setBorder(GUI_Propreties.card_default_border);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel firstLine=new JPanel();
		firstLine.setLayout(new BoxLayout(firstLine,BoxLayout.X_AXIS));
		String sec=null;
		for(Section s:card.getCard_sections())
			if(sec==null)
				sec=s.getName();
			else 
				sec+=", "+s.getName();

		JLabel secLab=new JLabel(sec);
		secLab.setFont(GUI_Propreties.card_default_font_1);
		firstLine.add(secLab);
		firstLine.add(Box.createHorizontalGlue());
		JLabel nameLab=new JLabel(card.getTeacher().getLastName());
		nameLab.setFont(GUI_Propreties.card_default_font_1);
		firstLine.add(nameLab);

		this.add(firstLine);
		JLabel j=new JLabel(card.getHtmlRepresentation());
		j.setFont(GUI_Propreties.card_default_font_2);

		add(j);



		if(card.getTimePeriod()==0)
			this.setBackground(GUI_Propreties.card_default_background);
		else
			this.setBackground(GUI_Propreties.card_color_placed);
	}
	public Card getCard(){
		return card;
	}

	public DisplayPanel getDisplayPanel(){
		return dp;
	}

	public String toSrring(){
		return card.toString();
	}
}
