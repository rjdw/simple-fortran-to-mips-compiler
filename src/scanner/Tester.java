package scanner;
import java.io.*;

/**
 * Tester for the Scanner Class Tests the scanner using ScannerTest.txt and
 * scannerTestAdvanced.txt from class
 * 
 * @author Richard Wang
 * @version 01/31/2018
 */
public class Tester
{
    /**
     * Uses ScannerTest.txt to test the Scanner
     * 
     * @throws IOException
     *             if file is wrong
     * @throws ScanErrorException
     *             if scan didn't work
     */
    public static void scannerTest() throws IOException, ScanErrorException
    {
        InputStream input = new FileInputStream(
                new File("src/testfiles/scanner_test/ScannerTest.txt"));
        Scanner scan = new Scanner(input);
        String res = "";
        for (int i = 0; i < 9; i++)
        {
            String next = scan.nextToken();
            res += next;
            System.out.println(next);
            //System.out.println("Line 1 result: " + res);
        }
        if (!res.equals("x=x+3/5-y"))
        {
            System.out.println("ScannerTest line 1 wrong");
            System.exit(-1);
        }
        res = "";
        for (int i = 0; i < 5; i++)
        {
            String next = scan.nextToken();
            res += next;
            System.out.print(next);
        }
        if (!res.equals("y=z%5"))
        {
            System.out.println("ScannerTest line 2 wrong");
            System.exit(-1);
            //System.out.println("Line 2 result: " + res);
        }
        res = "";
        while (scan.hasNext())
        {
            String next = scan.nextToken();
            res += next;
            System.out.println(next);
        }
        if (!res.equals("z=x+yxyzzy=303bytebaudybitandbranch."))
        {
            System.out.println("ScannerTest line 3 and beyond is wrong");
            System.out.println("expected: z=x+yxyzzy=303bytebaudybitandbranch. \n got: " + res);
            System.exit(-1);
        }
    }

    /**
     * Uses the scannerTestAdvanced.txt to test the Scanner
     * 
     * @throws IOException
     *             if file is wrong
     * @throws ScanErrorException
     *             if scan didn't work
     */
    public static void scannerTestAdvanced() throws IOException, ScanErrorException
    {
        InputStream input = new FileInputStream(
                new File("src/testfiles/scanner_test/scannerTestAdvanced.txt"));
        Scanner scan = new Scanner(input);
        String res = "";
        while (scan.hasNext())
        {
            String next = scan.nextToken();
            res += next;
            System.out.println(next);
        }
        if (!res.equals("VARx;PROCEDUREsquare(n);BEGINRETURNn*n;"
                + "END;BEGINx:=1;WHILEx<=10DOBEGINWRITELN(square(x));x:=x+1;END;END;."))
        {
            System.out.println("advancedScannerTest fail \n"
                    + "expected: "
                    + "VARx;PROCEDUREsquare(n);BEGINRETURNn*n;"
                    + "END;BEGINx:=1;WHILEx<=10DOBEGINWRITELN(square(x));x:=x+1;END;END;.\n"
                    + "got: " + res);
            System.exit(-1);
        }
    }

    /**
     * Takes the text file input and tests the scanner on token generation
     * 
     * @param args
     *            main string array
     * 
     * @throws IOException
     *             if file is wrong
     * @throws ScanErrorException
     *             if scan didn't work
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        scannerTest();
        scannerTestAdvanced();
        System.out.print("good");
    }
}
