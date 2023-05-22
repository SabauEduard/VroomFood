package config;

import utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private final Connection databaseConnection;

    public DatabaseConfiguration(){
        try {
            String url = Constants.DATABASE_URL;
            String username = Constants.DATABASE_USERNAME;
            String pass = Constants.DATABASE_PASSWORD;
            databaseConnection = DriverManager.getConnection(url, username, pass);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection(){
        return databaseConnection;
    }


}