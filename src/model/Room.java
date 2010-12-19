package model;

import java.util.*;

public class Room {
	private String name,type;
	private int seats;
	private boolean info; //is a computer class ?
	private ArrayList<Card> cards;
	
	public Room(){}
	
	public Room(String name, int seats, String type){
		this.name=name;
		this.seats=seats;
		this.type=type;
		if(type.equalsIgnoreCase("informatique"))info=true;
		else info=false;
		cards=new ArrayList<Card>();
				
	}
	
	public int getCapacity(){
		return seats;
	}
	
	public void addCard(Card c){
		//System.out.println("add card: "+c.getTimePeriod());
		cards.add(c);
	}
	public void removeCard(Card c){
		//System.out.println("add card: "+c.getTimePeriod());
		cards.remove(c);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isInfo() {
		return info;
	}

	public void setInfo(boolean info) {
		this.info = info;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public String toString(){
		return "room[name="+name+",type="+type+",info="+info+",seats="+seats+"]";
	}

}
