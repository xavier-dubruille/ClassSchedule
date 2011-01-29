package gui;

import gui_schedule.TimeBox;
import gui_schedule.TimeBoxCompare;
import gui_schedule.TimeBoxSolo;
import model.Card;
import model.Room;
import model.Section;
import model.Teacher;

public class ConstrainHandler {

	private int type;
	// 0 is a Gui_card
	// 1 is a TimeBox
	// 2 is a TimeBoxCompare

	private Card card;

	private int levelOfImpossibility;
	// 0 is really impossible
	// 1 is limit, but possible
	// 2 is ok

	private int reasonOfImpossibility;
	// 0 is teacher reason
	// 1 is section (group) reason
	// 2 is locas reason

	private Section selectedSection;
	private Teacher selectedTeacher;
	private Room selectedRoom;
	private TimeBox timeBox;


	public ConstrainHandler(int type, Card card) {
		this.type=type;
		this.card=card;
	}


	public ConstrainHandler(int type, TimeBox timeBox) {
		this.type=type;
		this.timeBox=timeBox;
	}

	public boolean canI(TimeBoxSolo t) {
		this.timeBox=t;
		
		return canI();
	}

	
	public boolean canI(TimeBoxCompare timeBoxComp){
		this.timeBox=timeBoxComp;
		selectedSection=timeBoxComp.getSectionConcerned();
		selectedTeacher=timeBoxComp.getTeacherConcerned();
		selectedRoom=timeBoxComp.getRoomConcerned();
		
		return canI();

	}

	public boolean canI(Card card) {
		this.card=card;
		return canI();
	}

	private boolean canI(){


		int currentTimePeriod=timeBox.getTimePeriod();
		int[] teacherPreferedMoments =card.getTeacher().getPreferedTimeSlides();
		levelOfImpossibility=2;


		// step 0: let's check if the card can be placed at all on the selected view	
		if(!(selectedTeacher==null) && card.getTeacher()!=selectedTeacher){
			reasonOfImpossibility=0;
			levelOfImpossibility=0;
			return false;
		}

		if(!(selectedSection==null) && !(card.getCard_sections().contains(selectedSection)) ) {
			reasonOfImpossibility=1;
			levelOfImpossibility=0;
			return false;
		}

		if(selectedRoom!=null && card.getSeatsToProvide()>selectedRoom.getCapacity()) {
			reasonOfImpossibility=2;
			levelOfImpossibility=0;
			return false;
		}




		// step 1: set the teacher (from the card) 's preferences	
		if (teacherPreferedMoments[currentTimePeriod]!=0){
			if (teacherPreferedMoments[currentTimePeriod]<=5){
				reasonOfImpossibility=0;
				levelOfImpossibility=1;
				// no return, because more important reasons may arrive
			}
			else{
				reasonOfImpossibility=0;
				levelOfImpossibility=0;
				return false;

			}
		}


		// step 2: let's check if the teacher is not giving already some courses
		for (Card c :card.getTeacher().getCards() )
		{
			//System.out.println("showPossiblities() --> "+card.getTimePeriod()+" ("+currentTimePeriod+")");
			if (c.getTimePeriod()==currentTimePeriod){
				reasonOfImpossibility=0;
				levelOfImpossibility=0;
				return false;
			}
		}



		// step 3: let's see if one of the section is not busy
		for (Section section: card.getCard_sections())
			for (Card card: section.getCards()){
				if (card.getTimePeriod()==currentTimePeriod){
					reasonOfImpossibility=1;
					levelOfImpossibility=0;
					return false;
				}
			}


		// step 4: let's see if the possible room are not busy ..
		//if (c.getTimePeriod()==0) {
		int nbRooms=card.findAllRoom(currentTimePeriod).size();
		if (nbRooms==0){
			reasonOfImpossibility=2;
			levelOfImpossibility=0;
			return false;
		}
		else if(nbRooms <2 ){
			reasonOfImpossibility=2;
			levelOfImpossibility=1;
		}
		//}


		if (levelOfImpossibility<2)
			return false;
		else
			return true;
	}




	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * @return the levelOfImpossibility
	 */
	public int getLevelOfImpossibility() {
		return levelOfImpossibility;
	}

	/**
	 * @param levelOfImpossibility the levelOfImpossibility to set
	 */
	public void setLevelOfImpossibility(int levelOfImpossibility) {
		this.levelOfImpossibility = levelOfImpossibility;
	}

	/**
	 * @return the reasonOfImpossibility
	 */
	public int getReasonOfImpossibility() {
		return reasonOfImpossibility;
	}

	/**
	 * @param reasonOfImpossibility the reasonOfImpossibility to set
	 */
	public void setReasonOfImpossibility(int reasonOfImpossibility) {
		this.reasonOfImpossibility = reasonOfImpossibility;
	}


	public void setViewParameters(Teacher selectedTeacher,
			Section selectedSection, Room selectedRoom) {

		this.selectedTeacher=selectedTeacher;
		this.selectedSection=selectedSection;
		this.selectedRoom=selectedRoom;


	}


	



}
