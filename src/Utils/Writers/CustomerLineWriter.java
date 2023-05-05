package Utils.Writers;

import Models.Customer;

public class CustomerLineWriter implements ILineWriter<Customer> {
    public String writeLine(Customer customer){
         return String.format("%s,%s,%s,%s,%s,%s", customer.getId(), customer.getName(), customer.getUsername(), customer.getPassword(), customer.getPhoneNumber(), customer.getAddress());
    }
}
