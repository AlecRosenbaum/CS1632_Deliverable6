package rpn;

import java.math.BigInteger;

public class LetAction implements Action {
	protected String varName;

	public LetAction(String varName) {
		this.varName = varName;
	}

	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.setVariable(varName, interpreter.pop());
		return interpreter.push(val).toString();
	}

	public String getOperator() {
		return "LET";
	}
}