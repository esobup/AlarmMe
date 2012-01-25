/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package learning.alarm.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author ubose
 */
public class Intializer {
    
    public void initialize() throws IOException {
    
        // TODO : Take an input file path
        String propFile = "D:\\Learning\\AlarmMe\\time.properties";
        Properties properties = new Properties();
        properties.load(new FileInputStream(propFile));
        
        
    }
    
}
