package model;

import java.util.ArrayList;
import java.util.Map;

import main.Propreties;

public class Card {


	// private String name;
	private Teacher teacher;
	private Lesson lesson;
	private int happy; 
	private int timePeriod; // representing the day and the period
	private int cardId;
	private Map<String,Room> all_rooms;
	private Room classRoom;
	private String mod;
	private Map<String,Section> all_sections;
	private ArrayList<Section> card_sections;
	private StateFullSchedule state;


	public Card(Lesson lesson, Teacher teacher,int cardId,Section s,StateFullSchedule state){
		this.all_rooms=state.getClassRoom();
		this.all_sections=state.getSections();
		this.state=state;

		this.lesson=lesson;
		this.teacher=teacher;
		this.cardId=cardId;

		happy=0;
		timePeriod=0;
		card_sections=new ArrayList<Section>();
		card_sections.add(s);

	}
	public int getCardId(){
		return cardId;
	}

	public void addSection(Section s){
		card_sections.add(s);
	}


	public void resetStatusCard() {
		timePeriod=0;
		classRoom=null;
	}
	
	public boolean setTimePeriod_and_pickARoom(int timePeriod){
		this.timePeriod=timePeriod;

		Room room = pick_best_room();
		if (room==null){
			happy=-10;
			return false;
		}

		// que faire si aucun local n'a �t� trouv�? placer dans un local four-tout(un par categorie)? rajouter un local (qui ne contiendrait que ce carton)?
		// dans pas de local ? dans le dernier, mais avec un status sp�cial ? et pour la gui?


		setTimePeriod_and_Room(timePeriod,room);


		return true;
	}

	public void setTimePeriod_and_Room(int time_nice_format, Room selectedRoom) {
		timePeriod=time_nice_format;

		classRoom=selectedRoom;
		classRoom.addCard(this);
		
		// update the sections 
		for(Section s: card_sections)
			s.addCard(this);
		
		//System.out.println("Card.setTimePeriodAndRoom() sections apr�s :"+card_sections);
		
	}
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


	public int getSeatsToProvide(){
		int seats_to_provide=0;

		for(Section s: card_sections)
			seats_to_provide+=s.getNum_of_students();
		return seats_to_provide;
	}

	public StateFullSchedule getState() {
		return state;
	}
	public int getTimePeriod(){
		return timePeriod;
	}

	public void update(){
		System.out.println("attention la methode Card.update() est vide! ");
	}


	public String getHtmlRepresentation(){
		return "<html>"+lesson.name+"<html>";
	}



	public Room getClassRoom() {
		return classRoom;
	}
	public Teacher getTeacher(){
		return teacher;
	}

	public Lesson getLesson() {
		return lesson;
	}
	public ArrayList<Section> getCard_sections() {
		return card_sections;
	}
	@Override
	public String toString(){
		return lesson.name+" "+teacher.lastName;
	}


}
