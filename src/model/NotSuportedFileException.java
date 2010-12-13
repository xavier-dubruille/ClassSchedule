package model;

import java.io.IOException;

public class NotSuportedFileException extends IOException{

	public NotSuportedFileException(){
		super();
	}
	
	public NotSuportedFileException(String msg){
		super(msg);
	}
}
