package hu.nye.progTech;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NeptunDatabaseConnectionTest {

    private static final String JDBC_URL = "jdbc:h2:./neptun";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";
    private static Connection connection;

    @BeforeAll
    public static void setup() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }

    @Test
    public void testRegistration() throws SQLException {
        NeptunDatabaseConnection.registerStudent(connection, "John Doe", "john.doe", "pass123");
        // TODO: Add assertions to verify the registration was successful
    }

    @Test
    public void testLogin() throws SQLException {
        NeptunDatabaseConnection.loginStudent(connection, "john.doe", "pass123");
        // TODO: Add assertions to verify the login was successful
    }

    @Test
    public void testDisplayStudentsTable() throws SQLException {
        NeptunDatabaseConnection.displayStudentsTable(connection);
        // TODO: Add assertions to verify the table display
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
