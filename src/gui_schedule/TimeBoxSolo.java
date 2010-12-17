package gui_schedule;

import model.StateFullSchedule;

public class TimeBoxSolo extends TimeBox {


	private static final long serialVersionUID = 1L;


	protected MainViewSolo view;
	protected OptionPanelSolo ops;
	
	/*
	 *constructor without transferHandler..
	 */
	public TimeBoxSolo(String s) {
		super(s);
	}

	public TimeBoxSolo(String s, int timePeriod, StateFullSchedule state, MainViewSolo view) {
		
		this(s);
		this.timePeriod=timePeriod;
		this.state=state;
		this.view=view;
		this.ops=view.getOptionPanelSolo();
		this.setTransferHandler(new TimeBoxTransferHandler(state,view.getDisplayPanel(),ops));
	}
	
	public MainViewSolo getView(){
		return view;
	}
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}


}
