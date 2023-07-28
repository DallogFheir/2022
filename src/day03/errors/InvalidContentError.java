package day03.errors;

public class InvalidContentError extends Exception {
    public InvalidContentError(String message) {
        super(message);
    }
}
