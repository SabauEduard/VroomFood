package Exceptions;

public class OwnerDoesNotHaveRestaurantException extends RuntimeException{

    private static final String MESSAGE = "This user does not own this restaurant\n";
    public OwnerDoesNotHaveRestaurantException() {
        super(MESSAGE);
    }
}
