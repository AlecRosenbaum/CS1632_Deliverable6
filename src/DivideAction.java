package rpn;

import java.math.BigInteger;

/**
 * Class for divide action.
 */
public class DivideAction implements Action {
	
	/**
	 * Apply action to an interpreter
	 *
	 * @param      interpreter  The interpreter
	 *
	 * @return     result of divide action
	 */
	public String apply(Interpreter interpreter) {
		BigInteger val = interpreter.pop();
		val = interpreter.pop().divide(val);
		return interpreter.push(val).toString();
	}

	/**
	 * Gets the operator.
	 *
	 * @return     The operator.
	 */
	public String getOperator() {
		return "/";
	}
}