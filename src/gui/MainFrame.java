package gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame{

	public MainFrame(){
		super("Gestion Horraires");
		
		setBounds( 100, 100, 800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setJMenuBar(new MyMenuBar());
		
		getContentPane().add(new MainPanel());
		
		
		setVisible(true);
	}

}
