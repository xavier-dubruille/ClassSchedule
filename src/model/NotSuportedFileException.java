package model;

import java.io.IOException;

public class NotSuportedFileException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotSuportedFileException(){
		super();
	}
	
	public NotSuportedFileException(String msg){
		super(msg);
	}
}
