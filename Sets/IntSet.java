/*
 * This class maintains a set of integers. 
 */
public class IntSet {
	IntNode head;

	public IntSet() {
		head = null;
	}
	
	/* Find if a key is present in the set. Returns false if the key is not present, otherwse true.*/
	public boolean find(int key) {
		int pos = 0;
		for (IntNode curr = head; curr != null && curr.key <= key; curr = curr.next, pos++) {
			if (curr.key == key)
				return true;
		}		
		return false;
	}
	
	/* Insert a key into the set. */
	public void insert(int key) {
		// Make sure that the key is not present.
		assert (!find(key));
		
		if (head == null || key < head.key) {
			head = new IntNode(key, head);
			return;
		}

		IntNode curr;
		for (curr = head; curr.next != null && curr.next.key < key; curr = curr.next);
		curr.next = new IntNode(key, curr.next);
	}
	
	/* Remove a key from the set. */
	public void remove(int key) {
		// Make sure that the key is present.
		assert (find(key));
		
		if (key == head.key) {
			head = head.next;
			return;
		}
		IntNode curr;
		for (curr = head; curr.next != null && curr.next.key < key; curr = curr.next);
		curr.next = curr.next.next;
	}
	
	/* Print the contents of the set in sorted order. */
	public void print() {
		IntNode curr;
		for (curr = head; curr != null; curr = curr.next) {
			System.out.print(curr.key + " ");
		}
		System.out.println();
	}
}
