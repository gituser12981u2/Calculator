package Calculator;

import java.util.HashMap;
import java.util.Map;

class Trie {
    private Node root;
    private Map<Character, Node> children;

    public Trie() {
        root = new Node();
        children = new HashMap<>();
    }

    public void insert(String value, int priority) {
        Node current = root;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            Node node = current.getChildren().get(ch);
            if (node == null) {
                node = new Node();
                current.getChildren().put(ch, node);
            }
            current = node;
        }
        current.setEndOfWord(true);
    }

    public Node search(String value) {
        Node current = root;
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            Node node = current.getChildren().get(ch);
            if (node == null) {
                return null;
            }
            current = node;
        }
        return current;
    }

    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(Node current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfWord()) {
                return false;
            }
            current.setEndOfWord(false);
            return current.children.size() == 0;
        }
        char ch = word.charAt(index);
        Node node = current.children.get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1);
            if (shouldDeleteCurrentNode) {
                current.children.remove(ch);
                return current.children.size() == 0;
            }
            return false;
    }
}