package dbAccess;

import controller.LoginForm;
import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Class DBCustomer*/
public abstract class DBCustomer {
/**It adds customer to the database
 * @param postalCode
 * @param phone
 * @param createdBy
 * @param customerName
 * @param address
 * @param divisionID
 * @return updated row
 * @throws SQLException*/
    public static int addCustomer(String customerName, String address, String postalCode, String phone,
                                  String createdBy, int divisionID) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                "Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, now(), ?, now(), ?, ?)";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        //ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
        ps.setString(5, LoginForm.getLoggedOnUser().getUserName());
        //ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
        ps.setString(6, LoginForm.getLoggedOnUser().getUserName());
        ps.setInt(7, divisionID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
    /**it takes new input to update a customer
     * @param customerID customer id
     * @param customerName customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone
     * @param division  customer division
     * @return updated row of customers
     * @throws SQLException*/
    public static int updateCustomer(String customerName, String address, String postalCode, String phone,
                                     int division, int customerID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update= now()," +
                " Last_Updated_By=?, Division_ID=? WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phone);
        //ps.setString(5, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
        ps.setString(5, LoginForm.getLoggedOnUser().getUserName());
        //ps.setInt(6, DBDivision.getDivisionID(division));
        ps.setInt(6, division);
        ps.setInt(7, customerID);
        System.out.println(ps.toString());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**it will delete a customer from the database
     * @param customerID customer id to delete
     * @return the updated row*/
    public static int deleteCustomer(int customerID) throws SQLException {
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**it will get a customer id from the customer's table depending on customer's name
     * @param customerID
     * @return customerID
     * @throws SQLException*/
//    public static String getCustomerID(int customerID) throws SQLException {
//        String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
//        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
//        ps.setInt(1, customerID);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            String customerName = rs.getString("Customer_Name");
//            //String contactEmail = rs.getString("Email");
//            Customer contact = new Contact(contactID, contactName, contactEmail);
//            return contact;
//        }
//        return null;
//    }
    /**it uses the following query to het all customers
     * @return a list of all customers
     * @throws SQLException*/
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        String sql = "SELECT Cm.Customer_ID, Cm.Customer_Name, Cm.Address, Cm.Postal_Code, Cm.Phone,\n" +
                "    fld.Division_ID, fld.Division, c.Country_ID, c.Country, c.Country_ID\n" +
                "FROM customers Cm\n" +
                "JOIN first_level_divisions fld on ( Cm.Division_ID = fld.Division_ID)\n" +
                "JOIN countries c on (fld.Country_ID = c.Country_ID)";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            String customerDivision = rs.getString("Division");
            int customerDivisionID = rs.getInt("Division_ID");
            String customerCountry = rs.getString("Country");
            int customerCountryID = rs.getInt("Country_ID");
            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone,
                    customerDivision, customerDivisionID, customerCountry, customerCountryID);
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    /**it will get a customer passing a customer id from the customer's table
     * @param customerID
     * @return customer
     * @throws SQLException*/
    public static Customer getCustomer(int customerID) throws SQLException{
        //String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
        String sql = "SELECT Cm.Customer_ID, Cm.Customer_Name, Cm.Address, Cm.Postal_Code, Cm.Phone, fld.Division_ID, fld.Division, c.Country, c.Country_ID " +
                "FROM client_schedule.customers Cm JOIN client_schedule.first_level_divisions fld on ( Cm.Division_ID = fld.Division_ID) " +
                "JOIN client_schedule.countries c on (fld.Country_ID = c.Country_ID) WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            String customerDivision = rs.getString("Division");
            int customerDivisionID = rs.getInt("Division_ID");
            String customerCountry = rs.getString("Country");
            int customerCountryID = rs.getInt("Country_ID");

            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone,
                    customerDivision, customerDivisionID, customerCountry, customerCountryID);
            return customer;
        }
        return null;
    }
}

