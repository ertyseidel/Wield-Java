package wield;

import java.awt.Graphics;

public class Weapon extends Actor{

	private int multiplier;
	
	public Weapon(String name, String imageURI, int multiplier) {
		super(name, imageURI);
		this.multiplier = multiplier;
	}
	
	public int getMultiplier(){
		return this.multiplier;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

}
