package rpn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileInput extends Input {
	protected BufferedReader br;

	public FileInput(File fin) throws FileNotFoundException {
		this.br = new BufferedReader(new FileReader(fin));
	}

	public String getLine() {
		try {
			return br.readLine();
		} catch (IOException e) {
			return null;
		}
	}
}