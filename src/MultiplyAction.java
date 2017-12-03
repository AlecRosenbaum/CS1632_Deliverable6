import java.math.BigInteger;

public class MultiplyAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop().multiply(interpreter.pop());
		return interpreter.push(val).toString();
	}
}