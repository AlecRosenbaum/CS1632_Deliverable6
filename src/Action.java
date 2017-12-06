package rpn;

public interface Action {

	public String apply(Interpreter interpreter);

	public String getOperator();
}