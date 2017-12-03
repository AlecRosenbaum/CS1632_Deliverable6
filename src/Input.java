public abstract class Input {
	public static String sanitize(String line) {
		return line.toUpperCase();
	}

	public abstract String getLine();

	public String getSanitized() {
		return sanitize(this.getLine());
	}
}