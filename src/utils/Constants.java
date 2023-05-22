package utils;

public class Constants {
    // Constants for the database
    public static final String DATABASE_URL = "jdbc:mysql://127.0.0.1:3306/models";
    public static final String DATABASE_USERNAME = "root";
    public static final String DATABASE_PASSWORD = "root";

    // Constants for the CSV files
    public static final String AUDIT_FILE_NAME = "src\\Csv\\audit.csv";
    public static final String CUSTOMER_FILE_NAME = "src\\Csv\\customers.csv";
    public static final String DRIVER_FILE_NAME = "src\\Csv\\drivers.csv";
    public static final String ORDER_FILE_NAME = "src\\Csv\\orders.csv";
    public static final String RESTAURANT_OWNER_FILE_NAME = "src\\Csv\\restaurantOwners.csv";
    public static final String RESTAURANT_FILE_NAME = "src\\Csv\\restaurants.csv";
    public static final String DRIVER_NOT_ASSIGNED_MESSAGE = "Driver not yet assigned\n";

    // Constants for the Audit Logs
    public static final String LOGIN_ACTION = "login";
    public static final String LOGOUT_ACTION = "logout";
    public static final String REGISTER_CUSTOMER_ACTION = "registerCustomer";
    public static final String REGISTER_CUSTOMERS_FROM_CSV_ACTION = "registerCustomersFromCsv";
    public static final String REGISTER_DRIVER_ACTION = "registerDriver";
    public static final String REGISTER_DRIVERS_FROM_CSV_ACTION = "registerDriversFromCsv";
    public static final String REGISTER_RESTAURANT_OWNER_ACTION = "registerRestaurantOwner";
    public static final String REGISTER_RESTAURANT_OWNERS_FROM_CSV_ACTION = "registerRestaurantOwnersFromCsv";
    public static final String START_ORDER_ACTION = "startOrder";
    public static final String CANCEL_ORDER_ACTION = "cancelOrder";
    public static final String SEND_ORDER_ACTION = "sendOrder";
    public static final String ADD_RECIPE_TO_ORDER_ACTION = "addRecipeToOrder";
    public static final String REMOVE_RECIPE_FROM_ORDER_ACTION = "removeRecipeFromOrder";
    public static final String ADD_RESTAURANT_ACTION = "addRestaurant";
    public static final String ADD_RESTAURANTS_FROM_CSV_ACTION = "addRestaurantsFromCsv";
    public static final String REMOVE_RESTAURANT_ACTION = "removeRestaurant";
    public static final String SORT_USERS_BY_NAME_ACTION = "sortUsersByName";
    public static final String PRINT_USERS_ACTION = "printUsers";
    public static final String PRINT_ORDER_HISTORY_ACTION = "printOrderHistory";
    public static final String ADD_RECIPE_TO_RESTAURANT_ACTION = "addRecipeToRestaurant";
    public static final String REMOVE_RECIPE_FROM_RESTAURANT_ACTION = "removeRecipeFromRestaurant";
    public static final String GET_ORDER_TO_DELIVER_ACTION = "getOrderToDeliver";
    public static final String DELIVER_ORDER_ACTION = "deliverOrder";
    public static final String MARK_AS_DELIVERED_ACTION = "markAsDelivered";
    public static final String REFUSE_DELIVERY_ACTION = "refuseDelivery";

    // Constants for the Order queries
    public static final String SELECT_ALL_ORDERS = "SELECT * FROM order_order";
    public static final String INSERT_ORDER = "INSERT INTO order_order (id, totalPrice, deliveryAddress, deliveryTime, status, customerId, driverId, restaurantId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_ORDER = "DELETE FROM order_order WHERE id = ?";
    public static final String SELECT_ORDER_RECIPE_BY_ID = "SELECT * FROM order_recipe WHERE orderId = ?";
    public static final String INSERT_ORDER_RECIPE = "INSERT INTO order_recipe (orderId, recipeId) VALUES (?, ?)";
    public static final String DELETE_ORDER_RECIPE = "DELETE FROM order_recipe WHERE orderId = ? AND recipeId = ?";
    public static final String UPDATE_ORDER = "UPDATE order_order SET id = ?, totalPrice = ?, deliveryAddress = ?, deliveryTime = ?, status = ?, customerId = ?, driverId = ?, restaurantId = ? WHERE id = ?";

    // Constants for the Recipe queries
    public static final String SELECT_ALL_RECIPES = "SELECT * FROM recipe";
    public static final String INSERT_RECIPE = "INSERT INTO recipe (id, name, description, price, preparationTime, restaurantId) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String DELETE_RECIPE = "DELETE FROM recipe WHERE id = ?";
    public static final String UPDATE_RECIPE = "UPDATE recipe SET id = ?, name = ?, description = ?, price = ?, preparationTime = ? WHERE id = ?";

    // Constants for the Restaurant queries
    public static final String SELECT_ALL_RESTAURANTS = "SELECT * FROM restaurant";
    public static final String INSERT_RESTAURANT = "INSERT INTO restaurant (id, name, address, phoneNumber, restaurantOwnerId) VALUES (?, ?, ?, ?, ?)";
    public static final String DELETE_RESTAURANT = "DELETE FROM restaurant WHERE id = ?";
    public static final String UPDATE_RESTAURANT =  "UPDATE restaurant SET id = ?, name = ?, address = ?, phoneNumber = ? WHERE id = ?";

    // Constants for the User queries
    public static final String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
    public static final String SELECT_ALL_DRIVERS = "SELECT * FROM driver";
    public static final String SELECT_ALL_RESTAURANT_OWNERS = "SELECT * FROM restaurant_owner";
    public static final String INSERT_CUSTOMER = "INSERT INTO customer (id, name, username, password, email, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_DRIVER = "INSERT INTO driver (id, name, username, password, email, phoneNumber, address, vehiclePlate, vehicleType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_RESTAURANT_OWNER = "INSERT INTO restaurant_owner (id, name, username, password, email, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = ?";
    public static final String DELETE_DRIVER = "DELETE FROM driver WHERE id = ?";
    public static final String DELETE_RESTAURANT_OWNER = "DELETE FROM restaurant_owner WHERE id = ?";
    public static final String UPDATE_CUSTOMER = "UPDATE customer SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ? WHERE id = ?";
    public static final String UPDATE_DRIVER = "UPDATE driver SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ?, vehiclePlate = ?, vehicleType = ? WHERE id = ?";
    public static final String UPDATE_RESTAURANT_OWNER = "UPDATE restaurant_owner SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ? WHERE id = ?";
}
