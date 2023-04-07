package Models;

public class RestaurantOwner extends User{
    private Restaurant restaurant;

    public RestaurantOwner(String name, String username, String password, String email, String phoneNumber, Restaurant restaurant) {
        super(name, username, password, email, phoneNumber);
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
