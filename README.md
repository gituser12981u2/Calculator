# Abstract

- Mathematical expression parser

  - made up of a Main, Lexer, MemoryPool, Parser, Trie, Node, and Token class

> This program is able to parse a user inputted mathematical expression. The user will first be prompted to but in a mathematical expression then that expression will be tokenized in the `Lexer` class. The tokenization method in the `Lexer` class will sort through each character in the string and use a `FSM` to determine the state in which that character is in: `DIGIT`, `OPERATOR`, `PARENTHESIS`, or `WHITESPACE`; The program will treat each case in the `FSM` as it should and tokenize the string checking for errors along the way. Reused operators will be sent to the `Memory Pool` for multiple reference calls to optimize memory. This tokenized string will be sent to the `Parser` class where the mathematical expression will be evaluated. The expression is parsed using a `Trie` which builds up a tree of numbers that branch off the operators to ensure proper PEMDAS evaluation. The `Parser` will assign a proper precedence to each token to construct the trie properly. The `Trie` will then help the `Parser` evaluate each part and send the final answer back to the user.

## Lexer

### Token

#### Memory Pool

##### The Memory Pool pooles in highly used tokens to be stored in memory in one instance instead of several eg

    - Operators
    - Some highly used numbers

 > Uses a custom data structure to handle both operators and numbers in the most proficent way

###### A more in-depth look - Basic uses of the Memory Pool

> The Memory pool will serve as a `highly` specialized garbage collector that will improve performance for the calculator. In calculations the only things often repeated are operation and certain numbers. So the Memory Pool will save memory by pre-allocating the amount of operators--eg however many operators there are--and every time there is a operator called for in the user input, it will be referenced to the memory where that operator is already present. For example, an expression such as (3 *4 + 5* 3) has two multiplication operators. The memory pool will reference the two operators into one place in memory and make a note for the program that there is a scale of 2 multiplication operators in index 1 and 5.

##### Further uses in functions

> The Memory Pool will also store functions in a longer term way

## Parser

### Trie

#### Node

## Main
