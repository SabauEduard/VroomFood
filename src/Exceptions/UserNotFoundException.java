package Exceptions;

public class UserNotFoundException extends RuntimeException{

    private static final String MESSAGE = "This is not a registered user \n";
    public UserNotFoundException() {
        super(MESSAGE);
    }
}
