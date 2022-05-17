package helper;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
/**timeConverter class*/
public class TimeConverter {
  /**convertToUTC
   * convert time to UTC
   * @param dateTime
   * @return utcOUT*/
    public static String convertToUTC(String dateTime){
        Timestamp currentTS = Timestamp.valueOf(String.valueOf(dateTime));
        LocalDateTime localDateTime = currentTS.toLocalDateTime();
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        LocalDateTime localDateTime1 = LocalDateTime.from(zonedDateTime.withZoneSameInstant(ZoneId.of("UTC")));
        String utcOUT = localDateTime1.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return utcOUT;
    }
}
