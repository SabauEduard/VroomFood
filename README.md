# VroomFood

&nbsp;&nbsp;&nbsp;&nbsp;VroomFood is a food delivery app that allows customers to order food from their favorite restaurants. Customers can also 
view the menu and order history of their favorite restaurants. Drivers can accept orders, view the orders that are assigned 
to them and mark them as delivered. Restaurant owners can add their Restaurants and update the menu.

## Possible actions for each user type are listed below:
1. All users
   * Register
   * Login
   * Logout
   * Sort the users by their names
   
2. Customers
   * Start an order
   * Add recipes to the order
   * Remove recipes from the order
   * Send the order
   * Cancel a order
   * View their order history **(to be implemented)**
3. Drivers
   * Accept an order **(to be implemented)**
   * Mark an order as delivered **(to be implemented)**
   * View their order history **(to be implemented)**
4. Restaurant owners
   * Add a restaurant
   * Add a recipe to the menu of their restaurant
   * Remove a recipe from the menu of their restaurant **(to be implemented)**
   * Remove a restaurant **(to be implemented)**


## A list of the entities in the system is listed below:
### Main entities
1. User (abstract)
   * Id
   * Name
   * Username
   * Password
   * Email
   * Phone number
   * Address
2. Customer (extends User)
   * Order history
3. Driver (extends User)
    * Order history
    * Rating
    * Vehicle Type
    * Vehicle License Plate
4. Restaurant Owner (extends User)
    * A list of the restaurant he owns
5. Restaurant
    * Id
    * Name
    * Address
    * Phone number
    * List of recipes (Menu)
    * Owner of the restaurant
6. Recipe
    * Id
    * Name
    * Price
    * Description
    * Preparation time
    * List of ingredients
7. Order
   * Id
   * Customer
   * Driver
   * Restaurant
   * List of recipes
   * Status
   * Total price
   * Delivery address
   * Delivery time

## Repository Structure
1. GenericRepository
   * List of objects
2. UserRepository (extends GenericRepository)
   * Functionalities for the User entity
3. RestaurantRepository (extends GenericRepository)
   * Functionalities for the Restaurant entity
4. RecipeRepository (extends GenericRepository)
    * Functionalities for the Recipe entity
5. OrderRepository (extends GenericRepository)
    * Functionalities for the Order entity

## Services Structure
1. AppService
   * All the functionalities of the app described above
## Utils Structure
1. OrderStatusType (enum)
   * PENDING
   * ACCEPTED
   * DELIVERED
   * CANCELLED
2. VehicleType (enum)
    * CAR
    * MOTORCYCLE
    * BICYCLE

## Exceptions
Multiple specific exceptions to deal with the different cases that can occur in the app.
   



