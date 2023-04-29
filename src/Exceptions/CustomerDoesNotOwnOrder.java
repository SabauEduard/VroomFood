package Exceptions;

public class CustomerDoesNotOwnOrder extends RuntimeException{

    private static final String MESSAGE = "You are not logged in as the customer of this order \n";
    public CustomerDoesNotOwnOrder(){
        super(MESSAGE);
    }
}
