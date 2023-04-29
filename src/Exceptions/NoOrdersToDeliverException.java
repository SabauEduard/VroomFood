package Exceptions;

public class NoOrdersToDeliverException extends RuntimeException{

    private static final String MESSAGE = "There are no orders to deliver \n";
    public NoOrdersToDeliverException() {
        super(MESSAGE);
    }
}
