package model;

import java.util.ArrayList;
import java.util.Map;

import main.Propreties;

/**
 * 
 * This Class represent the internal data of a Card
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 *
 */
public class Card {


	// private String name;
	private Teacher teacher;
	private Lesson lesson;
//	private int happy; 
	private int timePeriod; // representing the day and the period
	private int cardId;
	private Map<String,Room> all_rooms;
	private Room classRoom;
	private String mod;
	private boolean info;
	//private Map<String,Section> all_sections;
	private ArrayList<Section> card_sections;
	private StateFullSchedule state;


	/**
	 * 
	 * The only constructor for a card.  It will build a card with all it'll need. 
	 *
	 * @param lesson	the Lesson represented on the card
	 * @param teacher	the Teacher represented on the card
	 * @param cardId	an unique Id for the card
	 * @param s 	Section represented on the card
	 * @param state	   the main state, where all the model data can be accessed
	 * @param info		 will tell if it's a computer class or not
	 * @param mod 	the type of card (classe, groupe, ect)
	 */
	public Card(Lesson lesson, Teacher teacher,int cardId,Section s,StateFullSchedule state, String info, String mod){
		this.all_rooms=state.getClassRoom();
		//this.all_sections=state.getSections();
		this.state=state;
		this.mod=mod;
		this.info=info.equalsIgnoreCase("1") ? true :false ;


		this.lesson=lesson;
		this.teacher=teacher;
		this.cardId=cardId;


		//if(this.info) System.out.println("carton "+this.lesson.name+", "+cardId+" doit être en salle info");

		//happy=0;
		timePeriod=0;
		card_sections=new ArrayList<Section>();
		card_sections.add(s);

	}

	/**
	 *  will reset the card, i.e. will put it back in the selection panel
	 */
	public void resetStatusCard() {
		timePeriod=0;
		classRoom=null;
	}

	/**
	 * 
	 * Set the card TimePeriod and pick a convinient classRoom
	 * In other words, this method place the card.
	 * 
	 * @param timePeriod	the time period to set the card
	 * @return		if the method went well, if the card is placed
	 */
	public boolean setTimePeriod_and_pickARoom(int timePeriod){
		this.timePeriod=timePeriod;

		for(Room r :all_rooms.values())
			r.removeCard(this);

		Room room = pick_best_room();
		if (room==null){
		//	happy=-10;
			return false;
		}

		// que faire si aucun local n'a été trouvé? placer dans un local four-tout(un par categorie)? rajouter un local (qui ne contiendrait que ce carton)?
		// dans pas de local ? dans le dernier, mais avec un status spécial ? et pour la gui?


		setTimePeriod_and_Room(timePeriod,room);


		return true;
	}

	/**
	 * 
	 * will also place the card, but with a specified classRoom
	 * 
	 * @param timePeriod	the timePeriod to place the card
	 * @param selectedRoom	the room to place the card at
	 */
	public void setTimePeriod_and_Room(int timePeriod, Room selectedRoom) {
		this.timePeriod=timePeriod;

		for(Room r :all_rooms.values())
			r.removeCard(this);

		classRoom=selectedRoom;
		classRoom.addCard(this);

		// update the sections 
		for(Section s: card_sections)
			s.addCard(this);

		System.out.println("Card.setTimePeriodAndRoom(): card="+this+", classRoom="+classRoom);

	}

	/**
	 * 
	 * Depending on the type of room, it will/or not find a room that fit.
	 * 
	 * @return the room found or null otherwise
	 */
	private Room pick_best_room(){

		//System.out.println("sections toString: "+card_sections);

		int seats_to_provide=getSeatsToProvide();
		//System.out.println("pick best room. seats-to-provide: "+ seats_to_provide);

		/*
		 * let's find a local that: has right capacity AND does not already contain a card at the same time
		 */
		for(Room r:all_rooms.values()){
			if(r.getCapacity()>seats_to_provide && r.getCapacity()<(seats_to_provide+Propreties.max_empty_seat)){
				// ok, it's the right capacity

				// let's see if it's busy at that time
				boolean busy=false;

				for(Card c:r.getCards())
					if(c.timePeriod==this.timePeriod){
						busy=true;
					}

				if(!busy){
					//System.out.println("on peut placer dans le local "+r.getName());

					return r;
				}
			}
		}

		return null;
	}

	
	/**
	 * 
	 * Calculate the number of seats this card will need
	 * 
	 * @return the number of seats
	 */
	public int getSeatsToProvide(){
		int seats_to_provide=0;

		for(Section s: card_sections)
			seats_to_provide+=s.getNum_of_students();
		return seats_to_provide;
	}

	/**
	 * 
	 * @return the state
	 */
	public StateFullSchedule getState() {
		return state;
	}
	
	/**
	 * 
	 * @return the time period (i.e. day and period) of the card
	 */
	public int getTimePeriod(){
		return timePeriod;
	}

	/**
	 * not used yet.
	 * plan to update the card in specials circonstances
	 */
	public void update(){
		System.out.println("carefull, you're calling an empty method -->card.update() ");
	}


	/**
	 * 
	 * @return a html representation of the card
	 */
	public String getHtmlRepresentation(){
		return "<html>"+lesson.name+"<html>";
	}


	/**
	 * 
	 * @return the mode of the card (classe, groupe, ..)
	 */
	public String getMod() {
		return mod;
	}
	
	/**
	 * 
	 * @return if it's a computer class room
	 */
	public boolean isInfo() {
		return info;
	}
	
	/**
	 * 
	 * @return the room in witch the card course take place
	 */
	public Room getClassRoom() {
		return classRoom;
	}
	
	/**
	 * 
	 * @return the teacher giving this card course
	 */
	public Teacher getTeacher(){
		return teacher;
	}

	/**
	 * 
	 * @return the course of the card
	 */
	public Lesson getLesson() {
		return lesson;
	}
	
	/**
	 * 
	 * @return all the section that are attached to that card
	 */
	public ArrayList<Section> getCard_sections() {
		return card_sections;
	}

	/**
	 * 
	 * @return the unique Card id
	 */
	public int getCardId(){
		return cardId;
	}

	/**
	 * 
	 * @param s	the Section to attached at this card
	 */
	public void addSection(Section s){
		card_sections.add(s);
	}


	@Override
	public String toString(){
		return lesson.name+" "+teacher.lastName;
	}


}
