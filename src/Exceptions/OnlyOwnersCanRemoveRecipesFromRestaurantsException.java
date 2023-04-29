package Exceptions;

public class OnlyOwnersCanRemoveRecipesFromRestaurantsException extends RuntimeException{

    private static final String MESSAGE = "Only restaurant owners can remove recipes from restaurants \n";
    public OnlyOwnersCanRemoveRecipesFromRestaurantsException(){
        super(MESSAGE);
    }
}
