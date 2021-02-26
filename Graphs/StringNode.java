/**
	This class holds a single node of an element in a Hash Map.
**/
public class StringNode {

	private String key;				// This is the key field that is used as the key in the hash table.
	private String value;			// This is the associated value of the key field.
	private StringNode next;	// Reference to the next node in the hash map.

	/**
		Constructor: creates an instance of StringNode.
	**/
	public StringNode(String key, String value, StringNode next) {
		this.key = key;
		this.value = value;
		this.next = next;
	}

	// Several get and set methods follow.

	public String getKey() {
		return key;
	}

	public void setToken(String key) {
		this.key = key;
	}

	public StringNode getNext() {
		return next;
	}

	public void setNext(StringNode next) {
		this.next = next;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
