package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private final Connection databaseConnection;

    public DatabaseConfiguration(){
        try {
            String url = "jdbc:mysql://127.0.0.2:3306/VroomFood";
            String username = "root";
            String pass = "root";
            databaseConnection = DriverManager.getConnection(url, username, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return databaseConnection;
    }


}