package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**DBDivision*/
public class DBDivision extends Division {

    public DBDivision(int divisionID, String divisionName, int countryID) {
        super(divisionID, divisionName, countryID);
    }

    /**getDivisionID
     * We pass a division string, and it looks for its ID
     * @param division
     * @return division ID
     * @throws SQLException */
    public static Integer getDivisionID(String division) throws SQLException {
        int divisionID = 0;
        String sql = "SELECT Division_ID, Division FROM first_level_divisions WHERE Division = '?'";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setString(1, division);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            divisionID = rs.getInt("Division_ID");
        }
        ps.close();
        return divisionID;
    }
    /**getAllDivisions
     * Observable List that contains data from the first_level_divisions table
     * @return a list of matches at first level divisions
     * @throws SQLException*/
    public static ObservableList<DBDivision> getAllDivisions() throws SQLException {
        ObservableList<DBDivision> allDBDivisions = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int divisionID = rs.getInt("Division_ID");
            String divisionName = rs.getString("Division");
            int countryID = rs.getInt("Country_ID");
            DBDivision dbDivision = new DBDivision(divisionID, divisionName, countryID);
            allDBDivisions.add(dbDivision);
        }
        return allDBDivisions;
    }
    @Override
    public String toString(){
        //return (getClass().getName() + "@" + Integer.toHexString(hashCode()));
        return(Integer.toString(getDivisionID()) + "  " + getDivisionName());
    }
}
