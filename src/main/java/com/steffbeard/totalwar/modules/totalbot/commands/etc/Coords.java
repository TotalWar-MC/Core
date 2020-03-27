package com.steffbeard.totalwar.modules.totalbot.commands.etc;

import java.io.IOException;
import java.text.ParseException;

import com.steffbeard.totalwar.modules.totalbot.commands.Command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Coords implements Command {
	
//	@Override
//    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
//		String[] message = e.getMessage().getContentRaw().split(" ");
//		if(message.length == 1 && message[0].equalsIgnoreCase(".coords")){
//			
//		}
//	}

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent event) {
		return false;
	}

	@Override
	public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String help() {
		return null;
	}

	@Override
	public String description() {
		return null;
	}

	@Override
	public String commandType() {
		return null;
	}

	@Override
	public int permission() {
		return 1;
	}
}
