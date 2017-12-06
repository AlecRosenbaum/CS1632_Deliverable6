package rpn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for file input.
 */
public class FileInput extends Input {
	protected BufferedReader br;

	/**
	 * Constructs the object.
	 *
	 * @param      fin   The fin
	 */
	public FileInput(File fin) throws FileNotFoundException {
		this.br = new BufferedReader(new FileReader(fin));
	}

	/**
	 * Gets the line.
	 *
	 * @return     The line.
	 */
	public String getLine() {
		try {
			return br.readLine();
		} catch (IOException ex) {
			return null;
		}
	}
}