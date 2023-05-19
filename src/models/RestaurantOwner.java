package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RestaurantOwner extends User implements java.io.Serializable{
    private List<Restaurant> restaurants = new ArrayList<>();

    public RestaurantOwner(String name, String username, String password, String email, String phoneNumber, String address, List<Restaurant> restaurants) {
        super(name, username, password, email, phoneNumber, address);
        this.restaurants = restaurants;
    }

    public RestaurantOwner(String name, String username, String password, String email, String phoneNumber, String address) {
        super(name, username, password, email, phoneNumber, address);
    }
    public RestaurantOwner(ResultSet result){
        super(result);
    }

    public void addRestaurant(Restaurant restaurant) {
        this.restaurants.add(restaurant);
    }

    public void removeRestaurant(Restaurant restaurant) {
        this.restaurants.remove(restaurant);
    }

    public boolean hasRestaurant(Restaurant restaurant) {
        return this.restaurants.contains(restaurant);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public String toString(){
        return "Restaurant Owner\n" + super.toString();
    }
}
