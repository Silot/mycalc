/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpncalc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author mitsikeli
 */
public class RPNcalculator {

    Scanner in = new Scanner(System.in);
    
    private String input;
    
    private double Index;
    private String StringIndex;

    //Stack pou krataei mono telesteous 
    private Deque<Token> InputStack = new ArrayDeque<Token>();

    //Current token index
    private int CurrentToken = 0;

    private boolean PrintingLogging = false;

    public RPNcalculator() {
        calculate();
    }

    public RPNcalculator(boolean log) {
        PrintingLogging = log;
        calculate();
    }

    //print push/pop in stack
    public void setLogging(boolean enabled) {
        PrintingLogging = enabled;
    }

    private double calculate() {
  
        input = in.nextLine();

        //check illegal input
        if (input == null) {
            throw new NullPointerException("Input cannot be null");
        }

        //Reset the stack if already present
        if (InputStack.size() != 0) {
            InputStack = new ArrayDeque<Token>(input.length());
            CurrentToken = 0;
        }

        //Tokenize the input String and begin to process the tokens
        StringTokenizer tokenizer = new StringTokenizer(input, " ");
        while (tokenizer.hasMoreTokens()) {
            processToken(tokenizer.nextToken());
            CurrentToken++;
        }
        //Only one shall remain..
        if (InputStack.size() != 1) {
            throw new IllegalArgumentException("Invalid result of " + InputStack.size());
        }
        return InputStack.pop().getValue();
       
    }

    private void processToken(String token) {

        //Single Char 
        if (token.length() == 1) {
            char temp = token.charAt(0);

            //Digit
            if (Character.isDigit(temp)) {
                InputStack.push(new TokenValue(token));
                if (PrintingLogging) {
                    System.out.println("Pushing token " + token);
                }
            } else if (isMathSymbol(temp)) {
                //math symbol 
                proscessOperator(token);
            } else //Illegal Operator
            {
                throw new IllegalArgumentException("Invalid token character" + token
                        + " at position " + CurrentToken + ".");
            }
        } else {
            InputStack.push(new TokenValue(token));
            if (PrintingLogging) {
                System.out.println("Pushing token " + token);
            }
        }

    }

    /**
     * Determines if a token is a math symbol (+,-,/,*) or not
     *
     * @param token The token being examined
     * @return True if a math symbol (+,-,/,*), false otherwi se
     */
    private boolean isMathSymbol(char token) {
        switch (token) {
            case '+':
            case '-':
            case '/':
            case '*':
                return true;
            default:
                return false;
        }

    }

    /**
     * An operator was detected as the current token and needs to be processed
     *
     * @param operand The operand detected
     */
    private void proscessOperator(String operand) {

        if (InputStack.size() < 2) {
            throw new IllegalArgumentException("Does not have two operands for operation " + operand + " at position " + CurrentToken + ".");
        }

        char operator = operand.charAt(0);
        double first = InputStack.pop().getValue();
        double second = InputStack.pop().getValue();
        TokenValue result = null;

        if (PrintingLogging) {
            System.out.println("Popping " + first + " and " + second);
        }

        //check operator and perform action
        switch (operator) {

            case '+':
                result = new TokenValue(second + first);
                InputStack.push(result);
                break;
            case '-':
                result = new TokenValue(second - first);
                InputStack.push(result);
                break;
            case '*':
                result = new TokenValue(second * first);
                InputStack.push(result);
                break;
            case '/':
                if (first == 0) {
                    throw new IllegalArgumentException("Cannot divide by 0.");
                }

                result = new TokenValue(second / first);
                InputStack.push(result);
                break;
        }

        if (result != null && PrintingLogging) {
            System.out.println("Pushing result " + result.getValue());
        }
    }
}
