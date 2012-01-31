/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.alarm.util;

/**
 *
 * @author ubose
 */
public class Constants {

    public final static long ONE_HOUR = 60 * 60 * 1000;
    public static final class OUTPUT {
        public static final String IN = "intime";
        public static final String OUT = "outtime";
        public static final String SPENT = "spenttime";
        public static final String SHORT = "shortfall";
    }
    
    public static final class CONFIG {
        public static final String TIMESTAMP_STORE = "timestamp.log";
        public static final String CONFIG_PROPERTIES = "config.properties";
        public static final String TRAY_ICON_IMAGE = "trayIcon";
        public static final String TIME_TO_SPEND = "timeToSpend";
    }
}
