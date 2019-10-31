package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * the Number class
 * representing an integer
 * 
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Number extends Expression
{
    private int value;
    
    /**
     * the constructor for the number
     * 
     * @param value is the value of the number
     */
    public Number(int value)
    {
        this.value = value;
    }
    
    /**
     * Evaluation function for a number expression
     * 
     * @param env is the environment in which the expression is evaluated
     * @return the int value of the expression
     */
    public int eval(Environment env)
    {
        return value;
    }
    
    /**
     * Writes to the file using Emitter
     * MIPS code stores the number value in $v0
     * 
     * @param e Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}