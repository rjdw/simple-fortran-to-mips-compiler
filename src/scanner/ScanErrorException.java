package scanner;

/**
 * ScanErrorException is a sub class of Exception and is thrown to indicate a
 * scanning error. Usually, the scanning error is the result of an illegal
 * character in the input stream. The error is also thrown when the expected
 * value of the character stream does not match the actual value.
 * 
 * @author Mr. Page
 * @version 062014
 *
 */
public class ScanErrorException extends Exception
{
    public static final long serialVersionUID = 42L;
    /**
     * default constructor for ScanErrorObjects
     */
    public ScanErrorException()
    {
        super();
    }

    /**
     * Constructor for ScanErrorObjects that includes a reason for the error
     * 
     * @param reason
     *            for error
     */
    public ScanErrorException(String reason)
    {
        super(reason);
    }
}
