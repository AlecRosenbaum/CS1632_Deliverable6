import java.math.BigInteger;

public class LetAction implements Action {
	protected String varName;

	public LetAction(String varName) {
		this.varName = varName;
	}

	public String apply(Interpreter interpreter) {
		return interpreter.setVariable(varName, interpreter.pop()).toString();
	}
}