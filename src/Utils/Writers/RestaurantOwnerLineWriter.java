package Utils.Writers;

import Models.RestaurantOwner;

public class RestaurantOwnerLineWriter implements ILineWriter<RestaurantOwner>{
    @Override
    public String writeLine(RestaurantOwner restaurantOwner) {
        return String.format("%s,%s,%s,%s,%s,%s,%s", restaurantOwner.getId(), restaurantOwner.getName(), restaurantOwner.getUsername(),
                restaurantOwner.getPassword(), restaurantOwner.getEmail(), restaurantOwner.getPhoneNumber(), restaurantOwner.getAddress());
    }
}
