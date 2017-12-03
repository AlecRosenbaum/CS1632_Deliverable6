import java.math.BigInteger;

public class PushAction implements Action {
	protected BigInteger val;

	public PushAction(BigInteger val) {
		this.val = val;
	}

	public String apply(Interpreter interpreter) {
		return interpreter.push(this.val).toString();
	}
}