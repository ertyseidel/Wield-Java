package wield;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {

	private ActorColumn<Adventurer> adventurers = new ActorColumn<Adventurer>(1);
	private ActorColumn<Weapon> weapons = new ActorColumn<Weapon>(2);
	private ActorColumn<Site> sites = new ActorColumn<Site>(3);

	private ActorColumnContainer actorContainer = new ActorColumnContainer();

	private Actor lastHovered = null;
	private Point lastMousePos = new Point(0, 0);

	private Button adventureButton;

	private boolean cursorHover = false;

	public Game() {
		actorContainer.add(adventurers);
		actorContainer.add(weapons);
		actorContainer.add(sites);

		adventurers.add(new Adventurer("Levi", "data/green-fighter.png", 1, 1,
				Color.GREEN));
		adventurers.add(new Adventurer("Jorge", "data/black-fighter.png", 3, 2,
				Color.GRAY));
		weapons.add(new Weapon("Sword", "data/grey-sword.png", 2));
		weapons.add(new Weapon("Bow", "data/long-bow.png", 4));
		sites.add(new Site("Cavernous Caverns", "data/cavernous-caverns.png",
				10));
		sites.add(new Site("Sorcerer's Tower", "data/sorcerers-tower.png",
				40));

		this.adventureButton = new Button(new Rectangle(300, 300, 100, 50));
	}

	public void update(double deltaTime) {
		Iterator<ActorColumn<Actor>> it = this.actorContainer.iterator();
		while (it.hasNext()) {
			it.next().update(deltaTime);
		}
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		Iterator<ActorColumn<Actor>> it = this.actorContainer.iterator();
		while (it.hasNext()) {
			it.next().paint(g2);
		}
		if (this.lastHovered != null && !this.lastHovered.isEndNode()) {
			Point handle = this.lastHovered.getHandlePoints(Actor.HANDLE_RIGHT);
			g.drawLine(handle.x, handle.y, this.lastMousePos.x,
					this.lastMousePos.y);
		}
		this.adventureButton.paint(g2);
	}

	public void mousePressed(Point p) {
		Iterator<Actor> it = this.actorContainer.actorIterator();
		while (it.hasNext()) {
			Actor a = it.next();
			if (a.hasPointInside(p) && (a instanceof Adventurer || a.getAdventurer() != null)) {
				a.setSelected(true);
				a.recursiveDisconnect();
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

	public void mouseClicked(Point p) {
		if (this.adventureButton.hasPointInside(p)) {
			this.doAdventure();
		}
	}

	private void doAdventure() {
		// damage the sites and remove them if they are done with
		Iterator<Actor> it = this.sites.iterator();
		while (it.hasNext()) {
			Site s = (Site) it.next();
			ArrayList<Adventurer> adv = s.getAdventurers();
			Iterator<Adventurer> advi = adv.iterator();
			while(advi.hasNext() && !(s.getDanger() < 0)){
				s.takeDamage(advi.next().getAttack());
				if (s.getDanger() <= 0) {
					s.remove();
					it.remove();
				}
			}
		}
		// go through the remaining sites and notify them that it is a new round
		int dangerSum = 0;
		it = this.sites.iterator();
		while (it.hasNext()) {
			Site s = (Site) it.next();
			s.newRound();
			dangerSum += s.getDanger();
		}
		if (this.sites.size() == 0) {
			System.out.println("You Win!");
		} else if(dangerSum > 1000){
			System.out.println("You Lose!");
		}
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

	public void mouseMoved(Point p) {
		if (this.adventureButton.hasPointInside(p)) {
			this.adventureButton.setHover(true);
			this.cursorHover = true;
		} else if (this.adventureButton.getHover()) {
			this.adventureButton.setHover(false);
			this.cursorHover = false;
		}
	}

	public Cursor getCursor() {
		if (!this.cursorHover) {
			return new Cursor(Cursor.DEFAULT_CURSOR);
		} else {
			return new Cursor(Cursor.HAND_CURSOR);
		}

	}
}
