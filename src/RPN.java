package rpn;

import java.io.File;
import java.io.FileNotFoundException;

public class RPN {
	protected static int line = 1;

	public static int runREPL() {
		Input in = new REPLInput();
		Interpreter interpreter = new Interpreter();

		String input, output;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, true);
				RPN.line++;
			} catch (UninitializedVariableException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				output = null;
			} catch (OperatorAppliedToEmptyStackException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 2;
			} catch (StackSizeNonZeroException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 3;
			} catch (InvalidKeywordException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 4;
			} catch (QuitException e) {
				return 0;
			} catch (RuntimeException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 5;
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
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, false);
				RPN.line++;
			} catch (UninitializedVariableException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 1;
			} catch (OperatorAppliedToEmptyStackException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 2;
			} catch (StackSizeNonZeroException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 3;
			} catch (InvalidKeywordException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 4;
			} catch (QuitException e) {
				return 0;
			} catch (RuntimeException e) {
				System.err.println("Line " + RPN.line + ": " + e.getMessage());
				return 5;
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
			int ret = 0;
			for (String arg : args) {
				ret = runFile(new File(arg));
				if (ret != 0) {
					break;
				}
			}
			System.exit(ret);
		}
	}
}