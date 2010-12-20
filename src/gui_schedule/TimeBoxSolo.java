package gui_schedule;

import model.StateFullSchedule;

public class TimeBoxSolo extends TimeBox {


	private static final long serialVersionUID = 1L;


	protected MainViewSolo view;
	protected OptionPanelSolo ops;
	
	/*
	 *constructors without transferHandler..
	 */

	public TimeBoxSolo(int day_period, boolean day) {
		super(day_period,day);
	}

	public TimeBoxSolo(String s, int timePeriod, StateFullSchedule state, MainViewSolo view) {
		
		super();
		this.timePeriod=timePeriod;
		this.state=state;
		this.view=view;
		this.ops=view.getOptionPanelSolo();
		this.setTransferHandler(new TimeBoxSoloTransferHandler(state,view.getDisplayPanel(),ops));
	}
	
	public MainViewSolo getView(){
		return view;
	}
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}


}
