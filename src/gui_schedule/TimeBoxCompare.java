package gui_schedule;

import model.*;
import gui_selection.DisplayPanel;

public class TimeBoxCompare extends TimeBox {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private MainViewCompare mvc;
	//private OptionPanelCompare opc;
	private Section sectionConcerned;
	private Room roomConcerned;
	private Teacher teacherConcerned;
	//private  DisplayPanel dp;

	/**
	 * only for the satic timeBox!
	 */
	public TimeBoxCompare(String s) {
		super(s);
	}
	public TimeBoxCompare(int day_period, boolean day) {
		super(day_period,day);
	}

	private TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp) {

		super();

		//this.mvc=mvc;
		//this.opc=opc;
		this.timePeriod=timePeriod;
		//this.dp=dp;


		if(c==null)
			clear();

		else {
			setCard(c);
			//reDraw(); // necessaire ??
		}

		this.setTransferHandler(new TimeBoxCompareTransferHandler(state,mvc,opc, dp));
	}

	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Section sectionConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=sectionConcerned;
		this.roomConcerned=null;
		this.teacherConcerned=null;
	}
	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Teacher teacherConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=null;
		this.roomConcerned=null;
		this.teacherConcerned=teacherConcerned;
	}
	public TimeBoxCompare(Card c, StateFullSchedule state, int timePeriod, OptionPanelCompare opc, MainViewCompare mvc, DisplayPanel dp,Room roomConcerned) {
		this(c,state, timePeriod,opc,mvc, dp);
		this.sectionConcerned=null;
		this.roomConcerned=roomConcerned;
		this.teacherConcerned=null;
	}

	public Section getSectionConcerned() {
		return sectionConcerned;
	}

	public Room getRoomConcerned() {
		return roomConcerned;
	}

	public Teacher getTeacherConcerned() {
		return teacherConcerned;
	}



}
