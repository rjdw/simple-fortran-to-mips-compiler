package environment;

import java.util.HashMap;
import java.util.Map;

import ast.ProcedureDeclaration;

/**
 * The environment class is an environment where 
 * variables and procedures are defined
 * @author Richard Wang
 * @version 03/20/2018
 *
 */
public class Environment
{
    private Map<String, Integer> varMap;
    private Map<String, ProcedureDeclaration> procedureMap;
    private Environment parent;
    
    /**
     * Constructor for the environment
     */
    public Environment()
    {
        varMap = new HashMap<String, Integer>();
        procedureMap = new HashMap<String, ProcedureDeclaration>();
        parent = null;
    }
    

    /**
     * Constructor for the environment
     * with parent
     * 
     * @param parent is the parent of the environment
     */
    public Environment(Environment parent)
    {
        varMap = new HashMap<String, Integer>();
        procedureMap = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }
    
    /**
     * associates the given variable name with the given value
     * 
     * @param variable is the name of the variable
     * @param value is the value assigned to the variable
     */
    public void declareVariable(String variable, int value)
    {
        varMap.put(variable, new Integer(value));
    }
    
    /**
     * associates the given variable name with the given value
     * 
     * @param variable is the name of the variable
     * @param value is the value assigned to the variable
     */
    public void setVariable(String variable, int value)
    {
        if (varMap.containsKey(variable))
        {
            varMap.put(variable, new Integer(value));
        }
        else
        {
            Environment global = this;
            while (global.getParent() != null)
            {
                global = global.getParent();
            }
            global.getVarMap().put(variable, new Integer(value));
        }
    }
    
    /**
     * returns the value associated with the given variable name
     * @param variable is the variable to get
     * @return the integer associated with the variable
     */
    public int getVariable(String variable)
    {
        Environment correct = this;
        if (!correct.getVarMap().containsKey(variable))
        {
            correct = correct.getParent();
        }
        if (correct == null)
        {
            throw new Error("variable: " + variable + " does not exist");
        }
        return correct.getVarMap().get(variable);
    }
    
    /**
     * associates the given variable name with the given value
     * 
     * @param id is the name of the procedure
     * @param dec is the declaration of the procedure
     */
    public void setProcedure(String id, ProcedureDeclaration dec)
    {
        Environment global = this;
        while (global.getParent() != null)
        {
            global = global.getParent();
        }
        global.getProcedureMap().put(id, dec);
    }
    
    /**
     * returns the value associated with the given variable name
     * @param id is the procedure to get
     * @return the integer associated with the variable
     */
    public ProcedureDeclaration getProcedure(String id)
    {
        Environment global = this;
        while (global.getParent() != null)
        {
            global = global.getParent();
        }
        return global.getProcedureMap().get(id);
    }


    /**
     * @return the varMap
     */
    public Map<String, Integer> getVarMap()
    {
        return varMap;
    }


    /**
     * @param varMap the varMap to set
     */
    public void setVarMap(Map<String, Integer> varMap)
    {
        this.varMap = varMap;
    }


    /**
     * @return the procedureMap
     */
    public Map<String, ProcedureDeclaration> getProcedureMap()
    {
        return procedureMap;
    }


    /**
     * @param procedureMap the procedureMap to set
     */
    public void setProcedureMap(Map<String, ProcedureDeclaration> procedureMap)
    {
        this.procedureMap = procedureMap;
    }


    /**
     * @return the parent
     */
    public Environment getParent()
    {
        return parent;
    }


    /**
     * @param parent the parent to set
     */
    public void setParent(Environment parent)
    {
        this.parent = parent;
    }
}
