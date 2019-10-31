package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * An assignment operator 
 * 
 * @author Richard Wang
 * @version 03/20/2018
 * @version 03/20/2018
 *
 */
public class Assignment extends Statement
{
    private String var;
    private Expression exp;
    /**
     * constructor for an assignment
     * @param var the variable name for the assignment operator
     * @param exp the value to be assigned to the variable
     */
    public Assignment(String var, Expression exp)
    {
        this.exp = exp;
        this.var = var;
    }
    
    /**
     * Execution function for the assignment statement
     * 
     * @param env is the environment in which the statement is executed
     */
    public void exec(Environment env)
    {
        env.setVariable(var, exp.eval(env));
    }
    
    /**
     * Writes to the file using Emitter 
     * MIPS code assigns the value to the variable
     * 
     * @param e
     *            Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        //leaves result in $v0
        exp.compile(e);
        
        if (e.isLocalVariable(var))
        {
            int offset = e.getOffSet(var);
            e.emit("sw $v0 " + offset + "($sp)");
        }
        else
        {
            e.emit("sw $v0 var" + var);
        }
    }
}