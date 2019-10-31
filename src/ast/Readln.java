package ast;

import java.util.Scanner;

import environment.Environment;

/**
 * Reads a line of user input and assigns the 
 * value to the variable
 * @author richardwang007
 * @version 3/20/2018
 *
 */
public class Readln extends Statement
{
    private String param;
    
    /**
     * Constructor for the Readln statement
     *
     * @param param the variable name to be assigned
     */
    public Readln(String param)
    {
        this.param = param;
    }
    
    /**
     * Execution function for the readln statement
     * 
     * @param env is the environment in which the statement is executed
     */
    public void exec(Environment env)
    {
        Scanner scanner = new Scanner(System.in);
        int value = scanner.nextInt();
        scanner.close();
        env.setVariable(param, value);
    }
}
