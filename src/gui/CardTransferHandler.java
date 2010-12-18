package gui;

import gui_schedule.OptionPanelSolo;
import gui_schedule.TimeBox;
import gui_schedule.TimeBoxSolo;
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
	/*
	private Card_GUI card_gui;
	private DisplayPanel dp;
	private OptionPanelSolo ops;
*/

	/*
	 * constructor for Card_GUI
	 */
	public CardTransferHandler(StateFullSchedule state){
		this.state=state;	
	}
	
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par card_gui sur ce qui le survole (donc les timeBox..)
	 */
	public boolean canImport(TransferHandler.TransferSupport suport) {

		//System.out.println("canImport de cardHandler");
		//suport.getTransferable().toString()
		
		// il faudrait verifier ici que c'est bien une timeBox qui veut rentrer..
		
		return true; 
	}
	
	/*
	 * exextuté par card_gui, qd son carton est laché (quelque soit où)
	 * 
	 */
	public void exportDone(JComponent c, Transferable t, int action) { 
		// faudrait updater le display panel..
	}

	
	/*
	 * Efectué par card_gui !
	 */
	public Transferable createTransferable(JComponent comp) {
		//System.out.println("createTransferable de cardHandler");
		return new StringSelection(""+((Card_GUI)comp).getCard().getCardId());
	}


	/*
	 *  card_GUI recoit les info (de timeBox) !
	 */
	public boolean importData(TransferHandler.TransferSupport suport) {

		try{
			String trans=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			
			if (trans == null) return false;
			if (trans.equals("")) return false;
			
			
			
			int cardId=Integer.parseInt(trans);
			Card c=state.getCards().get(cardId);
			//System.out.println("card="+c);
			c.resetStatusCard();
			
			//repaint() equivalent
			((Card_GUI)suport.getComponent()).reDraw();
			
			
		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de CardTransfer: "+e);
			//e.printStackTrace();
			return false;
		}
		return true;
	}

	

}
