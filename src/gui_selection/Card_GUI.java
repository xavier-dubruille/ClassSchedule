package gui_selection;

import gui.CardTransferHandler;
import gui.GUI_Propreties;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import model.Card;
import model.Section;

public class Card_GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Card card;
	private DisplayPanel dp;
	private JLabel classRoomLab;
	public Card_GUI(Card card, DisplayPanel dp){

		super();
		this.card=card;
		this.dp=dp;

		this.drawMe();

		//this.setDropTarget(new DropTarget());
		this.setTransferHandler(new CardTransferHandler(card.getState(),dp));
		this.addMouseListener(new MouseAdapter() {

			@Override
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
		
		JPanel secondLine=new JPanel();
		secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
		secondLine.add(Box.createHorizontalGlue());
		JLabel nameLab=new JLabel(card.getTeacher().getLastName());
		nameLab.setFont(GUI_Propreties.card_default_font_1);
		secondLine.add(nameLab);

		JLabel midle=new JLabel(card.getHtmlRepresentation());
		midle.setFont(GUI_Propreties.card_default_font_2);
		
		JPanel lastLine=new JPanel();
		lastLine.setLayout(new BoxLayout(lastLine,BoxLayout.X_AXIS));
		classRoomLab=new JLabel(".");
		lastLine.add(classRoomLab);
		lastLine.add(Box.createHorizontalGlue());
		
		this.add(firstLine);
		this.add(secondLine);
		this.add(Box.createVerticalGlue());
		this.add(midle);
		this.add(Box.createVerticalGlue());
		this.add(lastLine);


		if(card.getTimePeriod()==0)
			this.setBackground(GUI_Propreties.card_default_background);
		else
			this.setBackground(GUI_Propreties.card_color_placed);
	}
	public void reDraw(){
		//System.out.println("CardGui.redraw(): ");
		
		// classRoom
		if (card.getClassRoom()==null || card.getClassRoom().getName()==null) {
			classRoomLab.setText(".");
		}
		else {
			classRoomLab.setText(card.getClassRoom().getName());
			System.out.println("redraw de la cart: "+card+", class room: "+card.getClassRoom().getName());
		
		}
		// timePeriod (i.e placed or not placed)
		if(card.getTimePeriod()!=0)
			setBackground(GUI_Propreties.card_color_placed);
		else{
			setBackground(GUI_Propreties.card_default_background);
			System.out.println("redraw de la cart: "+card+", timePeriod: "+card.getTimePeriod());
		}
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
