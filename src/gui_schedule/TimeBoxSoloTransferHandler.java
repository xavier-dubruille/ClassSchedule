package gui_schedule;
import gui.ConstrainHandler;
import gui_selection.DisplayPanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import main.Start;
import model.*;

public class TimeBoxSoloTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;
	
	private StateFullSchedule state;
	
	//private DisplayPanel dp;
	private OptionPanelSolo ops;



	/*
	 * constructor for TimeBox
	 */
	public TimeBoxSoloTransferHandler(StateFullSchedule state, DisplayPanel dp, OptionPanelSolo ops){
		this.state=state;
	//	this.dp=dp;
		this.ops=ops;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par TimeBox qd qqch le survole (une timebox ou une gui_card)
	 */
	@Override
	public boolean canImport(TransferHandler.TransferSupport suport) {
		
		
		//System.out.println("can import de timeboxhandler");
		if(ops.getSelectedTeacher()==null && ops.getSelectedRoom()==null && ops.getSelectedSection()==null)
			return false;

		Card card;
		TimeBoxSolo timeBoxSolo;

		try{
			timeBoxSolo=(TimeBoxSolo)suport.getComponent();
			String transferable=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			int cardId=Integer.parseInt(transferable.substring(1));
			card=state.getCards().get(cardId);
		}catch(Exception e){
			System.out.println("ne peut être importé ici.  "+e);
			return false;
		}


		ConstrainHandler consHand=timeBoxSolo.getMyConstrainHandler();
		if (consHand==null)
			return false;
		
		consHand.setViewParameters(ops.getSelectedTeacher(), ops.getSelectedSection(), ops.getSelectedRoom());
		
		return consHand.canI(card);
	}

	
	/*
	 * Effectué par timebox une fois que le carton a été laché
	 */
	@Override
	public void exportDone(JComponent c, Transferable t, int action) { 
		((TimeBoxSolo)c).getView().updateView();

	}

	/*
	 * Efectué par timeBox
	 */
	@Override
	public Transferable createTransferable(JComponent comp) {
		//System.out.println("createTransferable de timeBoxHandler");

		TimeBoxSolo tbs=((TimeBoxSolo)comp);
		if (tbs==null) return null;
		if (tbs.getCard()==null) return null;

		return new StringSelection("T"+tbs.getCard().getCardId());

	}


	/*
	 * TimeBox recoi les info (d'une gui_card ou d'une autre timeBox..)
	 */
	@Override
	public boolean importData(TransferHandler.TransferSupport suport) {

		System.out.println("import");
		TimeBoxSolo timeBoxSolo=(TimeBoxSolo)suport.getComponent();
		timeBoxSolo.getView().updateView();
		
		
		//System.out.println("importData de timeBoxHandler");
		if(!canImport(suport)) return false;

		try{
			timeBoxSolo=(TimeBoxSolo)suport.getComponent();
			String transferable=(String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor);
			int cardId=Integer.parseInt(transferable.substring(1));
			Card card=state.getCards().get(cardId);

			//place the card state: time and classRoom
			if (ops.getSelectedRoom()!=null)
				card.setTimePeriod_and_Room(timeBoxSolo.getTimePeriod(),ops.getSelectedRoom());
			else
				card.setTimePeriod_and_pickARoom(timeBoxSolo.getTimePeriod());

			//update the gui timeBox
			timeBoxSolo.getView().updateView();

			//update the selection view -- may not should be here..
			//dp.updateStatusCard();

			//System.out.println("importData de timeBoxHandler. carton:"+c+". Room:"+c.getClassRoom()+". timePeriod:"+c.getTimePeriod());

		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de timeBox: "+e);
			return false;
		}
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
