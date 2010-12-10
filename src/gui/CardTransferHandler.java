package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import gui_selection.*;
import gui_schedule.*;

public class CardTransferHandler extends TransferHandler{

	private static final DataFlavor flavors[] = { DataFlavor.imageFlavor };

	private Image image;

	public int getSourceActions(JComponent c) {
		return TransferHandler.MOVE;
	}

	public boolean canImport(TransferHandler.TransferSupport suport) {
		if(suport.isDrop())
			System.out.println("canImport: isDrop");
		else
			System.out.println("canImport: is cut/paste !");
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

	//ne devrait pas exister ?
	protected  void 	exportDone(JComponent source, Transferable data, int action) {
		System.out.println("export done");
	}

	public Transferable createTransferable(JComponent comp) {
		// Clear
		
		return new StringSelection(((Card_GUI)comp).toSrring());
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
	public boolean importData(JComponent comp, Transferable t) {
		try{
		System.out.println("importData "+(String)t.getTransferData(DataFlavor.stringFlavor)+" -- comp: "+((TimeBox)comp).toString());
		}
		catch (Exception e){
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

	// Transferable


	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true; // flavors[0].equals(flavor);
	}
}
