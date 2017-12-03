import java.math.BigInteger;

public class DivideAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop();
		val = interpreter.pop().divide(val);
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "/";
	}
}