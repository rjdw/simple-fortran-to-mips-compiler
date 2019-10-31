package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The condition class is a condition 
 * in an if statement or while loop
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Condition
{
    private Expression exp1;
    private String relop;
    private Expression exp2;
    
    /**
     * constructor for the condition
     * 
     * @param exp1 is the first expression
     * @param relop is the operator
     * @param exp2 is the second expression
     */
    public Condition(Expression exp1, String relop, Expression exp2)
    {
        this.exp1 = exp1;
        this.relop = relop;
        this.exp2 = exp2;
    }
    
    /**
     * Evaluates the boolean value of the condition
     * 
     * @param env is the environment in which the 
     * condition is evaluated
     * @return the boolean value of the condition
     */
    public boolean eval(Environment env)
    {
        int first = exp1.eval(env);
        int second = exp2.eval(env);
        if (relop.equals("<="))
        {
            return first <= second;
        }
        else if (relop.equals(">="))
        {
            return first >= second;
        }
        else if (relop.equals("="))
        {
            return first == second;
        }
        else if (relop.equals("<>"))
        {
            return first != second;
        }
        else if (relop.equals("<"))
        {
            return first < second;
        }
        else if (relop.equals(">"))
        {
            return first > second;
        }
        return false;
    }
    
    /**
     * Generates MIPS code to evaluate the condition
     * and sends the code to the specified targetLabel string
     * 
     * @param e the Emitter used to write to the file
     * @param targetLabel the string for the label that is the target of the jump
     */
    public void compile(Emitter e, String targetLabel)
    {
        //value of exp1 in $v0
        exp1.compile(e);
        //moves exp1 to $t0
        e.emit("move $t0 $v0");
        
        //value of exp2 in $v0
        exp2.compile(e);
        
        //emits the opposite test of the relop
        //ex. relop is <=, then emit >
        //easier for if and while label jump
        if (relop.equals("<="))
        {
            e.emit("bgt $t0 $v0 " + targetLabel);
        }
        else if (relop.equals(">="))
        {
            e.emit("blt $t0 $v0 " + targetLabel);
        }
        else if (relop.equals("="))
        {
            e.emit("bne $t0 $v0 " + targetLabel);
        }
        else if (relop.equals("<>"))
        {
            e.emit("beq $t0 $v0 " + targetLabel);
        }
        else if (relop.equals("<"))
        {
            e.emit("bge $t0 $v0 " + targetLabel);
        }
        else if (relop.equals(">"))
        {
            e.emit("ble $t0 $v0 " + targetLabel);
        }
    }
}
