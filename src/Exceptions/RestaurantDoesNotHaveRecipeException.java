package Exceptions;

public class RestaurantDoesNotHaveRecipeException extends RuntimeException{

    private static final String MESSAGE = "The restaurant does not have the recipe\n";
    public RestaurantDoesNotHaveRecipeException() {
        super(MESSAGE);
    }
}
