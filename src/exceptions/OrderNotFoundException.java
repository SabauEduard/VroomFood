package exceptions;

public class OrderNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Order not found\n";
    public OrderNotFoundException() {
        super(MESSAGE);
    }
}
