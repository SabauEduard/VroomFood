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
    public static void cancelOrder(Order order) throws OrderNotFoundException, NotLoggedInException, OwnerDoesNotHaveRestaurantException{
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        if (currentUser == null)
            throw new NotLoggedInException("There is no user currently logged in \n");
        if (currentUser instanceof RestaurantOwner restaurantOwner){
            if (!restaurantOwner.hasRestaurant(order.getRestaurant()))
                throw new OwnerDoesNotHaveRestaurantException("You are not logged in as the owner of this restaurant \n");
        }
        else if(currentUser instanceof Driver){
            throw new DriversCannotCancelOrdersException("Drivers cannot cancel orders \n");
        }
        else if(currentUser instanceof Customer customer){
            if (!customer.equals(order.getCustomer()))
                throw new CustomerDoesNotOwnOrder("You are not logged in as the customer of this order \n");
        }
        order.cancel();
        ordersToDeliver.remove(order);
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
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCandAddRestaurantsException("Only restaurant owners can add restaurants \n");
        Restaurant restaurant = new Restaurant(restaurantName, address, phoneNumber, restaurantOwner);
        restaurantRepository.add(restaurant);
        restaurantOwner.addRestaurant(restaurant);
    }
    public static void removeRestaurant(String restaurantName) throws OnlyOwnersCanRemoveRestaurantsException,
            RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanRemoveRestaurantsException("Only restaurant owners can remove restaurants \n");
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new RestaurantNotFoundException("Restaurant not found \n");
        if (!restaurantOwner.hasRestaurant(restaurant))
            throw new OwnerDoesNotHaveRestaurantException("User does not own this restaurant \n");
        restaurantRepository.remove(restaurant);
        restaurantOwner.removeRestaurant(restaurant);
    }
    public static void sortUsersByName(){
        userRepository.sortUsersByName();
    }

    public static void printUsers(){
        userRepository.printUsers();
    }
    public static void addRecipeToRestaurant(String recipeName, String restaurantName) throws OnlyOwnersCanAddRecipesToRestaurantsException,
            RecipeNotFoundException, RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanAddRecipesToRestaurantsException("Only restaurant owners can add recipes to restaurants \n");
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

    public static void removeRecipeFromRestaurant(String recipeName, String restaurantName) throws OnlyOwnersCanRemoveRecipesFromRestaurantsException,
            RecipeNotFoundException, RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanRemoveRecipesFromRestaurantsException("Only restaurant owners can remove recipes from restaurants \n");
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        if (recipe == null)
            throw new RecipeNotFoundException("Recipe not found \n");
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        if (restaurant == null)
            throw new RestaurantNotFoundException("Restaurant not found \n");
        if (!restaurantOwner.hasRestaurant(restaurant))
            throw new OwnerDoesNotHaveRestaurantException("User does not own this restaurant \n");
        restaurant.removeRecipe(recipe);
    }

    public static void printOrderHistory(){
        if (currentUser == null)
            throw new NotLoggedInException("There is no user currently logged in \n");
        if (currentUser instanceof RestaurantOwner)
            throw new RestaurantOwnerDoesNotHaveOrderHistoryException("Restaurant owners do not have order history \n");
        List<Order> orders = null;
        if (currentUser instanceof Driver)
            orders = orderRepository.getOrdersByDriver((Driver) currentUser);
        else if (currentUser instanceof Customer)
            orders = orderRepository.getOrdersByCustomer((Customer) currentUser);
        for (Order order : orders)
            System.out.println(order);
    }

    public static Order getOrderToDeliver(){
        if (currentUser == null)
            throw new NotLoggedInException("There is no user currently logged in \n");
        if (!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException("Only drivers can deliver orders \n");
        if (ordersToDeliver.isEmpty())
            throw new NoOrdersToDeliverException("There are no orders to deliver \n");
        Order order = ordersToDeliver.stream().findFirst().get();
        ordersToDeliver.remove(order);
        return order;
    }

    public static void deliverOrder(Order order){
        if(!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException("Only drivers can deliver orders \n");
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        if (!order.getDriver().equals(currentUser))
            throw new DriverDoesNotOwnOrderException("You are not logged in as the driver of this order \n");
        order.deliver();
    }

    public static void refuseDelivery(Order order){
        if(!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException("Only drivers can deliver orders \n");
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        if (!order.getDriver().equals(currentUser))
            throw new DriverDoesNotOwnOrderException("You are not logged in as the driver of this order \n");
        order.refuseDelivery();
        ordersToDeliver.add(order);
    }

    public static void markAsDelivered(Order order){
        if(!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException("Only drivers can deliver orders \n");
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
        if (!order.getDriver().equals(currentUser))
            throw new DriverDoesNotOwnOrderException("You are not logged in as the driver of this order \n");
        order.markAsDelivered();
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
