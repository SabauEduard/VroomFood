package Exceptions;

public class UsernameIsTakenException extends RuntimeException{
    public UsernameIsTakenException(String message) {
        super(message);
    }
}
