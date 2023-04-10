package Repositories;

import Models.Recipe;
import Repositories.GenericRepository;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepository extends GenericRepository<Recipe> {

    public RecipeRepository(List<Recipe> objectList) {
        super(objectList);
    }
    public RecipeRepository() {
        super();
    }
    public Recipe getRecipeById(int id) {
        for (Recipe recipe : objectList) {
            if (recipe.getId() == id)
                return recipe;
        }
        return null;
    }
    public Recipe getRecipeByName(String name) {
        for (Recipe recipe : objectList) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        return null;
    }
    public List<Recipe> getRecipeByPrice(int price) {
        List<Recipe> recipeList = new ArrayList<>();
        for (Recipe recipe : objectList) {
            if (recipe.getPrice() == price) {
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }

}
