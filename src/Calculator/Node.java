package Calculator;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Node left, right;
    private String value;
    private int priority;
    private boolean isEndOfWord;
    Map<Character, Node> children;

    public Node() {
        this.value = value;
        this.priority = priority;
        this.children = new HashMap<>();
    }

    public String getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.isEndOfWord = endOfWord;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Map<Character, Node> getChildren() {
        return children;
    }
    
}
