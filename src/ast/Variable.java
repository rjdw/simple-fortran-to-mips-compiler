package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The variable class represents a variable with a name
 * 
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Variable extends Expression
{
    private String name;
    private boolean isPositive;
    
    /**
     * constructor for a variable 
     * @param name is the name of the variable
     */
    public Variable(String name)
    {
        this.name = name;
        this.isPositive = true;
    }
    
    /**
     * constructor for a variable with isPositive
     * @param name is the name of the variable
     * @param isPositive tells if the variable is has a negative in front
     */
    public Variable(String name, boolean isPositive)
    {
        this.name = name;
        this.isPositive = isPositive;
    }
    
    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Evaluation function for a variable expression
     * 
     * @param env is the environment in which the expression is evaluated
     * @return the int value of the expression
     */
    public int eval(Environment env)
    {
        if (isPositive)
        {
            return env.getVariable(name);
        }
        return env.getVariable(name) * -1;
    }
    
    /**
     * Writes to the file using Emitter
     * MIPS code stores the address of the variable in $t0
     * then the number value of the variable in $v0
     * 
     * @param e Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        if (e.isLocalVariable(name))
        {
            int offset = e.getOffSet(name);
            e.emit("lw $v0 " + offset + "($sp)");
        }
        else
        {
            e.emit("la $t0 var" + name);
            e.emit("lw $v0 ($t0)");
        }
        if (!isPositive)
        {
            e.emit("sub $v0 $0 $v0");
        }
    }
}