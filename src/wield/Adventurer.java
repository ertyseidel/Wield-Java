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
	public Color getAdventurerColor(){
		return this.color;
	}

	public int getHealth(){
		return this.health;
	}
	
	public int getAttack(){
		return this.attack;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

}
