package Csv.ReaderWriters;

import Models.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeCSVReaderWriter implements ICSVReaderWriter<Recipe>{
    private static final String FILE_NAME = "src\\Csv\\recipes.csv";
    private static RecipeCSVReaderWriter instance = null;
    public static RecipeCSVReaderWriter getInstance() {
        if (instance == null) {
            instance = new RecipeCSVReaderWriter();
        }
        return instance;
    }
    private RecipeCSVReaderWriter() {
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(Recipe recipe) {
        return recipe.getId() + SEPARATOR + recipe.getName() + SEPARATOR + recipe.getDescription() + SEPARATOR +
                recipe.getPrice() + SEPARATOR + recipe.getPreparationTime();
    }
    @Override
    public Recipe processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Recipe(fields[0], fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3]));
    }
}
