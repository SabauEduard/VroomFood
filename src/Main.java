import Models.Order;
import Utils.VehicleType;
import Services.AppService;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Registering some users
        AppService.registerCustomer("John Doe", "johndoe", "123456", "johndoe1@gmail.com", "123456789", "123 Main St");
        AppService.registerCustomer("Mike Doe", "mikedoe", "123456", "mikedoe555@gmail.com", "123456789", "123 Side St");
        AppService.registerRestaurantOwner("Jane Doe", "janedoe", "123456", "janedoe32@yahoo.com", "987654321", "456 Main St");
        AppService.registerDriver("John Smith", "johnsmith", "123456", "johnsmith10@unibuc.ro", "123456789", "123 Main St", "B 123 ABC", VehicleType.BIKE);

        // Logging in as a restaurant owner
        AppService.login("janedoe", "123456");

        // Creating two recipes
        List<String> ingredientsSimplePizza = new ArrayList<>();
        ingredientsSimplePizza.add("cheese");
        ingredientsSimplePizza.add("tomato");
        ingredientsSimplePizza.add("pepperoni");
        AppService.addRecipe("Simple pizza", "Basic pizza" ,20, 30, ingredientsSimplePizza);

        List<String> ingredientsRatatouille = new ArrayList<>();
        ingredientsRatatouille.add("eggplant");
        ingredientsRatatouille.add("tomato");
        ingredientsRatatouille.add("onion");
        ingredientsRatatouille.add("pepper");
        AppService.addRecipe("Ratatouille", "French dish" ,30, 40, ingredientsRatatouille);

        // Creating a restaurant and adding the recipes to it
        AppService.addRestaurant("Blue Margarita", "123 Main St", "123456789");
        AppService.addRecipeToRestaurant("Simple pizza", "Blue Margarita");
        AppService.addRecipeToRestaurant("Ratatouille", "Blue Margarita");

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

        // Logging out
        AppService.logout();

        // Printing the order
        System.out.println(order);
    }
}