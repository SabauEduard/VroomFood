package Exceptions;

public class RestaurantOwnerDoesNotHaveOrderHistoryException extends RuntimeException{
    public RestaurantOwnerDoesNotHaveOrderHistoryException(String message) {
        super(message);
    }
}
