
public class Node {

	private int team;
	private int size;
	private Node left;
	private Node right;

	public Node(int team) {
		this.team = team;
		size = 1;
		left = null;
		right = null;
	}
	public Node(int team, Node left, Node right) {
		this.team = team;
		this.left = left;
		this.right = right;
		size = (left == null ? 0 : left.getSize()) + (right == null ? 0 : right.getSize()) + 1;
	}

	public int getTeam() {
		return team;
	}
	public int getSize() {
		return size;
	}
	public Node getLeft() {
		return left;
	}
	public Node getRight() {
		return right;
	}

	public void setTeam(int team) {
		this.team = team;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	public void setRight(Node right) {
		this.right = right;
	}

	public void incSize() {
		size++;
	}
	public void updateSize() {
		size = ((left == null) ? 0 : left.size) + ((right == null) ? 0 : right.size) + 1;
	}
}
