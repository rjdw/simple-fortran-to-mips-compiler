package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The abstract class Statment 
 * represents a statement in the ast
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public abstract class Statement
{
    /**
     * Executes the statement
     * 
     * @param env is the environment in which the statement is executed
     */
    public abstract void exec(Environment env);
    
    /**
     * Compile method for the Statement class
     * @param e Emitter which will write to the file
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }
}
