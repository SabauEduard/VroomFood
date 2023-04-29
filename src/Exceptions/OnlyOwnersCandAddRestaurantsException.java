package Exceptions;

public class OnlyOwnersCandAddRestaurantsException extends RuntimeException{

    private static final String MESSAGE = "Only restaurant owners can add restaurants \n";
    public OnlyOwnersCandAddRestaurantsException() {
        super(MESSAGE);
    }
}
