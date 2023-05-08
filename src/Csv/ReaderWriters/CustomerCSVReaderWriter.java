package Csv.ReaderWriters;

import Models.Customer;

public class CustomerCSVReaderWriter implements ICSVReaderWriter<Customer> {
    private static final String FILE_NAME = "src\\Csv\\customers.csv";
    private static CustomerCSVReaderWriter instance = null;

    public static CustomerCSVReaderWriter getInstance() {
        if (instance == null) {
            instance = new CustomerCSVReaderWriter();
        }
        return instance;
    }
    private CustomerCSVReaderWriter() {
    }

    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(Customer customer) {
        return customer.getId() + SEPARATOR + customer.getName() + SEPARATOR + customer.getUsername() + SEPARATOR +
                customer.getPassword() + SEPARATOR + customer.getEmail() + SEPARATOR + customer.getPhoneNumber() +
                SEPARATOR + customer.getAddress();
    }

    @Override
    public Customer processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Customer(fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
    }
}
