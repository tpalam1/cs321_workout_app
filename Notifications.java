import java.awt.*;
import java.awt.TrayIcon.MessageType;

/*
 * As I was unfamiliar with how to send notifications in java, I followed advice and demos
 * on stackoverflow to get this working.
 * INCOMPLETE AS OF NOW, STILL NEED TO FIGURE OUT SCHEDULING THE NOTIFS
 */

public class Notifications {
	
	public Notifications() {}
	
	public static void main(String[] args) throws AWTException{
		if(SystemTray.isSupported()) {
			Notifications notif = new Notifications();
			notif.notifyApp();
		}
		else {
			System.err.println("Not supported");
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
}
