package gui_schedule;




import gui.ConstrainHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.TransferHandler;

import model.StateFullSchedule;

/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class TimeBoxSolo extends TimeBox {


	private static final long serialVersionUID = 1L;


	private MainViewSolo view;
	private OptionPanelSolo ops;
	



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

				TimeBoxSolo comp = (TimeBoxSolo) me.getSource();
				if (comp==null) return;
				TransferHandler handler = comp.getTransferHandler();
				if (handler==null) return;
				handler.exportAsDrag(comp, me, TransferHandler.MOVE);

				if(!(comp.getCard()==null))
					view.showPossibilities(comp.getMyConstrainHandler());

			}

		});
	}

	/**
	 * 
	 * @return the main view panel, i.e. the pannel where the schedule is builded
	 */
	public MainViewSolo getView(){
		return view;
	}
	public OptionPanelSolo getOptionPanelSolo(){
		return ops;
	}


}
