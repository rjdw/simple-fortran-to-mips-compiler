package ast;

import emitter.Emitter;
import environment.Environment;

/**
 * The Writeln class represents the WRITELN statement.
 * WRITELN takes an expression as the parameter and prints it.
 * 
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Writeln extends Statement
{
    private Expression exp;
    
    /**
     * constructor for the writeln
     * @param exp the parameter of the writeln statement
     */
    public Writeln(Expression exp)
    {
        this.exp = exp;
    }
    
    /**
     * Execution function for the writeln statement
     * 
     * @param env is the environment in which the statement is executed
     */
    public void exec(Environment env)
    {
        System.out.println(exp.eval(env));
    }
    
    /**
     * Writes to the file using Emitter
     * MIPS code stores the parameter value in $v0
     * then it prints the integer
     * 
     * @param e Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        
        //prints newline
        e.emit("addi $a0, $0, 0xA");
        e.emit("addi $v0, $0, 0xB");
        e.emit("syscall");
    }
}