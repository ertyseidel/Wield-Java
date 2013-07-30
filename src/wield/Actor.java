package wield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Actor {

	public static final int HANDLE_LEFT = 0;
	public static final int HANDLE_RIGHT = 1;

	private String name;
	protected Rectangle rect;

	private Actor next;
	private ArrayList<Actor> prevs;

	protected boolean selected;
	private ActorColumn<Actor> column;
	
	private BufferedImage actorImage;
	
	protected boolean isEndNode;

	public Actor(String name, String imageURI) {
		this.name = name;
		this.selected = false;
		this.next = null;
		this.prevs = new ArrayList<Actor>();
		this.rect = new Rectangle(100, 100, 64, 64);
		this.isEndNode = false;
		try {
			this.actorImage = ImageIO.read(new File(imageURI));
		} catch (IOException e) {
			System.err.println("Could not find sprite for " + this.name);
			e.printStackTrace();
		}
	}

	public void setColumn(ActorColumn<Actor> column) {
		this.column = column;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public String getName() {
		return this.name;
	}

	public void update(int xIndex, int yIndex) {
		this.updateRect(xIndex, yIndex);
	}

	public void updateRect(int xIndex, int yIndex) {
		this.rect.y = 100 * yIndex + 100;
		this.rect.x = 100 * xIndex;
	}

	public int getOrder() {
		return this.column.getOrder();
	}

	public void paint(Graphics g) {
		g.drawImage(this.actorImage, this.rect.x, this.rect.y, null);
		if (this.next != null) {
			g.setColor(Color.CYAN);
			Point handle = this.getHandlePoints(Actor.HANDLE_RIGHT);
			Point other = this.next.getHandlePoints(Actor.HANDLE_LEFT);
			g.drawLine(handle.x, handle.y, other.x, other.y);
		}
		if(this.selected || this.hasPrev() || this.hasNext()){
			g.setColor(this.getAdventurerColor());
			g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		} else {
			g.setColor(Color.BLACK);
			g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		}
	}
	
	public Color getAdventurerColor(){
		if(this.hasPrev()){
			return this.getPrev(0).getAdventurerColor();
		} else{
			return Color.MAGENTA;
		}
	}

	public boolean hasPointInside(Point p) {
		return this.rect.contains(p);
	}

	public Point getHandlePoints(int side) {
		if (side == Actor.HANDLE_LEFT) {
			return new Point(this.rect.x,
					(int) (this.rect.y + this.rect.height / 2));
		} else {
			return new Point(this.rect.x + this.rect.width,
					(int) (this.rect.y + this.rect.height / 2));
		}
	}

	public void setNext(Actor a) {
		this.next = a;
	}

	public boolean hasNext() {
		return this.next != null;
	}

	public Actor getNext() {
		return this.next;
	}
	
	public void addPrev(Actor a) {
		this.prevs.add(a);
	}
	
	public void removePrev(Actor a){
		this.prevs.remove(a);
	}

	public boolean hasPrev() {
		return !this.prevs.isEmpty();
	}

	public ArrayList<Actor> getPrevs() {
		return this.prevs;
	}
	
	public Actor getPrev(int index){
		return this.prevs.get(index);
	}

	public void recursiveDisconnect() {
		if (this.next != null) {
			this.next.recursiveDisconnect();
			this.next.removePrev(this);
			this.next = null;
		}
	}

	public boolean isEndNode() {
		return this.isEndNode;
	}

}
