package College.Event.ManagementSystem;

import java.sql.*;

public class DBConnection {
    private Connection connection;
    Statement statement;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "Silu@512");
            statement = connection.createStatement();
            System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
