package rpn;

import java.math.BigInteger;

public class LetAction implements Action {
	protected String varName;

	public LetAction(String varName) {
		this.varName = varName;
	}

	public String apply(Interpreter interpreter) {
		BigInteger inVal = interpreter.pop();
		if (interpreter.stackSize() > 0) {
			throw new StackSizeNonZeroException("Malformed LET expression");
		}
		BigInteger val = interpreter.setVariable(varName, inVal);
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "LET";
	}
}