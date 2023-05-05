package Utils.Readers;

import Models.Driver;
import Utils.VehicleType;

public class DriverLineReader implements ILineReader<Driver>{
    @Override
    public Driver readLine(String line) {
        String[] values = line.split(",");
        return new Driver(values[0], values[1], values[2], values[3], values[4], values[5], values[6], VehicleType.valueOf(values[7]));
    }
}
