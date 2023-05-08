package Csv.ReaderWriters;

import Models.RestaurantOwner;

public class RestaurantOwnerCSVReaderWriter implements ICSVReaderWriter<RestaurantOwner>{
    private static RestaurantOwnerCSVReaderWriter instance = null;
    private static final String FILE_NAME = "src\\Csv\\restaurantOwners.csv";
    public static RestaurantOwnerCSVReaderWriter getInstance(){
        if(instance == null){
            instance = new RestaurantOwnerCSVReaderWriter();
        }
        return instance;
    }
    private RestaurantOwnerCSVReaderWriter(){
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(RestaurantOwner restaurantOwner) {
        return restaurantOwner.getId() + SEPARATOR + restaurantOwner.getName() + SEPARATOR + restaurantOwner.getUsername()
                + SEPARATOR + restaurantOwner.getPassword() + SEPARATOR + restaurantOwner.getEmail() + SEPARATOR +
                restaurantOwner.getPhoneNumber() + SEPARATOR + restaurantOwner.getAddress();
    }
    @Override
    public RestaurantOwner processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new RestaurantOwner(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
    }
}
