package net.stupendous.autoshutdown;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import net.stupendous.autoshutdown.misc.Log;
import net.stupendous.autoshutdown.misc.Util; 

import org.bukkit.configuration.file.FileConfiguration;

public class WarnTask extends TimerTask {
	protected AutoShutdownPlugin plugin = null;
	protected Log log = null;
	protected long seconds = 0;
	protected String msgShutdownNow = "Server is shutting down NOW!";
	protected String msgShutdownXSeconds = "Server is shutting down in %d seconds ...";
	protected String msgShutdown1Minute = "Server is shutting down in 1 minute ...";
	protected String msgShutdown1MinuteXSeconds = "Server is shutting down in 1 minute %d seconds ...";
	protected String msgShutdownXMinutes = "Server is shutting down in %d minutes ...";
	protected String msgShutdownXMinutesXSeconds = "Server is shutting down in %d minutes %d seconds ...";
	
	WarnTask(AutoShutdownPlugin instance, long seconds) {
		plugin = instance;
		log = plugin.log;
		this.seconds = seconds;

		FileConfiguration config = plugin.getConfig();
		String configMsgShutdownNow = config.getString("shutdownnow");
		String configMsgShutdownXSeconds = config.getString("shutdownXseconds");
		String configMsgShutdown1Minute = config.getString("shutdown1minute");
		String configMsgShutdown1MinuteXSeconds = config.getString("shutdown1minuteXseconds");
		String configMsgShutdownXMinutes = config.getString("shutdownXminutes");
		String configMsgShutdownXMinutesXSeconds = config.getString("shutdownXminutesXseconds");

		if (configMsgShutdownNow != null) {
			msgShutdownNow = configMsgShutdownNow;
		}

		if (configMsgShutdownXSeconds != null) {
			msgShutdownXSeconds = configMsgShutdownXSeconds;
		}

		if (configMsgShutdown1Minute != null) {
			msgShutdown1Minute = configMsgShutdown1Minute;
		}

		if (configMsgShutdown1MinuteXSeconds != null) {
			msgShutdown1MinuteXSeconds = configMsgShutdown1MinuteXSeconds;
		}

		if (configMsgShutdownXMinutes != null) {
			msgShutdownXMinutes = configMsgShutdownXMinutes;
		}

		if (configMsgShutdownXMinutesXSeconds != null) {
			msgShutdownXMinutesXSeconds = configMsgShutdownXMinutesXSeconds;
		}
	}
	
	public void run() {
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (TimeUnit.SECONDS.toMinutes(seconds) > 0) {
					if (TimeUnit.SECONDS.toMinutes(seconds) == 1) {
						if (seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)) == 0) {
							Util.broadcast(msgShutdown1Minute);
						} else {
							Util.broadcast(msgShutdown1MinuteXSeconds,
									seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)));
						}
						
					} else {
						if (seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)) == 0) {
							Util.broadcast(msgShutdownXMinutes, 
									TimeUnit.SECONDS.toMinutes(seconds));
						} else {
							Util.broadcast(msgShutdownXMinutesXSeconds, 
									TimeUnit.SECONDS.toMinutes(seconds),
									seconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds)));
						}
						
					}
				} else {
					if (TimeUnit.SECONDS.toSeconds(seconds) == 1) {
						Util.broadcast(msgShutdownNow);
					} else {
						Util.broadcast(msgShutdownXSeconds,	seconds);
					}
				}
			}
		});
	}
}
