package Calculator;
import java.util.ArrayList;
import java.util.List;

import Calculator.Token.TokenType;

abstract class Lexer {
    protected enum State {
        DIGIT,
        OPERATOR,
        PARANTHESIS,
        WHITESPACE;
    }

    public List<Token> tokenize(String input) {
        input = "1 + 3";
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        input = input.trim();
        State currentState = State.WHITESPACE;

        int openParan = 0;
        int closedParan = 0;


        if (input.isEmpty())
            System.out.println("There is not input");

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);


            if (c == '(') 
                openParan++;

            if (c == ')') 
                closedParan++;
            
            if (openParan != closedParan) 
                throw new IllegalStateException("Unbalanced Parentheses");

            switch (currentState) {
                case WHITESPACE:
                    if (Character.isWhitespace(c)) {

                    } else if (Character.isDigit(c) || c == '(') {
                        currentState = State.DIGIT;
                        i--;
                    } else if (isOperator(c)) {
                        currentState = State.OPERATOR;
                        i--;
                    } else {
                        handleNonMatchingCharacter(c, currentToken, currentState, tokens);
                    }
                    break;
                case DIGIT:
                    if (Character.isWhitespace(c)) {
                        continue;
                    }
                    if (c == '-' && (i == 0 || (isOperator(input.charAt(i - 1)) || input.charAt(i - 1) == '('))) {
                        currentToken.append(c);
                    }
                    if (Character.isDigit(c)) {
                        currentToken.append(c);
                    } else if (isOperator(c) || c == '(' || c == ')') {
                        if (c == '(') { // implicit multiplication
                            tokens.add(new Token(TokenType.OPERATOR, "*"));
                        }
                        tokens.add(new Token(getTokenType(currentState), currentToken.toString()));
                        currentToken = new StringBuilder();
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
                        if (i > 0 && (input.charAt(i - 1) == '(' || Character.isDigit(input.charAt(i - 1))) && c == '-') {
                            currentToken.append(c);
                            currentState = State.DIGIT;
                            break;
                        }
                        tokens.add(new Token(TokenType.OPERATOR, currentState.toString()));
                        currentToken = new StringBuilder();
                        currentToken.append(c);
                    } else if (Character.isDigit(c) || c == '(') {
                        tokens.add(new Token(TokenType.OPERATOR, currentToken.toString()));
                        currentToken = new StringBuilder();
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
                        if (i+1 < input.length() && Character.isDigit(input.charAt(i+1))) {
                            tokens.add(new Token(TokenType.OPERATOR, "*"));
                        }
                    }
                    tokens.add(new Token(getTokenType(currentState), currentToken.toString()));
                    currentToken = new StringBuilder();
                    currentState = State.PARANTHESIS;
                    i--;
                    break;
                default:
                    break;
            }
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

    private void handleNonMatchingCharacter(char c, StringBuilder currentToken, Lexer.State currentState, List<Token> tokens) {
        if (!(Character.isDigit(c) && !isOperator(c) && c == '(' && c == ')')) {
            throw new IllegalArgumentException("error: illegal character " + c);
        }
    }
}

