package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The While class is a while loop
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class While extends Statement
{
    private Condition condition;
    private Statement doStmt;
    
    /**
     * The constructor for the while class
     * @param condition the condition for the while loop
     * @param doStmt the statement in the while loop
     */
    public While(Condition condition, Statement doStmt)
    {
        this.condition = condition;
        this.doStmt = doStmt;
    }
    
    /**
     * Executes the while loop statement
     * 
     * @param env is the environment in which the
     * while loop is executed
     */
    public void exec(Environment env)
    {
        while (condition.eval(env))
        {
            doStmt.exec(env);
        }
    }
    
    /**
     * Compiles the while loop statement into mips code
     * generates a new label for skipping the while loop statement 
     * if the condition is not satisfied
     * 
     * @param e Emitter which will write to the file
     */
    public void compile(Emitter e)
    {
        //startLabel for looping
        int labelIDStart = e.nextLabelID();
        String startLabel = "startWhile" + labelIDStart;
        e.emit(startLabel +":");
        
        //end label for breaking
        int labelIDEnd = e.nextLabelID();
        String endLabel = "endWhile" + labelIDEnd;
        
        //Jumps to endLabel if condition not satisfied
        condition.compile(e, endLabel);
        doStmt.compile(e);
        //loops back to beginning
        e.emit("j " + startLabel);
        
        //label for condition not satisfied
        e.emit(endLabel + ": ");
    }
}
