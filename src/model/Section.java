package model;

import java.util.*;

import main.Propreties;
public class Section {
	private String name;
	private ArrayList<Card> cards;
	private int num_of_students;
	

	public Section(){}

	public Section(String name){
		cards=new ArrayList<Card>();
		this.name=name;
		
		num_of_students=Propreties.default_number_of_students_per_group;
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
