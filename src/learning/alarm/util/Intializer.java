/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.alarm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import learning.alarm.timer.AlarmTimeStore;
/**
 *
 * @author ubose
 */
public class Intializer {
    
    private Intializer(){
        // private constructor to prevent instantiation
    }
    public static AlarmTimeStore  initialize() throws IOException {
    
        // TODO : Take an input folder path
        String configFolderPath = "D:\\Learning\\AlarmMe\\config";
        Properties lastEntryProperties = new Properties();
        lastEntryProperties.load(new FileInputStream(new File(configFolderPath, Constants.CONFIG.TIMESTAMP_STORE)));
        
        Properties alarmConfig = new Properties();
        alarmConfig.load(new FileInputStream(new File(configFolderPath, Constants.CONFIG.CONFIG_PROPERTIES)));
        
        return configureAlarm(lastEntryProperties, alarmConfig);
        
    }
    
    private static AlarmTimeStore configureAlarm( Properties lastEntryProperties, Properties alarmConfig) {
        //System.out.println(alarmConfig);
        double toSpendTime = Double.parseDouble(alarmConfig.getProperty(Constants.CONFIG.TIME_TO_SPEND)) ;
        AlarmTimeStore theAlarmTimeStore = null;
        
        //2012-01-31 10:38:47.812
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");//DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
        
        String exitTime = lastEntryProperties.getProperty(Constants.OUTPUT.OUT);
        
        Date today = new Date();
        try {
            Date lastExitTime = df.parse(exitTime);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int todayDate = calendar.get(Calendar.DATE);
            calendar.setTime(lastExitTime);
            int lastExitDate = calendar.get(Calendar.DATE);
            
            if (todayDate == lastExitDate) {
                /* last exit time was also on today,
                 * might be due to an untimely shutdown/restart
                 * so will prorate the alarm time
                 */
                String entryTime = lastEntryProperties.getProperty(Constants.OUTPUT.IN);  
                calendar.setTime(df.parse(entryTime));
                long lastEntryMilis = calendar.getTimeInMillis();
                theAlarmTimeStore = new AlarmTimeStore(lastEntryMilis, toSpendTime);
                
            } else {
                theAlarmTimeStore = new AlarmTimeStore(toSpendTime);
            }
        } catch (ParseException ex) {
            /* Exception would mean there is no valid 
             * exit time recorded in the log file. Hence would
             * assume fairly that it is a record for a new day 
             */
            theAlarmTimeStore = new AlarmTimeStore(toSpendTime);
        }
        
        return theAlarmTimeStore;
    }
    
    public static void main(String[] args) {
        try {
            AlarmTimeStore ats = initialize();
            System.out.println("getStartTime " + ats.getStartTime());
            System.out.println("isItOver " + ats.isItOver());
            System.out.println("getTimeLeft " + ats.getTotalTimeSpent());
            
        } catch (IOException ex) {
            Logger.getLogger(Intializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
