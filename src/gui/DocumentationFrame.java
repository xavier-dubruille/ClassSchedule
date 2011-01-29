package gui;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class DocumentationFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String helpURL;

	public DocumentationFrame(){
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		
		helpURL="/documentation.html";

		//getClass().getResource("/cross_30.png")
		try {
			editorPane.setPage(getClass().getResource(helpURL));
		} catch (IOException e) {
			System.err.println("Attempted to read a bad URL: " + helpURL);
		}
		

		//Put the editor pane in a scroll pane.
		JScrollPane editorScrollPane = new JScrollPane(editorPane);
		editorScrollPane.setVerticalScrollBarPolicy(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		editorScrollPane.setPreferredSize(new Dimension(560, 645));
		
		
		this.setSize(new Dimension(560, 645));
		//this.setMinimumSize(new Dimension(650, 125));
		this.setLocation(466, 60);
		
		this.getContentPane().add(editorScrollPane);
		
		this.pack();
		this.setVisible(true);
	}
}
