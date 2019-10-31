package parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

//import environment.Environment;
import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Tester for the parser using test files in testfiles/parser_test
 * 
 * @author Richard Wang
 * @version 03/05/2018
 *
 */
public class Tester
{
    /**
     * Tests parser methods with test files
     * @param args 
     * @throws IOException throws it
     * @throws ScanErrorException throws it
     */
    public static void main(String[] args) throws IOException, ScanErrorException
    {
        InputStream input = new FileInputStream(
                new File("src/testfiles/compiling_procedures_test/compilingProceduresTest7.txt"));
        Scanner scan = new Scanner(input);
        Parser parser = new Parser(scan);
        //parser.parseProgram().exec(new Environment());
        parser.parseProgram().compile("compilingProcedures7");
    }
    
}
