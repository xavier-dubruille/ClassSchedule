package model;

import java.io.*;
public class NoFileException extends IOException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoFileException(){
		super();
	}
	
	public NoFileException(String msg){
		super(msg);
	}

}
