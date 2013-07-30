package wield;

import java.awt.Color;
import java.awt.Graphics;


public class Adventurer extends Actor{
	
	private int health;
	private int attack;
	
	public Adventurer(String name, int health, int attack){
		super(name);
		this.health = health;
		this.attack = attack;
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
		g.setColor(Color.BLUE);
		g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		if(this.selected){
			g.fillRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		}
		
	}

}
