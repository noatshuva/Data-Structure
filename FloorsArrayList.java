public class FloorsArrayList implements DynamicSet {
	private FloorsArrayLink fKey; 
	private FloorsArrayLink lKey; 
	private int numOfLinks; 
	private int maxArrSize; 

	public FloorsArrayList(int N){
		this.fKey = new FloorsArrayLink (Double.NEGATIVE_INFINITY, N+1);
		this.lKey = new FloorsArrayLink (Double.POSITIVE_INFINITY, N+1);
		for (int i = 1; i <= N + 1; i = i + 1) {
			fKey.setNext(i, lKey);
			lKey.setPrev(i, fKey);
		}		
		this.numOfLinks = 0; 
		this.maxArrSize = 0;
	}

	public int getSize(){
		return numOfLinks;
	}

	public void insert(double key, int arrSize) {

		if (arrSize > maxArrSize) {
			this.maxArrSize = arrSize;
		}
		FloorsArrayLink toAdd = new FloorsArrayLink (key, arrSize);
		int i = maxArrSize;     	
		FloorsArrayLink curr = fKey;
		while (i>0) { // check where to add the key
			if (curr.getNext(i).getKey() > key) // if the next key is bigger go down in the array.
				i = i-1;
			else if (curr.getNext(i).getKey() < key) { // if the next key is smaller start checking the next key.
				curr = curr.getNext(i);
			}
		}
		while (curr.getNext(1).getKey() < key) { // if the next key is smaller than go to next one
			curr = curr.getNext(1);
		}
		// if the next key is bigger so we found where to add
		FloorsArrayLink nextK = curr.getNext(1);
		int counter = arrSize;
		while (counter > 0) { // adding the next key
			if (curr.getArrSize() >= counter) {
				curr.setNext(counter, toAdd);
				toAdd.setPrev(counter, curr);
			}
			else { // find the next pointer
				int nullPointer = arrSize - curr.getArrSize();
				FloorsArrayLink prev = curr.getPrev(curr.getArrSize());
				while (nullPointer > 0) { // if there are still null pointers
					if (prev.getArrSize() >= toAdd.getArrSize() - nullPointer +1) { // check if the next key is suitable
						toAdd.setPrev(toAdd.getArrSize() - nullPointer +1, prev);
						prev.setNext(toAdd.getArrSize() - nullPointer +1, toAdd);
						nullPointer = nullPointer -1;
					}
					else // if not check the next one
						prev = prev.getPrev(prev.getArrSize());
				}
			}
			if (nextK.getArrSize() >= counter) {
				nextK.setPrev(counter, toAdd);
				toAdd.setNext(counter, nextK);
			}
			else {// find the next pointer
				int nullPointer = arrSize - nextK.getArrSize();
				FloorsArrayLink next = nextK.getNext(nextK.getArrSize());
				while (nullPointer > 0) { // if there are still null pointers
					if (next.getArrSize() >= toAdd.getArrSize() - nullPointer +1) { // check if the next key is suitable
						toAdd.setNext(toAdd.getArrSize() - nullPointer +1, next);
						next.setPrev(toAdd.getArrSize() - nullPointer +1, toAdd);
						nullPointer = nullPointer -1;
					}
					else
						next = next.getNext(next.getArrSize());
				}
			}
			counter = counter -1;
		}
		numOfLinks = numOfLinks + 1;
	}

	public void remove(FloorsArrayLink toRemove) {  	
		int counter = toRemove.getArrSize();
		if (maxArrSize == counter) {
			newMaxSize(toRemove);
		}
		while (counter > 0) { 
			FloorsArrayLink next = toRemove.getNext(counter);
			FloorsArrayLink prev = toRemove.getPrev(counter);
			next.setPrev(counter, prev);
			prev.setNext(counter, next);
			counter = counter-1;
		}
		numOfLinks = numOfLinks - 1;
	}

	private void newMaxSize (FloorsArrayLink toRemove) { //finding the new maxArrSize after removing a link
		boolean found = false;
		for (int i = maxArrSize ; i > 0 & !found ; i = i - 1){ //checking next&prev pointers from the top
			FloorsArrayLink prev = toRemove.getPrev(i);
			FloorsArrayLink next = toRemove.getNext(i);
			if (prev.getKey() != fKey.getKey()) { //checking if the pointer is to the first link - (-infinity)
				found = true; 
			}
			else if (next.getKey() != lKey.getKey()) { //checking if the pointer is to the last link - (infinity)
				found = true;
			}
			if (found==true) {
				this.maxArrSize = i;
			}
		}
	}



	public FloorsArrayLink lookup(double key) {
		int i = maxArrSize;
		boolean found = false;
		FloorsArrayLink curr = fKey;
		FloorsArrayLink ans = null;
		while (i > 0 & !found) {
			if (curr.getNext(i).getKey() != key) {
				if (curr.getNext(i).getKey() > key) {
					i = i-1;
				}
				else if (curr.getNext(i).getKey() < key) {
					curr = curr.getNext(i);
				}
			}
			else if (curr.getNext(i).getKey() == key) {
				found = true;
				ans = curr.getNext(i);
			}
		}
		return ans;
	}

	@Override
	public double successor(FloorsArrayLink link) {
		FloorsArrayLink succ = link.getNext(1);
		if (succ.equals(null)) {
			return Double.NEGATIVE_INFINITY;
		}
		return succ.getKey();
	}

	@Override
	public double predecessor(FloorsArrayLink link) {
		FloorsArrayLink pred = link.getPrev(1);
		if (pred.equals(null)) {
			return Double.POSITIVE_INFINITY;
		}
		return pred.getKey();
	}

	@Override
	public double minimum() {
		return successor(fKey);
	}

	@Override
	public double maximum() {
		return predecessor(lKey);
	}
}
