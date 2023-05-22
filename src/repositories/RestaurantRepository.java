package repositories;

import models.Recipe;
import models.Restaurant;
import models.RestaurantOwner;
import utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class RestaurantRepository extends GenericRepository<Restaurant>{
    private static final RestaurantRepository instance = new RestaurantRepository();
    private RestaurantRepository() {
        super();
        try{
            Statement statement = databaseConfiguration.getConnection().createStatement();
            ResultSet result = statement.executeQuery(Constants.SELECT_ALL_RESTAURANTS);
            while(result.next()) {
                Restaurant current = new Restaurant(result);
                objectList.add(current);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public static RestaurantRepository getInstance(){
        return instance;
    }
    @Override
    public void remove(Restaurant restaurant){
        try{
            String query = Constants.DELETE_RESTAURANT;
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, restaurant.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        objectList.remove(restaurant);
    }

    @Override
    public void update(Restaurant oldRestaurant, Restaurant newRestaurant){
        try{
            String query = Constants.UPDATE_RESTAURANT;
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newRestaurant.getId());
            preparedStmt.setString(2, newRestaurant.getName());
            preparedStmt.setString(3, newRestaurant.getAddress());
            preparedStmt.setString(4, newRestaurant.getPhoneNumber());
            preparedStmt.setInt(5, oldRestaurant.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }

        int index = objectList.indexOf(oldRestaurant);
        objectList.set(index, newRestaurant);
    }
    public void add(Restaurant restaurant, RestaurantOwner restaurantOwner){
        try{
            String query = Constants.INSERT_RESTAURANT;
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, restaurant.getId());
            preparedStmt.setString(2,restaurant.getName());
            preparedStmt.setString(3, restaurant.getAddress());
            preparedStmt.setString(4, restaurant.getPhoneNumber());
            preparedStmt.setInt(5, restaurantOwner.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        objectList.add(restaurant);
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
