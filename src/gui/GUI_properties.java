package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/*
 * 
 * 
 * some graphical satic properties
 */
public class GUI_properties {
	
	public static boolean playSound=true;

	private static int size=132;
	private static double ratio=0.62;
	
	public static Dimension card_dimension=new Dimension(size,(int)(size*ratio));
	public static Color card_default_background=Color.white;
	public static Color card_static_background= new Color(209,214,206); // Color.lightGray; 
	public static Color card_color_placed=new Color(230,241,250);
	public static Font card_default_font_1=new Font("Helvetica", Font.PLAIN, 11);
	public static Font card_default_font_2=new Font("Helvetica", Font.PLAIN, 10);
	public static Font timeBox_static_font=new Font("Papyrus", Font.BOLD, 17);
	public static Border card_default_border=BorderFactory.createLineBorder(Color.DARK_GRAY);

	public static Dimension size_label_dialog=new Dimension(320,20);
	public static Dimension default_comboBox_size=new Dimension(182,27);
	
	public static Dimension dialog_size=new Dimension(670,180);
	
	public static Color optionPanelSolo_color= new Color(111,179,66); // new Color(132,214,77);
	
	public static Color optionPanelCompare_color=new Color(255,193,59);
	public static Color displayPanel=new Color(250,247,225); 
	
	
	public static Color teacher_color=optionPanelSolo_color;
	public static Color room_color=optionPanelSolo_color;
	public static Color section_color=optionPanelSolo_color;
	
	public static Color timeBox_color_disable= new Color(250,212,200); // Color.red;
	public static Color timeBox_color_warning= new Color(250,232,192); // Color.orange;
	//public static Color timeBox_color_default= Color.white;
	public static Color timeBox_color_empty  = new Color(250,247,225); // Color.white;
	public static Color timeBox_color_placed = new Color(230,241,250); // new Color(179,230,255);
	public static Color timeBox_color_ok = new Color(217,250,197); // Color.green;
	
	public static Color class_color= Color.cyan;
	public static Color info_color= Color.yellow;
	public static Color group_color= Color.magenta;
	public static Color l35_color= Color.orange;
	public static Color a10_color= Color.pink;
	public static Color l63_color= Color.blue;
	public static Color labo_color= Color.green;

	
	
	/***************  String constants **************/
	public static final String option_teachers="Professeurs";
	public static final String option_rooms="Locaux";
	public static final String option_sections="Sections";
	
}
