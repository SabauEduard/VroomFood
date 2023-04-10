package Models;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private static int idCounter = 0;
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<Recipe> recipes = new ArrayList<>();
    private RestaurantOwner restaurantOwner;

    public Restaurant(String name, String address, String phoneNumber,  List<Recipe>recipes, RestaurantOwner restaurantOwner) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.recipes = recipes;
        this.restaurantOwner = restaurantOwner;
    }
    public Restaurant(String name, String address, String phoneNumber, RestaurantOwner restaurantOwner) {
        this.id = idCounter++;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.restaurantOwner = restaurantOwner;
    }
    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }
    public void removeRecipe(Recipe recipe){
        this.recipes.remove(recipe);
    }
    public boolean hasRecipe(Recipe recipe){
        return this.recipes.contains(recipe);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public  List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }
}
