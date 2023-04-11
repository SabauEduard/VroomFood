package Exceptions;

public class OnlyOwnersCanRemoveRestaurantsException extends RuntimeException{
    public OnlyOwnersCanRemoveRestaurantsException(String message){
        super(message);
    }
}
