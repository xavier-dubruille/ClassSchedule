package gui;

import javax.swing.*;

import java.awt.*;
import java.awt.datatransfer.*;

public class CardTransferHandler extends TransferHandler {

	Image draged;
            
	public CardTransferHandler(){
		super();
		draged=new ImageIcon("images/blank.png").getImage();
	}
    public boolean canImport(TransferHandler.TransferSupport info) {
    	
    	System.out.println("canImport");
        // Check for String flavor
        if (!info.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            return false;
        }
        return true;
   }

    protected Transferable createTransferable(JComponent c) {
    	System.out.println("transferable");
    	return new StringSelection(exportString(c));
    }
    
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY_OR_MOVE;
    }
    

    
    protected void exportDone(JComponent c, Transferable data, int action) {
        cleanup(c, action == TransferHandler.MOVE);
    }

    //Bundle up the selected items in the list
    //as a single string, for export.
    protected String exportString(JComponent c) {
        // JPanel jp = (JPanel)c;
        
    	System.out.println("export String");
        return c.getClass().toString();
    }

    //Take the incoming string and wherever there is a
    //newline, break it into a separate item in the list.
    protected void importString(JComponent c, String str) {
 
        System.out.println(str);
    }
    
    //If the remove argument is true, the drop has been
    //successful and it's time to remove the selected items 
    //from the list. If the remove argument is false, it
    //was a Copy operation and the original list is left
    //intact.
    protected void cleanup(JComponent c, boolean remove) {
     }
    

}
