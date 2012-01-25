/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package learning.alarm.tray;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import learning.alarm.timer.AlarmTimeStore;

/**
 *
 * @author ubose
 */
public class AlarmTrayCreator {

    private AlarmTimeStore alarmTimeStore = null;
    private SystemTray _thisSystemTray = null;
    private TrayIcon alarmIcon = null;
    private boolean allDone = false;

    public AlarmTrayCreator(AlarmTimeStore alarmTimeStore) {
        this.alarmTimeStore = alarmTimeStore;
    }

    public TrayIcon createTrayIcon() {

        try {
            _thisSystemTray = SystemTray.getSystemTray();
        } catch (Exception e) {
            //System.out.println(" No system tray, no nothing!!");
            return null;
        }

        alarmIcon = getIcon();
        if (alarmIcon == null) {
            //System.out.println(" No image, no icon!");
            return null;
        } else {
            try {
                _thisSystemTray.add(alarmIcon);
            } catch (AWTException ex) {
                //System.out.println(" Something wrong ! Going kaput!!!");
                return null;
            }
        }

        return alarmIcon;
    }

    public void signalAllDone() {
        //System.out.println(" Well, all done! Going .......");
        /*
         * if (allDone) { return ;
        }
         */
        if (alarmIcon != null) {
            Toolkit.getDefaultToolkit().beep();
            alarmIcon.displayMessage("Done, done!", "It is over!!", TrayIcon.MessageType.ERROR);

            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                // do nothing
            }
        }

        if (alarmIcon != null && _thisSystemTray != null) {
            _thisSystemTray.remove(alarmIcon);
        }
        alarmIcon = null;
    }

    private TrayIcon getIcon() {
        Image alarmIconImage = new ImageIcon(getClass().getResource("myIcon.jpg")).getImage();
        if (alarmIconImage == null) {
            //System.out.println("No image to add!!");
            return null;
        }
        //System.out.println("About image : " + alarmIconImage);
        TrayIcon alarmTrayIcon = new TrayIcon(alarmIconImage, "What's up!");

        PopupMenu alarmPopUp = new PopupMenu();
        MenuItem alarmPopMenu = new MenuItem("Bye!");
        alarmPopMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                alarmTimeStore.forceEnd();
                alarmTimeStore.fileIt();
                signalAllDone();

            }
        });
        alarmPopUp.add(alarmPopMenu);
        alarmTrayIcon.setPopupMenu(alarmPopUp);
        alarmTrayIcon.addMouseListener(new AlarmIconMouseListener());
        return alarmTrayIcon;
    }

    private class AlarmIconMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            registerIn(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            registerIn(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            registerOut(e);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            registerIn(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            registerOut(e);
        }

        private void registerIn(MouseEvent e) {
            //System.out.println("Registering mouse entering event");
            //alarmTimeStore.updateStoreState();
            Object evtSrc = e.getSource();

            if ((evtSrc instanceof TrayIcon)) {

                TrayIcon trIcn = (TrayIcon) evtSrc;

                if (alarmTimeStore.isItOver()) {
                    //System.out.println("Time to go!");
                    signalAllDone();
                } else {
                    trIcn.setToolTip(" Left  "
                            + alarmTimeStore.getTimeLeft());
                }
            }
        }

        private void registerOut(MouseEvent e) {
            //System.out.println("Registering mouse exiting event");
            Object evtSrc = e.getSource();

            if (evtSrc instanceof TrayIcon) {
                TrayIcon trIcn = (TrayIcon) evtSrc;
                trIcn.setToolTip(" Left  "
                        + alarmTimeStore.getTimeLeft());
            }/*
             * 
             */
        }
    }
}
