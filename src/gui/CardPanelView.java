/**
 * 
 */
package gui;

import javax.swing.*;
import java.awt.*;
import model.StateFullSchedule;
import model.Card;
/**
 * @author 
 *
 */
public class CardPanelView extends JPanel {

	StateFullSchedule state;
	/**
	 * 
	 */
	public CardPanelView(StateFullSchedule state) {

		this.state=state;
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
	//	this.add(new CardPanel(new Card())); //tests purposes

	}

	public void update(){

		// System.out.println("update de CardPanelView: "+state.cards); //debug

		// il faudra d'abord "netoyer" les cartons présent..

		// on pourait p-e fixer le layout en fonction de l'état..
		// mais je pense que c'est des complications inutiles
		

		//	System.out.println("création panelview\n"+state.getCards());

		// on rajoute les "cartons"
		for(Card c : state.getCards().values()){
			System.out.println(c);
			this.add(Box.createHorizontalStrut(20));
			this.add(new CardPanel(c));
		}

		this.add(Box.createHorizontalGlue());
		
		// toutes ces méthodes ne sont p-e pas utiles..
		this.updateUI();
		this.repaint();
		this.setVisible(true);

	}



}
