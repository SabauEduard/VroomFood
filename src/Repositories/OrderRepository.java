package Repositories;

import Models.Customer;
import Models.Driver;
import Models.Order;
import Models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends GenericRepository<Order>{
    public OrderRepository(List<Order> objectList){
        super(objectList);
    }
    public OrderRepository(){
        super();
    }
    public Order getOrderById(int id){
        for(Order order : objectList){
            if(order.getId() == id)
                return order;
        }
        return null;
    }
    public List<Order> getOrdersByCustomer(Customer customer){
        List<Order> orderList = new ArrayList<>();
        for(Order order : objectList){
            if(order.getCustomer().equals(customer)){
                orderList.add(order);
            }
        }
        return orderList;
    }
    public List<Order> getOrdersByDriver(Driver driver){
        List<Order> orderList = new ArrayList<>();
        for(Order order : objectList){
            if(order.getDriver().equals(driver)){
                orderList.add(order);
            }
        }
        return orderList;
    }
    public List<Order> getOrdersByRestaurant(Restaurant restaurant){
        List<Order> orderList = new ArrayList<>();
        for(Order order : objectList){
            if(order.getRestaurant().equals(restaurant)){
                orderList.add(order);
            }
        }
        return orderList;
    }

}
