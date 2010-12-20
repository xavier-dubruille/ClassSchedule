package model;

import java.util.*;

import main.Propreties;

/**
 * 
 * This class represent the year, the Section type, and the group number of a class.
 * e.g. "2TL1, 3CM2, .."
 * 
 * @author Dubruille Xavier
 * @author Delange Jonas
 * 
 */
public class Section {
	private String name,year,sectionType, groupNumber;
	private ArrayList<Card> cards;
	private int num_of_students;
	
	

	public Section(){}

	public Section(String year,String sectionType, String groupNumber){
		cards=new ArrayList<Card>();
		this.year=year;
		this.sectionType=sectionType;
		this.groupNumber=groupNumber;
		this.name=year+sectionType+groupNumber;
		
		num_of_students=Propreties.default_number_of_students_per_group;
	}
	
	/**
	 * 
	 * @return the year of this class
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * 
	 * @param year the year to be set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 
	 * @return the type of this class (e.g. C, T, D)
	 */
	public String getSectionType() {
		return sectionType;
	}

	/**
	 * 
	 * @param sectionType the type of this class to be set
	 */
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	/**
	 * 
	 * @return the group "number" of this class (e.g. L2, M1, ..)
	 */
	public String getGroupNumber() {
		return groupNumber;
	}

	/**
	 * 
	 * @param groupNumber the group number to be set
	 */
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	/**
	 * 
	 * @param cards all the cards we want to "attach" at this section class
	 */
	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	/**
	 * 
	 * @return the number of student currently in this class/section
	 */
	public int getNum_of_students() {
		return num_of_students;
	}

	/**
	 * 
	 * @param num_of_students the number of student is this class/section
	 */
	public void setNum_of_students(int num_of_students) {
		this.num_of_students = num_of_students;
	}

	/**
	 * 
	 * @param card card to be added this section's cards
	 */
	public void addCard(Card card){
		cards.add(card);
	}

	/**
	 * 
	 * @return the name of this section
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * 
	 * @param name the name of the section to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return all the cards belonging to this section
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	@Override
	public String toString() {
		return name;
	}
}
