package Utils.Writers;

import Models.Driver;

public class DriverLineWriter implements ILineWriter<Driver>{
    @Override
    public String writeLine(Driver driver) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s", driver.getId(), driver.getName(), driver.getUsername(), driver.getPassword(), driver.getEmail()
        , driver.getPhoneNumber(), driver.getAddress(), driver.getVehiclePlate(), driver.getVehicleType(), driver.getRating());
    }
}
