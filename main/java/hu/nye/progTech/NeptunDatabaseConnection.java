package hu.nye.progTech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class NeptunDatabaseConnection {

    private static final String JDBC_URL = "jdbc:h2:./neptun";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);

            if (connection != null) {
                System.out.println("Sikeresen csatlakozva az adatbázishoz!");

                Scanner scanner = new Scanner(System.in);
                int choice;

                do {
                    System.out.println("Válassz műveletet:");
                    System.out.println("1 - Regisztráció");
                    System.out.println("2 - Bejelentkezés");
                    System.out.println("3 - STUDENTS tábla kiírása");
                    System.out.println("0 - Kilépés");

                    choice = scanner.nextInt();
                    scanner.nextLine(); // Tisztítjuk a beolvasó bufferét

                    switch (choice) {
                        case 1:
                            performRegistration(connection, scanner);
                            break;
                        case 2:
                            performLogin(connection, scanner);
                            break;
                        case 3:
                            displayStudentsTable(connection);
                            break;
                        case 0:
                            System.out.println("Kilépés...");
                            break;
                        default:
                            System.out.println("Érvénytelen választás!");
                    }
                } while (choice != 0);

                connection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void performRegistration(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Add meg a neved:");
        String name = scanner.nextLine();

        System.out.println("Add meg a felhasználóneved:");
        String username = scanner.nextLine();

        System.out.println("Add meg a jelszavad:");
        String password = scanner.nextLine();

        registerStudent(connection, name, username, password);
    }

    private static void performLogin(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("Add meg a felhasználóneved:");
        String username = scanner.nextLine();

        System.out.println("Add meg a jelszavad:");
        String password = scanner.nextLine();

        loginStudent(connection, username, password);
    }

    static void displayStudentsTable(Connection connection) throws SQLException {
        String query = "SELECT * FROM STUDENTS";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id")
                        + ", Név: " + resultSet.getString("name")
                        + ", Felhasználónév: " + resultSet.getString("username")
                        + ", Jelszó: " + resultSet.getString("password"));
            }
        }
    }

    static void registerStudent(Connection connection, String name, String username, String password) throws SQLException {
        String query = "INSERT INTO STUDENTS (name, username, password) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Sikeres regisztráció!");
            }
        }
    }

    static void loginStudent(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM STUDENTS WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Sikeres bejelentkezés!");
            } else {
                System.out.println("Hibás felhasználónév vagy jelszó!");
            }
        }
    }
}
