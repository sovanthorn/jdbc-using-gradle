package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateLoginTable {
    public static void main(String[] args) {
        final String url = "jdbc:postgresql://localhost:5432/your_database_name";
        final String user = "your_username";
        final String password = "your_password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database");

                String createTableSQL = "CREATE TABLE login (" +
                        "id SERIAL PRIMARY KEY," +
                        "username VARCHAR(50) NOT NULL," +
                        "password VARCHAR(50) NOT NULL" +
                        ")";

                Statement statement = conn.createStatement();
                statement.executeUpdate(createTableSQL);

                System.out.println("Table 'login' created successfully");
            } else {
                System.out.println("Failed to make connection to the database");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
}
