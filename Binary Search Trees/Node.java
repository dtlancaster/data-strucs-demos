
public class Node {
	private int time;
	private int req_index;
	private Node left;
	private Node right;

	public Node(int time, int req_index) {
		this.time = time;
		this.req_index = req_index;
		left = right = null;
	}

	public int getTime() {
		return time;
	}

	public int getReq_index() {
		return req_index;
	}

	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setReq_index(int req_index) {
		this.req_index = req_index;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public String toString() {
		return time + " " + req_index;
	}
}
