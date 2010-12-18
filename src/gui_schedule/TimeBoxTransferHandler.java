package gui_schedule;

import gui_selection.Card_GUI;
import gui_selection.DisplayPanel;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import model.Card;
import model.StateFullSchedule;

public class TimeBoxTransferHandler extends TransferHandler {

	private static final long serialVersionUID = 1L;
	private StateFullSchedule state;
	private Card_GUI card_gui;
	private DisplayPanel dp;
	private OptionPanelSolo ops;



	/*
	 * constructor for TimeBox
	 */
	public TimeBoxTransferHandler(StateFullSchedule state, DisplayPanel dp, OptionPanelSolo ops){
		this.state=state;
		this.dp=dp;
		this.ops=ops;
	}

	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par TimeBox qd qqch le survole (une timebox ou une gui_card)
	 */
	public boolean canImport(TransferHandler.TransferSupport suport) {
		//System.out.println("can import de timeboxhandler");
		if(ops.getSelectedTeacher()==null && ops.getSelectedRoom()==null && ops.getSelectedSection()==null)
			return false;

		try{
			TimeBoxSolo timeBoxSolo=(TimeBoxSolo)suport.getComponent();
			int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
			Card c=state.getCards().get(cardId);
			
			
			/**********************************
			

			for(Card c:card.getTeacher().getCard())
				if(c.getTimePeriod()==timeBox.getTimePeriod())
					return false;

			if(ops.getSelectedTeacher()!=null && ops.getSelectedTeacher()!=card.getTeacher())
				return false;
			if(ops.getSelectedSection()!=null && !card.getCard_sections().contains(ops.getSelectedSection())){
				/
				System.out.println("----------");
				System.out.println("faux -- selection");
				if(ops.getSelectedSection()!=null){
					System.out.println("slection affiché: "+ops.getSelectedSection());
					System.out.println("slections contenu dans la cartes: "+c.getCard_sections());
				}
				System.out.println("----------");
				 /
				return false;
			}
			if(ops.getSelectedRoom()!=null && card.getSeatsToProvide()<ops.getSelectedRoom().getCapacity()) //faut aussi s'occuper des sall info
				return false;


			return true;
		
			*********************************/
			
			
		}catch(Exception e){
			System.out.println("ne peut être importé ici.  "+e);
			return false;
		}
		return true; 
	}

	/*
	 * Effectué par timebox une fois que le carton a été laché
	 */
	public void exportDone(JComponent c, Transferable t, int action) { 
		((TimeBoxSolo)c).getView().updateView();
	}

	/*
	 * Efectué par timeBox
	 */
	public Transferable createTransferable(JComponent comp) {
		//System.out.println("createTransferable de timeBoxHandler");

		TimeBoxSolo tbs=((TimeBoxSolo)comp);
		if (tbs==null) return null;
		if (tbs.getCard()==null) return null;

		return new StringSelection(""+tbs.getCard().getCardId());

	}


	/*
	 * TimeBox recoi les info (d'une gui_card ou d'une autre timeBox..)
	 */
	public boolean importData(TransferHandler.TransferSupport suport) {

		//System.out.println("importData de timeBoxHandler");
		if(!canImport(suport)) return false;

		try{
			TimeBoxSolo timeBoxSolo=(TimeBoxSolo)suport.getComponent();
			int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
			Card c=state.getCards().get(cardId);

			//place the card state: time and classRoom
			if (ops.getSelectedRoom()!=null)
				c.setTimePeriod_and_Room(timeBoxSolo.getTimePeriod(),ops.getSelectedRoom());
			else
				c.setTimePeriod_and_pickARoom(timeBoxSolo.getTimePeriod());

			//update the gui timeBox
			timeBoxSolo.getView().updateView();

			//update the selection view -- may not should be here..
			dp.updateStatusCard();
			
			//System.out.println("importData de timeBoxHandler. carton:"+c+". Room:"+c.getClassRoom()+". timePeriod:"+c.getTimePeriod());

		}
		catch (Exception e){
			System.err.println("exception dans importData(..) de timeBox: "+e);
			return false;
		}
		return true;
	}


}
