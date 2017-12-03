import java.math.BigInteger;

public class SubtractAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop();
		val = interpreter.pop().subtract(val);
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "-";
	}
}