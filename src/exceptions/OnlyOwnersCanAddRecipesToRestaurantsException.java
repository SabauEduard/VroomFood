package exceptions;

public class OnlyOwnersCanAddRecipesToRestaurantsException extends RuntimeException{

    private static final String MESSAGE = "Only restaurant owners can add recipes to restaurants \n";
    public OnlyOwnersCanAddRecipesToRestaurantsException() {
        super(MESSAGE);
    }
}
