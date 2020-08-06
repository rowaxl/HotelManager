package helper;

public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        System.err.println("Input value is invalid: " + message);
    }
}
