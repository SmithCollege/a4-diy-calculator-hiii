package a4;

import java.util.ArrayDeque;

public class Postfix {

    /**
     * @param tokens
     * @return result of operations that are in postfix form
     */
    public static Double postfix(ArrayDeque<Object> tokens) {
        Double num1, num2;
        char operator;
        ArrayDeque<Object> stack = new ArrayDeque<>();

        while(!tokens.isEmpty()){
            Object token = tokens.removeFirst();

            if(token instanceof Double){
                stack.push(token);
            }else if(token instanceof Character){
                if (stack.size()<2) {
                    throw new IllegalArgumentException("You don't have enough operands");
                }else{
                    // get two operands from the stack
                    num1 = (Double) stack.pop();
                    num2 = (Double) stack.pop();
                    operator = (Character) token;
                    double result;

                    // ! since it's a stack num1 is actually the 2nd operand
                    switch (operator) {
                        case '+':
                            result = num2 + num1;
                            break;
                        case '-':
                            result = num2 - num1;
                            break;
                        case '*':
                            result = num2 * num1;
                            break;
                        case '/':
                            if(num1 == 0.0){
                                throw new RuntimeException("Can't divide by zero");
                            }else{
                                result = num2/num1;
                            }
                            break;
                        case '^':
                            result = Math.pow(num2, num1);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid Operator");
                    }
                    // push the result to the stack
                    stack.push(result);
                }
            }else{
                throw new IllegalArgumentException("Invalid Token");
            }
        }

        // at this point the stack should only have 1 element: the result

        if (stack.size()>1) {
            throw new IllegalArgumentException("Too many operands or too few operators");
        }else{
            return (Double) stack.pop(); // return the result
        }
    }
}