public class StringNode {

  private String key;
  private StringNode next;

  public StringNode(String key, StringNode next) {
    this.key = key;
    this.next = next;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public StringNode getNext() {
    return next;
  }

  public void setNext(StringNode next) {
    this.next = next;
  }

}
