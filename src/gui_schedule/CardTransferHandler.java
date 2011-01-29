package gui_schedule;

import gui.GUI_properties;
import gui.Player;
import gui_selection.Card_GUI;
import gui_selection.DisplayPanel;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import main.Start;
import model.Card;
import model.StateFullSchedule;



/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class CardTransferHandler extends TransferHandler{

	private static final long serialVersionUID = 1L;

	private StateFullSchedule state;


	private DisplayPanel dp;
	/*
	private OptionPanelSolo ops;
		private Card_GUI card_gui;
	 */

	/*
	 * constructor for Card_GUI
	 */
	public CardTransferHandler(StateFullSchedule state, DisplayPanel dp){
		this.state=state;	
		this.dp=dp;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par card_gui sur ce qui le survole (donc les timeBox..)
	 */
	@Override
	public boolean canImport(TransferHandler.TransferSupport suport) {

		//System.out.println("canImport de cardHandler");
		try{
			String transferable=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			if(!transferable.startsWith("T")) return false;

		}catch(Exception e){
			return false;
		}

		return true; 
	}

	/*
	 * exextuté par card_gui, qd son carton est laché (quelque soit où)
	 * 
	 */
	@Override
	public void exportDone(JComponent c, Transferable t, int action) { 
		System.out.println("CardGui: export done");
		dp.updateStatusCard();
		dp.getMainViewSolo().updateView();
		
		Start.fSc.getMvc().constructView();
		Start.fSc.getMvc().revalidate();
		Start.fSc.getMvc().repaint();
	}



	/*
	 * Efectué par card_gui !
	 */
	@Override
	public Transferable createTransferable(JComponent comp) {
		//System.out.println("createTransferable de cardHandler");
		return new StringSelection("C"+((Card_GUI)comp).getCard().getCardId());
	}


	
	/*
	 *  card_GUI recoit les info (de timeBox) !
	 */
	@Override
	public boolean importData(TransferHandler.TransferSupport suport) {

		try{
			String trans=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);

			if (trans == null) return false;
			if (trans.equals("")) return false;

			
			
			int cardId=Integer.parseInt(trans.substring(1));
			Card c=state.getCards().get(cardId);
			//System.out.println("card="+c);
			c.resetStatusCard();

			//repaint() equivalent
			((Card_GUI)suport.getComponent()).reDraw();
			dp.updateStatusCard();


		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de CardTransfer: "+e);
			//e.printStackTrace();
			return false;
		}
		
		if (GUI_properties.playSound)
			Player.playNormal();
		
		return true;
	}



}
