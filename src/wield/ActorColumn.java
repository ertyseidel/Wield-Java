package wield;

import java.awt.Graphics;
import java.util.ArrayList;

public class ActorColumn<T> extends ArrayList<Actor> {

	private static final long serialVersionUID = -7245530119520670825L;

	private int offset = 0;
	private int order = -1;
	
	public ActorColumn(int order){
		super();
		this.order = order;
	}
	
	public int getOffset(){
		return this.offset;
	}
	
	public int getOrder(){
		return this.order;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Actor a){
		a.setColumn((ActorColumn<Actor>) this);
		return super.add(a);
	}
	
	public void update(double deltaTime) {
		for (int i = 0; i < this.size(); i++) {
			this.get(i).update(this.order, i);
		}
	}

	public void paint(Graphics g) {
		for (int i = 0; i < this.size(); i++) {
			this.get(i).paint(g);
		}
	}

}
