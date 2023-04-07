package Models;

import java.util.List;

public class Customer extends User{
    private List<Order> orderHistory;
    private int rating;

    public Customer(String name, String username, String password, String email, String phoneNumber, List<Order> orderHistory, int rating) {
        super(name, username, password, email, phoneNumber);
        this.orderHistory = orderHistory;
        this.rating = rating;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
