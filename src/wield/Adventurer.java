package wield;

import java.awt.Color;
import java.awt.Graphics;


public class Adventurer extends Actor{
	
	private int health;
	private int attack;
	private Color color;
	
	public Adventurer(String name, String imageURI, int health, int attack, Color color){
		super(name, imageURI);
		this.health = health;
		this.attack = attack;
		this.color = color;
	}
	
	@Override
	public Adventurer getAdventurer(){
		return this;
	}

	public Color getColor(){
		return this.color;
	}
	
	public int getHealth(){
		return this.health;
	}
	
	@Override
	public String toString(){
		return "Atk: " + this.getAttack();
	}
	
	public int getAttack(){
		int atk = this.attack;
		Actor a = this;
		while(a.hasNext()){
			atk *= a.getAttackMultiplier();
			a = a.getNext();
		}
		return atk;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

}
