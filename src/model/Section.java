package model;

import java.util.*;
public class Section {
	private String name;
	private ArrayList<Card> cards;
	

	public Section(){
		cards=new ArrayList<Card>();
	}
	
	public Section(String name, Card card){
		this();
		this.name=name;
		cards.add(card);
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
		return "Section [name=" + name + ", cards=" + cards + "]";
	}
}
