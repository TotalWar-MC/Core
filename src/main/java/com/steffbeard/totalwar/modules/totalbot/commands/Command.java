package com.steffbeard.totalwar.modules.totalbot.commands;

import java.io.IOException;
import java.text.ParseException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Command {
	
	boolean called(String[] args, GuildMessageReceivedEvent event);
	void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException;
	void executed(boolean success, GuildMessageReceivedEvent event);
	String help();
	String description();
	String commandType();
	int permission();

}
