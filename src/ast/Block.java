package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * The Block class represents a block statment
 * 
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Block extends Statement
{
    private List<Statement> stmts;

    /**
     * The constructor for a block statement
     * 
     * @param stmts
     *            a list of statements in the block
     */
    public Block(List<Statement> stmts)
    {
        this.stmts = stmts;
    }

    /**
     * Execution function for a block statement
     * 
     * @param env
     *            is the environment in which the statement is executed
     */
    public void exec(Environment env)
    {
        for (Statement stmt : stmts)
        {
            stmt.exec(env);
        }
    }

    /**
     * Writes to the file using Emitter MIPS code executes all statements in the
     * block expression
     * 
     * @param e
     *            Emitter that writes the code to a file
     */
    public void compile(Emitter e)
    {
        for (Statement stmt : stmts)
        {
            stmt.compile(e);
        }
    }
}
