package repositories;

import models.Restaurant;

import java.util.List;

public class RestaurantRepository extends GenericRepository<Restaurant>{
    public RestaurantRepository(List<Restaurant> objectList) {
        super(objectList);
    }
    public RestaurantRepository() {
        super();
    }
    public Restaurant getRestaurantById(int id){
        for(Restaurant restaurant : objectList){
            if(restaurant.getId() == id)
                return restaurant;
        }
        return null;
    }
    public Restaurant getRestaurantByName(String name){
        for(Restaurant restaurant: objectList){
            if(restaurant.getName().equals(name))
                return restaurant;
        }
        return null;
    }
}
