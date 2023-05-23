package services;

import csv.readers_writers.*;
import exceptions.*;
import models.*;
import repositories.OrderRepository;
import repositories.RecipeRepository;
import repositories.RestaurantRepository;
import repositories.UserRepository;
import utils.Constants;
import utils.OrderStatusType;
import utils.VehicleType;
import java.util.*;

public class AppService {
    private static UserRepository userRepository = UserRepository.getInstance();
    private static RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    private static RecipeRepository recipeRepository = RecipeRepository.getInstance();
    private static OrderRepository orderRepository = OrderRepository.getInstance();
    private static User currentUser = null;
    private static Set<Order> ordersToDeliver = new HashSet<>();
    private AppService() {
    }

    protected static void checkIfLoggedIn() throws NotLoggedInException{
        if (currentUser == null)
            throw new NotLoggedInException();
    }
    protected static void checkIfOrderExists(Order order) throws OrderNotFoundException{
        if (order == null)
            throw new OrderNotFoundException();
    }
    protected static void checkIfRestaurantExists(Restaurant restaurant) throws RestaurantNotFoundException{
        if (restaurant == null)
            throw new RestaurantNotFoundException();
    }
    protected static void checkIfRecipeExists(Recipe recipe) throws RecipeNotFoundException{
        if (recipe == null)
            throw new RecipeNotFoundException();
    }
    protected static void checkIfRestaurantHasRecipe(Restaurant restaurant, Recipe recipe) throws RestaurantDoesNotHaveRecipeException{
        if (!restaurant.hasRecipe(recipe))
            throw new RestaurantDoesNotHaveRecipeException();
    }
    protected static void checkIfUserOwnsRestaurant(RestaurantOwner user, Restaurant restaurant) throws OwnerDoesNotHaveRestaurantException{
        if (!user.hasRestaurant(restaurant))
            throw new OwnerDoesNotHaveRestaurantException();
    }
    protected static void checkUsername(String username) throws UsernameIsTakenException{
        if (userRepository.getUserByUsername(username) != null)
            throw new UsernameIsTakenException();
    }
    protected static void checkIfDriverOwnsOrder(Driver driver, Order order) throws DriverDoesNotOwnOrderException{
        if (!driver.equals(order.getDriver()))
            throw new DriverDoesNotOwnOrderException();
    }
    protected static void checkIfLoggedInAsDriver(){
        if (!(currentUser instanceof Driver))
            throw new OnlyDriversCanDeliverOrdersException();
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
            OnlyDriversCanDeliverOrdersException, DriverDoesNotOwnOrderException{

        checkIfOrderExists(order);
        checkIfLoggedIn();
        checkIfLoggedInAsDriver();
        Driver driver = (Driver) currentUser;
        checkIfDriverOwnsOrder(driver, order);
    }

    public static void login(String username, String password) throws UserNotFoundException, WrongPasswordException{
        AuditService.getInstance().logAction(Constants.LOGIN_ACTION);
        User user = userRepository.getUserByUsername(username);
        if (user == null)
            throw new UserNotFoundException();
        if (!user.getPassword().equals(password))
            throw new WrongPasswordException();
        currentUser = user;
    }
    public static void logout() throws NotLoggedInException{
        AuditService.getInstance().logAction(Constants.LOGOUT_ACTION);
        checkIfLoggedIn();
        currentUser = null;
    }
    public static void registerCustomersFromCSV() throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_CUSTOMERS_FROM_CSV_ACTION);
        CustomerCSVReaderWriter readerWriter = CustomerCSVReaderWriter.getInstance();
        List<Customer> customers = readerWriter.read();
        for (Customer customer : customers){
            checkUsername(customer.getUsername());
            userRepository.add(customer);
        }
    }
    public static void registerCustomer(String name, String username, String password, String email, String phoneNumber,
                                        String address) throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_CUSTOMER_ACTION);
        checkUsername(username);
        userRepository.add(new Customer(name, username, password, email, phoneNumber, address));
    }
    public static void registerDriversFromCSV() throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_DRIVERS_FROM_CSV_ACTION);
        DriverCSVReaderWriter readerWriter = DriverCSVReaderWriter.getInstance();
        List<Driver> drivers = readerWriter.read();
        for (Driver driver : drivers){
            checkUsername(driver.getUsername());
            userRepository.add(driver);
        }
    }
    public static void registerDriver(String name, String username, String password, String email, String phoneNumber,
                                      String address, String vehiclePlate, VehicleType vehicleType) throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_DRIVER_ACTION);
        checkUsername(username);
        userRepository.add(new Driver(name, username, password, email, phoneNumber, address, vehiclePlate, vehicleType));
    }
    public static void registerRestaurantOwnersFromCSV() throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_RESTAURANT_OWNERS_FROM_CSV_ACTION);
        RestaurantOwnerCSVReaderWriter readerWriter = RestaurantOwnerCSVReaderWriter.getInstance();
        List<RestaurantOwner> restaurantOwners = readerWriter.read();
        for (RestaurantOwner restaurantOwner : restaurantOwners){
            checkUsername(restaurantOwner.getUsername());
            userRepository.add(restaurantOwner);
        }
    }
    public static void registerRestaurantOwner(String name, String username, String password, String email,
                                               String phoneNumber, String address) throws UsernameIsTakenException{
        AuditService.getInstance().logAction(Constants.REGISTER_RESTAURANT_OWNER_ACTION);
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
        AuditService.getInstance().logAction(Constants.START_ORDER_ACTION);
        checkIfLoggedIn();
        if (!(currentUser instanceof Customer))
            throw new OnlyCustomersCanOrderException();
        Customer customer = (Customer) currentUser;
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        Order order = new Order(customer.getAddress());
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        orderRepository.add(order);
        return order;
    }
    public static void addRecipeToOrder(String recipeName, Order order) throws RecipeNotFoundException{
        AuditService.getInstance().logAction(Constants.ADD_RECIPE_TO_ORDER_ACTION);
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.addRecipe(recipe);
    }
    public static void cancelOrder(Order order) throws OrderNotFoundException, NotLoggedInException, OwnerDoesNotHaveRestaurantException{
        AuditService.getInstance().logAction(Constants.CANCEL_ORDER_ACTION);
        checkIfOrderExists(order);
        checkIfLoggedIn();
        if (currentUser instanceof RestaurantOwner restaurantOwner){
            checkIfUserOwnsRestaurant(restaurantOwner, order.getRestaurant());
        }
        else if(currentUser instanceof Driver){
            throw new DriversCannotCancelOrdersException();
        }
        else if(currentUser instanceof Customer customer){
            if (!customer.equals(order.getCustomer()))
                throw new CustomerDoesNotOwnOrder();
        }
        order.cancel();
        ordersToDeliver.remove(order);
    }
    public static void removeRecipeFromOrder(String recipeName, Order order) throws RecipeNotFoundException{
        AuditService.getInstance().logAction(Constants.REMOVE_RECIPE_FROM_ORDER_ACTION);
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkRecipe(recipeName, order);
        order.removeRecipe(recipe);
    }
    public static void sendOrder(Order order) throws OrderNotFoundException{
        AuditService.getInstance().logAction(Constants.SEND_ORDER_ACTION);
        checkIfOrderExists(order);
        order.setDeliveryTime(estimateDeliveryTime());
        order.setStatus(OrderStatusType.IN_DELIVERY);
        ordersToDeliver.add(order);
    }
    public static void readRestaurantsFromCSV(){
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCandAddRestaurantsException();
        RestaurantCSVReaderWriter readerWriter = RestaurantCSVReaderWriter.getInstance();
        List<Restaurant> restaurants = readerWriter.read();
        for (Restaurant restaurant : restaurants){
            restaurantRepository.add(restaurant, restaurantOwner);
            restaurantOwner.addRestaurant(restaurant);
        }
    }
    public static void addRestaurant(String restaurantName, String address, String phoneNumber) throws OnlyOwnersCandAddRestaurantsException{
        AuditService.getInstance().logAction(Constants.ADD_RESTAURANT_ACTION);
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCandAddRestaurantsException();
        Restaurant restaurant = new Restaurant(restaurantName, address, phoneNumber);
        restaurant.setRestaurantOwner(restaurantOwner);
        restaurantRepository.add(restaurant, restaurantOwner);
        restaurantOwner.addRestaurant(restaurant);
    }
    public static void removeRestaurant(String restaurantName) throws OnlyOwnersCanRemoveRestaurantsException,
            RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        AuditService.getInstance().logAction(Constants.REMOVE_RESTAURANT_ACTION);
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanRemoveRestaurantsException();
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        checkIfUserOwnsRestaurant(restaurantOwner, restaurant);

        restaurantRepository.remove(restaurant);
        restaurantOwner.removeRestaurant(restaurant);
    }
    public static void sortUsersByName(){
        AuditService.getInstance().logAction(Constants.SORT_USERS_BY_NAME_ACTION);
        userRepository.sortUsersByName();
    }
    public static void printUsers(){
        AuditService.getInstance().logAction(Constants.PRINT_USERS_ACTION);
        userRepository.printUsers();
    }
    public static void addRecipeToRestaurant(String recipeName, String description, Integer price, Integer preparationTime,
                                             String restaurantName) throws OnlyOwnersCanAddRecipesToRestaurantsException,
                                             RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        AuditService.getInstance().logAction(Constants.ADD_RECIPE_TO_RESTAURANT_ACTION);
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanAddRecipesToRestaurantsException();
        Recipe recipe = new Recipe(recipeName, description, price, preparationTime);
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        checkIfUserOwnsRestaurant(restaurantOwner, restaurant);
        restaurant.addRecipe(recipe);
        recipeRepository.add(recipe, restaurant);
    }
    public static void removeRecipeFromRestaurant(String recipeName, String restaurantName) throws OnlyOwnersCanRemoveRecipesFromRestaurantsException,
            RecipeNotFoundException, RestaurantNotFoundException, OwnerDoesNotHaveRestaurantException{
        AuditService.getInstance().logAction(Constants.REMOVE_RECIPE_FROM_RESTAURANT_ACTION);
        if(!(currentUser instanceof RestaurantOwner restaurantOwner))
            throw new OnlyOwnersCanRemoveRecipesFromRestaurantsException();
        Recipe recipe = recipeRepository.getRecipeByName(recipeName);
        checkIfRecipeExists(recipe);
        Restaurant restaurant = restaurantRepository.getRestaurantByName(restaurantName);
        checkIfRestaurantExists(restaurant);
        checkIfUserOwnsRestaurant(restaurantOwner, restaurant);
        restaurant.removeRecipe(recipe);
        recipeRepository.remove(recipe);
    }
    public static void printOrderHistory(boolean toFile) throws NotLoggedInException, RestaurantOwnerDoesNotHaveOrderHistoryException{
        AuditService.getInstance().logAction(Constants.PRINT_ORDER_HISTORY_ACTION);
        checkIfLoggedIn();
        if (currentUser instanceof RestaurantOwner)
            throw new RestaurantOwnerDoesNotHaveOrderHistoryException();
        List<Order> orders = null;
        if (currentUser instanceof Driver)
            orders = orderRepository.getOrdersByDriver((Driver) currentUser);
        else if (currentUser instanceof Customer)
            orders = orderRepository.getOrdersByCustomer((Customer) currentUser);
        if (toFile){
            OrderCSVReaderWriter readerWriter = OrderCSVReaderWriter.getInstance();
            for (Order order : orders){
                readerWriter.write(order);
            }
        }
        else{
            for (Order order : orders){
                System.out.println(order);
            }
        }
    }
    public static Order getOrderToDeliver() throws NoOrdersToDeliverException, NotLoggedInException, OnlyDriversCanDeliverOrdersException{
        AuditService.getInstance().logAction(Constants.GET_ORDER_TO_DELIVER_ACTION);
        checkIfLoggedIn();
        checkIfLoggedInAsDriver();
        if (ordersToDeliver.isEmpty())
            throw new NoOrdersToDeliverException();
        Order order = ordersToDeliver.stream().findFirst().get();
        ordersToDeliver.remove(order);
        order.setDriver((Driver) currentUser);
        return order;
    }
    public static void deliverOrder(Order order) throws OrderNotFoundException, NotLoggedInException, OnlyDriversCanDeliverOrdersException{
        AuditService.getInstance().logAction(Constants.DELIVER_ORDER_ACTION);
        checkForDelivery(order);
        order.deliver();
    }
    public static void refuseDelivery(Order order) throws OrderNotFoundException, NotLoggedInException, OnlyDriversCanDeliverOrdersException{
        AuditService.getInstance().logAction(Constants.REFUSE_DELIVERY_ACTION);
        checkForDelivery(order);
        order.setDriver(null);
        ordersToDeliver.add(order);
    }
    public static void markAsDelivered(Order order) throws OrderNotFoundException, NotLoggedInException, OnlyDriversCanDeliverOrdersException{
        AuditService.getInstance().logAction(Constants.MARK_AS_DELIVERED_ACTION);
        checkForDelivery(order);
        order.markAsDelivered();
        orderRepository.addToDB(order);
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
