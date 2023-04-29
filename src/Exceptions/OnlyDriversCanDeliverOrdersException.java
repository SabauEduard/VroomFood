package Exceptions;

public class OnlyDriversCanDeliverOrdersException extends RuntimeException{
    public OnlyDriversCanDeliverOrdersException(String message) {
        super(message);
    }
}
