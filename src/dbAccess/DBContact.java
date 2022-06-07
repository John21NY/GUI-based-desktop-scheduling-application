package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/** Class DBContact*/
public class DBContact {
    /**It takes a contact name as an input and returns the appropriate contact id
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

    /**it gets a list of all contacts
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
    /**It takes a contact name as an input and returns the appropriate contact id
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
