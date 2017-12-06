package rpn;

/**
 * Class for input.
 */
public abstract class Input {
	
	/**
	 * sanitize a line, convert everything to upper case
	 *
	 * @param      line  The line
	 *
	 * @return     { description_of_the_return_value }
	 */
	public static String sanitize(String line) {
		if (line == null) {
			return line;
		}
		return line.toUpperCase();
	}

	/**
	 * Gets the line.
	 *
	 * @return     The line.
	 */
	public abstract String getLine();

	/**
	 * Gets the sanitized line.
	 *
	 * @return     The sanitized line.
	 */
	public String getSanitized() {
		return sanitize(this.getLine());
	}
}