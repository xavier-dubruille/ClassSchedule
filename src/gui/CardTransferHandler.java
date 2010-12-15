package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import gui_selection.*;
import gui_schedule.*;
import model.*;

public class CardTransferHandler extends TransferHandler{

	private StateFullSchedule state;
	private Card_GUI card_gui;
	private DisplayPanel dp;
	private OptionPanelSolo ops;

	/*
	 * constructor for Card_GUI
	 */
	public CardTransferHandler(){	
	}
	
	/*
	 * constructor for TimeBox
	 */
	public CardTransferHandler(StateFullSchedule state, DisplayPanel dp, OptionPanelSolo ops){
		this.state=state;
		this.dp=dp;
		this.ops=ops;
	}
	
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	/*
	 * Efectué par card_gui
	 */
	public boolean canImport(TransferHandler.TransferSupport suport) {
	
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
	 * Efectué par card_gui ?
	 */
	public Transferable createTransferable(JComponent comp) {
		// Clear
		
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
	 * Efectué par time_box 
	 */
	public boolean importData(TransferHandler.TransferSupport suport) {
		
		
		//System.out.println(ops.getSelectedRoom()+" "+ops.getSelectedSection()+" "+ops.getSelectedTeacher());
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
