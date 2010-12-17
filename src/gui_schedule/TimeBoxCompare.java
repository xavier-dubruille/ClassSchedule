package gui_schedule;

import model.*;

public class TimeBoxCompare extends TimeBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeBoxCompare(String s) {
		super(s);
		// TODO Auto-generated constructor stub
	}
	
	public TimeBoxCompare(Card c) {
		this("");
		if(c==null){
			clear();
		}
		else {
			setCard(c);
		}
	}

}
