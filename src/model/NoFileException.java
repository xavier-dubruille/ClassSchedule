package model;

import java.io.*;
public class NoFileException extends IOException{
	public NoFileException(){
		super();
	}
	
	public NoFileException(String msg){
		super(msg);
	}

}
