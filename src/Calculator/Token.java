package Calculator;

public class Token {
    enum TokenType {
        NUMBER,
        OPERATOR,
        PARENTHESIS,
        WHITESPACE;
    }

    private final TokenType type;
    private final String value;
    private int index;
    private int priority;

    public Token(TokenType type, String value, int index) {
        this.type = type;
        this.value = value;
        this.index = index;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getIndex() {
        return index;
    }

}
