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
    private int priority;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
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

}
