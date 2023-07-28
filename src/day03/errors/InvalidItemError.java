package day03.errors;

public class InvalidItemError extends Exception {
    public InvalidItemError(String message) {
        super(message);
    }
}
