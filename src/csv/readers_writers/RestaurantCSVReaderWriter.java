package csv.readers_writers;

import models.Restaurant;
import utils.Constants;

public class RestaurantCSVReaderWriter implements ICSVReaderWriter<Restaurant>{
    private static final String FILE_NAME = Constants.RESTAURANT_FILE_NAME;
    private static RestaurantCSVReaderWriter instance = null;
    public static RestaurantCSVReaderWriter getInstance() {
        if (instance == null) {
            instance = new RestaurantCSVReaderWriter();
        }
        return instance;
    }
    private RestaurantCSVReaderWriter() {
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(Restaurant restaurant) {
        return restaurant.getId() + SEPARATOR + restaurant.getName() + SEPARATOR + restaurant.getAddress() + SEPARATOR +
                restaurant.getPhoneNumber();
    }
    @Override
    public Restaurant processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Restaurant(fields[0], fields[1], fields[2]);
    }
}
