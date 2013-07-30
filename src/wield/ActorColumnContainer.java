package wield;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ActorColumnContainer implements Collection<ArrayList<? extends Actor>> {

	private ArrayList<ArrayList<? extends Actor>> actorLists = new ArrayList<ArrayList<? extends Actor>>();

	@Override
	public boolean add(ArrayList<? extends Actor> e) {
		return actorLists.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends ArrayList<? extends Actor>> c) {
		return actorLists.addAll(c);
	}

	@Override
	public void clear() {
		this.actorLists = new ArrayList<ArrayList<? extends Actor>>();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<ArrayList<? extends Actor>> iterator() {
		return this.actorLists.iterator();
	}

	public Iterator<Actor> actorIterator() {
		Iterator<Actor> it = new Iterator<Actor>() {
			int listIndex = 0;
			int inListIndex = 0;

			@Override
			public boolean hasNext() {
				return (listIndex) < actorLists.size()
						&& inListIndex < actorLists.get(actorLists.size() - 1)
								.size();
			}

			@Override
			public Actor next() {
				Actor a = actorLists.get(listIndex).get(inListIndex);
				if (actorLists.get(listIndex).size() == inListIndex + 1) {
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

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		int sum = 0;
		for(int i = 0; i < this.actorLists.size(); i++){
			sum += this.actorLists.get(i).size();
		}
		return sum;
	}

	@Override
	public Actor[] toArray() {
		Actor[] arr = new Actor[this.size()];
		Iterator<Actor> it = this.actorIterator();
		int i = 0;
		while(it.hasNext()){
			arr[i] = it.next();
		}
		return arr;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

}