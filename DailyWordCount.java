package com.github.goatseatgrass.MyFirstBot;

import com.vdurmont.emoji.EmojiParser;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;
import java.util.prefs.Preferences;

public class DailyWordCount {
	
private static final Preferences prefs = Preferences.userNodeForPackage(DailyWordCount.class);
/* Names of keys
 * 1. author.getIdAsString() book1/book2 name - String book name 
 * 2. author.getIdAsString() book1/book2 number - Long word count
 * 3. author.getIdAsString() book1/book2 - Boolean book set or not
 * 4. author.getIdAsString() onbreak - Boolean on break
 * 5. author.getIdAsString() break localdatetime - String from
 * 6. author.getIdAsString() stats break - from to
 * 7. author.getIdAsString() stats break - from to
 */

public static void help(String[] message, TextChannel currentChannel, long ID, User author) throws ExecutionException {
	//wc.set book1 name or wc.set book2 name
MessageBuilder mb = new MessageBuilder();
EmbedBuilder eb = new EmbedBuilder();
eb.setColor(Color.WHITE);
eb.setAuthor("List of Commands");
eb.addField("1. wc.set book1/book2 <Name of the book>", "Adds the specified book as your first/second book. You can also use this command to rename your book");
eb.addField("2. wc.add book1/book2 <Number of words>", "Adds the amount of words to your first/second book.");
eb.addField("3. wc.undo book1/book2", "Removed the last added amount of words from your first/second book.");
eb.addField("4. wc.check book1/book2 <Number> ", "Shows the specified number of word count entries of your first/second book. If no number is specified, it shows the last 10 entries");
eb.addField("5. wc.total book1/book2", "Shows the total amount of words written for your first/second book.");
eb.addField("6. wc.reset book1/book2", "Resets all progress of your first/second book.");
eb.addField("7. wc.view book1/book2", "Shows the name of your first/second book.");
eb.addField("8. wc.break", "Puts you on break effective immediately.");
eb.addField("9. wc.back", "Ends your break");
eb.addField("10. wc.checkstats", "Shows you dem statistics");
eb.addField("11. wc.checkmonthlystats <Month>", "Shows you dem monthly statistics");
currentChannel.sendMessage(eb);

}

public static void set(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	//wc.set book1 name or wc.set book2 name
	String book = message[1];
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		prefs.put(author.getIdAsString() + book + " name", Main.getMessagefromArray(message, 2));
		prefs.putBoolean(author.getIdAsString() + book, true);
		currentChannel.getMessageById(ID).get().addReaction(EmojiParser.parseToUnicode(":white_check_mark:"));
		Organization.deleteInTime(currentChannel, ID, 1);
	}
}

public static void view(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	//wc.view book1 or wc.view book2 
	String book = message[1];
	if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		if (prefs.get(author.getIdAsString() + book + " name", "no book").equalsIgnoreCase("no book"))
			{currentChannel.sendMessage("You do not have a book atm. Use \"wc.set book1 <name of your book without these thingies at the end>\" to first set a book you dumb whore");}
		else
			{currentChannel.sendMessage(prefs.get(author.getIdAsString() + book + " name", "no book"));}
	}
}

public static void add(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	//wc.add book1 number or wc.add book2 number
	String book = message[1];
	boolean validEntry = true;
	
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		if (prefs.get(author.getIdAsString() + book, "no book").equalsIgnoreCase("no book"))
			{currentChannel.sendMessage("You do not have a book atm. Use \"wc.set book1 <name of your book without these thingies at the end>\" to first set a book you dumb whore");}
		else
		{String wc = prefs.get(author.getIdAsString() + book + " wc", "");
		try {
			int words = Integer.parseInt(message[2]);
		}
		catch(NumberFormatException e) {
			currentChannel.sendMessage("Please use a valid number and don't annoy me you cocksucker");
			validEntry = false;
		}
		
		if(validEntry) {
		wc+= message[2];
		wc+=" ";
		prefs.put(author.getIdAsString() + book + " wc", wc);
		prefs.put(author.getIdAsString() + " update", LocalDate.now().toString() + " ");}
		currentChannel.getMessageById(ID).get().addReaction(EmojiParser.parseToUnicode(":white_check_mark:"));
		Organization.deleteInTime(currentChannel, ID, 5);
		}
	}
}

public static void undo(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	//wc.add book1 number or wc.add book2 number
	String book = message[1];
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		String wc = prefs.get(author.getIdAsString() + book + " wc", "");
		String newwc = "";
		String months = prefs.get(author.getIdAsString() + " update", "");
		String newmonths = "";
		for (int i=0; i<wc.split(" ").length-1; i++)
			{newwc+=wc.split(" ")[i];
			newwc+=" ";}

		for (int i=0; i<months.split(" ").length-1; i++)
		{newmonths+=months.split(" ")[i];
			newmonths+=" ";}
		prefs.put(author.getIdAsString() + book + " wc", newwc);
		prefs.put(author.getIdAsString() + " update", newmonths);
		currentChannel.getMessageById(ID).get().addReaction(EmojiParser.parseToUnicode(":white_check_mark:"));
	}
}

public static void check(String[] message, TextChannel currentChannel, long ID, User author) {
	//wc.check book1 month or wc.check book2 month
	String book = message[1];
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're already on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		if (prefs.getBoolean(author.getIdAsString() + book, false)) {
			String words = "Words written - ";
			String[]temp = prefs.get(author.getIdAsString() + book + " wc", "0").split(" ");
			for (int i=0; i<temp.length; i++) {
				if (i==temp.length-1)
					{words+=temp[i];}
				else
					{words+= temp[i] + " ,";}
			}
			currentChannel.sendMessage(words);
		}
	}
}

public static int total(String[] message, TextChannel currentChannel, long ID, User author, String month) {
	int sum = 0;
	String book = message[1];
	if (month.equalsIgnoreCase("total")) {
		if (prefs.getBoolean(author.getIdAsString() + book, false)) {
			String allWords = prefs.get(author.getIdAsString() + book + " wc", "0");
			String[] words = allWords.split(" ");
			for (int i = 0; i < words.length; i++) {
				//System.out.println(words[i]);
				if (!words[i].isBlank()) {
					sum += Long.parseLong(words[i].strip());
				}
			}
		}
	}
	else {
		int counter = 0;
		boolean validMonth = true;
		int start = -1;
		boolean startSet = false;
		String[] dates = prefs.get(author.getIdAsString() + " update", "").split(" ");
		String[] words = prefs.get(author.getIdAsString() + book + " wc", "0").split(" ");

		try {
			Month.valueOf(month.toUpperCase());
		} catch (IllegalArgumentException e) {
			validMonth = false;
		}

		if (validMonth) {
			for (int i = 0; i< dates.length; i++) {
				if (LocalDate.parse(dates[i]).getMonth().compareTo(Month.valueOf(month.toUpperCase()))==0 && startSet) {
					counter++;
				}
				else if (LocalDate.parse(dates[i]).getMonth().compareTo(Month.valueOf(month.toUpperCase()))==0){
					start = i;
					startSet = true;
				}
			}

			for (int i = start; i < start + counter + 1; i++) {
				//System.out.println(words[i]);
				if (!words[i].isBlank()) {
					sum += Long.parseLong(words[i].strip());
				}
			}

			if (start == -1){
				sum = -18;
			}
		}

		else{
			sum = -18;
		}
	}


	return sum;
}

public static void sum(String[] message, TextChannel currentChannel, long ID, User author) {
	//wc.total book1 month or wc.check book2 month
	int sum = 0;
	String book = message[1];
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're already on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		sum = total(message, currentChannel, ID, author, "total");
		currentChannel.sendMessage("sum - " + sum + " words");
	}
	
	else {
		currentChannel.sendMessage("Please specify whether the changes are for book1 or book2");
	}
	
}

public static void reset(String[] message, TextChannel currentChannel, long ID, User author) {
	//wc.reset book1 month or wc.reset book2 month
	String book = message[1];
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
	{currentChannel.sendMessage("You're already on a break bitch ass bastard. Use wc.back to join the rat race");}
	
	else if (book.equalsIgnoreCase("book1")||book.equalsIgnoreCase("book2")) {
		if (prefs.getBoolean(author.getIdAsString() + book, false)) {
			prefs.remove(author.getIdAsString() + book + " name");
			prefs.remove(author.getIdAsString() + book + " wc");
			prefs.remove(author.getIdAsString() + book);
			currentChannel.sendMessage(book +" removed");
		}
	}
	
	else {
		currentChannel.sendMessage("Please specify if the changes are for book1 or book2");
	}
}

public static void goOnBreak(String[] message, TextChannel currentChannel, long ID, User author) {
	LocalDate breakStartsfrom = LocalDate.now();
	if (prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
		{currentChannel.sendMessage("You're already on a break bitch ass bastard. Use wc.back to join the rat race");}
	else
		{prefs.putBoolean(author.getIdAsString() + "isOnBreak", true);
		prefs.put(author.getIdAsString() + "break","From : " + LocalDate.now().toString());
		}
	}

public static void isBack(String[] message, TextChannel currentChannel, long ID, User author){
	if (!prefs.getBoolean(author.getIdAsString() + "isOnBreak", false)) 
		{currentChannel.sendMessage("You were never on a break bitch ass bastard. Use wc.break to quit on us like a damn pussy");}
	else
		{prefs.putBoolean(author.getIdAsString() + "isOnBreak", false);
		//prefs.put(author.getIdAsString() + "break", LocalDate.now().toString());
		String breakstats = prefs.get(author.getIdAsString() + "breakstats", "");
		breakstats+=prefs.get(author.getIdAsString() + "break", "");
		breakstats+=" To : ";
		breakstats+=LocalDate.now();
		breakstats+=",";
		prefs.put(author.getIdAsString() + "breakstats", breakstats);
		}
}

public static void checkStats(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	if (message.length>1) {
		if (Organization.checkPerms(author)) {
			boolean breakexists = true;
			User subject = Main.api.getUserById(message[2].substring(2, 21)).get();
			String[] breaks = prefs.get(subject.getIdAsString() + "breakstats", "").split(",");
			String breakstats = "";

			if (breaks.length==1&&breaks[0].isEmpty())
				breakexists = false;
			if (breakexists){
				for (int i=0; i< breaks.length; i++){
					breakstats += breaks[i];
					String[] toandfrom = breaks[i].split("-");
					breakstats += String.valueOf(Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(toandfrom[1]), LocalDate.parse(toandfrom[3])))) + " Days";
					breakstats += System.lineSeparator();
				}}
			else
			{breakstats = "No breaks taken, gg you hardworking whore";}

			String bookname1 = prefs.get(subject.getIdAsString() + "book1 name", "No name set");
			String bookname2 = prefs.get(subject.getIdAsString() + "book2 name", "No name set");
			int bookwords1 = total(new String[] {"bla", "book1"}, currentChannel, ID, subject, "total");
			int bookwords2 = total(new String[] {"bla", "book2"}, currentChannel, ID, subject, "total");
			EmbedBuilder eb = new EmbedBuilder();
			eb.setAuthor("Writing Stats");
			eb.addField(bookname1, Integer.toString(bookwords1));
			eb.addField(bookname2, Integer.toString(bookwords2));
			eb.addField("Breakstats", breakstats);
		}

		else
		{currentChannel.sendMessage("Use wc.checkstats <month> to check your own stats");}
	}

	else {
		Boolean breakexists = true;
		String breaks[] = prefs.get(author.getIdAsString() + "breakstats", "").split(",");
		String breakstats = "";

		if (breaks.length==1&&breaks[0].isEmpty())
			breakexists = false;

		if (breakexists){
			for (int i=0; i< breaks.length; i++){
				System.out.println("break - " + breaks[i]);
				breakstats += breaks[i];
				breakstats += "; " + String.valueOf(Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(breaks[i].substring(7,17)), LocalDate.parse(breaks[i].substring(23,33))))) + " Days";
				breakstats += System.lineSeparator();
			}}
		else
		{breakstats = "No breaks taken, gg you hardworking whore";}

		String bookname1 = prefs.get(author.getIdAsString() + "book1 name", "No name set");
		String bookname2 = prefs.get(author.getIdAsString() + "book2 name", "No name set");
		int bookwords1 = total(new String[] {"bla", "book1"}, currentChannel, ID, author, "total");
		int bookwords2 = total(new String[] {"bla", "book2"}, currentChannel, ID, author, "total");
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Writing Stats");
		eb.addField(bookname1, "Words written - " + Integer.toString(bookwords1));
		eb.addField(bookname2, "Words written - " +  Integer.toString(bookwords2));
		eb.addField("Breakstats", breakstats);
		//System.out.println("EUGIWYDUX");
		author.sendMessage(eb);
	}
}

public static void checkMonthlyStats(String[] message, TextChannel currentChannel, long ID, User author) throws InterruptedException, ExecutionException {
	//wc.checkmonthlystats month member
	if (message.length>2) {
		if (Organization.checkPerms(author)) {
			boolean breakexists = true;
			User subject = Main.api.getUserById(message[2].substring(2, 21)).get();
			String[] breaks = prefs.get(subject.getIdAsString() + "breakstats", "").split(",");
			String breakstats = "";

			if (breaks.length==1&&breaks[0].isEmpty())
				breakexists = false;
			if (breakexists){
			for (int i=0; i< breaks.length; i++){
				breakstats += breaks[i];
				String[] toandfrom = breaks[i].split("-");
				breakstats += String.valueOf(Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(toandfrom[1]), LocalDate.parse(toandfrom[3])))) + " Days";
				breakstats += System.lineSeparator();
			}}
			else
			{breakstats = "No breaks taken, gg you hardworking whore";}

			String bookname1 = prefs.get(subject.getIdAsString() + "book1 name", "No name set");
			String bookname2 = prefs.get(subject.getIdAsString() + "book2 name", "No name set");
			int bookwords1 = total(new String[] {"bla", "book1"}, currentChannel, ID, subject, message[1]);
			int bookwords2 = total(new String[] {"bla", "book2"}, currentChannel, ID, subject, message[1]);
			EmbedBuilder eb = new EmbedBuilder();
			eb.setAuthor("Writing Stats");
			eb.addField(bookname1, Integer.toString(bookwords1));
			eb.addField(bookname2, Integer.toString(bookwords2));
			eb.addField("Breakstats", breakstats);
			if (bookwords1==-18||bookwords2==-18)
				{author.sendMessage("The month you entered couldn't be recognized or you don't have words for mentioned month. Try again with a valid month.");}
			else
				{author.sendMessage(eb);}
		}

		else
		{currentChannel.sendMessage("Use wc.checkmonthlystats <month> to check your own stats");}
	}

	else {
		Boolean breakexists = true;
		String breaks[] = prefs.get(author.getIdAsString() + "breakstats", "").split(",");
		String breakstats = "";

		if (breaks.length==1&&breaks[0].isEmpty())
			breakexists = false;

		if (breakexists){
		for (int i=0; i< breaks.length; i++){
			System.out.println("break - " + breaks[i]);
			breakstats += breaks[i];
			breakstats += "; " + String.valueOf(Math.abs(ChronoUnit.DAYS.between(LocalDate.parse(breaks[i].substring(7,17)), LocalDate.parse(breaks[i].substring(23,33))))) + " Days";
			breakstats += System.lineSeparator();
		}}
		else
		{breakstats = "No breaks taken, gg you hardworking whore";}

		String bookname1 = prefs.get(author.getIdAsString() + "book1 name", "No name set");
		String bookname2 = prefs.get(author.getIdAsString() + "book2 name", "No name set");
		int bookwords1 = total(new String[] {"bla", "book1"}, currentChannel, ID, author, message[1]);
		int bookwords2 = total(new String[] {"bla", "book2"}, currentChannel, ID, author, message[1]);
		EmbedBuilder eb = new EmbedBuilder();
		eb.setAuthor("Writing Stats");
		eb.addField(bookname1, "Words written - " + Integer.toString(bookwords1));
		eb.addField(bookname2, "Words written - " +  Integer.toString(bookwords2));
		eb.addField("Breakstats", breakstats);

		if (bookwords1==-18||bookwords2==-18)
			{author.sendMessage("The month you entered couldn't be recognized or you don't have words for mentioned month. Try again with a valid month.");}
		else
			{author.sendMessage(eb);}
	}
}

public static void resetBreakStats(String[] message, TextChannel currentChannel, long ID, User author) {
	//wc.resetBreak
	prefs.remove(author.getIdAsString() + "breakstats");
	}

public static String getMonth(int n) {
	String month = "";
	switch(n) {
	case 1:
		month.concat("January");
	case 2:
		month.concat("February");
	case 3:
		month.concat("March");
	case 4:
		month.concat("April");
	case 5:
		month.concat("May");
	case 6:
		month.concat("June");
	case 7:
		month.concat("July");
	case 8:
		month.concat("August");
	case 9:
		month.concat("September");
	case 10:
		month.concat("October");
	case 11:
		month.concat("November");
	case 12:
		month.concat("December");
	}
	return month;
}

}

