package dbAccess;

import controller.LoginForm;
import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class DBAppointment {
    /**
     * getAllAppointments
     * we are looking for all appointments in the database
     *
     * @throws SQLException
     * @returns allAppointments, a list of all appointments
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            //LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            //String createdBy = rs.getString("Created_by");
            //LocalDateTime lastUpdateDateTime = rs.getTimestamp("Last_Update").toLocalDateTime();
            //String lastUpdateBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            //String contactName = rs.getString("Contact_Name");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime,
                    endDateTime, LocalDateTime.now(), "", LocalDateTime.now(), "",
                    customerID, userID, contactID);

            allAppointments.add(appointment);
        }
        ps.close();
        return allAppointments;
    }

    /**
     * getDateAppointments
     * we are looking for all filtered appointments in the database
     *
     * @param startDate start date for the range of LocalDateTime
     * @param endDate   end date for the range of LocalDateTime
     * @return DateFilteredAppointments a list for appointments based on the user input start date and end date
     * @throws SQLException
     */
    public static ObservableList<Appointment> getFilteredPerDateAppointments(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {
        ObservableList<Appointment> DateFilteredAppointments = FXCollections.observableArrayList();

        PreparedStatement ps = DBConnection.getConnection().prepareStatement("SELECT * FROM appointments WHERE Start BETWEEN ? AND ?");

        ps.setTimestamp(1, Timestamp.valueOf(startDate));
        ps.setTimestamp(2, Timestamp.valueOf(endDate));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            // LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            //String createdBy = rs.getString("Created_by");
            // LocalDateTime lastUpdateDateTime = rs.getTimestamp("Last_Update").toLocalDateTime();
            //String lastUpdateBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            //String contactName = rs.getString("Contact_Name");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime,
                    endDateTime, LocalDateTime.now(), "", LocalDateTime.now(), "",
                    customerID, userID, contactID);

            DateFilteredAppointments.add(appointment);
        }
        ps.close();
        return DateFilteredAppointments;
    }

    /**
     * getAppointmentsInTheNext15Minutes
     * find appointments in the database for logged users starting within 15 minutes
     *
     * @return appointmentsIn15Minutes, a list of appointments for logged users within 15 minutes
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAppointmentsInTheNext15Minutes() throws SQLException {
        ObservableList<Appointment> appointmentsIn15Minutes = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Start BETWEEN now() AND date_add(now(), interval 15 minute) AND User_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, LoginForm.getLoggedOnUser().getUserID());

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
           // LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            //String createdBy = rs.getString("Created_by");
           // LocalDateTime lastUpdateDateTime = rs.getTimestamp("Last_Update").toLocalDateTime();
            //String lastUpdateBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            //String contactName = rs.getString("Contact_Name");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime,
                    endDateTime, LocalDateTime.now(), "", LocalDateTime.now(), "",
                    customerID, userID, contactID);

            appointmentsIn15Minutes.add(appointment);
        }
        return appointmentsIn15Minutes;

    }
    /**addAppointment
     * it takes input and creates a new appointment in the database
     * @param addContactID  contact ID
     * @param addCreatedBy  created by
     * @param addCustomerID  customer ID
     * @param addDescription  description
     * @param addEnd end date
     * @param addStart start date
     * @param addLastUpdateBy last update by user
     * @param addLocation location
     * @param addTitle title
     * @param addType type
     * @param addUserID user ID
     * @return  boolean if the addition was successful
     * @throws SQLException*/
    public static boolean addAppointment(String addTitle, String addDescription, String addLocation, String addType,
                                         LocalDateTime addStart, LocalDateTime addEnd, String addCreatedBy,
                                         String addLastUpdateBy, int addCustomerID, int addUserID,
                                         int addContactID) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_date, Created_By, last_Update," +
                        " Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,now(),?,now(),?,?,?,?)";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);

        ps.setString(1, addTitle);
        ps.setString(2, addDescription);
        ps.setString(3, addLocation);
        ps.setString(4, addType);
        ps.setTimestamp(5, Timestamp.valueOf(addStart));
        ps.setTimestamp(6, Timestamp.valueOf(addEnd));
        ps.setString(7, addCreatedBy);
        ps.setString(8, addLastUpdateBy);
        ps.setInt(9, addCustomerID);
        ps.setInt(10, addUserID);
        ps.setInt(11, addContactID);
        try {
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**updateAppointment
     * it takes the chosen appointment and updates it in the database
     * @param addUserID  user id
     * @param addType type
     * @param addTitle title
     * @param addLocation location
     * @param addLastUpdateBy last update by
     * @param updateStart start date
     * @param updateEnd end date
     * @param addDescription description
     * @param addCustomerID customer id
     * @param addContactID contact id
     * @param addAppointmentID appointment id
     * @return a boolean returning true if the update was successful or false if it was not
     * @throws SQLException*/
    public static boolean updateAppointment(int addAppointmentID, String addTitle, String addDescription,
                                            String addLocation, String addType, LocalDateTime updateStart,
                                            LocalDateTime updateEnd, String addLastUpdateBy, int addCustomerID,
                                            int addUserID, int addContactID) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "last_update = now(), Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE appointment_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);

        ps.setString(1, addTitle);
        ps.setString(2, addDescription);
        ps.setString(3, addLocation);
        ps.setString(4, addType);
        ps.setTimestamp(5, Timestamp.valueOf(updateStart));
        ps.setTimestamp(6, Timestamp.valueOf(String.valueOf(updateEnd)));
        //ps.setString(7, ZonedDateTime.now(ZoneOffset.UTC).format(dtf));
        ps.setString(7, addLastUpdateBy);
        ps.setInt(8, addCustomerID);
        ps.setInt(9, addUserID);
        ps.setInt(10, addContactID);
        ps.setInt(11, addAppointmentID);
        try{
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            ps.close();
            return false;
        }
    }
    /**deleteAppointment
     * the user inserts the appointment id to delete it
     * @return a boolean that returns true if the deletion was successful or false if not
     * @throws SQLException*/
    public static boolean deleteAppointment(int addAppointmentID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, addAppointmentID);
        try{
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**getCustomerFilteredAppointments
     * queries the database to get all appointments for a customer on a specific date
     * @param appointmentDate appointment date
     * @param addCustomerID customer id
     * @return a list of appointments for a customer on a specific date
     * @throws SQLException
    public static ObservableList<Appointment> getCustomerFilteredAppointments(LocalDate appointmentDate,
                                                                              int addCustomerID) throws SQLException {
        ObservableList<Appointment> customerFilteredAppointments = FXCollections.observableArrayList();
        String sql = "SELECT * \n" +
                "FROM appointments a \n" +
                "LEFT OUTER JOIN contacts c \n" +
                "on (a.Contact_ID = c.Contact_ID)\n" +
                "WHERE datediff(a.Start, '?') = 0 AND Customer_ID = '?';";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, appointmentDate.toString());
        ps.setInt(2, addCustomerID);

        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            LocalDateTime startDateTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endDateTime = rs.getTimestamp("End").toLocalDateTime();
            LocalDateTime createdDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_by");
            LocalDateTime lastUpdateDateTime = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");

            Appointment appointment = new Appointment(appointmentID, title, description, location, type, startDateTime,
                    endDateTime, LocalDateTime.now(), "", LocalDateTime.now(), "",
                    customerID, userID, contactID);
            customerFilteredAppointments.add(appointment);
        }
        ps.close();
        return customerFilteredAppointments;
    }*/
    //public static boolean businessHours(String startTime, String endTime, String appointmentDate){
        //LocalDateTime localStart =
    //}
    /**deleteCustomerAppointments
     * delete all appointments for a specific customer
     * @param customerID customer id
     * @return boolean for a successful deletion or not
     * @throws SQLException*/
    public static boolean deleteCustomerAppointments(int customerID) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, customerID);
        try{
            ps.executeQuery();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

