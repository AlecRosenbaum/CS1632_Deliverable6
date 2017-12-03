public class RPN {
	public static int runREPL() {
		Input in = new REPLInput();
		Interpreter interpreter = new Interpreter();

		String input, output;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, true);
			} catch (RuntimeException e) {
				System.err.println(e.getMessage());
				break;
			}
			if (output != null) {
				System.out.println(output);
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		System.exit(runREPL());
	}
}