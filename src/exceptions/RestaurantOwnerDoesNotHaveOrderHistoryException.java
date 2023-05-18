package exceptions;

public class RestaurantOwnerDoesNotHaveOrderHistoryException extends RuntimeException{

    private static final String MESSAGE = "Restaurant owners do not have order history \n";
    public RestaurantOwnerDoesNotHaveOrderHistoryException() {
        super(MESSAGE);
    }
}
