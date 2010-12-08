package gui_schedule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeBox extends JPanel {

	JLabel jl;
	
	TimeBox(String s){
		super();

		setMaximumSize(new Dimension(140,50));
		setPreferredSize(new Dimension(140,50));

		jl=new JLabel(s);
		jl.setFont(new Font("Helvetica", Font.PLAIN, 12));

		add(jl);
		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.lightGray);
		//this.setDropTarget(new DropTarget());
		//this.setTransferHandler(new CardTransferHandler());

	}
	
	public void setLabel(String s){
		jl.setText(s);
	}

}
