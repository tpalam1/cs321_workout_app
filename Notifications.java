import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/*
 * As I was unfamiliar with how to send notifications in java, I followed advice and demos
 * on stackoverflow to get this working
 */

public class Notifications {
	
	Date date;
	boolean notifOn = true;
	
	public Notifications() {}
	
	public void notif() {
		TimerTask task = new TimerTask() {
			public void run() {
				if(SystemTray.isSupported()) {
					Notifications notif = new Notifications();
					try {
						notif.notifyApp();
					} catch (AWTException e) {
						e.printStackTrace();
					}
				}
				else {
					System.err.println("Not supported");
				}
			}
		};
		Timer timer = new Timer("Timer");
		long period = 1000L * 60L * 60L * 24L;
		this.setDate();
	    timer.scheduleAtFixedRate(task, date, period);
	    if(!notifOn) {
	    	timer.cancel();
	    }
	}
	
	public void notifyApp() throws AWTException{
		SystemTray st = SystemTray.getSystemTray();
		
		Image i = Toolkit.getDefaultToolkit().createImage("icon.png");
		
		TrayIcon ti = new TrayIcon(i, "Tray Demo");
		ti.setImageAutoSize(true);
		ti.setToolTip(null);
		st.add(ti);
		
		ti.displayMessage("Remember to workout today!",
				"Keep up the good work, you've got this!",
				MessageType.INFO);
	}
	
	private void setDate() {
		date = new Date();
	}

	public void notifOn() {
		this.notifOn = true;
	}
	
	public void notifOff() {
		this.notifOn = false;
	}
}
