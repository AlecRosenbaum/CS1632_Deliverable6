import java.math.BigInteger;

public class DivideAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop().divide(interpreter.pop());
		return interpreter.push(val).toString();
	}
}