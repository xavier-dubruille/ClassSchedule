package gui;

import gui_shedule.MyMenuBar;

import javax.swing.*;
import java.awt.*;
import model.StateFullSchedule;

public class MainFrame extends JFrame{

	
	public MainFrame(){}
	
	public MainFrame(StateFullSchedule state){
		super("Gestion Horraires");
		
		setBounds( 100, 100, 800, 660);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		MainPanel mainPanel=new MainPanel(state);
		
		setJMenuBar(new MyMenuBar(state, mainPanel));
		
		getContentPane().add(mainPanel);
		
		
		setVisible(true);
	}

}
