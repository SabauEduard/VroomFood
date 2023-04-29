package Exceptions;

public class NoOrdersToDeliverException extends RuntimeException{
    public NoOrdersToDeliverException(String message) {
        super(message);
    }
}
