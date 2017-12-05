package rpn;

public abstract class Input {
	public static String sanitize(String line) {
		if (line == null) {
			return line;
		}
		return line.toUpperCase();
	}

	public abstract String getLine();

	public String getSanitized() {
		return sanitize(this.getLine());
	}
}