package model;

public class Room {
	private String name,type;
	private int seats;
	private boolean info;
	
	public Room(String name, int seats, String type){
		this.name=name;
		this.seats=seats;
		this.type=type;
		if(type.equalsIgnoreCase("informatique"))info=true;
		else info=false;
				
	}
	
	public String toString(){
		return name+" "+type+" "+info+" "+seats;
	}

}
