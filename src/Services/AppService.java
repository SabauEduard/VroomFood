package Services;

import Exceptions.*;
import Models.*;
import Repositories.OrderRepository;
import Repositories.RecipeRepository;
import Repositories.RestaurantRepository;
import Repositories.UserRepository;
import Utils.OrderStatusType;
import Utils.VehicleType;

import java.util.*;

public class AppService {
    private static UserRepository userRepository = new UserRepository();
    private static RestaurantRepository restaurantRepository = new RestaurantRepository();
    private static RecipeRepository recipeRepository = new RecipeRepository();
    private static OrderRepository orderRepository = new OrderRepository();
    private static User currentUser = null;
    private static Set<Order> ordersToDeliver = new HashSet<>();

    public static void login(String username, String password) throws UserNotFoundException, WrongPasswordException{
        User user = userRepository.getUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException("This is not a registered user \n");
        if (!user.getPassword().equals(password))
            throw new WrongPasswordException("Wrong password \n");
        currentUser = user;
    }

    public static void logout() throws NotLoggedInException{
        if (currentUser == null)
            throw new NotLoggedInException("There is no user currently logged in \n");
        currentUser = null;
    }

    public static void registerCustomer(String name, String username, String password, String email, String phoneNumber,
                                        String address) throws UsernameIsTakenException{
       if (userRepository.getUserByUsername(username) != null)
            throw new UsernameIsTakenException("This username is already taken \n");
        userRepository.add(new Customer(name, username, password, email, phoneNumber, address));
    }
    public static void registerDriver(String name, String username, String password, String email, String phoneNumber,
                                      String address, String vehiclePlate, VehicleType vehicleType) throws UsernameIsTakenException{
        if (userRepository.getUserByUsername(username) != null)
              throw new UsernameIsTakenException("This username is already taken \n");
        userRepository.add(new Driver(name, username, password, email, phoneNumber, address, vehiclePlate, vehicleType));
    }
    public static void registerRestaurantOwner(String name, String username, String password, String email,
                                               String phoneNumber, String address) throws UsernameIsTakenException{
        if (userRepository.getUserByUsername(username) != null)
           throw new UsernameIsTakenException("This username is already taken \n");
        userRepository.add(new RestaurantOwner(name, username, password, email, phoneNumber, address));
    }

    protected static int estimateDeliveryTime(){
        Random r = new Random();
        int low = 10;
        int high = 90;
        return r.nextInt(high-low) + low;
    }
    public static Order startOrder(String restaurantName) throws RestaurantNotFoundException{
        Customer customer = (Customer) currentUser;
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new RestaurantNotFoundException("Restaurant not found \n");
        Order order = new Order(customer, null, restaurant, 0, customer.getAddress());
        orderRepository.add(order);
        return order;
    }
    protected static void checkRecipe(String recipeName, Order order) throws RecipeNotFoundException,
            OrderNotFoundException, RestaurantDoesNotHaveRecipeException{

        Customer customer = (Customer) currentUser;
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        if (recipe == null)
            throw new RecipeNotFoundException("Recipe not found \n");
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        Restaurant restaurant = order.getRestaurant();
        if (!restaurant.hasRecipe(recipe))
            throw new RestaurantDoesNotHaveRecipeException("This restaurant does not serve this recipe \n");
    }
    public static void addRecipeToOrder(String recipeName, Order order) throws RecipeNotFoundException{

        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.addRecipe(recipe);
    }
    public static void cancelOrder(Order order) throws OrderNotFoundException{
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        order.setStatus(OrderStatusType.CANCELLED);
    }
    public static void removeRecipe(String recipeName, Order order) throws RecipeNotFoundException{
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.removeRecipe(recipe);
    }
    public static void sendOrder(Order order) throws OrderNotFoundException{
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        order.setDeliveryTime(estimateDeliveryTime());
        order.setStatus(OrderStatusType.IN_DELIVERY);
        ordersToDeliver.add(order);
    }
    public static void addRecipe(String recipeName, String description, Integer price, Integer preparationTime, List<String> ingredientList){
        Recipe recipe = new Recipe(recipeName, description, price, preparationTime, ingredientList);
        recipeRepository.add(recipe);
    }
    public static void addRestaurant(String restaurantName, String address, String phoneNumber) throws OnlyOwnersCandAddRestaurantsException{
        if(!(currentUser instanceof RestaurantOwner))
            throw new OnlyOwnersCandAddRestaurantsException("Only restaurant owners can add restaurants \n");
        RestaurantOwner restaurantOwner = (RestaurantOwner) currentUser;
        Restaurant restaurant = new Restaurant(restaurantName, address, phoneNumber, restaurantOwner);
        restaurantRepository.add(restaurant);
        restaurantOwner.addRestaurant(restaurant);
    }
    public static void sortUsersByName(){
        userRepository.sortUsersByName();
    }
    public static void addRecipeToRestaurant(String recipeName, String restaurantName) throws OnlyOwnersCanAddRecipesToRestaurantsException,
            RecipeNotFoundException, RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        if(!(currentUser instanceof RestaurantOwner))
            throw new OnlyOwnersCanAddRecipesToRestaurantsException("Only restaurant owners can add recipes to restaurants \n");
        RestaurantOwner restaurantOwner = (RestaurantOwner) currentUser;
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        if (recipe == null)
            throw new RecipeNotFoundException("Recipe not found \n");
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new RestaurantNotFoundException("Restaurant not found \n");
        if (!restaurantOwner.hasRestaurant(restaurant))
            throw new OwnerDoesNotHaveRestaurantException("User does not own this restaurant \n");
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
