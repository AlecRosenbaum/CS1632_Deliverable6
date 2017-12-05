package rpn;

import java.util.Scanner;

public class REPLInput extends Input {
	protected Scanner scanner;

	public REPLInput() {
		System.out.println("Welcome to the REPL!");
		this.scanner = new Scanner(System.in);
	}

	public String getLine() {
		System.out.print("> ");
		return this.scanner.nextLine();
	}
}