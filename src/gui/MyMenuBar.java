/**
 * 
 */
package gui;

import javax.swing.*;

/**
 * @author xav
 *
 */
public class MyMenuBar extends JMenuBar {

	/**
	 * 
	 */
	public MyMenuBar() {
		JMenu file =new JMenu("Fichier");
		JMenu edit =new JMenu("Edit");
		
		JMenuItem open = new JMenuItem ("ouvrir");
		JMenuItem quit = new JMenuItem ("quiter");
		
		file.add(open);
		file.add(quit);
		
		add(file);
		add(edit);
		
	}

}
