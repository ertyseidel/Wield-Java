package wield;

import java.awt.Graphics;

public class Weapon extends Actor{
	
	public Weapon(String name, String imageURI, int multiplier) {
		super(name, imageURI);
		this.attackMultiplier = multiplier;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	@Override
	public String toString(){
		return "x" + this.attackMultiplier;
	}

}
