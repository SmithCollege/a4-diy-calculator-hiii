package a4;

import java.util.ArrayDeque;

public class Infix {
    /**
     * Ranks operator in reverse order of PEMDAS or BODMAS. It is in reverse order to align with the deque's stack feature.
     * @param token
     * @return integer that gives the operation appropriate priority
     */
    public static int getPrecedence(Character token){
        switch(token){
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    /**
     * converts an infix statement (such as 2+3) into a postfix such as (2 3 +)
     * @param tokens
     * @return Tokens in postfix form
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> stack = new ArrayDeque<>();
        ArrayDeque<Object> queue = new ArrayDeque<>();

        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();
            ArrayDeque<Object> parsedTokens = Tokenizer.readTokens(token.toString());

            if (token instanceof Double) {
                queue.addLast(token);
            }else if(token instanceof Character){
                if ((Character) token =='(') { // if it's an opening bracket, push it to stack
                    stack.push(token);
                }else if ((Character) token == ')') { // if it's a closing bracket, push it to queue and pop it from stack. close brackets until there's no matching opening bracket left
                    while (!stack.isEmpty() && (Character) stack.peek() != '(') {
                        queue.add(stack.pop());
                    }
                    if (stack.isEmpty()) { // if there is no matching opening bracket
                        throw new IllegalArgumentException("There's no matching opening brackets/parenthesis!");
                    }else{
                        stack.pop(); // remove closed bracket from stack
                    }
                }else{
                    while (!stack.isEmpty() && stack.peek() instanceof Character){ // if we're parsing an operator
                        char top = (Character) stack.peek();
                        if (getPrecedence(top) > getPrecedence((Character) token) ||
                                (getPrecedence(top) == getPrecedence((Character) token) && (Character) token != '^')) { // to ensure right associativity of the ^ operator
                            queue.add(stack.pop());

                        }else{
                            break;
                        }
                    }
                    stack.push(token);
                }
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals('(')) {
                throw new IllegalArgumentException("There's a missing closing parenthesis!");
            }else if (stack.element() instanceof Character) {
                queue.add(stack.pop());
            }
        }
        return Postfix.postfix(queue); // returns the postfix version of the calculator input
    }
}
