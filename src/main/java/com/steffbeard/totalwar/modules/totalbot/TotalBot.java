package com.steffbeard.totalwar.modules.totalbot;

import java.util.HashMap;

import com.steffbeard.totalwar.modules.totalbot.commands.Command;
import com.steffbeard.totalwar.modules.totalbot.listener.NewMemberListener;
import com.steffbeard.totalwar.modules.totalbot.moderation.Clear;
import com.steffbeard.totalwar.modules.totalbot.utils.CommandParser;
import com.steffbeard.totalwar.modules.totalbot.utils.SQL;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class TotalBot {
	
	public static JDA jda;
	
    public static HashMap<String, Command> commands = new HashMap<>();
    public static final CommandParser parser = new CommandParser();
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		jda = new JDABuilder(AccountType.BOT)
				.setToken("NTk5MjY0OTgxOTM4MDc3NzA2.XnY9oA.FSDo2xfxymhnLVJ3QBjeA4vlaUE")
				.setStatus(OnlineStatus.ONLINE)
				.build();
		
		initializeListeners();
        initializeCommands();
		SQL.connect();

		System.out.println("> TotalBot is online!");
	}
	
	private static void initializeCommands() {
		commands.put("clear", new Clear());
	}
	
	private static void initializeListeners() {
		jda.addEventListener(new NewMemberListener());
	}

}
