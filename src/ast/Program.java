package ast;

import java.util.List;
import emitter.Emitter;

import environment.Environment;

/**
 * The Program class is a full Program
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Program 
{
    private List<String> varList;
    private List<ProcedureDeclaration> decList;
    private Statement stmt;
    
    /**
     * The constructor for the Program
     * @param decList a list of the procedure declarations
     * @param stmt is the statement in the program
     * @param varList is the list of the variable names in the program
     */
    public Program(List<String> varList, List<ProcedureDeclaration> decList, Statement stmt)
    {
        this.varList = varList;
        this.decList = decList;
        this.stmt = stmt;
    }
    
    /**
     * Executes the program
     * @param env is the environment in which the program is executed
     */
    public void exec(Environment env)
    {
        for (ProcedureDeclaration dec : decList)
        {
            dec.exec(env);
        }
        stmt.exec(env);
    }
    
    /**
     * Takes an output file name and writes to that file using Emitter
     * @param fileName is the string for the name of the file to be made
     */
    public void compile(String fileName)
    {
        Emitter e = new Emitter(fileName + ".asm");
        
        //.data section with variable declarations
        //variable default value = 0
        e.emit(".data");
        for (String varName : varList)
        {
            e.emit("var" + varName + ": .word 0");
        }
        
        //.text section of mips code
        e.emit(".text");
        e.emit(".globl main");
        e.emit(" main:  #QTSPIM will automatically look for main");
        e.emit("# future code will go here");
        e.emit("\n");
        
        stmt.compile(e);
        
        e.emit("\n");
        e.emit("li $v0 10");
        e.emit("syscall   # halt");
        
        for (ProcedureDeclaration d: decList)
        {
            d.compile(e);
        }
        
        e.close();
    }
}
