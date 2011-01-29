package gui_schedule;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import main.Start;
import model.Card;
import model.StateFullSchedule;
import gui.ConstrainHandler;
import gui.GUI_properties;
import gui.Player;
import gui_selection.DisplayPanel;

/**
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class TimeBoxCompareTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private StateFullSchedule state;
	
	private MainViewCompare mvc;
	//private OptionPanelCompare opc;
	private DisplayPanel dp;
	
	TimeBoxCompareTransferHandler(StateFullSchedule state, MainViewCompare mvc, OptionPanelCompare opc, DisplayPanel dp){
		this.state=state;
		this.mvc=mvc;
		//this.opc=opc;
		this.dp=dp;
	}
	
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}
	
	@Override
	public boolean canImport(TransferHandler.TransferSupport suport) {

		
		//if(ops.getSelectedTeacher()==null && ops.getSelectedRoom()==null && ops.getSelectedSection()==null)
		//	return false;

		Card card;
		TimeBox timeBox;

		try{
			timeBox=(TimeBox)suport.getComponent();
			String transferable=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			int cardId=Integer.parseInt(transferable.substring(1));
			card=state.getCards().get(cardId);
		}catch(Exception e){
			System.out.println("ne peut être importé ici.  "+e);
			return false;
		}


		ConstrainHandler consHand=timeBox.getMyConstrainHandler();
		if (consHand==null)
			return false;
		
		return consHand.canI(card);
		
		
	}
	
	@Override
	public void exportDone(JComponent c, Transferable t, int action) { 
		Start.fSc.getMvc().constructView();
		Start.fSc.getMvc().revalidate();
		Start.fSc.getMvc().repaint();
	}


	@Override
	public Transferable createTransferable(JComponent comp) {

		TimeBoxCompare tbc=((TimeBoxCompare)comp);
		if (tbc==null) return null;
		if (tbc.getCard()==null) return null;

		return new StringSelection("T"+tbc.getCard().getCardId());

	}


	@Override
	public boolean importData(TransferHandler.TransferSupport suport) {

		try{
			String trans=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			
			
			if (trans == null) return false;
			if (trans.equals("")) return false;
			int cardId=Integer.parseInt(trans.substring(1));
	
			TimeBoxCompare tbc=(TimeBoxCompare)suport.getComponent();
			Card card=state.getCards().get(cardId);
			
			//System.out.println(" (timeBoxCompareTransfer.importData) cardId="+cardId);
			
			
			System.out.println(" (timeBoxCompareTransfer.importData) card="+card);

			tbc.clear();
			
			//place the card state: time and classRoom
			if (tbc.getRoomConcerned()!=null)
				card.setTimePeriod_and_Room(tbc.getTimePeriod(),tbc.getRoomConcerned());
			else
				card.setTimePeriod_and_pickARoom(tbc.getTimePeriod());

			//update the gui timeBox
			//tbc.getView().updateView();
				//tbc.setCard(card);
				
		//	mvc.constructView();
			//	mvc.revalidate();
			//	mvc.repaint();
			
				dp.updateStatusCard();	
			
			
		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de TimeBoxCompareTransfer: ");
			e.printStackTrace();
			return false;
		}
		if (GUI_properties.playSound)
			Player.playNormal();
		
		return true;
	}
	
	
	/**
	 * The purpose of this, is to allow the DnD only if it has been triggered by a "left click"
	 */
	public void exportAsDrag(JComponent comp, InputEvent e, int action) {
		
		if ( e instanceof MouseEvent && ((MouseEvent)e).getButton() == MouseEvent.BUTTON1 )
			super.exportAsDrag(comp,e, action);
	}

	

}
