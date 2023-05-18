package exceptions;

public class NotLoggedInException extends RuntimeException{

    private static final String MESSAGE = "There is no user currently logged in\n";
    public NotLoggedInException() {
        super(MESSAGE);
    }
}
