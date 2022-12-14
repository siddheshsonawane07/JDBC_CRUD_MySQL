import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class MyJDBC {

    private static final String sql_connection = "jdbc:mysql://localhost:3306/java_crud";

    private static Connection database_connection;

    public static void main(String[] args) throws SQLException {

        database_connection();

        System.out.println("1. Displaying all tables");
        System.out.println("2. Create a new table");
        System.out.println("3. Insert a record");
        System.out.println("4. Update a record");
        System.out.println("5. Delete a record");
        System.out.println("6. Delete a table");

        Scanner in = new Scanner(System.in);

        System.out.print("Enter Choice: ");
        int choice = in.nextInt();

        switch (choice) {
            case 1: {
                display_all_tables();
            }
            case 2: {
                create_table();
            }
            case 3: {
                insert_in_table();
            }
            case 4: {
                update_in_table();
            }
            case 5: {
                delete_in_table();
            }
            case 6: {
                delete_table();
            }
        }

    }

    private static void database_connection() {
        try {
            Connection connection = DriverManager.getConnection(sql_connection, "root", "Apple@5044");
            database_connection = connection;
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            printSQLException(e);
        }
    }


    private static void delete_table() {
    }

    private static void delete_in_table() {
    }

    private static void update_in_table() {
    }

    private static void insert_in_table() {
    }

    private static void create_table() {
    }

    private static void display_all_tables() {
    }

    //exception handling
    public static void printSQLException(SQLException ex) {

        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                if (!ignoreSQLException(
                        ((SQLException) e).
                                getSQLState())) {

                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " +
                            ((SQLException) e).getSQLState());

                    System.err.println("Error Code: " +
                            ((SQLException) e).getErrorCode());

                    System.err.println("Message: " + e.getMessage());

                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
    }

    public static boolean ignoreSQLException(String sqlState) {

        if (sqlState == null) {
            System.out.println("The SQL state is not defined!");
            return false;
        }

        // X0Y32: Jar file already exists in schema
        if (sqlState.equalsIgnoreCase("X0Y32"))
            return true;

        // 42Y55: Table already exists in schema
        if (sqlState.equalsIgnoreCase("42Y55"))
            return true;

        return false;
    }
}
