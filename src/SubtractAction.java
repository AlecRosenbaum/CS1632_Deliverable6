package rpn;

import java.math.BigInteger;

/**
 * Class for subtract action.
 */
public class SubtractAction implements Action {
	

	/**
	 * Applies this action to an interpreter
	 *
	 * @param      interpreter  The interpreter
	 *
	 * @return     the value to place back on the stack
	 */
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop();
		val = interpreter.pop().subtract(val);
		return interpreter.push(val).toString();
	}

	/**
	 * Gets the operator.
	 *
	 * @return     The operator.
	 */
	public String getOperator() {
		return "-";
	}
}