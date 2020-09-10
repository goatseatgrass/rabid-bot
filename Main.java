package com.github.goatseatgrass.MyFirstBot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
	public static DiscordApi api;
	public static Server RabidWriters;
	public static Server MyServer;


	public static void main(String[] args) throws IOException {
		api = new DiscordApiBuilder().setToken("NzIyNDQ2MTY2Njk2MTMyNjE4.XujNOg.xpsozD5-obej3Ae2ALzdrI6IKzY").login().join();
		System.out.println("Logged in!");
		String token = "NzIyNDQ2MTY2Njk2MTMyNjE4.XujNOg.xpsozD5-obej3Ae2ALzdrI6IKzY";
		RabidWriters = api.getServerById("695616904206876774").get();
		//MyServer = api.getServerById("722438697785622629").get();

		api.addMessageCreateListener(event -> {
			try {
				assign(event.getMessageContent(), event.getChannel(), event.getMessageId(), event.getMessageAuthor().asUser().get());
			} catch (InterruptedException | ExecutionException | IOException e) {
			}
		});
		Organization.status(new String[]{"idk", "invisible"});
		EmbedBuilder embed = new EmbedBuilder();
		embed.addField("name", "value");
		embed.setAuthor("Author");
		embed.setDescription("Description");
		embed.setColor(Color.WHITE);
		embed.setFooter("Footer");
		embed.setTimestampToNow();
		embed.setTitle("Title");

		api.addPrivateChannelCreateListener(i -> {
			i.getChannel().addMessageCreateListener(j -> {
				if (j.getMessageContent().equalsIgnoreCase("ping")) {
					j.getChannel().sendMessage("It somehow worked you bastard");
				}
			});
		});
		//api.addServerMemberJoinListener(i -> {Organization.autoNick(i.getUser());});

		// Add a listener which answers with Discord name if someone writes "!ping"
		api.addMessageCreateListener(event -> {
			if (event.getMessageContent().contains("jjskgey4u")) {
				//User user = api.getYourself();
				//User user = event.getMessageAuthor().asUser().get();
				//event.getChannel().sendMessage(api.createBotInvite());
				//for (int i=0; i<user.getRoles(MyServer).size(); i++)
				//{event.getChannel().sendMessage(user.getRoles(MyServer).get(i).getName());}
				System.out.println("Working");
				//event.getChannel().sendMessage(user.getIdAsString());
				event.getChannel().sendMessage(event.getMessageContent());
				System.out.println(event.getMessageContent());
				event.getChannel().sendMessage(event.getReadableMessageContent());
				System.out.println(event.getReadableMessageContent());
				//event.getChannel().sendMessage("Discriminated Name - " + user.getDiscriminatedName());
				//event.getChannel().sendMessage("Discriminator Name - " + user.getDiscriminator());
				//event.getChannel().sendMessage("Mention Tag - " + user.getMentionTag());
				//event.getChannel().sendMessage("Nick Mention Tag - " + user.getNicknameMentionTag());
				//event.getChannel().sendMessage("Name - " + user.getName());
				//event.getChannel().sendMessage("Display Name - " + user.getDisplayName(event.getServer().get()));
			}
		});

	}

	public static String getMessagefromArray(String[] array, int startingPoint) {
		String message = "";
		for (int i = startingPoint; i < array.length; i++) {
			message += array[i] + " ";
		}
		return message.trim();
	}

	public static void assign(String message, TextChannel channel, long ID, User author) throws InterruptedException, ExecutionException, IOException {
		String[] breakdown = message.split(" ");
		if (author.isBot()) {
		} else {
			if (breakdown[0].equalsIgnoreCase("wolf.status")) {
				Organization.status(breakdown);
			}
			if (breakdown[0].equalsIgnoreCase("wolf.announce")) {
				Organization.announce(breakdown, channel, author);
			}
			if (breakdown[0].equalsIgnoreCase("wolf.warn")) {
				Organization.warn(breakdown, channel, author);
			}
			if (breakdown[0].equalsIgnoreCase("wolf.delete")) {
				Organization.delete(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wolf.react")) {
				Organization.react(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wolf.removereactions")) {
				Organization.removeReactions(breakdown, channel, ID, author);
			}
			if (author.getDiscriminatedName().equalsIgnoreCase("DISBOARD#2760")) {
				Bumpwars.bumpCount(message, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.add")) {
				DailyWordCount.add(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.set")) {
				DailyWordCount.set(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.check")) {
				DailyWordCount.check(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.total")) {
				DailyWordCount.sum(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.reset")) {
				DailyWordCount.reset(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.undo")) {
				DailyWordCount.undo(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.view")) {
				DailyWordCount.view(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.help")) {
				DailyWordCount.help(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.break")) {
				DailyWordCount.goOnBreak(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.back")) {
				DailyWordCount.isBack(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.resetbreak")) {
				DailyWordCount.resetBreakStats(breakdown, channel, ID, author);
			}
			if (breakdown[0].equalsIgnoreCase("wc.checkstats")) {
				DailyWordCount.checkStats(breakdown, channel, ID, author);
			}

			if (breakdown[0].equalsIgnoreCase("wc.checkmonthlystats")) {
				System.out.println("Monthly");
				DailyWordCount.checkMonthlyStats(breakdown, channel, ID, author);
			}
			}
		}
	}



