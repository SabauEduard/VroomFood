package Models;

import Utils.OrderStatusType;

import java.util.List;

public class Order {
    private Customer customer;
    private Driver driver;
    private Restaurant restaurant;
    private List<Recipe> recipeList;
    private int totalPrice;
    private String deliveryAddress;
    private String deliveryTime;
    private OrderStatusType status;

    public Order(Customer customer, Driver driver, Restaurant restaurant, List<Recipe> recipeList, int totalPrice, String deliveryAddress, String deliveryTime, OrderStatusType status) {
        this.customer = customer;
        this.driver = driver;
        this.restaurant = restaurant;
        this.recipeList = recipeList;
        this.totalPrice = totalPrice;
        this.deliveryAddress = deliveryAddress;
        this.deliveryTime = deliveryTime;
        this.status = status;
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

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
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

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public OrderStatusType getStatus() {
        return status;
    }

    public void setStatus(OrderStatusType status) {
        this.status = status;
    }
}
