package Services;

import Models.*;
import Repositories.OrderRepository;
import Repositories.RecipeRepository;
import Repositories.RestaurantRepository;
import Repositories.UserRepository;
import Utils.OrderStatusType;
import Utils.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppService {
    private static UserRepository userRepository = new UserRepository();
    private static RestaurantRepository restaurantRepository = new RestaurantRepository();
    private static RecipeRepository recipeRepository = new RecipeRepository();
    private static OrderRepository orderRepository = new OrderRepository();
    private static User currentUser = null;
    private static List<Order> ordersToDeliver = new ArrayList<>();

    public static void login(String username, String password) throws IllegalArgumentException{
        User user = userRepository.getUserByUsername(username);
        if (user == null)
            throw new IllegalArgumentException("User not found");
        if (!user.getPassword().equals(password))
            throw new IllegalArgumentException("Wrong password");
        currentUser = user;
    }

    public static void logout() throws IllegalArgumentException{
        if (currentUser == null)
            throw new IllegalArgumentException("No user logged in");
        currentUser = null;
    }

    public static void registerCustomer(String name, String username, String password, String email, String phoneNumber, String address) throws IllegalArgumentException{
       if (userRepository.getUserByUsername(username) != null)
            throw new IllegalArgumentException("Username already exists");
        userRepository.add(new Customer(name, username, password, email, phoneNumber, address));
    }
    public static void registerDriver(String name, String username, String password, String email, String phoneNumber, String address, String vehiclePlate, VehicleType vehicleType) throws IllegalArgumentException{
        if (userRepository.getUserByUsername(username) != null)
              throw new IllegalArgumentException("Username already exists");
        userRepository.add(new Driver(name, username, password, email, phoneNumber, address, vehiclePlate, vehicleType));
    }
    public static void registerRestaurantOwner(String name, String username, String password, String email, String phoneNumber, String address) throws IllegalArgumentException{
        if (userRepository.getUserByUsername(username) != null)
           throw new IllegalArgumentException("Username already exists");
        userRepository.add(new RestaurantOwner(name, username, password, email, phoneNumber, address));
    }

    protected static int estimateDeliveryTime(){
        Random r = new Random();
        int low = 10;
        int high = 90;
        return r.nextInt(high-low) + low;
    }
    public static Order startOrder(String restaurantName) throws IllegalArgumentException{
        Customer customer = (Customer) currentUser;
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new IllegalArgumentException("Restaurant not found");
        Order order = new Order(customer, null, restaurant, 0, customer.getAddress());
        orderRepository.add(order);
        return order;
    }
    protected static void checkRecipe(String recipeName, Order order) throws IllegalArgumentException {

        Customer customer = (Customer) currentUser;
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        if (recipe == null)
            throw new IllegalArgumentException("Recipe not found");
        if (order == null)
            throw new IllegalArgumentException("Order not found");
        Restaurant restaurant = order.getRestaurant();
        if (!restaurant.hasRecipe(recipe))
            throw new IllegalArgumentException("Recipe not found in restaurant");
    }
    public static void addRecipeToOrder(String recipeName, Order order) throws IllegalArgumentException{

        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.addRecipe(recipe);
    }
    public static void cancelOrder(Order order) throws IllegalArgumentException{
        if (order == null)
            throw new IllegalArgumentException("Order not found");
        order.setStatus(OrderStatusType.CANCELLED);
    }
    public static void removeRecipe(String recipeName, Order order) throws IllegalArgumentException{
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.removeRecipe(recipe);
    }
    public static void sendOrder(Order order) throws IllegalArgumentException{
        if (order == null)
            throw new IllegalArgumentException("Order not found");
        order.setDeliveryTime(estimateDeliveryTime());
        order.setStatus(OrderStatusType.IN_DELIVERY);
        ordersToDeliver.add(order);
    }
    public static void addRecipe(String recipeName, String description, Integer price, Integer preparationTime, List<String> ingredientList){
        Recipe recipe = new Recipe(recipeName, description, price, preparationTime, ingredientList);
        recipeRepository.add(recipe);
    }
    public static void addRestaurant(String restaurantName, String address, String phoneNumber){
        if(!(currentUser instanceof RestaurantOwner))
            throw new IllegalArgumentException("Only restaurant owners can add restaurants");
        RestaurantOwner restaurantOwner = (RestaurantOwner) currentUser;
        Restaurant restaurant = new Restaurant(restaurantName, address, phoneNumber, restaurantOwner);
        restaurantRepository.add(restaurant);
        restaurantOwner.addRestaurant(restaurant);
    }
    public static void addRecipeToRestaurant(String recipeName, String restaurantName) throws IllegalArgumentException{
        if(!(currentUser instanceof RestaurantOwner))
            throw new IllegalArgumentException("Only restaurant owners can add recipes to restaurants");
        RestaurantOwner restaurantOwner = (RestaurantOwner) currentUser;
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        if (recipe == null)
            throw new IllegalArgumentException("Recipe not found");
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new IllegalArgumentException("Restaurant not found");
        if (!restaurantOwner.hasRestaurant(restaurant))
            throw new IllegalArgumentException("Restaurant not found in restaurant owner");
        restaurant.addRecipe(recipe);
    }
    public static UserRepository getUserRepository() {
        return userRepository;
    }

    public static void setUserRepository(UserRepository userRepository) {
        AppService.userRepository = userRepository;
    }

    public static RestaurantRepository getRestaurantRepository() {
        return restaurantRepository;
    }

    public static void setRestaurantRepository(RestaurantRepository restaurantRepository) {
        AppService.restaurantRepository = restaurantRepository;
    }

    public static RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }

    public static void setRecipeRepository(RecipeRepository recipeRepository) {
        AppService.recipeRepository = recipeRepository;
    }

    public static OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public static void setOrderRepository(OrderRepository orderRepository) {
        AppService.orderRepository = orderRepository;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
