package wield;

import java.awt.Color;
import java.awt.Graphics;

public class Weapon extends Actor{

	private int multiplier;
	
	public Weapon(String name, int multiplier) {
		super(name);
		this.multiplier = multiplier;
		this.columnOrder = 1;
	}
	
	public int getMultiplier(){
		return this.multiplier;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.GREEN);
		g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		
	}

}
