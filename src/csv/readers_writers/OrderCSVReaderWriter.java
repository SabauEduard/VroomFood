package csv.readers_writers;

import models.Order;
import utils.Constants;

public class OrderCSVReaderWriter implements ICSVReaderWriter<Order>{
    private static String FILE_NAME = Constants.ORDER_FILE_NAME;
    private static OrderCSVReaderWriter instance = null;
    public static OrderCSVReaderWriter getInstance() {
        if (instance == null) {
            instance = new OrderCSVReaderWriter();
        }
        return instance;
    }
    private OrderCSVReaderWriter() {
    }
    @Override
    public String getReadingFileName() {
        return FILE_NAME;
    }
    @Override
    public String convertObjectToCSVLine(Order order) {
        return order.getId() + SEPARATOR + order.getTotalPrice() + SEPARATOR + order.getDeliveryAddress() + SEPARATOR +
            order.getDeliveryTime() + SEPARATOR + order.getStatus();
    }
    @Override
    public Order processCSVLine(String line) {
        String[] fields = line.split(SEPARATOR);
        return new Order(fields[0]);
    }
}
