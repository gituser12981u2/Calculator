package Calculator;

import java.util.List;

public class Parser {
    private Trie trie;

    public Parser() {
        trie = new Trie();
    }

    public double evaluate(List<Token> tokens) {
        assignPriority(tokens);
        for (Token token : tokens) {
            trie.insert(token.getValue(), token.getPriority());
        }
        Node highestPriority = trie.search(getHighestPriority(tokens));
        return solve(highestPriority);
    }

    private void assignPriority(List<Token> tokens) {
        for (Token token : tokens) {
            switch (token.getType()) {
                case OPERATOR:
                    token.setPriority(getOperatorPriority(token.getValue()));
                    break;
                case PARENTHESIS:
                    token.setPriority(getParanthesisPriority(token.getValue()));
                    break;
                case NUMBER:
                    token.setPriority(0);
                    break;
                default:
                    break;
            }
        }
    }

    private int getOperatorPriority(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            case "disco":
                System.out.println("DISCO");
            default:
                return -1;
        }
    }

    private int getParanthesisPriority(String paranthesis) {
        switch (paranthesis) {
            case "(":
                return 4;
            case ")":
                return -1;
            default:
                return -1;
        }
    }

    private String getHighestPriority(List<Token> tokens) {
        int maxPriority = Integer.MIN_VALUE;
        String highestPriority = "";
        for (Token token : tokens) {
            if (token.getPriority() > maxPriority) {
                maxPriority = token.getPriority();
                highestPriority = token.getValue();
            }
        }
        return highestPriority;
    }

    private double solve(Node node) {
        if (node.isEndOfWord()) {
            return Double.parseDouble(node.getValue());
        } else {
            double left = solve(node.getLeft());
            double right = solve(node.getRight());
            switch (node.getValue()) {
                case "+":
                    return left + right;
                case "-":
                    return left - right;
                case "*":
                    return left * right;
                case "/":
                    return left / right;
                case "^":
                    return Math.pow(left, right);
                default:
                    throw new IllegalArgumentException("Invalid operator: " + node.getValue());
            }
        }
    }
}
