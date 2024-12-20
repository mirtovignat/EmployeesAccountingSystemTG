package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresJavaConnection {
    public void start() throws ClassNotFoundException, SQLException {
        String connect = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(connect, user, user);
        try (connection) {
            System.out.println("PostgreSQL has been successfully connected with Java!");
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
