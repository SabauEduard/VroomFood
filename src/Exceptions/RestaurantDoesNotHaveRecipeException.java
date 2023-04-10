package Exceptions;

public class RestaurantDoesNotHaveRecipeException extends RuntimeException{
    public RestaurantDoesNotHaveRecipeException(String message) {
        super(message);
    }
}
