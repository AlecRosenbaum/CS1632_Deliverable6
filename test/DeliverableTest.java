package rpn;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.*;
import java.io.File;
import org.junit.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.*;

public class DeliverableTest {

    Interpreter _interp;

    @Before
    public void setup() {
        _interp = new Interpreter();
        RPN.line = 1;
    }


    // Assert that creating a new Interpreter instance does not return null
    @Test
    public void testInterpreterExists() {
        assertNotNull(_interp);
    }

    //This allows us to read directly from console
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    //This allows is to read 
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();
    //This allows us to deal with the system exit calls in the main program 
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    // Test and see that large numbers can be handled by the system 
    @Test
    public void testLargeNumber1() {
        assertEquals("999999999999999998000000000000000001", _interp.interpret("999999999999999999 999999999999999999 *", true));
    }

    // Test and see that large negative numbers can be handled by the system     
    @Test
    public void testLargeNumber2() {
        assertEquals("-999999999999999999999999999", _interp.interpret("LET a 0 999999999999999999999999999 -", true));
    }

    // Test and see that large numbers can be handled by the system and that they can be assigned to variables
    @Test
    public void testLargeNumber3() {
        _interp.interpret("LET a 0 999999999999999999999999999 -", true);
        _interp.interpret("LET b -1", true);
        assertEquals("-1000000000000000000000000000", _interp.interpret("a b +", true));
    }

    // Test and see that case sensitivity is handled in the program
    @Test
    public void testCaseSensitive1() {
        _interp.interpret("LET A 0 999999999999999999999999999 -", true);
        _interp.interpret("LET B -1", true);
        assertEquals("-1000000000000000000000000000", _interp.interpret(Input.sanitize("a b +"), true));
    }

    // Test and see that case sensitivity is handled in the program with multicharacter variable names 
    @Test
    public void testCaseSensitive2() {
        _interp.interpret("LET ABBA 0 999999999999999999999999999 -", true);
        _interp.interpret("LET BABY -1", true);
        assertEquals("-1000000000000000000000000000", _interp.interpret(Input.sanitize("aBbA bAbY +"), true));
    }

    // Test and see that case sensitivity is handled in the program with multicharacter variable names 
    @Test
    public void testCaseSensitive3() {
        _interp.interpret("LET WACKY 0 999999999999999999999999999 -", true);
        _interp.interpret("LET BARBAZ -1", true);
        assertEquals("-1000000000000000000000000000", _interp.interpret(Input.sanitize("wacky bArBaz +"), true));
    }

    // Test and see that chained opperations work
    @Test
    public void testOperator1() {
        assertEquals("125", _interp.interpret("10 10 * 5 5 * 0 0 * + +", true));
    }

    // Test and see that + opperations work
    @Test
    public void testOperator2() {
        assertEquals("7", _interp.interpret("4 3 +", true));
    }

    // Test and see that / opperations work
    @Test
    public void testOperator3() {
        assertEquals("5", _interp.interpret("10 2 /", true));
    }

    // Test and see that * opperations work
    @Test
    public void testOperator4() {
        assertEquals("20", _interp.interpret("10 2 *", true));
    }

    // Test and see that - opperations work
    @Test
    public void testOperator5() {
        assertEquals("8", _interp.interpret("10 2 -", true));
    }

    //Test and see that the QUIT command works
    @Test
    public void testQuit1() throws Exception {
        try {
            _interp.interpret("QUIT", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final QuitException e ) {
            final String msg = "";
            assertEquals(msg, e.getMessage());
        }
    }

    //Test and see that anything after the QUIT command is ignored 
    @Test
    public void testQuit2() throws Exception {
        try {
            _interp.interpret("QUIT bumblebee", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final QuitException e ) {
            final String msg = "";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that variables work as expected
    @Test
    public void testVars1() {
        _interp.interpret("LET a 10", true);
        _interp.interpret("LET b 100", true);
        assertEquals("110", _interp.interpret("a b +", true));
    }

    // Test and see that variables work as expected with long names
    @Test
    public void testVars2() {
        _interp.interpret("LET wackyvariablename 10", true);
        _interp.interpret("LET wackyvariablename2 100", true);
        assertEquals("1000", _interp.interpret("wackyvariablename wackyvariablename2 *", true));
    }

    // Will return null if it does not send a print command
    //This test should not rteurn null and pass
    @Test
    public void testPrint1() {
        _interp.interpret("LET a 10", true);
        _interp.interpret("LET b 100", true);
        assertNotNull(_interp.interpret("PRINT a b +", false));
    }

    // Will return null if it does not send a print command
    //This test should return null and pass
    @Test
    public void testPrint2() {
        _interp.interpret("LET a 10", true);
        _interp.interpret("LET b 100", true);
        assertNull(_interp.interpret("a b +", false));
    }

    // Test and see that variables work as expected with case sensitivity and printing
    @Test
    public void testCaseSensitivePrint1() {
        _interp.interpret("LET A 10", true);
        _interp.interpret("LET B 100", true);
        assertNotNull(_interp.interpret(Input.sanitize("print A B +"), false));
    }

    // Test and see that variables work as expected with case sensitivity and printing
    @Test
    public void testCaseSensitivePrint2() {
        _interp.interpret("LET A 10", true);
        _interp.interpret("LET B 100", true);
        assertNotNull(_interp.interpret(Input.sanitize("PRINT a B +"), false));
    }

    // Test and see that variables work as expected with case sensitivity and printing
    @Test
    public void testCaseSensitivePrint3() {
        _interp.interpret("LET A 10", true);
        _interp.interpret("LET B 100", true);
        assertNotNull(_interp.interpret(Input.sanitize("pRiNt A b +"), false));
    }

    // Test and see that keywords that are out of order return an error
    @Test
    public void testKeywordPlacement1() throws Exception {
        try {
            _interp.interpret("1 2 + PRINT 3", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final RuntimeException e ) {
            final String msg = "Could not evaluate expression";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that keywords that are out of order return an error
    @Test
    public void testKeywordPlacement2() throws Exception {
        try {
            _interp.interpret("1 LET a 2 + 3", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final RuntimeException e ) {
            final String msg = "Could not evaluate expression";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that a LET without any variable name will return an error
    @Test
    public void testEmptyLet1() throws Exception {
        try {
            _interp.interpret("LET", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final RuntimeException e ) {
            final String msg = "Could not evaluate expression";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that a LET without any variable value will return an error
    @Test
    public void testEmptyLet2() throws Exception {
        try {
            _interp.interpret("LET a", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final RuntimeException e ) {
            final String msg = "Operator LET applied to empty stack";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that a missing variable with error and operation 
    @Test
    public void testMissingVar1() throws Exception {
        try {
            _interp.interpret("a 5 +", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final UninitializedVariableException e ) {
            final String msg = "Variable a is not initialized!";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that a missing variable with error and operation in reverse order 
    @Test
    public void testMissingVar2() throws Exception {
        try {
            _interp.interpret("5 a +", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final UninitializedVariableException e ) {
            final String msg = "Variable a is not initialized!";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that bad operation will return a proper error message
    @Test
    public void testBadOp1() throws Exception {
        try {
            _interp.interpret("5 + ", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final OperatorAppliedToEmptyStackException e ) {
            final String msg = "Operator + applied to empty stack";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that bad operation will return a proper error message
    @Test
    public void testBadOp2() throws Exception {
        try {
            _interp.interpret("5 / ", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final OperatorAppliedToEmptyStackException e ) {
            final String msg = "Operator / applied to empty stack";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that bad operation will return a proper error message
    @Test
    public void testBadOp3() throws Exception {
        try {
            _interp.interpret("", true);
        } catch ( final Exception e ) {
            fail("Should not have thrown SomeException!");
        }
    }

    // Test and see that bad operation will return a proper error message
    @Test
    public void testBadOp4() throws Exception {
        try {
            _interp.interpret("5 5 5 ", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final StackSizeNonZeroException e ) {
            final String msg = "3 elements in stack after evaluation";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that bad operation will return a proper error message
    @Test
    public void testBadOp5() throws Exception {
        try {
            _interp.interpret("5 5 5 6 6 6 ", true);
            fail("Should have thrown SomeException but did not!");
        } catch ( final StackSizeNonZeroException e ) {
            final String msg = "6 elements in stack after evaluation";
            assertEquals(msg, e.getMessage());
        }
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles1() {
        File theFile = new File("resources/Bad.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 3: 3 elements in stack after evaluation\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles2() {
        File theFile = new File("resources/Bad.rpn");
        assertEquals(3, RPN.runFile(theFile));
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles3() {
        File theFile = new File("resources/Bad2.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 1: Unknown keyword QUOMBLE\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles4() {
        File theFile = new File("resources/Bad2.rpn");
        assertEquals(4, RPN.runFile(theFile));
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles5() {
        File theFile = new File("resources/Bad3.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 2: Operator + applied to empty stack\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles6() {
        File theFile = new File("resources/Bad3.rpn");
        assertEquals(2, RPN.runFile(theFile));
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles7() {
        File theFile = new File("resources/Bad4.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 3: Operator + applied to empty stack\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles8() {
        File theFile = new File("resources/Bad4.rpn");
        assertEquals(2, RPN.runFile(theFile));
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles9() {
        File theFile = new File("resources/Bad5.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 1: Variable B is not initialized!\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles10() {
        File theFile = new File("resources/Bad5.rpn");
        assertEquals(1, RPN.runFile(theFile));
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles11() {
        File theFile = new File("resources/Bad6.rpn");
        RPN.runFile(theFile);
        assertEquals("Line 1: Could not evaluate expression\n", systemErrRule.getLog());
    }

    // Test and see that the expected results are returned for each of the different supplied bad testing files
    @Test
    public void testBadFiles12() {
        File theFile = new File("resources/Bad6.rpn");
        assertEquals(5, RPN.runFile(theFile));
    }

    // Test and see that the prompt value is set correctly to >
    @Test
    public void testPrompt1() {
        assertEquals(">", REPLInput.PROMPT);
    }

    // Test and see that expected errors occur when multiple files are ran
    @Test
    public void testMultipleFiles1() {
        exit.expectSystemExitWithStatus(3);
        RPN.main(new String[] {"resources/Bad.rpn", "resources/File1.rpn"});
    }

    // Test and see that expected errors occur when multiple files are ran
    @Test
    public void testMultipleFiles2() {
        exit.expectSystemExitWithStatus(2);
        RPN.main(new String[] {"resources/Bad3.rpn", "resources/File1.rpn"});
    }

    // Test and see that an exmty file will be handled
    @Test
    public void testEmptyFile1() {
        exit.expectSystemExitWithStatus(0);
        RPN.main(new String[] {"resources/Empty.rpn", "resources/File1.rpn"});
    }

    // Test and see that line count builds from file to file
    @Test
    public void testLineCount1() {
        exit.expectSystemExitWithStatus(3);
        exit.checkAssertionAfterwards(new Assertion() {
            public void checkAssertion() {
                assertEquals("Line 6: 3 elements in stack after evaluation\n", systemErrRule.getLog());
            }
        });
        RPN.main(new String[] {"resources/File1.rpn", "resources/Bad.rpn"});
    }
}
