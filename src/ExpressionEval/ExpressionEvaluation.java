//
// Project: ExpressionEvaluation
// TODO 1
// Author: Erik Rodriguez
//
package ExpressionEval;

public class ExpressionEvaluation {
    /* --- Do not modify below ---*/
    public static void main(String[] args) {
        String expr, postFix;
        int result;

        expr = "2 + ( 4 + 3 * 2 + 1 ) / 3";
        postFix = toPostFix(expr); // 2 4 3 2 * + 1 + 3 / +
        result = evalPostFix(postFix); // 5
        System.out.println("InFix:   " + expr);
        System.out.println("PostFix: " + postFix);
        System.out.println("Result:  " + result);

        expr = "3 * ( 14 + 5 ) / 2";
        postFix = toPostFix(expr);
        result = evalPostFix(postFix);
        System.out.println("InFix:   " + expr);
        System.out.println("PostFix: " + postFix);
        System.out.println("Result:  " + result);

        expr = "2 * 3 / ( 2 - 1 ) + 5 * -3";
        System.out.println("InFix:   " + expr);
        postFix = toPostFix(expr);
        System.out.println("PostFix: " + postFix);
        result = evalPostFix(postFix);
        System.out.println("Result:  " + result);

        expr = "7 - ( 2 * 3 + 5 ) * ( 8 - 4 / 2 )";
        postFix = toPostFix(expr);
        result = evalPostFix(postFix);
        System.out.println("InFix:   " + expr);
        System.out.println("PostFix: " + postFix);
        System.out.println("Result:  " + result);
    }
    /* --- Do not modify above ---*/

    // converts infix expression passed in to postfix
    // returns the postfix expression
    public static String toPostFix(String infixExpr) {
        // TODO 2
        String output = "";
        char[] operators = {'+', '-', '*', '/'};
        GenericStack<Character> genStack = new GenericStack<>();
        char[] cArray = infixExpr.toCharArray();

        // replacing negatives with tilda to show that it's a negation of a value, not subtracting a value
        for (int i = 0; i < cArray.length; i++) {
            if (cArray[i] == '*' || cArray[i] == '/') {
                if (cArray[i + 2] == '-') {
                    cArray[i + 2] = '~';
                }
            }
        }

        for (char token : cArray) {
            int num = Character.getNumericValue(token); // This method returns the numeric value of the character, as a nonnegative int value; -2 if the character has a numeric value that is not a nonnegative integer; -1 if the character has no numeric value.

            // if the character has a numeric value
            if (num != -1) {
                output += token;
            }

            boolean isAnOperator = charInArray(token, operators);

            if (token == '~'){
                genStack.push(token);
            }

            if (isAnOperator && genStack.isEmpty()) {
                genStack.push(token);
            } else if (isAnOperator && !genStack.isEmpty()) {
                // only start popping when the token is of lower priority than the stack operator
                boolean currHasPrecedence = currHasPrecedence(token, genStack.peek());
                if (!currHasPrecedence) {
                    while (!genStack.isEmpty() && genStack.peek() != '(') {
                        char popped = genStack.peek();
                        genStack.pop();
                        output += popped;
                    }
                }
                genStack.push(token);
            }

            if (token == '(') {
                genStack.push(token);
            }

            if (token == ')') {
                while (genStack.peek() != '(') {
                    char popped = genStack.peek();
                    genStack.pop();
                    output += popped;
                }
                genStack.pop(); // it will pop and ignore '(' since it stops there in the while loop
            }
        }

        while (!genStack.isEmpty()) {
            char popped = genStack.peek();
            genStack.pop();
            output += popped;
        }

        return output;
    }

    // converts postfix expression passed in
    // returns the resulting value
    public static int evalPostFix(String postfixExpr) {
        // TODO 3
        GenericStack<Integer> genStack = new GenericStack<>();
        char[] cArray = postfixExpr.toCharArray();

        for (char token : cArray) {
            if (token == '~')
            {
                int popped = genStack.peek();
                genStack.pop();
                genStack.push(-popped);
                continue;
            }
            int num = Character.getNumericValue(token); // -1 if the character has no numeric value.
            if (num != -1) {
                genStack.push(num);
            } else {
                int firstPopped = genStack.peek();
                genStack.pop();
                boolean isEmpty = genStack.isEmpty();
                int secondPopped = 0;
                if (!isEmpty) {
                    secondPopped = genStack.peek();
                }
                genStack.pop();
                int result = evaluate(token, firstPopped, secondPopped);
                genStack.push(result);
            }
        }
        return genStack.peek();
    }

    // TODO 4
    // add any utility methods (if any) here

    // checks if the current operator has a higher priority than the stack peek operator
    private static boolean currHasPrecedence(char currentOperator, char stackOperator) {
        if (currentOperator == '+' || currentOperator == '-') {
            return stackOperator != '*' && stackOperator != '/' && stackOperator != '~';
        }
        return true;
    }

    // searches for a char in the given array and returns if it is found or not
    private static boolean charInArray(char charToSearch, char[] charArray) {
        for (char token : charArray) {
            if (token == charToSearch) {
                return true;
            }
        }
        return false;
    }

    // evaluates a given char operator and returns a result from the operation
    private static int evaluate(char operator, int firstNumber, int secondNumber) {
        switch (operator) {
            case '+':
                return secondNumber + firstNumber;
            case '-':
                return secondNumber - firstNumber;
            case '*':
                return secondNumber * firstNumber;
            case '/':
                return secondNumber / firstNumber;
        }
        return 0;
    }
}

// TODO 5
/*
InFix:   2 + ( 4 + 3 * 2 + 1 ) / 3
PostFix: 2432*+1+3/+
Result:  5
InFix:   3 * ( 14 + 5 ) / 2
PostFix: 3145+2/*
Result:  4
InFix:   2 * 3 / ( 2 - 1 ) + 5 * -3
PostFix: 2321-/*53~*+
Result:  -9
InFix:   7 - ( 2 * 3 + 5 ) * ( 8 - 4 / 2 )
PostFix: 723*5+842/-*-
Result:  -59
*/
