package exceptions;

public class RestaurantNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Restaurant not found\n";
    public RestaurantNotFoundException() {
        super(MESSAGE);
    }
}
