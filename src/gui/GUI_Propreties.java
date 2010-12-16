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
public class GUI_Propreties {
	

	private static int size=134;
	private static double ratio=0.62;
	
	public static Dimension card_dimension=new Dimension(size,(int)(size*ratio));
	public static Color card_default_background=Color.lightGray;
	public static Color card_color_placed=Color.green;
	public static Font card_default_font_1=new Font("Helvetica", Font.PLAIN, 11);
	public static Font card_default_font_2=new Font("Helvetica", Font.PLAIN, 10);
	public static Border card_default_border=BorderFactory.createLineBorder(Color.DARK_GRAY);
	
	public static Color timeBox_color_placed=Color.white;

	public static Dimension size_label_dialog=new Dimension(320,20);
	public static Dimension default_comboBox_size=new Dimension(182,27);
	
	public static Dimension dialog_size=new Dimension(670,180);
	
	public static Color optionPanelSolo_color=Color.white;
	public static Color optionPanelCompare_color=Color.yellow;
	
	public static Color teacher_color=Color.pink;
	public static Color room_color=Color.cyan;
	public static Color section_color=Color.magenta;
	
	
	
	/***************  String constants **************/
	public static final String option_teachers="Professeurs";
	public static final String option_rooms="Locaux";
	public static final String option_sections="Sections";
	
}
