package rpn;

import java.math.BigInteger;

public class LetAction implements Action {
	protected String varName;

	public LetAction(String varName) {
		this.varName = varName;
	}

	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.setVariable(varName, interpreter.pop());
		if (interpreter.stackSize() > 0) {
			throw new RuntimeException("Could not evaluate expression");
		}
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "LET";
	}
}