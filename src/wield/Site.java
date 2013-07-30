package wield;

import java.awt.Graphics;

public class Site extends Actor {

	private int danger;
	
	public Site(String name, String imageURI, int danger) {
		super(name, imageURI);
		this.danger = danger;
		this.isEndNode = true;
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
	}


}
