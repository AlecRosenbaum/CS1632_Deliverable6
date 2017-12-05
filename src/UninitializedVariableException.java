package rpn;

public class UninitializedVariableException extends RuntimeException {
    public UninitializedVariableException(String message) {
        super(message);
    }
}