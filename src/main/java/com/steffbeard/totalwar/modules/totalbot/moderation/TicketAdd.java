package com.steffbeard.totalwar.modules.totalbot.moderation;

import java.io.IOException;
import java.text.ParseException;

import com.steffbeard.totalwar.modules.totalbot.commands.Command;
import com.steffbeard.totalwar.modules.totalbot.utils.CalendarUtils;
import com.steffbeard.totalwar.modules.totalbot.utils.EmbedSender;
import com.steffbeard.totalwar.modules.totalbot.utils.PermHandler;
import com.steffbeard.totalwar.modules.totalbot.utils.Statics;
import com.steffbeard.totalwar.modules.totalbot.utils.logging.Logger;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class TicketAdd implements Command {

	@Override
	public boolean called(String[] args, GuildMessageReceivedEvent event) {
		return false;
	}

	public void action(GuildMessageReceivedEvent event, Message msg, String s) throws ParseException, IOException {
		Guild guild = msg.getGuild();
	    TextChannel tc = msg.getTextChannel();
		event.getMessage().delete().queue();
        if(PermHandler.check(2, event)) return;
        String[] args = s.split(" ");
        if(args.length <= 1){
        	EmbedSender.error().setDescription(help()).setTitle("ERROR!").build();
            return;
        }
	}

	@Override
	public void executed(boolean success, GuildMessageReceivedEvent event) {
		 Logger.logCommand("ticket add", event);
		 System.out.println(CalendarUtils.getCurrentSystemTime() + " [Info] [Commands]: Command " + Statics.PREFIX + "ticket add was executed by '" + event.getAuthor().getName() + "' (" + event.getGuild().getName() + ") in (" + event.getChannel().getId() + ") ");	
	}

	@Override
	public String help() {
		return "USAGE: `member <memberID>` Adds a member to the ticket, `role <roleID>` Adds a role to the ticket";
	}

	@Override
	public String description() {
		return  "Adds a user or role to a ticket.\n" +
                "Can only be run in a ticket!\n";
	}

	@Override
	public String commandType() {
		return Statics.CMDTYPE.moderation;
	}

	@Override
	public int permission() {
		return 2;
	}
	
}
