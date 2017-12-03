public class PrintAction implements Action {
	public String apply(Interpreter interpreter) {
		return interpreter.pop().toString();
	}

	public String getOperator() {
		return "PRINT";
	}
}