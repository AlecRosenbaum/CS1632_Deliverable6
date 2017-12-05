package rpn;

public class ConsumeAction implements Action {
	public String apply(Interpreter interpreter) {
		interpreter.pop();
		return null;
	}

	public String getOperator() {
		return "";
	}
}