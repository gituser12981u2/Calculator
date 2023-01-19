package Calculator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Calculator.Token.TokenType;

class Lexer {
    enum State {
        DIGIT,
        OPERATOR,
        PARANTHESIS,
        WHITESPACE;
    }

    private LinkedList<Token> tokenList;
    private State currentState;
    private MemoryPool memoryPool;

    public Lexer() {
        this.memoryPool = new MemoryPool();
        this.tokenList = new LinkedList<>();
    }

    public List<Token> tokenize(String input) {
        StringBuilder currentToken = new StringBuilder();
        currentState = State.WHITESPACE;
        int index = 0;

        try {
            while (index < input.length()) {
                char c = input.charAt(index);

                // Start FSM
                switch (currentState) {
                    case WHITESPACE:
                        if (Character.isWhitespace(c))
                            continue;
                    case DIGIT:
                        if (Character.isWhitespace(c)) {
                            continue;
                        }
                        if (c == '-' && (index == 0 || (isOperator(input.charAt(index - 1)) ||
                                input.charAt(index - 1) == '('))) {
                            currentToken.append(c);
                        }
                        if (Character.isDigit(c)) {
                            currentToken.append(c);
                        } else if (isOperator(c) || c == '(' || c == ')') {
                            if (c == '(') { // implicit multiplication
                                tokens.add(new Token(TokenType.OPERATOR, "*"));
                            }
                            tokens.add(new Token(getTokenType(currentState), currentToken.toString()));
                            currentToken.setLength(0);
                            currentState = State.DIGIT;
                            i--;
                        } else {
                            handleNonMatchingCharacter(c, currentToken, currentState, tokens);
                        }
                        break;
                    case OPERATOR:
                        if (Character.isWhitespace(c)) {
                            continue;
                        }
                        if (isOperator(c)) {
                            currentToken.append(c);
                            if (i > 0 && (input.charAt(i - 1) == '(' || Character.isDigit(input.charAt(i - 1)))
                                    && c == '-') {
                                currentToken.append(c);
                                currentState = State.DIGIT;
                                break;
                            }
                            tokens.add(new Token(TokenType.OPERATOR, currentState.toString()));
                            currentToken = new StringBuilder();
                            currentToken.append(c);
                        } else if (Character.isDigit(c) || c == '(') {
                            tokens.add(new Token(TokenType.OPERATOR, currentToken.toString()));
                            currentToken.setLength(0);
                            currentState = State.OPERATOR;
                        } else {
                            handleNonMatchingCharacter(c, currentToken, currentState, tokens);
                        }
                        break;
                    case PARANTHESIS:
                        if (!(Character.isDigit(c) || c == '(' || c == ')')) {
                            handleNonMatchingCharacter(c, currentToken, currentState, tokens);
                        }
                        if (c == ')') { // implicit multiplication
                            if (i + 1 < input.length() && Character.isDigit(input.charAt(i + 1))) {
                                tokens.add(new Token(TokenType.OPERATOR, "*"));
                            }
                        }
                        tokens.add(new Token(getTokenType(currentState), currentToken.toString()));
                        currentToken.setLength(0);
                        currentState = State.PARANTHESIS;
                        i--;
                        break;
                    default:
                        break;
                }
            }
        } catch (java.lang.NullPointerException e) {
            System.out.println("There is no input");
        }
        if (currentToken.length() > 0) {
            tokens.add(new Token(getTokenType(currentState), currentToken.toString()));
        }
        return tokens;
    }

    private TokenType getTokenType(State currentState) {
        switch (currentState) {
            case DIGIT:
                return TokenType.NUMBER;
            case OPERATOR:
                return TokenType.OPERATOR;
            case PARANTHESIS:
                return TokenType.PARENTHESIS;
            default:
                throw new IllegalArgumentException("Invalid state: " + currentState);
        }
    }

    protected boolean isOperator(char c) {
        return "+-*/^".indexOf(c) != -1;
    }

    private void handleNonMatchingCharacter(char c, StringBuilder currentToken, Lexer.State currentState,
            List<Token> tokens) {
        if (!(Character.isDigit(c) && !isOperator(c) && c == '(' && c == ')')) {
            throw new IllegalArgumentException("error: illegal character " + c);
        }
    }
}
