package exceptions;

public class OnlyOwnersCanRemoveRestaurantsException extends RuntimeException{

    private static final String MESSAGE = "Only restaurant owners can remove restaurants \n";
    public OnlyOwnersCanRemoveRestaurantsException(){
        super(MESSAGE);
    }
}
