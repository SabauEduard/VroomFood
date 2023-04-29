package Models;

import Utils.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User implements java.io.Serializable{
    private String vehiclePlate;
    private VehicleType vehicleType;
    int rating;
    private List<Order> orderHistory = new ArrayList<>();


    public Driver(String name, String username, String password, String email, String phoneNumber, String address,
                  String vehiclePlate, VehicleType vehicleType, int rating, List<Order> orderHistory) {
        super(name, username, password, email, phoneNumber, address);
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
        this.rating = rating;
        this.orderHistory = orderHistory;
    }
    public Driver(String name, String username, String password, String email, String phoneNumber, String address,
                  String vehiclePlate, VehicleType vehicleType){
        super(name, username, password, email, phoneNumber, address);
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
    }

    public String getVehiclePlate() {
        return vehiclePlate;
    }

    public void setVehiclePlate(String vehiclePlate) {
        this.vehiclePlate = vehiclePlate;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }
}
