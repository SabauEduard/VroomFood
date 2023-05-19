package repositories;

import config.DatabaseConfiguration;
import models.Recipe;
import models.Restaurant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository extends GenericRepository<Recipe> {
    private static final RecipeRepository instance = new RecipeRepository();

    private RecipeRepository() {
        super();
        try{
            Statement statement = databaseConfiguration.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM recipe");
            while(result.next()) {
                Recipe current = new Recipe(result);
                objectList.add(current);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }

    }
    public static RecipeRepository getInstance(){
        return instance;
    }

    public void add(Recipe recipe, Restaurant restaurant){
        try{
            String query = "INSERT INTO recipe (id, name, description, price, preparationTime, restaurantId) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, recipe.getId());
            preparedStmt.setString(2,recipe.getName());
            preparedStmt.setString(3, recipe.getDescription());
            preparedStmt.setInt(4, recipe.getPrice());
            preparedStmt.setInt(5, recipe.getPreparationTime());
            preparedStmt.setInt(6, restaurant.getId());
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
