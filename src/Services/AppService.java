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

    protected static void checkIfLoggedIn() throws NotLoggedInException{
        if (currentUser == null)
            throw new NotLoggedInException("There is no user currently logged in \n");
    }
    protected static void checkIfOrderExists(Order order) throws OrderNotFoundException{
        if (order == null)
            throw new OrderNotFoundException("Order not found \n");
    }
    protected static void checkIfRestaurantExists(Restaurant restaurant) throws RestaurantNotFoundException{
        if (restaurant == null)
            throw new RestaurantNotFoundException("Restaurant not found \n");
    }
    protected static void checkIfRecipeExists(Recipe recipe) throws RecipeNotFoundException{
        if (recipe == null)
            throw new RecipeNotFoundException("Recipe not found \n");
    }
    protected static void checkIfRestaurantHasRecipe(Restaurant restaurant, Recipe recipe) throws RestaurantDoesNotHaveRecipeException{
        if (!restaurant.hasRecipe(recipe))
            throw new RestaurantDoesNotHaveRecipeException("This restaurant does not serve this recipe \n");
    }
    protected static void checkIfUserOwnsRestaurant(RestaurantOwner user, Restaurant restaurant) throws OwnerDoesNotHaveRestaurantException{
        if (!user.hasRestaurant(restaurant))
            throw new OwnerDoesNotHaveRestaurantException("This user does not own this restaurant \n");
    }
    protected static void checkUsername(String username) throws UsernameIsTakenException{
        if (userRepository.getUserByUsername(username) != null)
            throw new UsernameIsTakenException("This username is already taken \n");
    }
    protected static void checkIfDriverOwnsOrder(Driver driver, Order order) throws DriverDoesNotOwnOrderException{
        if (!driver.equals(order.getDriver()))
            throw new DriverDoesNotOwnOrderException("This driver does not own this order \n");
    }
    protected static void checkIfLoggedInAsDriver(){
        if (!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException("Only drivers can deliver orders \n");
    }
    protected static void checkRecipe(String recipeName, Order order) throws RecipeNotFoundException,
            OrderNotFoundException, RestaurantDoesNotHaveRecipeException{

        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkIfRecipeExists(recipe);
        checkIfOrderExists(order);
        Restaurant restaurant = order.getRestaurant();
        checkIfRestaurantHasRecipe(restaurant, recipe);
    }
    protected static void checkForDelivery(Order order) throws OrderNotFoundException, NotLoggedInException,
            DriverDoesNotOwnOrderException, OnlyDriversCanDeliverOrdersException{

        checkIfOrderExists(order);
        checkIfLoggedIn();
        checkIfLoggedInAsDriver();
        Driver driver = (Driver) currentUser;
        checkIfDriverOwnsOrder(driver, order);
    }

    public static void login(String username, String password) throws UserNotFoundException, WrongPasswordException{
        User user = userRepository.getUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException("This is not a registered user \n");
        if (!user.getPassword().equals(password))
            throw new WrongPasswordException("Wrong password \n");
        currentUser = user;
    }

    public static void logout() throws NotLoggedInException{
        checkIfLoggedIn();
        currentUser = null;
    }

    public static void registerCustomer(String name, String username, String password, String email, String phoneNumber,
                                        String address) throws UsernameIsTakenException{
        checkUsername(username);
        userRepository.add(new Customer(name, username, password, email, phoneNumber, address));
    }
    public static void registerDriver(String name, String username, String password, String email, String phoneNumber,
                                      String address, String vehiclePlate, VehicleType vehicleType) throws UsernameIsTakenException{
        checkUsername(username);
        userRepository.add(new Driver(name, username, password, email, phoneNumber, address, vehiclePlate, vehicleType));
    }
    public static void registerRestaurantOwner(String name, String username, String password, String email,
                                               String phoneNumber, String address) throws UsernameIsTakenException{
        checkUsername(username);
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
        checkIfRestaurantExists(restaurant);
        Order order = new Order(customer, null, restaurant, 0, customer.getAddress());
        orderRepository.add(order);
        return order;
    }

    public static void addRecipeToOrder(String recipeName, Order order) throws RecipeNotFoundException{

        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.addRecipe(recipe);
    }
    public static void cancelOrder(Order order) throws OrderNotFoundException, NotLoggedInException, OwnerDoesNotHaveRestaurantException{
        checkIfOrderExists(order);
        checkIfLoggedIn();
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
        checkIfOrderExists(order);
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
        checkIfRestaurantExists(restaurant);
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
        checkIfRecipeExists(recipe);
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        checkIfUserOwnsRestaurant(restaurantOwner, restaurant);
        restaurant.addRecipe(recipe);
    }

    public static void removeRecipeFromRestaurant(String recipeName, String restaurantName) throws OnlyOwnersCanRemoveRecipesFromRestaurantsException,
            RecipeNotFoundException, RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanRemoveRecipesFromRestaurantsException("Only restaurant owners can remove recipes from restaurants \n");
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkIfRecipeExists(recipe);
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        checkIfUserOwnsRestaurant(restaurantOwner, restaurant);
        restaurant.removeRecipe(recipe);
    }

    public static void printOrderHistory(){
        checkIfLoggedIn();
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
        checkIfLoggedIn();
        checkIfLoggedInAsDriver();
        if (ordersToDeliver.isEmpty())
            throw new NoOrdersToDeliverException("There are no orders to deliver \n");
        Order order = ordersToDeliver.stream().findFirst().get();
        ordersToDeliver.remove(order);
        return order;
    }

    public static void deliverOrder(Order order){
        checkForDelivery(order);
        order.deliver();
    }

    public static void refuseDelivery(Order order){
        checkForDelivery(order);
        ordersToDeliver.add(order);
    }

    public static void markAsDelivered(Order order){
        checkForDelivery(order);
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
