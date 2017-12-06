package rpn;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class for rpn.
 */
public class RPN {
	protected static int line = 1;

	/**
	 * Run the REPL
	 *
	 * @return     exit code
	 */
	public static int runREPL() {
		Input in = new REPLInput();
		Interpreter interpreter = new Interpreter();

		String input;
		String output;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, true);
				RPN.line++;
			} catch (UninitializedVariableException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				output = null;
			} catch (OperatorAppliedToEmptyStackException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				output = null;
			} catch (StackSizeNonZeroException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				output = null;
			} catch (InvalidKeywordException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				output = null;
			} catch (QuitException ex) {
				return 0;
			} catch (OtherException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 5;
			}
			if (output != null) {
				System.out.println(output);
			}
		}
		return 0;
	}

	/**
	 * interpret a file
	 *
	 * @param      file  The file
	 *
	 * @return     exit code
	 */
	public static int runFile(File file) {
		Input in;
		try {
			in = new FileInput(file);
		} catch (FileNotFoundException ex) {
			System.err.println("File " + file + " not found.");
			return 1;
		}
		Interpreter interpreter = new Interpreter();

		String input;
		String output;
		while ((input = in.getSanitized()) != null) {
			try {
				output = interpreter.interpret(input, false);
				RPN.line++;
			} catch (UninitializedVariableException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 1;
			} catch (OperatorAppliedToEmptyStackException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 2;
			} catch (StackSizeNonZeroException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 3;
			} catch (InvalidKeywordException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 4;
			} catch (QuitException ex) {
				return 0;
			} catch (OtherException ex) {
				System.err.println("Line " + RPN.line + ": " + ex.getMessage());
				return 5;
			}
			if (output != null) {
				System.out.println(output);
			}
		}
		return 0;
	}

	/**
	 * main
	 *
	 * @param      args  Command line arguments
	 */
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