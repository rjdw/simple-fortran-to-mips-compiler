package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * The ProcedureDeclaration class is a procedure declaration
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class ProcedureDeclaration extends Statement
{
    private String id;
    private List<String> paramList;
    private Statement stmt;
    private List<String> varList;
    

    /**
     * The constructor for the ProcedureDeclaration class
     * with no parameters
     * @param id is the id of the procedure
     * @param stmt the statement in the procedure
     */
    public ProcedureDeclaration(String id, Statement stmt)
    {
        this.id = id;
        this.paramList = null;
        this.stmt = stmt;
        this.varList = null;
    }
    /**
     * The constructor for the ProcedureDeclaration class
     * with no parameters
     * @param id is the id of the procedure
     * @param stmt the statement in the procedure
     * @param varList is the list of local variable names
     */
    public ProcedureDeclaration(String id, Statement stmt, List<String> varList)
    {
        this.id = id;
        this.paramList = null;
        this.stmt = stmt;
        this.varList = varList;
    }
    /**
     * The constructor for the ProcedureDeclaration class
     * @param id is the id of the procedure
     * @param paramList is the list of parameters for the procedure
     * @param stmt the statement in the procedure
     */
    public ProcedureDeclaration(String id, List<String> paramList, Statement stmt)
    {
        this.id = id;
        this.paramList = paramList;
        this.stmt = stmt;
        this.varList = null;
    }
    /**
     * The constructor for the ProcedureDeclaration class
     * @param id is the id of the procedure
     * @param paramList is the list of parameters for the procedure
     * @param stmt the statement in the procedure
     * @param varList is the list of local variable names
     */
    public ProcedureDeclaration(String id, List<String> paramList, Statement stmt, 
                                    List<String> varList)
    {
        this.id = id;
        this.paramList = paramList;
        this.stmt = stmt;
        this.varList = varList;
    }
    /**
     * Gets the varList
     * @return the varList, a string of local variable names
     */
    public List<String> getVarList()
    {
        return varList;
    }
    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }
    
    /**
     * @return the paramList
     */
    public List<String> getParamList()
    {
        return paramList;
    }
    
    /**
     * @param paramList the paramList to set
     */
    public void setParamList(List<String> paramList)
    {
        this.paramList = paramList;
    }
    
    /**
     * @return the stmt
     */
    public Statement getStmt()
    {
        return stmt;
    }
    /**
     * @param stmt the stmt to set
     */
    public void setStmt(Statement stmt)
    {
        this.stmt = stmt;
    }
    /**
     * Executes the ProcedureDeclaration
     * 
     * @param env is the environment in which the
     * ProcedureDeclaration
     */
    public void exec(Environment env)
    {
        env.setProcedure(id, this);
    }
    
    /**
     * Compiles the ProcedureDeclaration 
     * @param e is the Emitter used to write to the file
     */
    public void compile(Emitter e)
    { 
        e.emit("");
        e.emit("proc" + id + ":");
        
        //for the return value
        e.emit("li $v0 0");
        e.emitPush();
        
        //for the rest of the local variables
        for (int i = 0; i<varList.size(); i++)
        {
            e.emit("li $v0 0");
            e.emitPush();
        }
        
        e.setProcedureContext(this);
        
        stmt.compile(e);
        
        //pop all local variables
        for (int i = 0; i<varList.size(); i++)
        {
            e.emitPop();
        }
        
        
        //int offset = e.getOffSet(id);
        e.emitPop();
        //e.emit("lw $v0 " + offset + "($sp)");
        e.emit("move $v0 $t0");
        
        e.emit("jr $ra");
        e.emit("");
        e.clearProcedureContext();
    }
}
