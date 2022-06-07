package helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


/**Class Logger*/
public class Logger {
     private static String logPath = "loginActivity.txt";
    /**it will write the buffered output to the login activity in the login activity text file
     * @param userName username
     * @param isLogged boolean for successful login or not
     * @throws IOException*/
     public static void checkLogin(String userName, boolean isLogged) {
         try {
             BufferedWriter logger = new BufferedWriter(new FileWriter(logPath, true));
             logger.append(ZonedDateTime.now(ZoneOffset.UTC) + " UTC - LoginAttempt - Username: "
                     + userName + "Successful Login: " + isLogged + "\n");
             logger.flush();
             logger.close();

     } catch (IOException e) {
             e.printStackTrace();
         }
     }

     }
