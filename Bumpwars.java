package com.github.goatseatgrass.MyFirstBot;

import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;

public class Bumpwars {
/*Methods needed:
 * 1. Bump Count per member
 * 2. Check Bump Count
 * 3. Bump Leaderboard
 * 4. Bump titles
 */
	private static Preferences prefs = Preferences.userNodeForPackage(Bumpwars.class);
	
	public static void bumpCount(String message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
		//prefs.
	}
}
