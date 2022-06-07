package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static helper.DBConnection.conn;

/**Class DBUser*/
public class DBUser extends User {
    /**
     * Constructor for Class User
     *
     * @param addUserID
     * @param addUserName
     */
    public DBUser(Integer addUserID, String addUserName) {

        super(addUserID, addUserName);
    }

    /** A list where it gets  unique allUserID from the database
     * @return list of allUserID */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        String sql = "SELECT User_ID, User_Name FROM Users";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            User user = new User(userID, userName);
            allUsers.add(user);
        }
        ps.close();
        return allUsers;
    }
    /**It takes a username as an input and returns the appropriate user id
     * @param userID the name of the user that I am searching for
     * @return the appropriate user ID
     * @throws SQLException*/
    public static User getUser(int userID) throws SQLException {
        String sql = "SELECT * FROM users WHERE User_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String userName = rs.getString("User_Name");
            //String contactEmail = rs.getString("Email");
            User user = new User(userID, userName);
            return user;
        }
        return null;
    }

    /**validates the user credential to login
     * @param userNameInput
     * @param passwordInput
     * @return boolean for successful login or not
     * @throws SQLException*/
    public static User validateLogin(String userNameInput, String passwordInput) throws SQLException {
        DBConnection.getConnection();
        String sql = "SELECT * FROM users WHERE User_Name = ? AND password = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, userNameInput);
        ps.setString(2, passwordInput);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            int userID = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            User user = new User(userID, userName);
            ps.close();
            return user;
        }

        return null;
    }
}

