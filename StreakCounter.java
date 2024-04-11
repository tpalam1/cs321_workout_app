import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

public class StreakCounter {
	
	int streak = 0;
	int streakFreezes = 0;
	boolean workedOutToday = false;
	Date date;
	
	public StreakCounter() {
		TimerTask task = new TimerTask() {
			public void run() {
				if(!workedOutToday) {
					if(streakFreezes > 0) {
						streakFreezes--;
						return;
					}
					else {
						streak = 0;
					}
				}
			}
		};
		Timer timer = new Timer("Timer");
		long period = 1000L * 60L * 60L * 24L;
		date = new Date();
	  timer.scheduleAtFixedRate(task, date, period);
	}
	
	public int getStreak() {
		return streak;
	}

	public int getStreakFreezes() {
		return streakFreezes;
	}
	
	public void streakUpdate() {
		if(workedOutToday)
			return;
		streak++;
		if(streak%5 == 0 && streakFreezes < 3)
			streakFreezes++;
		workedOutToday = true;
	}
}
