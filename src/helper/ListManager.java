package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Division;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**Class ListManager
 * it will create a list of business hours, and it will iterate until create a full list of the business hours that
 * are described in the requirements
 * @returns startTimeList*/
public class ListManager {
    public static ObservableList<LocalTime> generateTimeList(int bh, int iter){
        ZonedDateTime estZdt = ZonedDateTime.of(LocalDate.now(), LocalTime.of(bh,0), ZoneId.of("America/New_York"));
        ZonedDateTime localZdt = ZonedDateTime.ofInstant(estZdt.toInstant(), ZoneId.systemDefault());
        ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
        for(int h = 0; h < iter; h++ ){
            startTimeList.add(localZdt.toLocalTime());
            localZdt = localZdt.plusHours(1);

        }
        return startTimeList;
    }
    public static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
}
