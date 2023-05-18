package exceptions;

public class OnlyCustomersCanOrderException extends RuntimeException{
    private static final String MESSAGE = "Only customers can order!";
    public OnlyCustomersCanOrderException() {
        super(MESSAGE);
    }

}
