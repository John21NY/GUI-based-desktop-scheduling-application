package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;

/**DBContact*/
public class DBContact {
    /**sumMinutesScheduled
     * Calculates the sum of minutes for all appointments of a contact
     * @param contactID the contact ID to find the sum of minutes
     * @return minutes in total
     * @throws java.sql.SQLException*/
//    public static Integer sumMinutesScheduled(String contactID) throws SQLException {
//        int minutesInTotal = 0;
//        String sql = "SELECT * FROM appointments WHERE Contact_ID = '?'";
//        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
//        ps.setString(1, contactID);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            LocalDateTime startDate = rs.getTimestamp("Start").toLocalDateTime();
//            LocalDateTime endDate = rs.getTimestamp("End").toLocalDateTime();
//            minutesInTotal += (int) Duration.between(startDate, endDate).toMinutes();
//        }
//        ps.close();
//        return minutesInTotal;
//    }
    /**getContactID
     * It takes a contact name as an input and returns the appropriate contact id
     * @param contactID the name of the contact that I am searching for
     * @return the appropriate contact ID
     * @throws SQLException*/
    public static String getContactID(String contactID) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_Name = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, contactID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            contactID = rs.getString("Contact_ID");
        }
        return contactID;
    }

    /**getAllContacts
     * it gets a list of all contacts
     * @return allContacts a list of all contact names
     * @throws SQLException*/
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            allContacts.add(contact);
        }
        return allContacts;
    }

    /**getContactAppointments
     * Gets all appointments for a contact
     * @param contactID the contact id that we are looking for appointments
     * @return contactAppointments, a list for the appointments of a contact
     * @throws SQLException*/
//    public static ObservableList<String> getContactAppointments(String contactID) throws SQLException {
//        ObservableList<String> contactAppointments = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM appointments WHERE Contact_ID = '?'";
//        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
//        ps.setString(1, contactID);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            String appointmentID = rs.getString("Appointment_ID");
//            String title = rs.getString("Title");
//            String type = rs.getString("Type");
//            String start = rs.getString("Start");
//            String end = rs.getString("End");
//            String customerID = rs.getString("Customer_ID");
//
//            String newLine = " AppointmentID: " + appointmentID + "\n";
//            newLine += "       Title:" + title + "\n";
//            newLine += "       Type:" + type + "\n";
//            newLine += "       Start:" + start + " UTC\n";
//            newLine += "       End:" + end + " UTC\n";
//            newLine += "       CustomerID:" + customerID + "\n";
//
//            contactAppointments.add(newLine);
//        }
//        ps.close();
//        return contactAppointments;
//    }

    /**
     * It takes a contact name as an input and returns the appropriate contact id
     * @param contactID the name of the contact that I am searching for
     * @return the appropriate contact ID
     * @throws SQLException*/
    public static Contact getContact(int contactID) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, contactID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String contactName = rs.getString("Contact_Name");
            String contactEmail = rs.getString("Email");
            Contact contact = new Contact(contactID, contactName, contactEmail);
            return contact;
        }
        return null;
    }


}
