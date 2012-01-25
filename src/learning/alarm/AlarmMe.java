/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.alarm;

import learning.alarm.timer.AlarmTimeStore;
import learning.alarm.tray.AlarmTrayCreator;

/**
 *
 * @author ubose
 */
public class AlarmMe {

    

    public static void main(String[] args) {
        AlarmTimeStore alarmTimeStore = new AlarmTimeStore();
        //AlarmTimer alarmTimer = new AlarmTimer(alarmTimeStore);
        //alarmTimer.startTimer();

        AlarmTrayCreator alarmTrayCreator = new AlarmTrayCreator(alarmTimeStore);
        alarmTrayCreator.createTrayIcon();


        while (true) {
            if (alarmTimeStore.isItOver()) {
                System.out.println("Woke up, but time to go! ");
                alarmTimeStore.fileIt();
                alarmTrayCreator.signalAllDone();
                
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                // do nothing
            }
        }

        System.exit(0);
    }

    
}
