package Exceptions;

public class DriversCannotCancelOrdersException extends RuntimeException{
    public DriversCannotCancelOrdersException(String message){
        super(message);
    }
}
