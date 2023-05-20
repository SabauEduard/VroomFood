package repositories;

import models.*;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends GenericRepository<Order>{
    private static final OrderRepository instance = new OrderRepository();
    public OrderRepository(){
        super();
        try{
            PreparedStatement statement = databaseConfiguration.getConnection().prepareStatement("SELECT * FROM order_order");
            var result = statement.executeQuery();
            while(result.next()) {
                Order current = new Order(result);
                current.setRecipes(getRecipesFromTable(current));
                objectList.add(current);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public Order getOrderById(int id){
        for(Order order : objectList){
            if(order.getId() == id)
                return order;
        }
        return null;
    }
    public static OrderRepository getInstance(){
        return instance;
    }
    public List<Recipe> getRecipesFromTable(Order order){
        String query = "SELECT * FROM order_recipe WHERE orderId = ?";
        List<Recipe> recipeList = new ArrayList<>();
        try{
            PreparedStatement statement = databaseConfiguration.getConnection().prepareStatement(query);
            statement.setInt(1, order.getId());
            var result = statement.executeQuery();
            while(result.next()) {
                int recipeId = result.getInt("recipeId");
                Recipe recipe = RecipeRepository.getInstance().getRecipeById(recipeId);
                recipeList.add(recipe);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return recipeList;
    }
    public void addToDB(Order order){
        try{
            String query = "INSERT INTO order_order (id, totalPrice, deliveryAddress, deliveryTime, status, customerId, driverId, restaurantId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, order.getId());
            preparedStmt.setInt(2, order.getTotalPrice());
            preparedStmt.setString(3, order.getDeliveryAddress());
            preparedStmt.setInt(4, order.getDeliveryTime());
            preparedStmt.setString(5, order.getStatus().toString());
            preparedStmt.setInt(6, order.getCustomer().getId());
            preparedStmt.setInt(7, order.getDriver().getId());
            preparedStmt.setInt(8, order.getRestaurant().getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        for(Recipe recipe : order.getRecipes()){
            try{
                String query = "INSERT INTO order_recipe (orderId, recipeId) VALUES (?, ?)";
                PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
                preparedStmt.setInt(1, order.getId());
                preparedStmt.setInt(2, recipe.getId());
                preparedStmt.execute();
                preparedStmt.close();
            }catch (Exception e){
                System.out.println(e.toString());
            }
        }
        objectList.add(order);
    }
    @Override
    public void remove(Order order){
        try {
            for(Recipe recipe : order.getRecipes()) {
                String query = "DELETE FROM order_recipe WHERE orderId = ? AND recipeId = ?";
                PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
                preparedStmt.setInt(1, order.getId());
                preparedStmt.setInt(2, recipe.getId());
                preparedStmt.execute();
                preparedStmt.close();
            }
        }catch (Exception e){
            System.out.println(e.toString());

        }
        try{
            String query = "DELETE FROM order_order WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, order.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }

        objectList.remove(order);
    }
    @Override
    public void update(Order oldOrder, Order newOrder){
        try{
            String query = "UPDATE order_order SET id = ?, totalPrice = ?, deliveryAddress = ?, deliveryTime = ?, status = ?, customerId = ?, driverId = ?, restaurantId = ? WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newOrder.getId());
            preparedStmt.setInt(2, newOrder.getTotalPrice());
            preparedStmt.setString(3, newOrder.getDeliveryAddress());
            preparedStmt.setInt(4, newOrder.getDeliveryTime());
            preparedStmt.setString(5, newOrder.getStatus().toString());
            preparedStmt.setInt(6, newOrder.getCustomer().getId());
            preparedStmt.setInt(7, newOrder.getDriver().getId());
            preparedStmt.setInt(8, newOrder.getRestaurant().getId());
            preparedStmt.setInt(9, oldOrder.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
        objectList.set(objectList.indexOf(oldOrder), newOrder);
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
            if(order.getDriver() != null && order.getDriver().equals(driver)){
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
