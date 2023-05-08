package Csv.ReaderWriters;

import Models.Order;

public class OrderCSVReaderWriter implements ICSVReaderWriter<Order>{
    private static String FILE_NAME = "src\\Csv\\orders.csv";
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
