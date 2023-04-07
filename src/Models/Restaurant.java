package Models;

import java.util.List;

public class Restaurant {
    private String name;
    private String address;
    private String phoneNumber;
    private List<Recipe> recipeList;
    private RestaurantOwner restaurantOwner;


    public Restaurant(String name, String address, String phoneNumber, List<Recipe> recipeList, RestaurantOwner restaurantOwner) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.recipeList = recipeList;
        this.restaurantOwner = restaurantOwner;
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

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public RestaurantOwner getRestaurantOwner() {
        return restaurantOwner;
    }

    public void setRestaurantOwner(RestaurantOwner restaurantOwner) {
        this.restaurantOwner = restaurantOwner;
    }
}
