package gui;

import javax.swing.Box;
import java.awt.*;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.StateFullSchedule;

public class TopPanel extends JPanel {

	private CardSelectionView cardSelectV;
	private CardPanelView cardPanelV;
	private StateFullSchedule state;
	
	public TopPanel(StateFullSchedule state){
		
		this.state=state;
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		cardSelectV=new CardSelectionView();
		cardPanelV=new CardPanelView(state);
		
		
		// il faut mieux gérer cette taille, mais ca m'ennuie
		 cardSelectV.setMaximumSize(new Dimension(900,900));
		//cardSelectV.setSize(new Dimension(60,60));
		
		add(cardSelectV);
		add(Box.createHorizontalStrut(11));
		add(new JScrollPane(cardPanelV,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS));


	}
	
	public CardSelectionView getCardSelectionView(){
		return cardSelectV;
	}
	
	public CardPanelView getCardPanelView(){
		return cardPanelV;
	}
	
	public void update(){
		System.out.println("update de TopPanel: "+state.cards);
		cardPanelV.update();
		cardSelectV.update();
	}

}
