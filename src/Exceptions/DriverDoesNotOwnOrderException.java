package Exceptions;

public class DriverDoesNotOwnOrderException extends RuntimeException{
    public DriverDoesNotOwnOrderException(String message) {
        super(message);
    }
}
