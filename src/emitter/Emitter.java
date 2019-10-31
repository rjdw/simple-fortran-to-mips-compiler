package emitter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import ast.ProcedureDeclaration;

/**
 * Given class which writes to a file
 * 
 * @author atcs compilers
 * @version 1
 */
public class Emitter
{
    private PrintWriter out;
    private int nextLabelID;
    private ProcedureDeclaration proc;
    private int excessStackHeight;
 
    /**
     * creates an emitter for writing to a new file with given name
     * @param outputFileName the name of the output file
     */
    public Emitter(String outputFileName)
    {
        excessStackHeight = 0;
        proc = null;
        nextLabelID = 1;
        try
        {
            out = new PrintWriter(new FileWriter(outputFileName), true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Keeps track of the label ids in the mips program ensures no duplicates by
     * numbering system
     * 
     * @return the integer count of the labels generated
     */
    public int nextLabelID()
    {
        return nextLabelID++;
    }

    /**
     * prints one line of code to file (with non-labels indented)
     * @param code the code to be written to the file
     */
    public void emit(String code)
    {
        if (!code.endsWith(":"))
            code = "\t" + code;
        out.println(code);
    }

    // closes the file. should be called after all calls to emit.
    /**
     * closes the emitter
     */
    public void close()
    {
        out.close();
    }

    /**
     * Emits commands in mips command pushes the contents of $v0 onto the stack
     * $sp also moves the stack pointer
     */
    public void emitPush()
    {
        if (proc != null)
        {
            excessStackHeight += 4;
        }
        emit("subu $sp $sp 4");
        emit("sw $v0 ($sp)");
    }

    /**
     * Emits commands in mips command pops the contents of $v0 from the stack
     * also moves the stack pointer
     */
    public void emitPop()
    {
        if (proc!=null)
        {
            excessStackHeight -= 4;
        }
        emit("lw $t0 ($sp)");
        emit("addu $sp $sp 4");
    }
    
    /**
     * remember proc as current procedure context
     * @param procedureDec is the ProcedureDeclaration to be set
     */
    public void setProcedureContext(ProcedureDeclaration procedureDec)
    {
        this.proc = procedureDec;
    }
    
    /**
     * clear current procedure context (remember null)
     */
    public void clearProcedureContext()
    {
        proc = null;
        excessStackHeight = 0;
    }
    
    /**
     * Checks to see if the variable is a local variable 
     * 
     * @param varName is the String to be checked
     * @return true if the varName is a local variable; otherwise, false
     */
    public boolean isLocalVariable(String varName)
    {
        if (proc == null)
        {
            return false;
        }
        
        
        if (varName.equals(proc.getId()))
        {
            return true;
        }
        
        
        for (String pos : proc.getParamList())
        {
            if (pos.equals(varName))
            {
                return true;
            }
        }
        
        for (String localVar : proc.getVarList())
        {
            if (localVar.equals(varName))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the distance in memory away from the stack pointer that a 
     * variable exists at
     * 
     * @param id is the String name of the variable in question
     * @return the int distance from the $sp stack pointer
     */
    public int getOffSet(String id)
    {
        List<String> varList = proc.getVarList();
        for (int i = 0; i < varList.size(); i ++)
        {
            if (varList.get(i).equals(id))
            {
                return i * 4;
            }
        }
        
        List<String> paramList = proc.getParamList();
        for (int i = 0; i < paramList.size(); i++)
        {
            if (paramList.get(i).equals(id))
            {
                int locVarStackHeight = varList.size() * 4;
                return ((paramList.size() - i - 1) * 4) + excessStackHeight + 4 + locVarStackHeight;
            }
        }
        return 0;
    }
}
