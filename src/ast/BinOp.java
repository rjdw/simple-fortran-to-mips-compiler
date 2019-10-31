package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The BinOp class represents a binary operator
 * 
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class BinOp extends Expression
{
    private Expression exp1;
    private Expression exp2;
    private String op;
    
    /**
     * Constructor for the binary operator 
     * comparing two expressions
     * 
     * @param op the operator
     * @param exp1 the first expression
     * @param exp2 the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2)
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }
    
    /**
     * Evaluation function for a BinOp expression
     * 
     * @param env is the environment in which the expression is evaluated
     * @return the int value of the expression
     */
    public int eval(Environment env)
    {
        int first = exp1.eval(env);
        int second = exp2.eval(env);
        if (op.equals("+"))
        {
            return first + second;
        }
        else if (op.equals("-"))
        {
            return first - second;
        }
        else if (op.equals("*"))
        {
            return first * second;
        }
        else if (op.equals("/"))
        {
            return first/second;
        }
        
        //wrong op
        return -1;
    }
    
    /**
     * Writes to the file using Emitter
     * MIPS code executes the binop
     * then leaves the result in $v0
     * 
     * @param e Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        exp1.compile(e);
        
        //push
        e.emitPush();
        
        exp2.compile(e);
        
        e.emitPop();
        if (op.equals("+"))
        {
            e.emit("add $v0 $t0 $v0");
           
        }
        else if (op.equals("-"))
        {
            e.emit("sub $v0 $t0 $v0");
        }
        else if (op.equals("*"))
        {
            e.emit("mult $t0 $v0");
            e.emit("mflo $v0");
        }
        else if (op.equals("/"))
        {
            e.emit("div $t0 $v0");
            e.emit("mflo $v0");
        }
    }
}