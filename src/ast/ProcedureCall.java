package ast;

import java.util.List;

import emitter.Emitter;
import environment.Environment;

/**
 * The ProcedureCall class is a procedure declaration
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class ProcedureCall extends Expression
{
    private String id;
    private List<Expression> paramList;
    private boolean isPositive;
    
    /**
     * The constructor for the ProcedureCall class
     * @param id is the id of the procedure
     * @param paramList is the list of arguments for the procedure call
     */
    public ProcedureCall(String id, List<Expression> paramList)
    {
        this.id = id;
        this.paramList = paramList;
        this.isPositive = true;
    }
    
    /**
     * The constructor for the ProcedureCall class
     * @param id is the id of the procedure
     * @param paramList is the list of arguments for the procedure call
     * @param isPositive tells if there is a negative sign in front of the proc call
     */
    public ProcedureCall(String id, List<Expression> paramList, boolean isPositive)
    {
        this.id = id;
        this.paramList = paramList;
        this.isPositive = isPositive;
    }
    
    /**
     * The constructor for the ProcedureCall class
     * @param id is the id of the procedure
     */
    public ProcedureCall(String id)
    {
        this.id = id;
        this.paramList = null;
        this.isPositive = true;
    }
    
    /**
     * The constructor for the ProcedureCall class
     * @param id is the id of the procedure
     * @param isPositive tells if there is a negative sign in front of the proc call
     */
    public ProcedureCall(String id, boolean isPositive)
    {
        this.id = id;
        this.paramList = null;
        this.isPositive = isPositive;
    }
    
    /**
     * Executes the ProcedureCall
     * 
     * @param env is the environment in which the ProcedureCall executed
     * @return the int result of the procedure
     */
    public int eval(Environment env)
    {
        //sub-environment
        Environment child = new Environment(env);
        
        //variable for returning
        child.declareVariable(id, 0);
        
        //set parameters
        ProcedureDeclaration dec = env.getProcedure(id);
        List<String> decParamList = dec.getParamList();
        if (paramList != null)
        {
            for (int i = 0; i<paramList.size(); i++)
            {
                child.declareVariable(decParamList.get(i), paramList.get(i).eval(env));
            }
        }
        dec.getStmt().exec(child);
        int res = child.getVariable(id);
        
        if (!isPositive)
        {
            return res * -1;
        }
        return res;
    }
    
    /**
     * Compiles the ProcedureCall and translates the call to MIPS
     * @param e is the Emitter used to write to the file
     */
    public void compile(Emitter e)
    {
        e.emit("subu $sp, $sp, 4");
        e.emit("sw $ra, ($sp)");
        
        for (Expression exp : paramList)
        {
            exp.compile(e);
            e.emitPush();
        }
        
        e.emit("jal proc" + id);
        if (!isPositive)
        {
            e.emit("sub $v0 $0 $v0");
        }
        
        
        for (int i = 0; i < paramList.size(); i ++)
        {
            e.emitPop();
        }
        
        e.emit("lw $ra, ($sp)");
        e.emit("addu $sp, $sp, 4");
    }
}