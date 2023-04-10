package Exceptions;

public class OnlyOwnersCanAddRecipesToRestaurantsException extends RuntimeException{
    public OnlyOwnersCanAddRecipesToRestaurantsException(String message) {
        super(message);
    }
}
