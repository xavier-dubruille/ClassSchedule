package model;

import java.util.*;

import main.Propreties;
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
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public int getNum_of_students() {
		return num_of_students;
	}

	public void setNum_of_students(int num_of_students) {
		this.num_of_students = num_of_students;
	}

	public void addCard(Card card){
		cards.add(card);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	@Override
	public String toString() {
		return name;
	}
}
