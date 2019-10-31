package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab
 * exercise 1
 * 
 * @author Richard Wang
 * @version 01/31/2018
 * 
 *          Usage: Takes in an inputStream or string and outputs tokens based on
 *          basic code lexical analysis Removes the comments and whitespaces,
 *          separating the input into operands, identifiers, and numbers
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;

    /**
     * Scanner constructor for construction of a scanner that uses an
     * InputStream object for input. Usage: FileInputStream inStream = new
     * FileInputStream(new File(<file name>); Scanner lex = new
     * Scanner(inStream);
     * 
     * @param inStream
     *            the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that scans a given input
     * string. It sets the end-of-file flag an then reads the first character of
     * the input string into the instance field currentChar. Usage: Scanner lex
     * = new Scanner(input_string);
     * 
     * @param inString
     *            the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Sets the instance field currentChar to the value read by from the input
     * stream.
     * 
     * Precondition: NA
     * Postcondition: Sets the next char to the next char, eats the currentchar
     */
    private void getNextChar()
    {
        try
        {
            int next = in.read();
            String nextChar = (char)next + "";
            if (next == -1 || nextChar.equals("."))
            {
                eof = true;
            }
            else
            {
                currentChar = (char)next;
            }
        }
        catch (IOException e)
        {
            // :(
            System.out.println("IOException in getNextChar");
            System.exit(-1);
        }
    }

    /**
     * Compares the expected char input value and the known, and sees if they
     * are the same. If not, throws ScanErrorExpection.
     * 
     * Precondition: NA
     * 
     * Postcondition: Eaten the currentChar and moves the currentChar to the next char
     * 
     * @param expected
     *            the expected currentChar
     * @throws ScanErrorException
     *             when expected does not match currentChar
     */
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal Character - expected " 
                    + currentChar + " , and found " + expected);
        }
    }

    /**
     * Sees if the input stream has pasted the end of the file
     * 
     * Precondition: NA
     * 
     * Postcondition: returns if there is a next
     * 
     * @return true if the stream has pasted the last character false if not
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Tests if the input char is a digit
     * 
     * Precondition: NA
     * 
     * Postcondition: returns if tested is a digit
     * 
     * @param tested
     *            the char to be tested
     * @return true if the char is a digit; else false
     */
    public static boolean isDigit(char tested)
    {
        return String.valueOf(tested).matches("[0-9]");
    }

    /**
     * Tests if the input char is a letter
     * 
     * Precondition: NA
     * 
     * Postcondition: returns if tested is a letter
     * 
     * @param tested
     *            the char to be tested
     * @return true if the char is a letter; else false
     */
    public static boolean isLetter(char tested)
    {
        return String.valueOf(tested).matches("[a-zA-Z]");
    }

    /**
     * Tests if the input char is a whitespace
     * 
     * Precondition: NA
     * 
     * Postcondition: returns if tested is a whitespace
     * 
     * @param tested
     *            the char to be tested
     * @return true if the char is a whitespace; else false
     */
    public static boolean isWhiteSpace(char tested)
    {
        return String.valueOf(tested).matches("[' ' '\t' '\r' '\n']");
    }

    /**
     * Tests if the input char is a operator
     * 
     * Precondition: NA
     * 
     * Postcondition: returns if tested is an operator
     * 
     * @param tested
     *            the char to be tested
     * @return true if the char is a operator; else false
     */
    public static boolean isOperand(char tested)
    {
        
        return String.valueOf(tested).matches("[\\+|\\-|\\*|=|/|(|)|%|;|:|<|>|,]");
    }
    
    /**
     * Skips a line with the BufferedReadered
     * 
     * Precondition: NA
     * 
     * Postcondition: skips a line in the bufferedReader
     * 
     * @throws IOException  
     *          bad io
     */
    public void skipLine() throws IOException
    {
        in.readLine();
    }

    /**
     * returns the number token as a string
     * 
     * Precondition: the currentChar is a number
     * 
     * Postcondition: returns the digit and moves/eats the currentChar
     * 
     * @return the digit token next in the input
     * @throws ScanErrorException
     *             when currentChar is not correct
     */
    private String scanNumber() throws ScanErrorException
    {
        if (!isDigit(currentChar))
            throw new ScanErrorException("Expected digit currentChar," +
                    " instead currentChar: " + currentChar);
        String result = "";
        while (!eof && isDigit(currentChar))
        {
            result += currentChar;
            eat(currentChar);
        }
        return result;
    }

    /**
     * returns the number token as a string
     * 
     * Precondition: the currentChar is an id
     * 
     * Postcondition: returns the id and moves/eats the currentChar
     * 
     * @return the digit token next in the input
     * @throws ScanErrorException
     *             when currentChar is not correct
     * @throws IOException
     */
    private String scanIdentifier() throws ScanErrorException
    {
        if (!isLetter(currentChar))
            throw new ScanErrorException("Expected letter currentChar," +
                    " instead currentChar: " + currentChar);
        String result = "";
        while (!eof && (isLetter(currentChar) || isDigit(currentChar)))
        {
            result += currentChar;
            eat(currentChar);
        }
        return result;
    }

    /**
     * returns the number token as a string
     * 
     * Precondition: the currentChar is an operand
     * 
     * Postcondition: returns the operand and moves/eats the currentChar
     * 
     * @return the digit token next in the input
     * @throws ScanErrorException
     *             when currentChar is not correct
     * @throws IOException 
     *              bad io
     */
    private String scanOperand() throws ScanErrorException, IOException
    {
        if (!isOperand(currentChar))
            throw new ScanErrorException("Expected operand currentChar," + 
                    " instead currentChar: " + currentChar);
        String result = currentChar + "";
        
        //for single line comments
        if (result.equals("/"))
        {
            in.mark(1);
            String next = (char)in.read() + "";
            if (next.equals("/"))
            {
                in.readLine();
                getNextChar();
                return nextToken();
            }
            else
            {
                in.reset();
                eat(currentChar);
                return result;
            }
        }
        //for <= and >= and :=
        else if (result.equals("<") || result.equals(">") || result.equalsIgnoreCase(":"))
        {
            in.mark(1);
            String next = (char)in.read() + "";
            if (next.equals("="))
            {
                getNextChar();
                return result+next;
            }
            else if(result.equals("<") && next.equals(">"))
            {
                getNextChar();
                return result+next;
            }
            else
            {
                in.reset();
                eat(currentChar);
                return result;
            }
        }
        else 
        {
            eat(currentChar);
            return result;
        }
    }

    /**
     * Skips any leading white space. Looks at currentChar and scans the next
     * token accordingly.
     * 
     * Precondition: NA
     * 
     * Postcondition: returns the next token
     * 
     * @return a string representation of the resulting lexeme. Or "END" if the
     *         input stream is at the end of the file
     * @throws ScanErrorException
     *             when currentChar is not correct
     * @throws IOException  
     *              when bad io happens
     */
    public String nextToken() throws ScanErrorException, IOException
    {
        if (eof)
            return ".";
        if (isLetter(currentChar))
            return scanIdentifier();
        if (isDigit(currentChar))
            return scanNumber();
        if (isOperand(currentChar))
            return scanOperand();
        if (isWhiteSpace(currentChar))
        {
            getNextChar();
            return nextToken();
        }
        throw new ScanErrorException("\"" + currentChar + "\" is not an accepted character.");
    }
}
