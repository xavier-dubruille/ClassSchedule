package gui_schedule;




import gui.ConstrainHandler;

import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.event.MouseInputAdapter;

import main.Start;
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

	private boolean dragging=false;


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
		MouseInputAdapter listener=new MouseInputAdapter() {

			
			

			
			@Override
			public void mousePressed(MouseEvent me) {

				//Toolkit.getDefaultToolkit().getScreenSize()
				
				//MouseInfo.getPointerInfo().
				//System.out.println("mouse event= "+ me);
				if (me.getButton()==MouseEvent.BUTTON3){

					JOptionPane.showMessageDialog(null,  "<html>Bient™t on pourra modifier les infos de la carte.. </html>", 
							"Edition",JOptionPane.INFORMATION_MESSAGE); 
					return;
				}

				else {

					TimeBoxSolo comp = (TimeBoxSolo) me.getSource();
					if (comp==null) return;
					TransferHandler handler = comp.getTransferHandler();
					if (handler==null) return;
					//handler.exportAsDrag(comp, me, TransferHandler.MOVE);
					
				
					
					if(!(comp.getCard()==null))
						view.showPossibilities(comp.getMyConstrainHandler());
						
				}

			}
			
			
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!dragging) {
                    dragging = true;
                    System.out.println("mouse draged ");
                    TimeBoxSolo comp = (TimeBoxSolo) e.getSource();
                    getTransferHandler().exportAsDrag(comp, e, TransferHandler.MOVE);
                }
            }
 
            @Override
            public void mouseReleased(MouseEvent e) {
                dragging = false;
                System.out.println("mouse released");
            }

		};
		this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
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
