package dbAccess;

import helper.DBConnection;
import helper.ListManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Class DBDivision*/
public class DBDivision extends Division {

    public DBDivision(int divisionID, String divisionName, int countryID) {
        super(divisionID, divisionName, countryID);
    }

    /**We pass a division string, and it looks for its ID
     * @param division
     * @return division ID
     * @throws SQLException */
    public static Integer getDivisionID(String division) throws SQLException {
        int divisionID = 0;
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            divisionID = rs.getInt("Division_ID");
        }
        ps.close();
        return divisionID;
    }
    /**it selects the division from the division table
     * @throws SQLException*/
    public static void select() throws SQLException {
        ListManager.allDivisions.clear();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            DBDivision dbDivision = new DBDivision(divisionID, divisionName, countryID);
            ListManager.allDivisions.add(dbDivision);
        }

    }
    /**we pass a division name and it gets a division By the id
     * @param divisionName
     * @return divisionName
     * @throws SQLException*/
    public static String getDivisionByID(String divisionName) throws SQLException {
        ObservableList<Division> allDBDivisionNames = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions WHERE division = " + divisionName;
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionID = rs.getInt("Division_ID");
            String divisionName1 = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            DBDivision dbDivision = new DBDivision(divisionID, divisionName1, countryID);
            allDBDivisionNames.add(dbDivision);
        }
        return divisionName;
    }
    public static Division getDivision(int divisionID) throws SQLException {
        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, divisionID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            //String contactEmail = rs.getString("Email");
            Division division = new Division(divisionID, divisionName, countryID);
            return division;
        }
        return null;
    }

    @Override
    public String toString(){
        //return (getClass().getName() + "@" + Integer.toHexString(hashCode()));
        return(Integer.toString(getDivisionID()) + "  " + getDivisionName());
    }
}
