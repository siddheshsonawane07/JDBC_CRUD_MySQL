import java.net.ServerSocket;
import java.sql.*;
import java.util.Scanner;

public class MyJDBC {

    static final String sql_connection = "jdbc:mysql://localhost:3306/java_crud";
    static Connection db_connection;
    static Scanner in = new Scanner(System.in);
    static String stg;

    public static void main(String[] args) {

        database_connection();
        boolean loop = true;
        System.out.println("Enter SQL query for creating a table");
        stg = in.nextLine();

        while(loop) {
            System.out.println();
            System.out.println("1. Displaying all tables");
            System.out.println("2. Create a new table");
            System.out.println("3. Display table");
            System.out.println("4. Insert a record");
            System.out.println("5. Update a record");
            System.out.println("6. Delete a record");
            System.out.println("7. Delete a table");
            System.out.println("8. Exit loop");


            System.out.print("Enter Choice: ");
            int choice = in.nextInt();

            switch (choice) {
                case 1: {
                    display_all_tables();
                    break;
                }
                case 2: {
                    create_table();
                    break;
                }
                case 3: {
                    display_table();
                    break;
                }
                case 4: {
                    insert_in_table();
                    break;
                }
                case 5: {
                    update_in_table();
                    break;
                }
                case 6: {
                    delete_table();
                    break;
                }
                case 7:{

                }
                default: {
                    loop = false;
                    break;
                }
            }
        }

    }


    private static void database_connection() {
        try {
            db_connection = DriverManager.getConnection(sql_connection, "root", "Apple@5044");
            System.out.println("Connection Successful");
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static void display_all_tables() {
        try {
            Statement statement = db_connection.createStatement();
            ResultSet rs = statement.executeQuery("show tables;");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static void display_table(){
        try{
            String sql = "SELECT * FROM users";

            Statement statement = db_connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            while (result.next()){
                String name = result.getString(2);
                String email = result.getString("email");

                System.out.println(name + "  " + email);
            }
        }catch(SQLException e){
            printSQLException(e);
        }
    }

    private static void create_table (){
        try {
            System.out.println(stg);
            Statement statement = db_connection.createStatement();
            statement.executeUpdate(stg);
            display_all_tables();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static void insert_in_table() {
        String sql = "INSERT INTO users (name, email, country, password) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement statement = db_connection.prepareStatement(sql);
            statement.setString(1, "Siddhesh");
            statement.setString(2, "siddheshsonawane17@gmail.com");
            statement.setString(3, "USA");
            statement.setString(4, "password");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private static void delete_table() {
    }

    private static void delete_in_table() {
    }

    private static void update_in_table() {
        String sql = "UPDATE users SET id = ?, name = ?, email = ?, country = ?, password = ? WHERE id = 1";

        try {
            PreparedStatement statement = db_connection.prepareStatement(sql);

            statement.setString(1, "1");
            statement.setString(2, "Siddhesh1");
            statement.setString(3, "temp@gmail.com");
            statement.setString(4, "UK");
            statement.setString(5, "rfddf");

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Existing user was updated successfully!");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
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
        return sqlState.equalsIgnoreCase("42Y55");
    }
}
