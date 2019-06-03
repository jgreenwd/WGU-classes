/*
 * author Jeremy Greenwood
 * mentor Rebekah Coggin
 * WGU-ID 000917613
 * course C195
 */
package lib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/* ===============================================================
 * (4025.01.07) - J: Login attempt logging
 *
 * Provide the ability to track user activity by recording timestamps 
 * for user log-ins in a .txt file. Each new record should be appended 
 * to the log file, if the file already exists.
 * =============================================================== */
public class LogGen {
    private static final String LOGFILE = "log.txt";
    
    // append username & login success to EoF
    public static void log(String user, boolean val) throws IOException {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(LOGFILE, true)));
            String entry = ZonedDateTime.now() + " " + user + (val ? " VALID" : " INVALID");
            pw.println(entry);
            pw.flush();
            
        } catch (IOException e) {
            System.out.println("LogGen Error: " + e.getMessage());
        }
    }
}
