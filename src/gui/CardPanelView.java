/**
 * 
 */
package gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author 
 *
 */
public class CardPanelView extends JPanel {

	/**
	 * 
	 */
	public CardPanelView() {

		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		
		// je les rajoutes comme ca, juste pour des fins de tests,
		// mais, bien évidement, ils devront être généré celon les critaires
		// sélectionné

		add(Box.createHorizontalStrut(20));
		add(new CardPanel("<html>Programmation<br> multimédia</html>"));
		add(Box.createHorizontalStrut(20));
		add(new CardPanel("Télécomunication"));
		add(Box.createHorizontalStrut(20));
		add(new CardPanel("<html>Programation<br>avancée</html>"));
		add(Box.createHorizontalStrut(20));
		add(new CardPanel("<html>System<br>d'exploitation"));
		add(Box.createHorizontalStrut(20));
		add(new CardPanel("<html>Electronique<br>digital</html>"));
		add(Box.createHorizontalGlue());
	}



}
