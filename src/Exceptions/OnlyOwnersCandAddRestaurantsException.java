package Exceptions;

public class OnlyOwnersCandAddRestaurantsException extends RuntimeException{
    public OnlyOwnersCandAddRestaurantsException(String message) {
        super(message);
    }
}
