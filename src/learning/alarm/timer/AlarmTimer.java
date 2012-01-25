/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.alarm.timer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author ubose
 */
public class AlarmTimer {
    
    private AlarmTimeStore alarmTimeStore = null;
    

    public AlarmTimer(AlarmTimeStore alarmTimeStore) {
        this.alarmTimeStore = alarmTimeStore;
    }
    
    public void startTimer() {
        Timer alarmTimer = new Timer();
        //alarmTimer.schedule(new AlarmTimerTask(), this.alarmTimeStore.getOneHour());
    }
    
    public boolean isItOver() {
        this.alarmTimeStore.updateStoreState();
        return (this.alarmTimeStore.isItOver());
    }

    private class AlarmTimerTask extends TimerTask {
 
        @Override
        public void run() {
            alarmTimeStore.setCurrentTime();
            alarmTimeStore.updateStoreState();
        }
    
    }

}
