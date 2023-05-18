package exceptions;

public class DriversCannotCancelOrdersException extends RuntimeException{

    private static final String MESSAGE = "Drivers cannot cancel orders \n";
    public DriversCannotCancelOrdersException(){
        super(MESSAGE);
    }
}
