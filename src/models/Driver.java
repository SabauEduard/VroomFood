package models;

import utils.VehicleType;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User implements java.io.Serializable{
    private String vehiclePlate;
    private VehicleType vehicleType;
    private List<Order> orderHistory = new ArrayList<>();


    public Driver(String name, String username, String password, String email, String phoneNumber, String address,
                  String vehiclePlate, VehicleType vehicleType, List<Order> orderHistory) {
        super(name, username, password, email, phoneNumber, address);
        this.vehiclePlate = vehiclePlate;
        this.vehicleType = vehicleType;
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

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public void setOrderHistory(List<Order> orderHistory) {
        this.orderHistory = orderHistory;
    }

    public String toString(){
        return "Driver\n" + super.toString();
    }
}
