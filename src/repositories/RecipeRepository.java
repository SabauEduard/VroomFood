package repositories;

import models.Recipe;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository extends GenericRepository<Recipe> {

    public RecipeRepository(List<Recipe> objectList) {
        super(objectList);
    }
    public RecipeRepository() {
        super();
    }

    @Override
    public void add(Recipe recipe){
        try{
            String query = "INSERT INTO recipe (id, name, description, price, preparationTime) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, recipe.getId());
            preparedStmt.setString(2,recipe.getName());
            preparedStmt.setString(3, recipe.getDescription());
            preparedStmt.setInt(4, recipe.getPrice());
            preparedStmt.setInt(5, recipe.getPreparationTime());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        objectList.add(recipe);
    }

    @Override
    public void remove(Recipe recipe){
        try{
            String query = "DELETE FROM recipe WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, recipe.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        objectList.remove(recipe);
    }

    @Override
    public void update(Recipe oldRecipe, Recipe newRecipe){
        try{
            String query = "UPDATE recipe SET id = ?, name = ?, description = ?, price = ?, preparationTime = ? WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newRecipe.getId());
            preparedStmt.setString(2,newRecipe.getName());
            preparedStmt.setString(3, newRecipe.getDescription());
            preparedStmt.setInt(4, newRecipe.getPrice());
            preparedStmt.setInt(5, newRecipe.getPreparationTime());
            preparedStmt.setInt(6, oldRecipe.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }

        int index = objectList.indexOf(oldRecipe);
        objectList.set(index, newRecipe);
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
