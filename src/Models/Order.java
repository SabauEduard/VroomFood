package Models;

import Utils.OrderStatusType;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int idCounter = 0;
    private int id;
    private Customer customer;
    private Driver driver;
    private Restaurant restaurant;
    private List<Recipe> recipes = new ArrayList<>();
    private int totalPrice;
    private String deliveryAddress;
    private int deliveryTime;
    private OrderStatusType status;

    public Order(Customer customer, Driver driver, Restaurant restaurant, int totalPrice, String deliveryAddress) {
        this.id = idCounter++;
        this.customer = customer;
        this.driver = driver;
        this.restaurant = restaurant;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = 0;
        this.status = OrderStatusType.ACCEPTED;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        this.totalPrice += recipe.getPrice();
        this.recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe) {
        this.totalPrice -= recipe.getPrice();
        this.recipes.remove(recipe);
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Order " + this.id + "\n"
                + "Customer: " + this.customer.getUsername() + "\n"
                //+ "Driver: " + this.driver.getUsername() + "\n"
                + "Restaurant: " + this.restaurant.getName() + "\n"
                + "Delivery address: " + this.deliveryAddress + "\n"
                + "Delivery time: " + this.deliveryTime + "\n"
                + "Status: " + this.status + "\n"
                + "Total price: " + this.totalPrice + "\n"
                + "Recipes: ");
        for (Recipe recipe : this.recipes) {
            result.append(recipe.getName()).append(", ");
        }
        return result.toString();
    }
}