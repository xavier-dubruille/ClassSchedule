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

	/*
	 * constructor for Card_GUI
	 */
	public CardTransferHandler(){	
	}
	
	/*
	 * constructor for TimeBox
	 */
	public CardTransferHandler(StateFullSchedule state, DisplayPanel dp){
		this.state=state;
		this.dp=dp;
	}
	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	public boolean canImport(TransferHandler.TransferSupport suport) {
		/*
		if(suport.isDrop())
			System.out.println("canImport: isDrop");
		else
			System.out.println("canImport: is cut/paste !");
		*/
		return true; 
		/*
		    if (!(comp instanceof JLabel) && !(comp instanceof AbstractButton)) {
		      return false;
		    }
		    for (int i = 0, n = flavor.length; i < n; i++) {
		      for (int j = 0, m = flavors.length; j < m; j++) {
		        if (flavor[i].equals(flavors[j])) {
		          return true;
		        }
		      }
		    }
		    return false;*/
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

	// juste qq tests..
	public static Action getCopyAction(){
		System.out.println("copy action");
		return null;
	}
	public static Action getCutAction(){
		System.out.println("cut action");
		return null;
	}
	public static Action getPasteAction() {
		System.out.println("paste action");
		return null;
	}
	public boolean importData(TransferHandler.TransferSupport suport) {
		try{
			
			
			TimeBox timeBox=(TimeBox)suport.getComponent();
			int cardId=Integer.parseInt((String)suport.getTransferable().getTransferData(DataFlavor.stringFlavor));
			Card c=state.getCards().get(cardId);
			
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


}
