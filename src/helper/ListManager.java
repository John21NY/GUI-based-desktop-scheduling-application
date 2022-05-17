package helper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ListManager {
    public static ObservableList<LocalTime> generateTimeList(int bh, int iter){
        ZonedDateTime estZdt = ZonedDateTime.of(LocalDate.now(), LocalTime.of(bh,0), ZoneId.of("America/New_York"));
        ZonedDateTime localZdt = ZonedDateTime.ofInstant(estZdt.toInstant(), ZoneId.systemDefault());
        ObservableList<LocalTime> startTimeList = FXCollections.observableArrayList();
        int startHour = localZdt.getHour();
        int maxIter = startHour + iter;
        for(int h = startHour; h <= maxIter; h++ ){
            startTimeList.add(LocalTime.of(h, 0));
        }
        return startTimeList;
    }
}
