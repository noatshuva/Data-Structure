public class FloorsArrayLink {
	private FloorsArrayLink[] nextPointers;
	private FloorsArrayLink[] prevPointers;
	private double key;

	
	public FloorsArrayLink(double key, int arrSize){
		this.key = key;
		nextPointers = new FloorsArrayLink[arrSize];
		prevPointers = new FloorsArrayLink[arrSize];
	}

	public double getKey() {
		return key;
	}

	public FloorsArrayLink getNext(int i) {
		if (i > nextPointers.length) {
			return null;
		}
		else {
			return nextPointers[i-1];
		}
	}

	public FloorsArrayLink getPrev(int i) {
		if (i > prevPointers.length) {
			return null;
		}
		else {
			return prevPointers[i-1];
		}
	}

	public void setNext(int i, FloorsArrayLink next) {
		if (i <= nextPointers.length) {
			nextPointers[i-1] = next;
		}
	}

	public void setPrev(int i, FloorsArrayLink prev) {
		if (i <= prevPointers.length) {
			prevPointers[i-1] = prev;
		}
	}

	public int getArrSize(){
		return prevPointers.length;
	}
}

