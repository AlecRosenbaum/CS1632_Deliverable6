package rpn;

public class OperatorAppliedToEmptyStackException extends RuntimeException {
    public OperatorAppliedToEmptyStackException(String message) {
        super(message);
    }
}