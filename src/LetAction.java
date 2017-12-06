package rpn;

import java.math.BigInteger;

/**
 * Class for let action.
 */
public class LetAction implements Action {
	protected String varName;

	/**
	 * Constructs the object.
	 *
	 * @param      varName  The variable name
	 */
	public LetAction(String varName) {
		this.varName = varName;
	}

	/**
	 * Apply action to the interpreter
	 *
	 * @param      interpreter  The interpreter
	 *
	 * @return     result of action
	 */
	public String apply(Interpreter interpreter) {
		BigInteger inVal = interpreter.pop();
		if (interpreter.stackSize() > 0) {
			throw new StackSizeNonZeroException("Malformed LET expression");
		}
		BigInteger val = interpreter.setVariable(varName, inVal);
		return interpreter.push(val).toString();
	}

	/**
	 * Gets the operator.
	 *
	 * @return     The operator.
	 */
	public String getOperator() {
		return "LET";
	}
}