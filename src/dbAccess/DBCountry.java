package dbAccess;

import helper.DBConnection;
import helper.ListManager;
import model.Contact;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** Class DBCountry*/
public class DBCountry extends Country {
    /**Generate the constructor
     * @param countryID
     * @param countryName
     */
    public DBCountry(int countryID, String countryName) {

        super(countryID, countryName);
    }

    /**it uses the following query to select country id's from countries and adds the result in
     * the appropriate list of countries. Each country will have the correct divisions depicted in their list*/
    public static void select() {
        try{
            ListManager.allCountries.clear();
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                DBCountry country = new DBCountry(countryID, countryName);
                ListManager.allCountries.add(country);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public static Country getCountry(int countryID) throws SQLException {
        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String countryName = rs.getString("Country");
            Country country = new Country(countryID, countryName);
            return country;
        }
        return null;
    }

//    @Override
//    public String toString(){
//        //return (getClass().getName() + "@" + Integer.toHexString(hashCode()));
//        return(Integer.toString(getCountryID()) + "  " + getCountryName());
//    }

}
