package gui_selection;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.Card;
import model.StateFullSchedule;

public class PanelTransferHandler extends TransferHandler{

	private static final long serialVersionUID = 1L;

	private StateFullSchedule state;


	private DisplayPanel dp;

	
	public PanelTransferHandler(StateFullSchedule state, DisplayPanel dp){
		this.state=state;	
		this.dp=dp;
	}

	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

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



	public Transferable createTransferable(JComponent comp) {
		return null;
	}


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
			dp.updateStatusCard();


		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de CardTransfer: "+e);
			//e.printStackTrace();
			return false;
		}
		return true;
	}



}
