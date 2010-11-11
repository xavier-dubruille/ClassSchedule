package model;

import java.util.HashMap;
import java.util.Map;

public class Teacher {
	String name;
	Map<Integer,Room> preferedClassRoom;
	// we have to had a "prefered schedule"
	
	public Teacher(String name){
		preferedClassRoom =new HashMap<Integer, Room>();
		this.name=name;
	}
	
	public void putLocal(int priority,Room l){
		preferedClassRoom.put(priority, l);
	}
	
}
