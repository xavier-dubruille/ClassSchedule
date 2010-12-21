package gui_schedule;



import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.TransferHandler;

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

	public TimeBoxSolo(String s, int timePeriod, StateFullSchedule state, final MainViewSolo view) {
		
		super();
		this.timePeriod=timePeriod;
		this.state=state;
		this.view=view;
		this.ops=view.getOptionPanelSolo();
		this.setTransferHandler(new TimeBoxSoloTransferHandler(state,view.getDisplayPanel(),ops));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent me) {

				TimeBox comp = (TimeBox) me.getSource();
				if (comp==null) return;
				TransferHandler handler = comp.getTransferHandler();
				if (handler==null) return;
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);
				
				if(!(comp.getCard()==null))
					view.showPossibilities(comp.getCard());

			}
				
		});
	}
	
	public MainViewSolo getView(){
		return view;
	}
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}


}
