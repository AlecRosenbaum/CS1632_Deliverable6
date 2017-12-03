import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.math.BigInteger;
import java.lang.NumberFormatException;

/**
 * Class for inpterpreter.
 */
public class Interpreter {
	// some stack variable
	// variable hashmap
	protected Stack<BigInteger> stack;
	protected HashMap<String, BigInteger> vars;
	protected Input input;
	
	/**
	 * Constructs the object.
	 *
	 * @param      input  The input
	 */
	public Interpreter() {
		this.stack = new Stack<BigInteger>();
		this.vars = new HashMap<String, BigInteger>();
	}

	/**
	 * Gets a variable.
	 *
	 * @param      x     variable name
	 *
	 * @return     The variable value.
	 */
	public BigInteger getVariable(String x) {
		return this.vars.get(x);
	}

	/**
	 * Sets the variable.
	 *
	 * @param      x     variable to set
	 * @param      val   The value
	 *
	 * @return     The variable's new value
	 */
	public BigInteger setVariable(String x, BigInteger val) {
		this.vars.put(x, val);
		return this.vars.get(x);
	}

	public BigInteger push(BigInteger val) {
		this.stack.push(val);
		return this.stack.peek();
	}

	public BigInteger pop() {
		return this.stack.pop();
	}

	/**
	 * parse line into a list of actions
	 *
	 * @param      line  The line
	 *
	 * @return     a list of actions
	 */
	public ArrayList<Action> parse(String line) {
		ArrayList<Action> actions = new ArrayList<>();
		
		// handle LET keyword
		String[] commands = line.split(" ");
		Action letAction = null;
		if (commands.length > 1 && commands[0].equals("LET")) {
			letAction = new LetAction(commands[1]);
			commands = Arrays.copyOfRange(commands, 2, commands.length);
		}
		
		// parse rest of command
		for (String str : commands) {
			switch (str) {
				case "":
					break;
				case "+":
					actions.add(new AddAction());
					break;
				case "-":
					actions.add(new SubtractAction());
					break;
				case "*":
					actions.add(new MultiplyAction());
					break;
				case "/":
					actions.add(new DivideAction());
					break;
				case "QUIT":
					throw new RuntimeException("Exiting...");
				case "PRINT":
					break;
				case "LET":
					throw new RuntimeException("Unable to Parse Command.");
				default:
					BigInteger val = null;
					try {
						val = new BigInteger(str);
					} catch (NumberFormatException e) {
						val = this.vars.get(str);
					}

					if (val == null) {
						throw new RuntimeException("Variable " + str + " is not initialized!");
					}

					actions.add(new PushAction(val));
			}
		}

		// finish LET handling
		if (letAction != null) {
			actions.add(letAction);
		}

		return actions;
	}

	/**
	 * parse and interpret a line
	 *
	 * @param      line  The line
	 *
	 * @return     string to print as the result of these actions
	 */
	public String interpret(String line) {
		String print = "";
		for (Action a : this.parse(line)) {
			try {
				print = a.apply(this);
			} catch (RuntimeException e) {
				print = e.getMessage();
				break;
			}
		}
		return print;
	}
}