package ast;

import environment.Environment;

/**
 * The For class is a for loop
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class For extends Statement
{
    private String variable;
    private Assignment assign;
    private Expression maxEx;
    private Statement doStmt;
    
    /**
     * The constructor for the for class
     * 
     * @param assign is the assignment for the variable in the for loop
     * @param variable is the id of the variable
     * @param doStmt the statement in the for loop
     * @param maxEx is the expression for the max limiter
     */
    public For(String variable, Assignment assign, Expression maxEx, Statement doStmt)
    {
        this.variable = variable;
        this.assign = assign;
        this.maxEx= maxEx;
        this.doStmt = doStmt;
    }
    
    /**
     * Executes the for loop statement
     * 
     * @param env is the environment in which the
     * for loop is executed
     */
    public void exec(Environment env)
    {
        int max = maxEx.eval(env);
        assign.exec(env);
        while (env.getVariable(variable)<=max)
        {
            doStmt.exec(env);
            env.setVariable(variable, env.getVariable(variable)+1);
        }
    }
}
