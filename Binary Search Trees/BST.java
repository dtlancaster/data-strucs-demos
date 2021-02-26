
public class BST {
	private Node root; // The root node of the tree.

	public BST() {
		root = null;
	}

	/**
		Inserts a time, along with the req_id. The tree is keyed on time, while req_id provides a pointer to the request.
		This is a public wrapper function that calls the recursive insert method.
		Note that the insert method should return the root node of the subtree in which we insert the key (and its value).
	**/
	public void insert(int time, int req_index) {
		root = insert(root, time, req_index);
	}

	/**
		Recursive insert routine to insert a key time along with its associated value req_id.
	**/
	private Node insert(Node x, int time, int req_index) {
		if (x == null) 
			return new Node(time, req_index);
		if (time < x.getTime()) {
			x.setLeft(insert(x.getLeft(), time, req_index));
		}
		else {
			x.setRight(insert(x.getRight(), time, req_index));
		}
		return x;
	}

	/**
		Returns a pointer to the Node that is the predecessor of time. The predecessor element is the largest
		element within the tree that is smaller or equal to time. This is the deepest ancestor with this property.
		Please return the predecessor element. You may return null if time does not have a predecessor.
	**/
	public Node pred(int time) {
		Node x = root;
		Node y = null;
		while(x != null) {
			int tx = x.getTime();
			if (tx <= time) {
				y = x; // y points to the deepest node (yet found) that is bigger than k. Might be the predecessor.
				x = x.getRight();
			}
			else {
				x = x.getLeft();
			}
		}
		return y;
	}

	/**
		Returns a pointer to the Node that is the successor of time. The successor element is the largest
		element within the tree that is larger or equal to time. This is the deepest ancestor with this property.
		Please return the successor element. You may return null if time does not have a successor.
	**/
	public Node succ(int time) {
		Node x = root;
		Node y = null;
		while(x != null) {
			int tx = x.getTime();
			if (tx >= time) {
				y = x; // y points to the deepest node (yet found) that is bigger than k. Might be the successor.
				x = x.getLeft();
			}
			else {
				x = x.getRight();
			}
		}
		return y;
	}

	/**
		Returns the minimum element in the binary search tree or null if the tree is empty.
	**/
	public Node min() {
		Node x = root;
		if (x == null) return null;
		while (x.getLeft() != null)
			x = x.getLeft();
		return x;
	}

	/**
		Returns the maximum element in the binary search tree or null if the tree is empty.
	**/
	public Node max() {
		Node x = root;
		if (x == null) return null;
		while (x.getRight() != null)
			x = x.getRight();
		return x;
	}

	/**
		Remove the node that contains this time. If this time is not present in the structure, this method does nothing.
	**/
	public void delete(int time) {
		root = delete(root, time);
	}

	/**
		Recursive method to delete a node with key time on a subtree rooted at x. Like insert, this method returns the 
		root of the subtree in which we delete this element. 
	**/
	private Node delete(Node x, int time) {
		if (x == null) return null;

		if (x.getTime() == time) {
			if (x.getLeft() == null && x.getRight() == null) return null;
			if (x.getLeft() == null || x.getRight() == null) {
				return (x.getLeft() == null) ? x.getRight() : x.getLeft();
			}
			// Copy successor's data into x
			Node s = succ(x.getTime());
			x.setTime(s.getTime());
			x.setReq_index(s.getReq_index());
			// Then delete successor
			x.setRight(delete(x.getRight(), s.getTime()));
			return x;
		}
		if (time < x.getTime()) {
			x.setLeft(delete(x.getLeft(), time));
		}
		else {
			x.setRight(delete(x.getRight(), time));
		}
		return x;
	}

	/**
		Prints the contents of the tree in sorted order.
	**/
	public void print() {
		print(root);
	}

	/**
		Recursive routine to print the contents of the tree.
	**/
	private void print(Node x) {
		if (x == null) return;
		print(x.getLeft());
		System.out.println(x);
		print(x.getRight());
	}

}
