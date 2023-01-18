## Abstract

## Memory Pool

### The Memory Pool pooles in highly used tokens to be stored in memory in one instance instead of several eg

    - Operators
    - Some highly used numbers

 > Uses a custom data structure to handle both operators and numbers in the most proficent way

#### A more in-depth look - Basic uses of the Memory Pool

> The Memory pool will serve as a `highly` specilized garbage collector that will improve preformance for the calculator. In calculations the only things often repetead are operation and certain numbers. So the Memory Pool will save memory by pre-allocating the amount of operators--eg however many operators there are--and everytime there is a operator called for in the user input, it will be referenced to the memory where that operator is already present. For example, an expression such as (3 * 4 + 5 * 3) has two multiplication operators. The memory pool will reference the two operators into one place in memory and make a note for the program that there is a scale of 2 multiplication operators in index 1 and 5.