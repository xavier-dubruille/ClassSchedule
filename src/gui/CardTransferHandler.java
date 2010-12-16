package gui;

import gui_schedule.OptionPanelSolo;
import gui_selection.Card_GUI;
import gui_selection.DisplayPanel;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.Card;
import model.StateFullSchedule;

public class CardTransferHandler extends TransferHandler{

	private static final long serialVersionUID = 1L;
	
	private StateFullSchedule state;
	private Card_GUI card_gui;
	private DisplayPanel dp;
	private OptionPanelSolo ops;

	/*
	 * constructor for Card_GUI
	 */
	public CardTransferHandler(StateFullSchedule state){
		this.state=state;	
	}
	
	/*
	 * constructor for TimeBox
	
	public CardTransferHandler(StateFullSchedule state, DisplayPanel dp, OptionPanelSolo ops){
		this.state=state;
		this.dp=dp;
		this.ops=ops;
	} */
	
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par card_gui
	 */
	public boolean canImport(TransferHandler.TransferSupport suport) {

		//System.out.println("canImport de cardHandler");
/*
		try{
		
//		TimeBox timeBox=(TimeBox)suport.getComponent();
//		int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
//		Card c=state.getCards().get(cardId);
//		System.out.println(ops.getSelectedRoom()+" "+ops.getSelectedSection()+" "+ops.getSelectedTeacher());
		
		} catch(Exception e){
			System.err.println("cant import..");
			e.printStackTrace();
			return false;
		}
		*/
		return true; 
	}

	public  void exportDone(Component j,Transferable t){
		System.out.println("export done v1");
	}
	public  void exportDone(TransferHandler.TransferSupport suport) {
		
		System.out.println("export done v2");
		/*
		System.out.println("card_gui= "+card_gui);
		
		try{


			//TimeBox timeBox=(TimeBox)suport.getComponent();
			//int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
			card_gui.getDisplayPanel().updateView();
				
		}catch (Exception e){
			System.err.println("Exception in CardTransferHandler.exportDone(..)");
			e.printStackTrace();
		}
		*/
		
	}

	/*
	 * Efectué par card_gui !
	 */
	public Transferable createTransferable(JComponent comp) {
		// Clear
		//System.out.println("createTransferable de cardHandler");
		return new StringSelection(""+((Card_GUI)comp).getCard().getCardId());
		/*image = null;

		    if (comp instanceof JLabel) {
		      JLabel label = (JLabel) comp;
		      Icon icon = label.getIcon();
		      if (icon instanceof ImageIcon) {
		        image = ((ImageIcon) icon).getImage();
		        return this;
		      }
		    } else if (comp instanceof AbstractButton) {
		      AbstractButton button = (AbstractButton) comp;
		      Icon icon = button.getIcon();
		      if (icon instanceof ImageIcon) {
		        image = ((ImageIcon) icon).getImage();
		        return this;
		      }
		    }
		    return null;*/
	}


	/*
	 *  cart_GUI recoit les info de timeBox !
	 */
	public boolean importData(TransferHandler.TransferSupport suport) {

		try{
			String trans=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			if (trans.equals(""))
				return false;
			
			Card_GUI card_GUI=(Card_GUI)suport.getComponent();
			
			int cardId=Integer.parseInt(trans);
			Card c=state.getCards().get(cardId);
			System.out.println("card="+c);
			c.resetTimePeriod();
			
			//update !
			card_GUI.getDisplayPanel().updateStatusCard();
			
		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de CardTransfer");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	

}
