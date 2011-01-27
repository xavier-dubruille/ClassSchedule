package model;

import java.io.Serializable;
import java.util.*;

import main.Main_properties;

/**
 * 
 * This class represent a class room: the type, the number of seats available, ..
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 * 
 */
public class Room implements Serializable{
	
	static private final long serialVersionUID = Main_properties.serialVersionUID;
	
	private String name,type;
	private int seats;
	private boolean info; //if it's a computer class
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
	
	/**
	 * 
	 * @return the number of seats available
	 */
	public int getCapacity(){
		return seats;
	}
	
	/**
	 * return if the room is busy at that time
	 * Careful, if the card is already in the room.. 
	 * 
	 * @param timePeriod
	 * @return if it's busy
	 */
	public boolean isBusy(int timePeriod){

		for(Card c:cards)
		{
			System.out.println("is busy(): card="+c+", ist timePeriod="+c.getTimePeriod()+". et la tp recherché est :"+timePeriod);
			if(c.getTimePeriod()==timePeriod){
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Add a card in this Class Room
	 * @param c the card to be added
	 */
	public void addCard(Card c){
		//System.out.println("add card: "+c.getTimePeriod());
		cards.add(c);
	}
	
	/**
	 *  Remove a card form this Class Room
	 * @param c the card to be removed
	 */
	public void removeCard(Card c){
		//System.out.println("add card: "+c.getTimePeriod());
		cards.remove(c);
	}
	
	/**
	 * 
	 * @return the name of the class room
	 */
	public String getName() {
		return name;
	}

	
	/**
	 * set the name of the class room
	 * @param name the new name for this class room
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return the type of this class room
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set the class room type
	 * @param type the type to be set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return if this class room is a Computer room
	 */
	public boolean isInfo() {
		return info;
	}

	/**
	 * this will set if the class room is a computer room or not
	 * @param info if it's a computeur room
	 */
	public void setInfo(boolean info) {
		this.info = info;
	}

	/**
	 * 
	 * @return all the card taking place in this Class room
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * set the number of seats in this class room
	 * @param seats the number of seats
	 */
	public void setSeats(int seats) {
		this.seats = seats;
	}

	@Override
	public String toString(){
		return "room[name="+name+",type="+type+",info="+info+",seats="+seats+"]";
	}

	public boolean canAcceptCard(Card card) {

		if (card.isInfo() && !isInfo() )
			return false;
		if (card.getSeatsToProvide()>this.getCapacity())
			return false;
		return true;
	}

}
