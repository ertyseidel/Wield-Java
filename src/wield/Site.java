package wield;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class Site extends Actor {

	private int danger;
	private boolean tookDamageThisRound = false;
	
	public Site(String name, String imageURI, int danger) {
		super(name, imageURI);
		this.danger = danger;
		this.isEndNode = true;
	}
	
	public int getDanger(){
		return this.danger;
	}
	
	public void growDanger(){
		this.danger = (int) Math.ceil(this.danger * 1.25);
	}
	
	public void setDanger(int d){
		this.danger = d;
	}
	
	@Override
	public String toString(){
		return "Danger: " + this.danger;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	public void takeDamage(int d) {
		this.danger -= d;
		this.tookDamageThisRound = true;
	}

	public void newRound() {
		if(!this.tookDamageThisRound ){
			this.growDanger();
		}
		this.tookDamageThisRound = false;
	}
	
	@Override
	public Adventurer getAdventurer(){
		if(this.getPrevs().isEmpty()) return null;
		return this.getPrevs().get(0).getAdventurer();
	}

	public ArrayList<Adventurer> getAdventurers() {
		ArrayList<Adventurer> adv = new ArrayList<Adventurer>();
		Iterator<Actor> it = this.getPrevs().iterator();
		while(it.hasNext()){
			adv.add(it.next().getAdventurer());
		}
		return adv;
	}


}
