public abstract class Input {
	public static String sanitize(String line) {
		// TODO
		return line;
	}

	public abstract String getLine();

	public String getSanitized() {
		return sanitize(this.getLine());
	}
}