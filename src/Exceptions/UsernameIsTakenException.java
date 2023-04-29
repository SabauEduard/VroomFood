package Exceptions;

public class UsernameIsTakenException extends RuntimeException{

    private static final String MESSAGE = "Username is already taken\n";
    public UsernameIsTakenException() {
        super(MESSAGE);
    }
}
