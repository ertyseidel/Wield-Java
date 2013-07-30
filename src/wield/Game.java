package wield;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Iterator;

public class Game {

	private ActorColumn<Adventurer> adventurers = new ActorColumn<Adventurer>(1);
	private ActorColumn<Weapon> weapons = new ActorColumn<Weapon>(2);
	private ActorColumn<Site> sites = new ActorColumn<Site>(3);

	private ActorColumnContainer actorContainer = new ActorColumnContainer();

	private Actor lastHovered = null;
	private Point lastMousePos = new Point(0, 0);

	public Game() {
		actorContainer.add(adventurers);
		actorContainer.add(weapons);
		actorContainer.add(sites);

		adventurers.add(new Adventurer("Levi", "data/green-fighter.png", 1, 1, Color.GREEN));
		adventurers.add(new Adventurer("Jorge","data/black-fighter.png", 3, 2, Color.GRAY));
		weapons.add(new Weapon("Sword", "data/grey-sword.png", 2));
		weapons.add(new Weapon("Bow", "data/long-bow.png", 4));
		sites.add(new Site("Cavernous Caverns", "data/cavernous-caverns.png", 10));
	}

	public void update(double deltaTime) {
		Iterator<ActorColumn<Actor>> it = this.actorContainer.iterator();
		while (it.hasNext()) {
			it.next().update(deltaTime);
		}
	}

	public void paint(Graphics g) {
		Iterator<ActorColumn<Actor>> it = this.actorContainer.iterator();
		while (it.hasNext()) {
			it.next().paint(g);
		}
		if (this.lastHovered != null && !this.lastHovered.isEndNode()) {
			Point handle = this.lastHovered.getHandlePoints(Actor.HANDLE_RIGHT);
			g.drawLine(handle.x, handle.y, this.lastMousePos.x,
					this.lastMousePos.y);
		}
	}

	public void mousePressed(Point p) {
		Iterator<Actor> it = this.actorContainer.actorIterator();
		while (it.hasNext()) {
			Actor a = it.next();
			if (a.hasPointInside(p)) {
				a.setSelected(true);
				this.lastHovered = a;
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
						&& a.getOrder() == this.lastHovered.getOrder() + 1) {
					if (this.lastHovered.hasNext()) {
						this.lastHovered.recursiveDisconnect();
					}
					if (a.hasPrev() && !a.isEndNode()) {
						a.getPrev(0).recursiveDisconnect();
					}
					this.lastHovered.setNext(a);
					a.addPrev(this.lastHovered);
					this.lastHovered = a;
					this.lastMousePos = a.getHandlePoints(Actor.HANDLE_LEFT);
				}
			}
		}
	}
}
