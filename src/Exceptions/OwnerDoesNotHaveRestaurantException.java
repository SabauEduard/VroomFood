package Exceptions;

public class OwnerDoesNotHaveRestaurantException extends RuntimeException{
    public OwnerDoesNotHaveRestaurantException(String message) {
        super(message);
    }
}
