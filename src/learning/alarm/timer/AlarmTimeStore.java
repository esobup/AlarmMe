/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.alarm.timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Timestamp;
import static learning.alarm.util.Constants.TIMESTAMP_STORE;
/**
 *
 * @author ubose
 */
public class AlarmTimeStore {

    private long startTime = 0L;
    private long currentTime = 0L;
    private long endTime = 0L;
    private long oneHour = 0L;
    private long toBeDoneHour = 0L;

    public AlarmTimeStore() {

        startTime = System.currentTimeMillis();
        currentTime = System.currentTimeMillis();
        oneHour = 60 * 60 * 1000;//60000; // Reduced for debugging //
        toBeDoneHour = (long) (oneHour * 8.25);
    }

    public String getTimeLeft() {
        //System.out.println("currentTime " + currentTime);
        //System.out.println("startTime " + startTime);
        //double timeLeft = (double)((toBeDoneHour - (currentTime - startTime))/(1000));
        return getFormatedTime((currentTime - startTime), toBeDoneHour);
    }
    
    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime() {
        this.currentTime = System.currentTimeMillis();
    }

    public long getEndTime() {
        return endTime;
    }

    public long getOneHour() {
        return oneHour;
    }

    public long getStartTime() {
        return startTime;
    }

    public double getToBeDoneHour() {
        return toBeDoneHour;
    }

    public void updateStoreState() {
        setCurrentTime();
        if ((currentTime - startTime) >= toBeDoneHour) {
            endTime = currentTime;
        }
    }

    public boolean isItOver() {
        if (endTime == 0L) {
            updateStoreState();
        }
        return (endTime != 0L);
    }
    
    public String getTotalTimeSpent() {  
        return getFormatedTime(startTime, endTime);
    }
    
    private String getFormatedTime(long startingTime, long endingTime) {
        long spentTimeLong = endingTime - startingTime;
        
        long totSec = (long)(spentTimeLong / 1000);
        
        long totHr = (long) (totSec / 3600);
        long remSec = (long) (totSec - (totHr * 3600));
        int totMin = (int) (remSec / 60);

        return (new StringBuilder(totHr + "")
                .append(":")
                .append(totMin)              
                .toString());
    }

    public void fileIt() {

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append("\n-------------------------------------\n")
            .append("Date : ")
            .append(new Timestamp(System.currentTimeMillis()))
            .append("\n").append(" Enrty time : ")
            .append(new Timestamp(this.getStartTime()))
            .append(" Exit time : ")
            .append(new Timestamp(this.getEndTime()))
            .append(" Total time : ")
            .append(this.getTotalTimeSpent());

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(TIMESTAMP_STORE, true));

            bw.append(logBuilder);

            bw.flush();

            bw.close();
        } catch (Exception e) {
            // do nothing
        }
    }
    /*
    public static void main(String[] args) {
        AlarmTimeStore ats = new AlarmTimeStore();
        System.out.println("startTime : " + ats.startTime);
        ats.endTime = 1321009999996L;
        System.out.println("ho ho : " + ats.getTotalTimeSpent());
    }*/

    public void forceEnd() {
        this.endTime = System.currentTimeMillis();
    }
}
