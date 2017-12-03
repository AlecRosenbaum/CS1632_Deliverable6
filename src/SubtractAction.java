import java.math.BigInteger;

public class SubtractAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop().subtract(interpreter.pop());
		return interpreter.push(val).toString();
	}
}