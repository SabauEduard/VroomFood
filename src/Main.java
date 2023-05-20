import config.DatabaseConfiguration;
import models.Order;
import utils.VehicleType;
import services.AppService;
import exceptions.*;

import java.sql.Statement;

public class Main {
    public static void nukeDB(){
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        try{
            Statement statement = databaseConfiguration.getConnection().createStatement();
            statement.execute("DELETE FROM order_recipe WHERE id >= 0");
            statement.execute("DELETE FROM order_order WHERE id >= 0");
            statement.execute("DELETE FROM customer WHERE id >= 0");
            statement.execute("DELETE FROM driver WHERE id >= 0");
            statement.execute("DELETE FROM recipe WHERE id >= 0");
            statement.execute("DELETE FROM restaurant WHERE id >= 0");
            statement.execute("DELETE FROM restaurant_owner WHERE id >= 0");
            statement.close();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public static void runTests(){
        // Registering some users
        AppService.registerCustomer("John Doe", "johndoe", "123456", "johndoe1@gmail.com",
                "123456789", "123 Main St");
        AppService.registerRestaurantOwner("Jane Doe", "janedoe", "123456", "janedoe32@yahoo.com",
                "987654321", "456 Main St");
        AppService.registerDriver("John Smith", "johnsmith", "123456", "johnsmith10@unibuc.ro",
                "123456789", "123 Main St", "B 12 ABC", VehicleType.BIKE);

        // Registering customers from CSV
        AppService.registerCustomersFromCSV();

        // Registering restaurant owners from CSV
        AppService.registerRestaurantOwnersFromCSV();

        // Registering drivers from CSV
        AppService.registerDriversFromCSV();

        // Sorting the users by name
        AppService.sortUsersByName();

        // Printing the users
        AppService.printUsers();

        // Logging in as a restaurant owner
        AppService.login("janedoe", "123456");

        // Creating a restaurant and adding the recipes to it
        AppService.addRestaurant("Blue Margarita", "123 Main St", "123456789");
        AppService.addRecipeToRestaurant("Simple pizza", "Basic pizza" ,20, 30, "Blue Margarita");
        AppService.addRecipeToRestaurant("Ratatouille", "French dish" ,30, 40, "Blue Margarita");

        // Adding the restaurants from the CSV to the current user (restaurant owner)
        AppService.readRestaurantsFromCSV();

        // Adding some recipes to the restaurants from the CSV
        AppService.addRecipeToRestaurant("Cous cous", "French dish" ,10, 10, "Podu' cu Lanturi");
        AppService.addRecipeToRestaurant("Goulash", "Hungarian dish" ,35, 50, "Caru' cu Bere");

        //Logging out
        AppService.logout();

        // Logging in as a customer
        AppService.login("johndoe", "123456");

        // Creating an order
        Order order = AppService.startOrder("Blue Margarita");

        // Adding recipes to the order
        AppService.addRecipeToOrder("Simple pizza", order);
        AppService.addRecipeToOrder("Ratatouille", order);

        // Sending the order
        AppService.sendOrder(order);

        // Printing the order history of the customer
        AppService.printOrderHistory(false);

        // Logging out
        AppService.logout();

        // Printing the order
        System.out.println(order);

        // Logging as a customer from the CSV file
        AppService.login("mihpop", "123456");

        // Creating an order and adding recipes from a restaurant and cancelling it
        Order order2 = AppService.startOrder("Caru' cu Bere");
        AppService.addRecipeToOrder("Goulash", order2);
        AppService.cancelOrder(order2);

        // Printing the order history of the customer
        AppService.printOrderHistory(false);
        AppService.logout();

        // Logging in as a driver
        AppService.login("anip99", "123456");

        // Getting an order to delive
        Order orderToDeliver = AppService.getOrderToDeliver();

        // Delivering the order
        AppService.deliverOrder(orderToDeliver);

        // Mark the order as delivered
        AppService.markAsDelivered(orderToDeliver);

        // Printing the order history of the driver
        AppService.printOrderHistory(false);

        // Printing the order history of the driver to the CSV file output.csv
        AppService.printOrderHistory(true);
        AppService.logout();

        // Testing the exception handling

        // Trying to register a user with an already taken username
        try {
            AppService.registerCustomer("Joh Doeeee", "johndoe", "123456", "johndoe@gmail.com", "123456789", "123 Main St");
        } catch (UsernameIsTakenException e) {
            System.out.println(e.getMessage());
        }

        // Trying to log out without being logged in
        try {
            AppService.logout();
        } catch (NotLoggedInException e) {
            System.out.println(e.getMessage());
        }

        // Trying to log in with the wrong password
        try {
            AppService.login("johndoe", "1234567");
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
        }

        // Trying to add a restaurant as a Customer
        try {
            AppService.login("johndoe", "123456");
            AppService.addRestaurant("Blue Margarita", "123 Main St", "123456789");
        } catch (OnlyOwnersCandAddRestaurantsException e) {
            System.out.println(e.getMessage());
        }

        // Trying to add a recipe as a Customer
        try {
            AppService.addRecipeToRestaurant("Peperoni pizza","Pizza with peperoni", 30, 30, "Blue Margarita");
        } catch (OnlyOwnersCanAddRecipesToRestaurantsException e) {
            System.out.println(e.getMessage());
        }

        // Trying to add a recipe to a non-existent restaurant
        try {
            AppService.login("janedoe", "123456");
            AppService.addRecipeToRestaurant("Peperoni pizza","Pizza with peperoni", 30, 30,"Blue Margaritaaaaa");
        } catch (RestaurantNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // Trying to add a recipe to a restaurant that doesn't belong to the logged in restaurant owner
        try{
            AppService.login("mikesmith", "123456"); // Logging in as a restaurant owner
            AppService.addRecipeToRestaurant("Peperoni pizza","Pizza with peperoni", 30, 30, "Blue Margarita"); // User does not own Blue Margarita
        } catch (OwnerDoesNotHaveRestaurantException e) {
            System.out.println(e.getMessage());
        }

    }
    public static void main(String[] args) {
        runTests();
        // Deleting everything from the database
        //nukeDB();
    }
}