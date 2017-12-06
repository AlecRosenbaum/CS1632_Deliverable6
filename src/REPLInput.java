package rpn;

import java.util.Scanner;

public class REPLInput extends Input {

	protected Scanner scanner;
    static final String PROMPT = ">";

	public REPLInput() {
		System.out.println("Welcome to the REPL!");
		this.scanner = new Scanner(System.in);
	}

	public String getLine() {
		System.out.print(this.PROMPT + " ");
		return this.scanner.nextLine();
	}
}