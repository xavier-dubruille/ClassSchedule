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
	 * Efectué par TimeBox sur la gui_card qu'il veut updater
	 */
	public boolean canImport(TransferHandler.TransferSupport suport) {
		//System.out.println("can import de timeboxhandler");
		return true; 
	}

	public void exportDone(JComponent c, Transferable t, int action) { 
		((TimeBox)c).getView().updateView();
	}
	
	/*
	 * Efectué par timeBox
	 */
	public Transferable createTransferable(JComponent comp) {
		//System.out.println("createTransferable de timeBoxHandler");
		
		OptionPanelSolo ops;
		TimeBox tb=((TimeBox)comp);
		int timePeriod=tb.getTimePeriod();
		ops=tb.getOptionPanelSolo();
		String cardId="";
		if(ops.getSelectedRoom()!=null)
			for(Card c:ops.getSelectedRoom().getCards())
				if(c.getTimePeriod()==timePeriod)
					cardId=""+c.getCardId();
		
		if(ops.getSelectedTeacher()!=null)
			for(Card c:ops.getSelectedTeacher().getCard())
				if(c.getTimePeriod()==timePeriod)
					cardId=""+c.getCardId();
		
		if(ops.getSelectedSection()!=null)
			for(Card c:ops.getSelectedSection().getCards())
				if(c.getTimePeriod()==timePeriod)
					cardId=""+c.getCardId();
		
		return new StringSelection(cardId);
		

	}


	/*
	 * On recoit les info de card_GUI !
	 */
	public boolean importData(TransferHandler.TransferSupport suport) {
		
		//System.out.println("importData de timeBoxHandler");
		try{
			
			
			TimeBox timeBox=(TimeBox)suport.getComponent();
			int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
			Card c=state.getCards().get(cardId);
			
			//let's check if we can import (i know, it's not supposed to be here)
			if(!checkCanImport(c,timeBox))
				return false;
			
			//place the card state: time and classRoom
			c.setTimePeriod_and_pickARoom(timeBox.getTimePeriod());
			
			//update the gui timeBox
			timeBox.getView().updateView();
			
			//update the selection view
			dp.updateStatusCard();
			
		//System.out.println("importData "+(String)t.getTransferData(DataFlavor.stringFlavor)+" -- comp: "+((TimeBox)comp).getTimePeriod());
			
			
		}
		catch (Exception e){
			System.err.println("exception dans importData(..)");
			return false;
		}
		return true;
		/*
			  if (comp instanceof JLabel) {
		      JLabel label = (JLabel) comp;
		      if (t.isDataFlavorSupported(flavors[0])) {
		        try {
		          image = (Image) t.getTransferData(flavors[0]);
		          ImageIcon icon = new ImageIcon(image);
		          label.setIcon(icon);
		          return true;
		        } catch (UnsupportedFlavorException ignored) {
		        } catch (IOException ignored) {
		        }
		      }
		    } else if (comp instanceof AbstractButton) {
		      AbstractButton button = (AbstractButton) comp;
		      if (t.isDataFlavorSupported(flavors[0])) {
		        try {
		          image = (Image) t.getTransferData(flavors[0]);
		          ImageIcon icon = new ImageIcon(image);
		          button.setIcon(icon);
		          return true;
		        } catch (UnsupportedFlavorException ignored) {
		        } catch (IOException ignored) {
		        }
		      }
		    }
		    return false;
		 */
	}

	/*
	 * Efectué par time_box 
	 */
	private boolean checkCanImport(Card card,TimeBox timeBox){
		
		for(Card c:card.getTeacher().getCard())
			if(c.getTimePeriod()==timeBox.getTimePeriod())
				return false;
		
		if(ops.getSelectedTeacher()!=null && ops.getSelectedTeacher()!=card.getTeacher())
			return false;
		if(ops.getSelectedSection()!=null && !card.getCard_sections().contains(ops.getSelectedSection())){
		/*
			System.out.println("----------");
			System.out.println("faux -- selection");
			if(ops.getSelectedSection()!=null){
				System.out.println("slection affiché: "+ops.getSelectedSection());
				System.out.println("slections contenu dans la cartes: "+c.getCard_sections());
			}
			System.out.println("----------");
			*/
			return false;
		}
		if(ops.getSelectedRoom()!=null && card.getSeatsToProvide()<ops.getSelectedRoom().getCapacity()) //faut aussi s'occuper des sall info
			return false;
		
		
		return true;
	}
}
