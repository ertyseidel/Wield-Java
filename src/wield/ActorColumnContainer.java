package wield;

import java.util.ArrayList;
import java.util.Iterator;

public class ActorColumnContainer extends ArrayList<ActorColumn<Actor>> {
	private static final long serialVersionUID = -7671489449093183987L;
	
	public Iterator<Actor> actorIterator() {
		Iterator<Actor> it = new Iterator<Actor>() {
			int listIndex = 0;
			int inListIndex = 0;

			@Override
			public boolean hasNext() {
				int _li = listIndex;
				int _ili = inListIndex;
				if(_li >= size()) return false;
				if(_ili >= get(_li).size()) _li ++;
				while(_li < size() && get(_li).size() == 0 ){
					_li ++;
				}
				if(_li >= size()) return false;
				return true;
			}

			@Override
			public Actor next() {
				while(get(listIndex).size() == 0) listIndex ++;
				Actor a = get(listIndex).get(inListIndex);
				if (get(listIndex).size() == inListIndex + 1) {
					listIndex++;
					inListIndex = 0;
				} else {
					inListIndex++;
				}
				return a;
			}

			@Override
			public void remove() {
				// TODO Auto-generated method stub
			}
		};
		return it;
	}
	
	public Actor[] getActorArray() {
		Actor[] arr = new Actor[this.size()];
		Iterator<Actor> it = this.actorIterator();
		int i = 0;
		while(it.hasNext()){
			arr[i] = it.next();
		}
		return arr;
	}

	@SuppressWarnings("unchecked")
	public boolean add(ActorColumn<? extends Actor> e) {
		return super.add((ActorColumn<Actor>) e);
	}

}