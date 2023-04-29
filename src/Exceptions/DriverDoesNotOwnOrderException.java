package Exceptions;

public class DriverDoesNotOwnOrderException extends RuntimeException{

    private static final String MESSAGE = "Driver does not own order\n";
    public DriverDoesNotOwnOrderException() {
        super(MESSAGE);
    }
}
