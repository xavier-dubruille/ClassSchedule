package gui_schedule;


import gui.ConstrainHandler;
import gui.GUI_properties;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import main.Main_properties;
import model.Card;
import model.Section;
import model.StateFullSchedule;

/**
 * 
 *
 */
public class TimeBox extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected StateFullSchedule state;

	protected JLabel staticLabel;
	protected int timePeriod;
	
	final protected ConstrainHandler myConstrainHandler;

	
	protected Card card;

	private JLabel sectionLabel;
	private JLabel teacherLabel;
	private JLabel classRoomLabel;
	private JLabel midleLabel;
	private JLabel problem_level;
	private JLabel problem_category;



	private JPanel firstLine;
	private JPanel secondLine;
	private JPanel lastLine;




	/**
	 * for static timeBox only!
	 * @param s
	 */
	protected TimeBox(String s){
		String text=s;
		myConstrainHandler=null;
		
		//setMaximumSize(GUI_Propreties.card_dimension);
		//setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_properties.card_dimension);

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		staticLabel=new JLabel(text);

		staticLabel.setFont(GUI_properties.timeBox_static_font);

		this.add(Box.createHorizontalGlue());
		JPanel center=new JPanel();
		center.setLayout(new BoxLayout(center,BoxLayout.X_AXIS));
		center.add(Box.createVerticalGlue());
		center.add(staticLabel);
		center.add(Box.createVerticalGlue());
		center.setBackground(GUI_properties.card_static_background);

		this.add(Box.createHorizontalGlue());
		this.add(center);
		this.add(Box.createHorizontalGlue());

		this.setBorder(GUI_properties.card_default_border);
		this.setBackground(GUI_properties.card_static_background);


	}

	/**
	 * default constructor, shouldn't be called directly, but by his child. and not for static timeBoxes!
	 */
	protected TimeBox(){
		super();

		myConstrainHandler=new ConstrainHandler(1,this);
		
		//setMaximumSize(GUI_Propreties.card_dimension);
		//setMinimumSize(GUI_Propreties.card_dimension);
		setPreferredSize(GUI_properties.card_dimension);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));




		firstLine=new JPanel();
		firstLine.setLayout(new BoxLayout(firstLine,BoxLayout.X_AXIS));

		sectionLabel=new JLabel("");
		sectionLabel.setFont(GUI_properties.card_default_font_1);
		firstLine.add(sectionLabel);
		firstLine.add(Box.createHorizontalGlue());

		secondLine=new JPanel();
		secondLine.setLayout(new BoxLayout(secondLine,BoxLayout.X_AXIS));
		secondLine.add(Box.createHorizontalGlue());
		teacherLabel=new JLabel(" ");
		teacherLabel.setFont(GUI_properties.card_default_font_1);
		secondLine.add(teacherLabel);

		midleLabel=new JLabel("");
		midleLabel.setFont(GUI_properties.card_default_font_2);


		problem_level = new JLabel();
		problem_category=new JLabel();
		lastLine=new JPanel();
		lastLine.setLayout(new BoxLayout(lastLine,BoxLayout.X_AXIS));
		classRoomLabel=new JLabel(" ");
		lastLine.add(classRoomLabel);
		lastLine.add(Box.createHorizontalGlue());
		lastLine.add(problem_level);
		lastLine.add(problem_category);

		this.setBorder(GUI_properties.card_default_border);

		this.add(firstLine);
		this.add(secondLine);
		this.add(Box.createVerticalGlue());
		this.add(midleLabel);
		this.add(Box.createVerticalGlue());
		this.add(lastLine);


		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {

				JComponent comp = (JComponent) me.getSource();
				if (comp==null) return;
				TransferHandler handler = comp.getTransferHandler();
				if (handler==null) return;
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);

			}
			
			
		});
	}






	/**
	 * 
	 * Constructor called only for "static" timeBox !
	 * 
	 * @param day_period
	 * @param day
	 */
	protected TimeBox(int day_period, boolean day){

		this("");


		if(day)
			staticLabel.setText(correspondingDay(day_period));
		else
			staticLabel.setText(Main_properties.Periods_name[day_period-1]);


	}




	/**
	 * 
	 * @param day_period
	 * @return
	 */
	public static String correspondingDay(int day_period) {
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

	/**
	 * 
	 * @param c
	 */
	public void setCard(Card c){
		// System.out.println("TimeBox.set Card :"+ c);
		this.card=c;
		String sec=null;
		
		myConstrainHandler.setCard(c);
		
		for(Section s:card.getCard_sections())
			if(sec==null)
				sec=s.getName();
			else 
				sec+=", "+s.getName();

		this.midleLabel.setText(c.getHtmlRepresentation());
		midleLabel.setBackground(card.findBackgroundColor());
		sectionLabel.setText(sec);
		teacherLabel.setText(c.getTeacher().getLastName());
		classRoomLabel.setText(c.getClassRoom().getName());

		this.setAllBackground(GUI_properties.timeBox_color_placed);

	}

	private void setAllBackground(Color c){
		this.setBackground(c);
		this.firstLine.setBackground(c);
		this.secondLine.setBackground(c);
		this.lastLine.setBackground(c);
	}

	/**
	 * 
	 */
	public void clear(){
		this.card=null;
		this.midleLabel.setText("");
		sectionLabel.setText(" ");
		teacherLabel.setText(" ");
		classRoomLabel.setText(" ");
		problem_level.setIcon(null);
		problem_category.setIcon(null);


		this.setAllBackground(GUI_properties.timeBox_color_empty);

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
	public int getTimePeriod(){
		return timePeriod;
	}

	/**
	 * 
	 * @param timePeriod
	 */
	public void setTimePeriod(int timePeriod){
		this.timePeriod=timePeriod;
	}

	/**
	 * staticLabel can't be null !
	 * @param text
	 */
	public void setStaticText(String text) {
		staticLabel.setText(text);
		staticLabel.setFont(GUI_properties.timeBox_static_font);

	}

	/**
	 * 
	 * @param i
	 */
	public void setPref(int i) {
		//this.setBorder(BorderFactory.createLineBorder(Color.red));

		if (i==0) return;
		
		if (i<=5) // warning
			drawAdvised(0,1);
		else //bad
			drawAdvised(0,0);
		
	}
	
	public ConstrainHandler getMyConstrainHandler(){
		return myConstrainHandler;
	}

	/**
	 * this will draw the timeBox according to the reason and the level
	 * @param reason the reason of the draw: 0 for the teacher, 1 for the selection and 2 for the room
	 * @param level the level of the draw: 0 is disable, 1 is warning, 2 is ok, and 3 is advice
	 */
	public void drawAdvised(int reason, int level) {



		switch(reason){

		case 0:
			problem_category.setIcon(MainViewSolo.problem_category_image[reason]);
			break;
		case 1:
			problem_category.setIcon(MainViewSolo.problem_category_image[reason]);
			break;
		case 2:
			problem_category.setIcon(MainViewSolo.problem_category_image[reason]);
			break;
		default:problem_category.setIcon(null);
		}

		switch(level){
		case 0: // cross
			setAllBackground(GUI_properties.timeBox_color_disable);
			problem_level.setIcon(MainViewSolo.problem_level_images[0]);
			break;
		case 1: // warning
			setAllBackground(GUI_properties.timeBox_color_warning);
			problem_level.setIcon(MainViewSolo.problem_level_images[1]);
			break;
		case 2: // ok
			setAllBackground(GUI_properties.timeBox_color_ok);
			problem_level.setIcon(MainViewSolo.problem_level_images[2]);
			break;
		}

	}

	public JLabel getStaticLabel() {
		return staticLabel;
	}
}
