package Exceptions;

public class OnlyOwnersCanRemoveRecipesFromRestaurantsException extends RuntimeException{
    public OnlyOwnersCanRemoveRecipesFromRestaurantsException(String message){
        super(message);
    }
}
