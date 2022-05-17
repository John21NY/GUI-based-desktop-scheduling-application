package dbAccess;

import helper.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Reports;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBReports extends Appointment {
    /**
     * Generate the constructor for Class Appointment
     *
     * @param appointmentID      appointmentID as a Primary Key
     * @param title              appointment title
     * @param description        appointment description
     * @param location           appointment location
     * @param type               appointment type
     * @param startDateTime      appointment start date time
     * @param endDateTime        appointment end date time
     * @param createdDate        appointment date creation
     * @param createdBy          appointment creation by user (name)
     * @param lastUpdateDateTime appointment last update
     * @param lastUpdateBy       appointment last update (user name)
     * @param customerID         customer ID as a foreign key
     * @param userID             user ID as a foreign key
     * @param contactID          contact ID as a foreign key
     */
    public DBReports(int appointmentID, String title, String description, String location, String type, LocalDateTime startDateTime,
                     LocalDateTime endDateTime, LocalDateTime createdDate, String createdBy, LocalDateTime lastUpdateDateTime,
                     String lastUpdateBy, int customerID, int userID, int contactID) {
        super(appointmentID, title, description, location, type, startDateTime, endDateTime, createdDate, createdBy,
                lastUpdateDateTime, lastUpdateBy, customerID, userID, contactID);


    }
    public static ObservableList<Reports> getCountries() throws SQLException {
        ObservableList<Reports> countriesList = FXCollections.observableArrayList();
        String sql = "SELECT countries.Country, count(*) as countryCount FROM client_schedule.customers INNER JOIN" +
                " client_schedule.first_level_divisions on customers.Division_ID = first_level_divisions.Division_ID " +
                "INNER JOIN client_schedule.countries on countries.Country_ID = first_level_divisions.Country_ID " +
                "WHERE customers.Division_ID = first_level_divisions.Division_ID GROUP BY first_level_divisions.Country_ID " +
                "ORDER BY count(*) desc";
        PreparedStatement ps = DBConnection.conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String countryName = rs.getString("country");
            int countryCount = rs.getInt("countryCount");
            Reports report = new Reports(countryName, countryCount);
            countriesList.add(report);
        }
        return countriesList;
    }
}
