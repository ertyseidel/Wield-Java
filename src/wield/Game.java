package wield;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {

	private ArrayList<Adventurer> adventurers = new ArrayList<Adventurer>();
	private ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	private ArrayList<Site> sites = new ArrayList<Site>();

	private ActorColumnContainer actorContainer = new ActorColumnContainer();

	private Actor lastHovered = null;
	private Point lastMousePos = new Point(0, 0);

	public Game() {
		actorContainer.add(adventurers);
		actorContainer.add(weapons);
		actorContainer.add(sites);

		adventurers.add(new Adventurer("Levi", 1, 1));
		weapons.add(new Weapon("Sword", 2));
		sites.add(new Site("Cavernous Caverns", 10));
	}

	public void update(double deltaTime) {
		Iterator<Actor> it = this.actorContainer.actorIterator();
		while (it.hasNext()) {
			it.next().update(deltaTime);
		}
	}

	public void paint(Graphics g) {
		Iterator<Actor> it = this.actorContainer.actorIterator();
		while (it.hasNext()) {
			it.next().paint(g);
		}
		if (this.lastHovered != null) {
			Point handle = this.lastHovered.getHandlePoints(Actor.HANDLE_RIGHT);
			g.drawLine(handle.x, handle.y, this.lastMousePos.x,
					this.lastMousePos.y);
		}
	}

	public void mousePressed(Point p) {
		for (int i = 0; i < this.adventurers.size(); i++) {
			if (this.adventurers.get(i).hasPointInside(p)) {
				this.adventurers.get(i).setSelected(true);
				this.lastHovered = this.adventurers.get(i);
			}
		}
		this.lastMousePos = p;
	}

	public void mouseReleased(Point p) {
		for (int i = 0; i < this.adventurers.size(); i++) {
			this.adventurers.get(i).setSelected(false);
		}
		this.lastHovered = null;
		this.lastMousePos = p;
	}

	public void mouseDragged(Point p) {
		this.lastMousePos = p;
		if (this.lastHovered != null) {
			Iterator<Actor> it = this.actorContainer.actorIterator();
			while (it.hasNext()) {
				Actor a = it.next();
				if (a.hasPointInside(p)
						&& a.getOrder() > this.lastHovered.getOrder()) {
					this.lastHovered.connectTo(a);
					this.lastHovered = a;
					this.lastMousePos = a.getHandlePoints(Actor.HANDLE_LEFT);
				}
			}
		}
	}
}
