package parser;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import scanner.ScanErrorException;
import scanner.Scanner;
import ast.*;
import ast.Number;

/**
 * Parser is a simple parser for Compilers and Interpreters (2014-2015) lab
 * Using the scanner previously created
 * 
 * @author Richard Wang
 * @version 03/05/2018
 *
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

    /**
     * Constructor for Parser, takes a scanner instance variable and a
     * currentToken from that scanner
     * 
     * @param scanner
     *            is the instance variable taken for the Parser
     * @throws IOException
     *             when the scanner has an IOException
     * @throws ScanErrorException
     *             when scanner has an error
     */
    public Parser(Scanner scanner) throws IOException, ScanErrorException
    {
        this.scanner = scanner;
        currentToken = this.scanner.nextToken();
    }

    /**
     * Compares the expected char input token and the known, and sees if they
     * are the same. If not, throws IllegalArgumentException.
     * 
     * Precondition: N/A
     * Postcondition: the currentToken is moved to the next token.
     * The currentToken is compared and method throws error if not the expected value
     * 
     * @param expected
     *            the expected currentChar
     * @throws IllegalArgumentException
     *             when expected does not match currentChar
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     * 
     */
    private void eat(String expected) throws IOException, ScanErrorException
    {
        // check for expected = currentToken
        if (!expected.equals(currentToken))
            throw new IllegalArgumentException("expected: " + expected + 
                    "\ncurrentToken: " + currentToken);

        currentToken = scanner.nextToken();
    }

    /**
     * parseNumber eats the number token, parses it and returns the value of the
     * token This is done knowing that the token is a number
     * 
     * Precondition: currentToken is a number
     * 
     * Postcondition: currentToken is moved down one, number is returned
     * 
     * @return the parsed number
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     */
    private Number parseNumber(boolean isPositive) throws IOException, ScanErrorException
    {   
        int intArg = 0;
        if (isPositive)
        {
            intArg = Integer.parseInt(currentToken);
        } 
        else
        {
            intArg = Integer.parseInt(currentToken) * -1;
        }
        Number num = new Number(intArg);
        eat(currentToken);
        return num;
    }

    /**
     * Parses the factor of grammar: 
     * 
     * factor → (factor ) 
     * factor → - factor 
     * factor → num
     * 
     * Precondition: currentToken is a factor defined by the above grammar
     * 
     * Postcondition: Factor is eaten, the currentToken is moved down,
     * and the resulting expression is returned
     * 
     * @return the resulting expression
     * @throws IOException when there is error
     * @throws ScanErrorException when there is error
     */
    private Expression parseFactor(boolean isPositive) throws IOException, ScanErrorException
    {
        if (currentToken.equals("-"))
        {
            eat("-");
            return parseFactor(!isPositive);
        }
        else if (currentToken.equals("("))
        {
            eat("(");
            Expression result = parseExpression(isPositive);
            eat(")");
            return result;
        }
        else
        {
            try
            {
                return parseNumber(isPositive);
            }
            catch (NumberFormatException e)
            {
                String id = parseID();
                
                //procedure call within a statement or another procedure call
                if (currentToken.equals("("))
                {
                    eat("(");
                    
                    //if the procedure call has parameters
                    if (!currentToken.equals(")"))
                    {
                        List<Expression> args = new ArrayList<Expression>();
                        args = parseArgs();
                        eat(")");
                        return new ProcedureCall(id, args, isPositive);
                    }
                    
                    //if there are no parameters in the procedure call
                    eat(")");
                    
                    return new ProcedureCall(id, isPositive);
                }
                
                //no parentheses, just a normal variable
                return new Variable(id, isPositive);
            }
        }
    }


    /**
     * Parses an ID
     * 
     * Precondition: currentToekn is an id
     * 
     * Postcondition: currentToken is moved down one token,
     * the id is eaten, and the string is returned
     * 
     * @return the id token as a String
     * @throws IOException when there is error
     * @throws ScanErrorException when there is error
     */
    public String parseID() throws IOException, ScanErrorException
    {
        String res = currentToken;
        eat(currentToken);
        return res;
    }

    /**
     * Parses a term defined by the grammar: 
     * stmt → WRITELN ( term ) ; 
     * term → term * factor | term / factor | factor 
     * factor →(term)|-factor |num
     * 
     * Precondition: currentToken is the beginning of 
     * a term as defined by the above grammar
     * 
     * Postcondition: the term is eaten, expression is returned,
     *  and currentToken is moved
     * 
     * @param isPositive is true if the value has no negative sign in the beginning
     *      otherwise it is false.
     * @return expression parsed
     * @throws IOException when there is error
     * @throws ScanErrorException when there is error
     */
    public Expression parseTerm(boolean isPositive) throws IOException, ScanErrorException
    {
        Expression res = parseFactor(isPositive);
        while (currentToken.equals("*") || currentToken.equals("/"))
        {
            if (currentToken.equals("*"))
            {
                eat("*");
                res = new BinOp("*", res, parseFactor(isPositive));
            }
            else if (currentToken.equals("/"))
            {
                eat("/");
                res = new BinOp("/", res, parseFactor(isPositive));
            }
        }
        return res;
    }

    /**
     * Parses an expression following the grammar: 
     * stmt → WRITELN ( expr ) ;
     * expr → expr+term | expr-term | term 
     * term → term * factor | term / factor| factor 
     * factor →(expr)|-factor | num
     * 
     * Precondition: currentToken is the beginning of an expression
     * as defined by the above grammar
     * 
     * Postcondition: The expression is returned,
     * the expression is eaten, and the currentToken is moved forward
     * 
     * @param isPositive is true if the value has no negative sign in the beginning
     *      otherwise it is false.
     * 
     * @return the expression parsed
     * 
     * @throws IOException when there is error
     * 
     * @throws ScanErrorException when there is error
     */
    public Expression parseExpression(boolean isPositive) throws IOException, ScanErrorException
    {
        Expression res = parseTerm(isPositive);
        while (currentToken.equals("+") || currentToken.equals("-"))
        {
            if (currentToken.equals("+"))
            {
                eat("+");
                res = new BinOp("+", res, parseTerm(isPositive));
            }
            else if (currentToken.equals("-"))
            {
                eat("-");
                res = new BinOp("-", res, parseTerm(isPositive));
            }
        }
        return res;
    }
    
    /**
     * Overloads the parseTerm method
     * if there is not argument, 
     * then the number is presumed to be positive
     * 
     * @throws IOException when there is error
     * 
     * @throws ScanErrorException when there is error
     */
    private Expression parseExpression() throws IOException, ScanErrorException
    {
        return parseExpression(true);
    }
    
    /**
     * Parses an expression following the grammar: 
     * stmt → WRITELN ( expr ) ;
     * expr → expr+term | expr-term | term 
     * term → term * factor | term / factor| factor 
     * factor →(expr)|-factor | num
     * cond → expr relop expr
     * relop →=|<>|<|>|<=|>=
     * 
     * Precondition: currentToken is the beginning of an condition
     * as defined by the above grammar
     * 
     * Postcondition: The Condition is returned,
     * the expression is eaten, and the currentToken is moved forward
     * 
     * @return the condition parsed
     * 
     * @throws IOException when there is error
     * 
     * @throws ScanErrorException when there is error
     */
    public Condition parseCondition() throws IOException, ScanErrorException
    {
        Expression exp1 = parseExpression();
        String relop = parseRelop();
        Expression exp2 = parseExpression();
        return new Condition(exp1, relop, exp2);
    }

    /**
     * Parses an expression following the grammar: 
     * stmt → WRITELN ( expr ) ;
     * expr → expr+term | expr-term | term 
     * term → term * factor | term / factor| factor 
     * factor →(expr)|-factor | num
     * cond → expr relop expr
     * relop →=|<>|<|>|<=|>=
     * 
     * Precondition: currentToken is the beginning of 
     * a relop as defined by the above grammar
     * 
     * Postcondition: the relop is eaten, relop is returned,
     *  and currentToken is moved
     * 
     * @return string relop parsed
     * @throws IOException when there is error
     * @throws ScanErrorException when there is error
     */
    public String parseRelop() throws IOException, ScanErrorException
    {
        if (currentToken.equals("="))
        {
            eat("=");
            return "=";
        }
        else if (currentToken.equals("<="))
        {
            eat("<=");
            return "<=";
        }
        else if (currentToken.equals(">="))
        {
            eat(">=");
            return ">=";
        }
        else if (currentToken.equals("<>"))
        {
            eat("<>");
            return "<>";
        }
        else if (currentToken.equals(">"))
        {
            eat(">");
            return ">";
        }
        else if (currentToken.equals("<"))
        {
            eat("<");
            return "<";
        }
        else
        {
            throw new ScanErrorException("unexpected relop: " + currentToken);
        }
    }

    /**
     * parseStatement eats the statement, parses it and prints the value of the
     * WRITELN parameter This is done knowing that the token is a statement
     * stmt → WRITELN ( expr ) ; | BEGIN stmts END | id:=expr
     * | IF cond THEN stmt | WHILE cond DO stmt ; 
     * stmts → stmts stmt | ε
     * expr → expr+term | expr-term | term
     * term → term * factor | term / factor | factor
     * factor →(expr)|-factor |num
     * 
     * Precondition: currentToken is the beginning of a statement as defined
     * by the above grammar, 
     * 
     * Postcondition: all statements are executed and the program is run to completion
     * 
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     * @return the parsed Statement
     */
    public Statement parseStatement() throws IOException, ScanErrorException
    {
        if (currentToken.equals("FOR"))
        {
            eat("FOR");
            
            String id = parseID();
            eat(":=");
            Expression exp = parseExpression();
            Assignment assignment = new Assignment(id, exp);

            eat("TO");
            Expression expr = parseExpression();
            eat("DO");
            
            Statement stmt = parseStatement();
            
            return new For(id, assignment, expr, stmt);
        }
        if (currentToken.equals("IF"))
        {
            eat("IF");
            Condition condition = parseCondition();
            eat("THEN");
            Statement stmt = parseStatement();
            return new If(condition, stmt);
        }
        if (currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition condition = parseCondition();
            eat("DO");
            Statement stmt = parseStatement();
            return new While(condition, stmt);
        }
        if (currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        
        if (currentToken.equals("READLN"))
        {
            eat("READLN");
            eat("(");
            String param = parseID();
            eat(")");
            eat(";");
            return new Readln(param);
        }
        if (currentToken.equals("BEGIN"))
        {
            List<Statement> stmtList = new ArrayList<Statement>();
            eat("BEGIN");

            while(!currentToken.equals("END"))
            {
                stmtList.add(parseStatement());
            }
            if (currentToken.equals("END"))
            {
                eat("END");
                eat(";");
            }

            return new Block(stmtList);
        }
        else
        { 
            String id = parseID();
            eat(":=");
            Expression exp = parseExpression();
            
            //for procedure call
            if (currentToken.equals("("))
            {
                eat("(");
                //for arguments
                if (!currentToken.equals(")"))
                {
                    List<Expression> args = new ArrayList<Expression>();
                    args = parseArgs();
                    eat(")");
                    eat(";");
                    String procedureName = ((Variable) exp).getName();
                    ProcedureCall call = new ProcedureCall(procedureName, args);
                    return new Assignment(id, call);
                }
                eat(")");
                eat(";");
                String procedureName = ((Variable) exp).getName();
                ProcedureCall call = new ProcedureCall(procedureName);
                return new Assignment(id, call);
            }
            eat(";");
            return new Assignment(id, exp);
        }
    }
    
    /**
     * Parses a arguments following the grammar:
     * factor →(expr)|-factor |num|id(maybeargs)|id 
     * maybeargs → args | ε
     * args → args , expr | expr
     * 
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     *             
     * @return a list of the arg expressions
     */
    public List<Expression> parseArgs() throws IOException, ScanErrorException 
    {
        List<Expression> argList = new ArrayList<Expression>();
        while (!currentToken.equals(")"))
        {
            argList.add(parseExpression());
            if (currentToken.equals(","))
            {
                eat(",");
                continue;
            }
            break;
        }
        return argList;
    }
    
    /**
     * Parses a params following the grammar:
     * program → PROCEDURE id ( maybeparms ) ; stmt program | stmt . 
     * maybeparms → params | ε
     * parms → parms , id | id
     * 
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     *             
     * @return a list of the param id strings
     */
    public List<String> parseParams() throws IOException, ScanErrorException 
    {
        List<String> paramList = new ArrayList<String>();
        while (!currentToken.equals(")"))
        {
            paramList.add(parseID());
            if (currentToken.equals(","))
            {
                eat(",");
                continue;
            }
            break;
        }
        return paramList;
    }
    
    /**
     * Parses a procedure declaration
     * 
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     *             
     * @return ProcedureDeclaration that is written
     */
    public ProcedureDeclaration parseProcedureDeclaration() throws IOException, ScanErrorException 
    {
        eat("PROCEDURE");
        String id = parseID();
        eat("(");
        if (!currentToken.equals(")"))
        {
            List<String> params = parseParams();
            eat(")");
            eat(";");
            if (currentToken.equals("VAR"))
            {
                List<String> varList = parseVarList();
                Statement stmt = parseStatement();
                return new ProcedureDeclaration(id, params, stmt, varList);
            }
            Statement stmt = parseStatement();
            return new ProcedureDeclaration(id, params, stmt);
        }
        eat(")");
        eat(";");
        
        if (currentToken.equals("VAR"))
        {
            List<String> varList = parseVarList();
            Statement stmt = parseStatement();
            return new ProcedureDeclaration(id, stmt, varList);
        }
            
        Statement stmt = parseStatement();
        return new ProcedureDeclaration(id, stmt);
    }
    
    /**
     * Parses the list of variable names following the keyword VAR
     * at the beginning of the program
     * @return a List of the declared variable names
     * @throws IOException throws exception
     * @throws ScanErrorException throws exception
     */
    private List<String> parseVarList() throws IOException, ScanErrorException
    {
        List<String> varList = new ArrayList<String>();
        eat("VAR");
        varList.add(parseID());
        while(currentToken.equals(","))
        {
            eat(",");
            varList.add(parseID());
        }
        eat(";");
        return varList;
    }
    
    /**
     * Parses a program following the grammar:
     * program → PROCEDURE id ( maybeparms ) ; stmt program | stmt 
     * 
     * @throws IOException
     *             when scanner has IOException
     * @throws ScanErrorException
     *             when scanner has an error
     *             
     * @return Program that is written
     */
    public Program parseProgram() throws IOException, ScanErrorException
    {
        List<String> varList = new ArrayList<String>();
        if (currentToken.equals("VAR"))
        {
            varList = parseVarList();
        }
        List<ProcedureDeclaration> decList = new ArrayList<ProcedureDeclaration>();
        while (currentToken.equals("PROCEDURE"))
        {
            decList.add(parseProcedureDeclaration());
        }
        Statement stmt = parseStatement();
        return new Program(varList, decList, stmt);
    }
}
