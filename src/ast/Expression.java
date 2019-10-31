package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The abstract class Expression 
 * represents an expression in the ast
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public abstract class Expression
{
    /**
     * evaluation function for all Expressions
     * @param env is the environment in which the Expression is evaluated
     * @return the int value of the expression
     */
    public abstract int eval(Environment env);
    
    /**
     * Compile method for Expression
     * @param e Emitter which will write to the file
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}