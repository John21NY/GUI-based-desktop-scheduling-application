package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**DB Country*/
public class DBCountry extends Country {
    /**
     * Generate the constructor
     *
     * @param countryID
     * @param countryName
     */
    public DBCountry(int countryID, String countryName) {
        super(countryID, countryName);
    }

    /**getAllCountries
     * it uses the following query to get all countries in a Lo
     * @return a list of all countries
     * @throws SQLException*/
    public static ObservableList<DBCountry> getAllCountries() {
        ObservableList<DBCountry> allCountries = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryID = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                DBCountry country = new DBCountry(countryID, countryName);
                allCountries.add(country);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return allCountries;
    }

    @Override
    public String toString(){
        //return (getClass().getName() + "@" + Integer.toHexString(hashCode()));
        return(Integer.toString(getCountryID()) + "  " + getCountryName());
    }

}
