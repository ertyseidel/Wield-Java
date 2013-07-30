package wield;

import java.awt.Color;
import java.awt.Graphics;

public class Site extends Actor {

	private int danger;
	
	public Site(String name,int danger) {
		super(name);
		this.danger = danger;
	}
	
	public int getDanger(){
		return this.danger;
	}
	
	public void growDanger(){
		this.danger *= 1.25;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.RED);
		g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		
	}


}
