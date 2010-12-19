package gui_schedule;

import gui.GUI_Propreties;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import main.Propreties;
import model.Card;
import model.StateFullSchedule;

public class TimeBox extends JPanel {
	private static final long serialVersionUID = 1L;

	JLabel jl;
	String name;
	protected int timePeriod;
	protected StateFullSchedule state;
	protected Card card;

	/*
	 * constructor without TransferHandler
	 */
	TimeBox(String s){
		super();
		name=s;
		setMaximumSize(GUI_Propreties.card_dimension);
		setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_Propreties.card_dimension);


		jl=new JLabel(name);

		jl.setFont(GUI_Propreties.timeBox_static_font);

		add(jl);
		this.setBorder(GUI_Propreties.card_default_border);
		this.setBackground(Color.lightGray);


		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent me) {

				JComponent comp = (JComponent) me.getSource();
				if (comp==null) return;
				TransferHandler handler = comp.getTransferHandler();
				if (handler==null) return;
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);

			}
		});

	}

	public TimeBox(int day_period, boolean day){
		this("");
		if(day)
			jl.setText(correspondingDay(day_period));
		else
			jl.setText(Propreties.Periods_name[day_period-1]);

	}

	private String correspondingDay(int day_period) {
		switch (day_period){
		case 1:return "Lundi";
		case 2:return "Mardi";
		case 3:return "Mercredi";
		case 4:return "Jeudi";
		case 5:return "Vendredi";
		case 6:return "Samdi";
		case 7:return "Dimanche";
		default:return "";
		}
	}

	public void setCard(Card c){
		// System.out.println("TimeBox.set Card :"+ c);
		this.card=c;
		jl.setText(c.getHtmlRepresentation());

		jl.setOpaque(true);
		this.setBackground(GUI_Propreties.timeBox_color_placed);
		jl.repaint();
	}

	public void clear(){
		this.card=null;
		jl.setText("");

		jl.setOpaque(true);
		this.setBackground(GUI_Propreties.timeBox_color_placed);
		jl.repaint();
	}

	public Card getCard(){
		return card;
	}



	public String toString(){
		return "timebox: "+name;
	}

	public int getTimePeriod(){
		return timePeriod;
	}

	public void setTimePeriod(int timePeriod){
		this.timePeriod=timePeriod;
	}



	public void setPref(int i) {
		this.setBorder(BorderFactory.createLineBorder(Color.red));

	}
}
