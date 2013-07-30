package wield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class Actor{
	
	public static final int HANDLE_LEFT = 0;
	public static final int HANDLE_RIGHT = 1;
	
	private String name;
	protected Rectangle rect;
	
	protected int columnOrder = -1;
	
	private Actor connectedTo;
	
	protected boolean selected;
	
	public Actor(String name){
		this.name = name;
		this.selected = false;
		this.connectedTo = null;
		this.rect = new Rectangle(100, 100, 50, 50);
	}
	
	public void setRect(Rectangle rect){
		this.rect = rect;
	}
	
	public void setSelected(boolean selected){
		this.selected = selected;
	}
	
	public boolean isSelected(){
		return this.selected;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void update(double deltaTime){
		this.updateRect();
	}
	
	public void updateRect(){
		this.rect.x = 100 * this.columnOrder;
	}
	
	public int getOrder(){
		return this.columnOrder;
	}
	
	public void paint(Graphics g){
		if(this.connectedTo != null){
			g.setColor(Color.CYAN);
			Point handle = this.getHandlePoints(Actor.HANDLE_RIGHT);
			Point other = this.connectedTo.getHandlePoints(Actor.HANDLE_LEFT);
			g.drawLine(handle.x, handle.y, other.x, other.y);
		}
	}
	
	public boolean hasPointInside(Point p){
		return this.rect.contains(p);
	}
	
	public Point getHandlePoints(int side){
		if(side == Actor.HANDLE_LEFT){
			return new Point(this.rect.x, (int)(this.rect.y + this.rect.height / 2));
		} else{
			return new Point(this.rect.x + this.rect.width, (int)(this.rect.y + this.rect.height / 2));
		}
	}

	public void connectTo(Actor a) {
		this.connectedTo = a;
	}
	
}
