package models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User implements java.io.Serializable{
    private List<Order> orderHistory = new ArrayList<>();

    public Customer(String name, String username, String password, String email, String phoneNumber, String address, List<Order> orderHistory) {
        super(name, username, password, email, phoneNumber, address);
        this.orderHistory = orderHistory;
    }
    public Customer(String name, String username, String password, String email, String phoneNumber, String address){
        super(name, username, password, email, phoneNumber, address);
    }
    public void buyRecipe(Recipe recipe, Order order){
        order.addRecipe(recipe);
    }
    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public String toString(){
        return "Customer\n" + super.toString();
    }

}

