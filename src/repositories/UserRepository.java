package repositories;

import models.Customer;
import models.Driver;
import models.RestaurantOwner;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Comparator;
import java.util.List;

public class UserRepository extends GenericRepository<User>{
    private static final UserRepository instance = new UserRepository();
    private UserRepository() {
        super();
        try {
            Statement statement = databaseConfiguration.getConnection().createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM customer");
            while (result.next()) {
                Customer current = new Customer(result);
                objectList.add(current);
            }
            statement.close();
            statement = databaseConfiguration.getConnection().createStatement();
            result = statement.executeQuery("SELECT * FROM driver");
            while (result.next()) {
                Driver current = new Driver(result);
                objectList.add(current);
            }
            statement.close();
            statement = databaseConfiguration.getConnection().createStatement();
            result = statement.executeQuery("SELECT * FROM restaurant_owner");
            while (result.next()) {
                RestaurantOwner current = new RestaurantOwner(result);
                objectList.add(current);
            }
            statement.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public static UserRepository getInstance(){
        return instance;
    }
    public void sortUsersByName(){
        objectList.sort(Comparator.comparing(User::getName));
    }
    public void sortUsersByUsername(){
        objectList.sort(Comparator.comparing(User::getUsername));
    }

    @Override
    public void add(User user){
        if (user instanceof Customer){
            addCustomerToDB((Customer) user);
        }
        else if(user instanceof Driver){
            addDriverToDB((Driver) user);
        }
        else {
            addRestaurantOwnerToDB((RestaurantOwner) user);
        }
        objectList.add(user);
    }
    protected void addCustomerToDB(Customer customer){
        try{
            String query = "INSERT INTO customer (id, name, username, password, email, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, customer.getId());
            preparedStmt.setString(2,customer.getName());
            preparedStmt.setString(3, customer.getUsername());
            preparedStmt.setString(4, customer.getPassword());
            preparedStmt.setString(5, customer.getEmail());
            preparedStmt.setString(6, customer.getPhoneNumber());
            preparedStmt.setString(7, customer.getAddress());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void addRestaurantOwnerToDB(RestaurantOwner restaurantOwner){
        try{
            String query = "INSERT INTO restaurant_owner (id, name, username, password, email, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, restaurantOwner.getId());
            preparedStmt.setString(2,restaurantOwner.getName());
            preparedStmt.setString(3, restaurantOwner.getUsername());
            preparedStmt.setString(4, restaurantOwner.getPassword());
            preparedStmt.setString(5, restaurantOwner.getEmail());
            preparedStmt.setString(6, restaurantOwner.getPhoneNumber());
            preparedStmt.setString(7, restaurantOwner.getAddress());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void addDriverToDB(Driver driver) {
        try{
            String query = "INSERT INTO driver (id, name, username, password, email, phoneNumber, address, " +
                    "vehiclePlate, vehicleType) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, driver.getId());
            preparedStmt.setString(2, driver.getName());
            preparedStmt.setString(3, driver.getUsername());
            preparedStmt.setString(4, driver.getPassword());
            preparedStmt.setString(5, driver.getEmail());
            preparedStmt.setString(6, driver.getPhoneNumber());
            preparedStmt.setString(7, driver.getAddress());
            preparedStmt.setString(8, driver.getVehiclePlate());
            preparedStmt.setString(9, driver.getVehicleType().toString());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    @Override
    public void remove(User user){
        if (user instanceof Customer){
            removeCustomerFromDB((Customer) user);
        }
        else if(user instanceof Driver){
            removeDriverFromDB((Driver) user);
        }
        else {
            removeRestaurantOwnerFromDB((RestaurantOwner) user);
        }
        objectList.remove(user);
    }
    protected void removeCustomerFromDB(Customer customer){
        try{
            String query = "DELETE FROM customer WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, customer.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void removeRestaurantOwnerFromDB(RestaurantOwner restaurantOwner){
        try{
            String query = "DELETE FROM restaurant_owner WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, restaurantOwner.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void removeDriverFromDB(Driver driver){
        try{
            String query = "DELETE FROM driver WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, driver.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    @Override
    public void update(User newUser, User oldUser){
        if (oldUser instanceof Customer){
            updateCustomerInDB((Customer) newUser, (Customer) oldUser);
        }
        else if(oldUser instanceof Driver){
            updateDriverInDB((Driver) newUser, (Driver) oldUser);
        }
        else {
            updateRestaurantOwnerInDB((RestaurantOwner) newUser, (RestaurantOwner) oldUser);
        }
    }
    protected void updateCustomerInDB(Customer newCustomer, Customer oldCustomer){
        try{
            String query = "UPDATE customer SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newCustomer.getId());
            preparedStmt.setString(2, newCustomer.getName());
            preparedStmt.setString(3, newCustomer.getUsername());
            preparedStmt.setString(4, newCustomer.getPassword());
            preparedStmt.setString(5, newCustomer.getEmail());
            preparedStmt.setString(6, newCustomer.getPhoneNumber());
            preparedStmt.setString(7, newCustomer.getAddress());
            preparedStmt.setInt(8, oldCustomer.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void updateRestaurantOwnerInDB(RestaurantOwner newRestaurantOwner, RestaurantOwner oldRestaurantOwner){
        try{
            String query = "UPDATE restaurant_owner SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ? WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newRestaurantOwner.getId());
            preparedStmt.setString(2, newRestaurantOwner.getName());
            preparedStmt.setString(3, newRestaurantOwner.getUsername());
            preparedStmt.setString(4, newRestaurantOwner.getPassword());
            preparedStmt.setString(5, newRestaurantOwner.getEmail());
            preparedStmt.setString(6, newRestaurantOwner.getPhoneNumber());
            preparedStmt.setString(7, newRestaurantOwner.getAddress());
            preparedStmt.setInt(8, oldRestaurantOwner.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    protected void updateDriverInDB(Driver newDriver, Driver oldDriver){
        try{
            String query = "UPDATE driver SET id = ?, name = ?, username = ?, password = ?, email = ?, phoneNumber = ?, address = ?, vehiclePlate = ?, vehicleType = ? WHERE id = ?";
            PreparedStatement preparedStmt = databaseConfiguration.getConnection().prepareStatement(query);
            preparedStmt.setInt(1, newDriver.getId());
            preparedStmt.setString(2, newDriver.getName());
            preparedStmt.setString(3, newDriver.getUsername());
            preparedStmt.setString(4, newDriver.getPassword());
            preparedStmt.setString(5, newDriver.getEmail());
            preparedStmt.setString(6, newDriver.getPhoneNumber());
            preparedStmt.setString(7, newDriver.getAddress());
            preparedStmt.setString(8, newDriver.getVehiclePlate());
            preparedStmt.setString(9, newDriver.getVehicleType().toString());
            preparedStmt.setInt(10, oldDriver.getId());
            preparedStmt.execute();
            preparedStmt.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
    public User getUserByUsername(String username){
        for(User user : objectList){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }
    public User getUserByEmail(String email){
        for(User user : objectList){
            if(user.getEmail().equals(email))
                return user;
        }
        return null;
    }
    public void printUsers(){
        for(User user : objectList){
            System.out.println(user);
        }
    }
}
