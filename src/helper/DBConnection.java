package helper;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**Class DBConnection
 * contains the details for the connection class*/
public class DBConnection {
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//localhost:3306/";
    private static final String dbName = "client_schedule";
    //private static final String location = "//localhost/";
    private static final String jdbcUrl = protocol + vendorName + ipAddress + dbName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String MYSQLJBCDriver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    public static Connection conn = null; // Connection Interface


/**Connect to the DB
 * @return conn*/
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver); // Locate Driver
            conn = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
    /**DatabaseConnection
     * @return connection*/
    public static Connection getConnection()  {
        if(conn == null)
            startConnection();
        return conn;
    }
/**Disconnect from the DB*/
    public static void closeConnection() {
        try {
            conn.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();

        }
    }

}

