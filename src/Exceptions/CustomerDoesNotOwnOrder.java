package Exceptions;

public class CustomerDoesNotOwnOrder extends RuntimeException{
    public CustomerDoesNotOwnOrder(String message){
        super(message);
    }
}
