package Utils.Readers;

import Models.Customer;

public class CustomerLineReader implements ILineReader<Customer> {
    public Customer readLine(String line){
        String[] values = line.split(",");
        return new Customer(values[0], values[1], values[2], values[3], values[4], values[5]);
    }
}
