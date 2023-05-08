package Csv.ReaderWriters;

import Models.Driver;
import Utils.VehicleType;

public class DriverCSVReaderWriter implements ICSVReaderWriter<Driver>{
    private static DriverCSVReaderWriter instance = null;
    private static final String FILE_NAME = "src\\Csv\\drivers.csv";
    public static DriverCSVReaderWriter getInstance(){
        if(instance == null){
            instance = new DriverCSVReaderWriter();
        }
        return instance;
    }
    private DriverCSVReaderWriter(){
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(Driver driver) {
        return driver.getId() + SEPARATOR + driver.getName() + SEPARATOR + driver.getUsername() + SEPARATOR +
                driver.getPassword() + SEPARATOR + driver.getEmail() + SEPARATOR + driver.getPhoneNumber() +
                SEPARATOR + driver.getAddress() + SEPARATOR + driver.getVehiclePlate() + SEPARATOR +
                driver.getVehicleType() + SEPARATOR + driver.getRating();
    }
    @Override
    public Driver processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Driver(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5], fields[6],
                VehicleType.valueOf(fields[7]));
    }

}
