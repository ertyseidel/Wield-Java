package wield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Button implements Clickable{
	private Rectangle rect;
	private boolean hover;
	
	public Button(Rectangle rect){
		this.rect = rect;
		this.hover = false;
	}
	
	public boolean hasPointInside(Point p) {
		return this.rect.contains(p);
	}
	
	public void paint(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		if(this.hover){
			g.fillRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		}
		g.setColor(Color.DARK_GRAY);
		g.drawChars("Adventure!".toCharArray(), 0, 9, this.rect.x + 30, this.rect.y + 30);
		g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
	}

	public void setHover(boolean b) {
		this.hover = b;
	}

	public boolean getHover() {
		return this.hover;
	}
}
