package wield;

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

		adventurers.add(new Adventurer("Levi", 1, 1));
		adventurers.add(new Adventurer("Jorge", 3, 2));
		weapons.add(new Weapon("Sword", 2));
		weapons.add(new Weapon("Bow", 4));
		sites.add(new Site("Cavernous Caverns", 10));
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
						&& a.getOrder() == this.lastHovered.getOrder() + 1
						&& !a.hasConnection()) {
					this.lastHovered.connectTo(a);
					this.lastHovered = a;
					this.lastMousePos = a.getHandlePoints(Actor.HANDLE_LEFT);
				}
			}
		}
	}
}
