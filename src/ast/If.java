package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The if class is an if statement
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class If extends Statement
{
    private Condition condition;
    private Statement then;
    
    /**
     * constructor for the if statement
     * 
     * @param condition is the condition
     * @param then is the expression after the then
     */
    public If(Condition condition, Statement then)
    {
        this.condition = condition;
        this.then = then;
    }
    
    /**
     * Executes the if statement
     * 
     * @param env is the environment in which the
     * if statement is executed
     */
    public void exec(Environment env)
    {
        if (condition.eval(env))
        {
            then.exec(env);
        }
    }
    
    /**
     * Compiles the if statement into mips code
     * generates a new label for skipping the if statement 
     * if the condition is not satisfied
     * 
     * @param e Emitter which will write to the file
     */
    public void compile(Emitter e)
    {
        int labelID = e.nextLabelID();
        String skipLabel = "endif" + labelID;
        condition.compile(e, skipLabel);
        then.compile(e);
        //label for condition not satisfied
        e.emit(skipLabel + ": ");
    }
}
