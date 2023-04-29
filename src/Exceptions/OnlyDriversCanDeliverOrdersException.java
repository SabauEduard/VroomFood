package Exceptions;

public class OnlyDriversCanDeliverOrdersException extends RuntimeException{

    private final static String MESSAGE = "Only drivers can deliver orders \n";
    public OnlyDriversCanDeliverOrdersException() {
        super(MESSAGE);
    }
}
