package rpn;

import java.math.BigInteger;

public class AddAction implements Action {
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop().add(interpreter.pop());
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "+";
	}
}