package exceptions;

public class RecipeNotFoundException extends RuntimeException{

    private static final String MESSAGE = "Recipe not found\n";
    public RecipeNotFoundException() {
        super(MESSAGE);
    }
}
