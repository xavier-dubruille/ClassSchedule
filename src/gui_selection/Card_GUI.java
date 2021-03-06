package gui_selection;

import gui.ConstrainHandler;
import gui.GUI_properties;
import gui_schedule.CardTransferHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.TransferHandler;

import main.Start;
import model.Card;
import model.Section;

public class Card_GUI extends JPanel {

	private static final long serialVersionUID = 1L;
	private Card card;
	private DisplayPanel dp;
	private JLabel classRoomLab;
	private ConstrainHandler myConstrainHandler;
	private JPopupMenu popup;


	/**
	 * 
	 * @param card
	 * @param dp
	 */
	public Card_GUI(Card card, final DisplayPanel dp){

		super();


		this.card=card;
		this.dp=dp;

		myConstrainHandler=new ConstrainHandler(0,card);

		this.drawMe();

		//this.setDropTarget(new DropTarget());
		
		popup = new JPopupMenu("Popup");
		JMenuItem menuItem;
		menuItem = new JMenuItem("Modifier");
		menuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null,  "<html>Bient�t, Bient�t,.. ;-) </html>", 
								"Edition",JOptionPane.INFORMATION_MESSAGE); 
			}});
		popup.add(menuItem);
		menuItem = new JMenuItem("Faire autre chose..");
		//menuItem.addActionListener(this);
		popup.add(menuItem);
		
		this.setTransferHandler(new CardTransferHandler(card.getState(),dp));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent me){
				if (me.isPopupTrigger()) {
					popup.show(me.getComponent(), me.getX(), me.getY());
				}

				//	Start.fSc.getMvc().constructView();
				//	dp.getMainViewSolo().updateView();
			}

			@Override
			public void mousePressed(MouseEvent me) {


				if (me.getButton()==MouseEvent.BUTTON3){
					if (me.isPopupTrigger()) {
						popup.show(me.getComponent(), me.getX(), me.getY());
					}
					return;
				}

				else{
					Card_GUI card_gui = (Card_GUI) me.getSource();
					TransferHandler handler = card_gui.getTransferHandler();
					dp.getMainViewSolo().showPossibilities(card_gui.getMyConstrainHandler());
					Start.fSc.getMvc().showPossibilities(card_gui.getMyConstrainHandler());

					//	System.out.println("class name: "+handler.getClass());
					//	System.out.println("handeler tostring: "+handler);


					/*
				Toolkit toolkit=Toolkit.getDefaultToolkit();
				Clipboard clip=toolkit.getSystemClipboard();

				handler.exportToCC5921lipboard(comp, clip, TransferHandler.MOVE);

				Transferable clipData = clip.getContents(clip);
				if (clipData != null) {
					//if (clipData.isDataFlavorSupported(DataFlavor.imageFlavor)) {

					try{
						System.out.println(clipData.getTransferData(DataFlavor.stringFlavor));

					}catch(Exception e){
						System.err.println("rat�");
					}
					//}
				}*/
					handler.exportAsDrag(card_gui, me, TransferHandler.MOVE);
				}

			}


		});

		//System.out.println("la carte "+card.getHtmlRepresentation()+" a ete cree");
		//
	}


	/**
	 * construct the card.. should be called at the beginning only.. otherwise it may become to slow
	 */
	private void drawMe(){

		setMaximumSize(GUI_properties.card_dimension);
		setMinimumSize(GUI_properties.card_dimension);
		setPreferredSize(GUI_properties.card_dimension);
		setSize(GUI_properties.card_dimension);
		this.setBorder(GUI_properties.card_default_border);

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
		secLab.setFont(GUI_properties.card_default_font_1);
		firstLine.add(secLab);
		firstLine.add(Box.createHorizontalGlue());

		JPanel secondLine=new JPanel();
		secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
		secondLine.add(Box.createHorizontalGlue());
		JLabel nameLab=new JLabel(card.getTeacher().getLastName());
		nameLab.setFont(GUI_properties.card_default_font_1);
		secondLine.add(nameLab);

		JLabel midle=new JLabel(card.getHtmlRepresentation());
		midle.setFont(GUI_properties.card_default_font_2);
		midle.setBackground(card.findBackgroundColor());

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
			this.setBackground(GUI_properties.card_default_background);
		else
			this.setBackground(GUI_properties.card_color_placed);
	}


	/**
	 * 
	 */
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
			setBackground(GUI_properties.card_color_placed);
		else{
			setBackground(GUI_properties.card_default_background);
			//System.out.println("redraw de la cart: "+card+", timePeriod: "+card.getTimePeriod());
		}
	}


	/**
	 * 
	 * @return
	 */
	public Card getCard(){
		return card;
	}


	/**
	 * 
	 * @return
	 */
	public DisplayPanel getDisplayPanel(){
		return dp;
	}



	/**
	 * @return the myConstrainHandler
	 */
	public ConstrainHandler getMyConstrainHandler() {
		return myConstrainHandler;
	}


	/**
	 * 
	 * @return
	 */
	public String toSrring(){
		return card.toString();
	}
}
