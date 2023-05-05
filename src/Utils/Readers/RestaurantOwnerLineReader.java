package Utils.Readers;

import Models.RestaurantOwner;

public class RestaurantOwnerLineReader implements ILineReader<RestaurantOwner>{
    @Override
    public RestaurantOwner readLine(String line) {
        String[] values = line.split(",");
        return new RestaurantOwner(values[0], values[1], values[2], values[3], values[4], values[5]);
    }
}
