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


}
