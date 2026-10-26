import java.util.InputMismatchException;
import java.util.Scanner;

/** STUDENT FILE: Complete evaluate. Leave all other project files unchanged. */
public class PostfixEvaluator {
    /**
     * Evaluates a space-separated postfix expression using integer arithmetic.
     * Supported operators: +, -, *, / (integer division).
     *
     * @throws PostFixException for blank, malformed, or invalid expressions
     * @throws ArithmeticException for division by zero
     */
    public static int evaluate(String expression) {
        ArrayStack<Integer> stack = new ArrayStack<>(100);
        Scanner tokens = new Scanner(expression);
        int operand1;
        int operand2;
        int numberSymbols = 0;
        int numberNumbers = 0;
        tokens.useDelimiter(" ");
        if(!tokens.hasNext())
        {
            tokens.close();
            throw new PostFixException("No Expression");
        }
        while(tokens.hasNext())
        {
            operand1 = 0;
            operand2 = 0;
            String next = tokens.next();
            if(next.equals("+"))
            {
                numberSymbols++;
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand1 = stack.top();
                stack.pop();
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand2 = stack.top();
                stack.pop();
                stack.push(operand1+operand2);
            }
            else if(next.equals("-"))
            {
                numberSymbols++;
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand1 = stack.top();
                stack.pop();
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand2 = stack.top();
                stack.pop();
                stack.push(operand2-operand1);
            }
            else if(next.equals("*"))
            {
                numberSymbols++;
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand1 = stack.top();
                stack.pop();
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand2 = stack.top();
                stack.pop();
                stack.push(operand2*operand1);
            }
            else if(next.equals("/"))
            {
                numberSymbols++;
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand1 = stack.top();
                stack.pop();
                if(stack.isEmpty())
                {
                    tokens.close();
                    throw new PostFixException("Too few operands.");
                }
                operand2 = stack.top();
                if(operand1==0)
                {
                    tokens.close();
                    throw new ArithmeticException("Cannot divide by 0.");
                }
                stack.pop();
                stack.push(operand2/operand1);
            }
            else
            {
                try{
                    stack.push(Integer.parseInt(next));
                    numberNumbers++;
                }
                catch(NumberFormatException e)
                {
                    if(!e.getMessage().contains("For input string: \"\""))
                    {
                        tokens.close();
                        throw new PostFixException("Invalid token.");
                    }
                }
            }
        }
        // TODO 1: Process tokens one at a time.
        // TODO 2: If a token is an integer, push it onto the stack.
        // TODO 3: If it is +, -, *, or /, pop two operands.
        //         IMPORTANT: the first popped number is the RIGHT operand.
        // TODO 4: Calculate and push the result.
        // TODO 5: Reject unknown tokens and expressions with too few operands.
        // TODO 6: After processing, require EXACTLY one result on the stack.
        //         Catch stack underflow/overflow or check before operations.
        // Close the Scanner when you are finished.

        tokens.close();
        //throw new UnsupportedOperationException("TODO: implement postfix evaluation");
        if(numberNumbers==numberSymbols+1)
            return stack.top();
        throw new PostFixException("Too many operands.");
    }
}
