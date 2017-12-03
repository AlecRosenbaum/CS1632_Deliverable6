public class Run {
	public static void main(String[] args) {
		Input in = new REPLInput();
		Interpreter interpreter = new Interpreter();

		String input, output;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input);
			} catch (RuntimeException e) {
				System.out.println(e.getMessage());
				break;
			}
			System.out.println(output);
		}
	}
}