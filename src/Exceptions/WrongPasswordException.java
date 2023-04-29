package Exceptions;

public class WrongPasswordException extends RuntimeException{

    private static final String MESSAGE = "Wrong password \n";
    public WrongPasswordException() {
        super(MESSAGE);
    }
}
