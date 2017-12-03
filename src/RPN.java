import java.io.File;
import java.io.FileNotFoundException;

public class RPN {
	public static int runREPL() {
		Input in = new REPLInput();
		Interpreter interpreter = new Interpreter();

		String input, output;
		int line = 1;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, true);
				line++;
			} catch (RuntimeException e) {
				System.err.println("Line " + line + ": " + e.getMessage());
				break; // TODO catch individual exceptions for different exit codes
			}
			if (output != null) {
				System.out.println(output);
			}
		}
		return 0;
	}

	public static int runFile(File file) {
		Input in;
		try {
			in = new FileInput(file);
		} catch (FileNotFoundException e) {
			System.err.println("File " + file + " not found.");
			return 1;
		}
		Interpreter interpreter = new Interpreter();

		String input, output;
		int line = 1;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, false);
				line++;
			} catch (RuntimeException e) {
				System.err.println("Line " + line + ": " + e.getMessage());
				break; // TODO catch individual exceptions for different exit codes
			}
			if (output != null) {
				System.out.println(output);
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			System.exit(runREPL());
		} else {
			System.exit(runFile(new File(args[0])));
		}
	}
}