package com.github.goatseatgrass.MyFirstBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.prefs.Preferences;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;

public class Organization{
	/*
	 * Methods: 1. Announce 
	 * 2. Warn
	 * 3. Delete
	 * 4. React
	 * 5. Autonick
	 * 6. Status
	 */
	
	private DiscordApi api;
	private boolean announce = true;
	private boolean warn = true;
	private boolean delete = true;
	private boolean react = true;
	private boolean autonick = true;
	private static Preferences prefs = Preferences.userNodeForPackage(Organization.class);
	
	public Organization(DiscordApi api) throws IOException {
		this.api = api;
	}

	public static void announce(String[] message, TextChannel currentChannel, User author) {
		// !announce #channel-name message
		if (checkPerms(author))
		{String channelName = message[1].substring(2, 20);
		//currentChannel.sendMessage(channelName);
		TextChannel target = Main.api.getTextChannelById(channelName).get();
		String text = Main.getMessagefromArray(message, 2);
		currentChannel.sendMessage("Message sent!");
		target.sendMessage(text);}
		else
		{currentChannel.sendMessage("You are not alpha enough for this command " + author.getMentionTag());}
	}

	public static void warn(String[] message, TextChannel currentChannel, User author)
			throws InterruptedException, ExecutionException {
		// !warn @member message
		if (checkPerms(author))
		{String userId = message[1].substring(2, 20);
		User user = Main.api.getUserById(userId).get();
		String text = Main.getMessagefromArray(message, 2);
		user.sendMessage(text);
		currentChannel.sendMessage(user.getDiscriminatedName() + " has been warned :>");}
		else
		{currentChannel.sendMessage("You are not alpha enough for this command " + author.getMentionTag());}

	}

	public static void delete(String[] message, TextChannel currentChannel, long ID, User author)
			throws InterruptedException, ExecutionException {
		// !delete number
		if (checkPerms(author))
		{MessageSet messages = currentChannel.getMessagesBefore(Integer.parseInt(message[1]), ID).get();
		messages.deleteAll();
		currentChannel.getMessageById(ID).get().delete();}
		else
		{currentChannel.sendMessage("You are not alpha enough for this command " + author.getMentionTag());}

	}
	
	public static void react(String[] message, TextChannel currentChannel, long ID, User author) throws NumberFormatException, InterruptedException, ExecutionException {
		// !react number emoji
		if (checkPerms(author))
		{MessageSet messages = currentChannel.getMessagesBefore(Integer.parseInt(message[1]), ID).get();
		if (EmojiManager.isEmoji(message[2]))
			{messages.forEach(i -> {i.addReaction(EmojiParser.parseToUnicode(message[2]));});}
		else
		{String alias = message[2].substring(1, message[2].length()-1);
		System.out.println("alias " + alias);
		messages.forEach(i -> {i.addReactions(EmojiManager.getForAlias(alias).getUnicode());});
		currentChannel.getMessageById(ID).get().delete();}}
		else
		{currentChannel.sendMessage("You are not alpha enough for this command " + author.getMentionTag());}

		}
	
	public static void removeReactions(String[] message, TextChannel currentChannel, long ID, User author) throws NumberFormatException, InterruptedException, ExecutionException {
		// !removeReactions number emoji
		MessageSet messages = currentChannel.getMessagesBefore(Integer.parseInt(message[1]), ID).get();
		if (message.length==2)
		{String alias = message[2].substring(0, message[2].length()-1);
		 messages.forEach(i -> {i.removeReactionByEmoji(EmojiManager.getForAlias(alias).getUnicode());});}
		else
		{messages.forEach(i -> {i.removeAllReactions();});}
		}
	
	public static void status(String[] message) {
		// !status message
		//if (checkPerms(author))
		{String text = Main.getMessagefromArray(message, 1);
		if (text.equalsIgnoreCase("invisible"))
			{Main.api.updateStatus(UserStatus.INVISIBLE);}
		else if (text.equalsIgnoreCase("do not disturb"))
			{Main.api.updateStatus(UserStatus.DO_NOT_DISTURB);}
		else if (text.equalsIgnoreCase("online"))
			{Main.api.updateStatus(UserStatus.ONLINE);}
		else if (text.equalsIgnoreCase("offline"))
			{Main.api.updateStatus(UserStatus.OFFLINE);}
		else if (text.equalsIgnoreCase("idle"))
			{Main.api.updateStatus(UserStatus.IDLE);}
		//else
			//{currentChannel.sendMessage("Didn't recognise the status");}
		}
		//else
		//{currentChannel.sendMessage("You are not alpha enough for this command " + author.getMentionTag());}
	}
	
	public static void deleteInTime(TextChannel currentChannel, long ID, long time) {
		new java.util.Timer().schedule(new java.util.TimerTask() {@Override public void run() {try {
			currentChannel.getMessageById(ID).get().delete();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}}, time*60000);
	}
	
	public static boolean checkPerms(User author) {
		boolean hasModPerms = false;
		List<Role> roles = author.getRoles(Main.RabidWriters);
		for (int i=0; i<roles.size(); i++) {
			//System.out.println(roles.get(i).getName());
			if (roles.get(i).getName().equalsIgnoreCase("Mother")||roles.get(i).getName().equalsIgnoreCase("Modhound")) {
				hasModPerms = true;
			}
		}
		System.out.println(hasModPerms);
		return hasModPerms;
	}

	public static void autoNick(User user) {
		user.updateNickname(Main.RabidWriters, "Stray Dog");
	}
	
	public static void disable(String method) {
		
	}
	
	
	
}


