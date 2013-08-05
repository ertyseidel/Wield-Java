package wield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

public abstract class Actor implements Clickable {

	public static final int HANDLE_LEFT = 0;
	public static final int HANDLE_RIGHT = 1;

	private BufferedImage actorImage;
	protected int attackMultiplier = 1;
	private ActorColumn<Actor> column;
	protected boolean isEndNode;
	private String name;
	private Actor next;
	private ArrayList<Actor> prevs;
	protected Rectangle rect;

	protected boolean selected;

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

	public void addPrev(Actor a) {
		this.prevs.add(a);
	}

	public Adventurer getAdventurer() {
		if (this.hasPrev()) {
			return this.getPrev(0).getAdventurer();
		} else {
			return null;
		}
	}

	public int getAttackMultiplier() {
		return this.attackMultiplier;
	}

	public Point getHandlePoints(int side) {
		if (side == Actor.HANDLE_LEFT) {
			return new Point(this.rect.x, this.rect.y + this.rect.height / 2);
		} else {
			return new Point(this.rect.x + this.rect.width, this.rect.y
					+ this.rect.height / 2);
		}
	}

	public String getName() {
		return this.name;
	}

	public Actor getNext() {
		return this.next;
	}

	public int getOrder() {
		return this.column.getOrder();
	}

	public Actor getPrev(int index) {
		return this.prevs.get(index);
	}

	public ArrayList<Actor> getPrevs() {
		return this.prevs;
	}

	public boolean hasNext() {
		return this.next != null;
	}

	@Override
	public boolean hasPointInside(Point p) {
		return this.rect.contains(p);
	}

	public boolean hasPrev() {
		return !this.prevs.isEmpty();
	}

	public boolean isEndNode() {
		return this.isEndNode;
	}

	public boolean isSelected() {
		return this.selected;
	}

	public void paint(Graphics g) {
		g.drawImage(this.actorImage, this.rect.x, this.rect.y, null);
		if (this.next != null) {
			g.setColor(this.getAdventurer().getColor());
			Point handle = this.getHandlePoints(Actor.HANDLE_RIGHT);
			Point other = this.next.getHandlePoints(Actor.HANDLE_LEFT);
			g.drawLine(handle.x, handle.y, other.x, other.y);
		}
		if (this.getAdventurer() != null) {
				g.setColor(this.getAdventurer().getColor());
		} else {
			g.setColor(Color.BLACK);
		}
		g.drawRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
		g.setColor(Color.BLACK);
		g.drawChars(this.toString().toCharArray(), 0, this.toString().length(), this.rect.x, this.rect.y + this.rect.height + 20);
	}

	public void recursiveDisconnect() {
		if (this.next != null) {
			this.next.recursiveDisconnect();
			this.next.removePrev(this);
			this.next = null;
		}
	}

	public void remove() {
		if (this.next != null) {
			this.next.recursiveDisconnect();
		}
		Iterator<Actor> it = this.prevs.iterator();
		while (it.hasNext()) {
			Actor a = it.next();
			it.remove();
			a.recursiveDisconnect();
		}
	}

	public void removePrev(Actor a) {
		this.prevs.remove(a);
	}

	public void setColumn(ActorColumn<Actor> column) {
		this.column = column;
	}

	public void setNext(Actor a) {
		this.next = a;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void update(int xIndex, int yIndex) {
		this.updateRect(xIndex, yIndex);
	}

	public void updateRect(int xIndex, int yIndex) {
		this.rect.y = 100 * yIndex + 100;
		this.rect.x = 100 * xIndex;
	}

}
