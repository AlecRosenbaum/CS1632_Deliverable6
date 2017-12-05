package rpn;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.*;
import org.junit.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class DeliverableTest {

    Interpreter _interp;

    @Before
    public void setup() {
        _interp = new Interpreter();
    }


    // Assert that creating a new Interpreter instance does not return null
    @Test
    public void testInterpreterExists() {
        assertNotNull(_interp);
    }

    //This allows us to read directly from console
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    // Assert that creating a new Interpreter instance does not return null
    @Test
    public void testLargeNumber1() {
        assertEquals("999999999999999998000000000000000001",_interp.interpret("999999999999999999 999999999999999999 *",true));
    }

    // Assert that creating a new Interpreter instance does not return null
    @Test
    public void testLargeNumber2() {
        assertEquals("-999999999999999999999999999",_interp.interpret("LET a 0 999999999999999999999999999 -",true));
    }
    
    // Assert that creating a new Interpreter instance does not return null
    @Test
    public void testLargeNumber3() {
        _interp.interpret("LET a 0 999999999999999999999999999 -",true);
        _interp.interpret("LET b -1",true);
        assertEquals("-1000000000000000000000000000",_interp.interpret("a b +",true));
    }
    

}
