package gui;

import javax.swing.*;

import java.awt.*;


/**
 *  devrait représenter un horaire type, dans lequel on viendrait 
 *  poser les "cartons".
 *  
 *  version de tests
 *
 */
public class DaySchedule extends JPanel {

	DaySchedule(){
		
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		
		JPanel gridCenter=new JPanel(new GridLayout(8,7));

		
	
		JLabel[] jLabelTab=new JLabel[56];
		
		
		jLabelTab[0]=new JLabel(new ImageIcon("blank.png"));
		gridCenter.add(jLabelTab[0]);
		
		for(int i=1; i<7; i++){
			jLabelTab[i]=new JLabel(new ImageIcon("day_"+i+".png"));
			gridCenter.add(jLabelTab[i]);
		}
		
		for(int i=7; i<56; i++){
			if(i%7==0)
				jLabelTab[i]=new JLabel(new ImageIcon("timeSlide_"+i/7+".png"));
			else 
				jLabelTab[i]=new JLabel(new ImageIcon("blank.png"));
			//jLabelTab[i].setBorder(null);
			gridCenter.add(jLabelTab[i]);
			
		}
		
		
		gridCenter.setMaximumSize(new Dimension(680, 470));
		
		add(gridCenter);
		add(Box.createVerticalGlue());
	}
}
