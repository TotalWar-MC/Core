package com.steffbeard.totalwar.modules.totalbot.moderation;

import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.steffbeard.totalwar.modules.totalbot.commands.Command;
import com.steffbeard.totalwar.modules.totalbot.utils.CalendarUtils;
import com.steffbeard.totalwar.modules.totalbot.utils.EmbedSender;
import com.steffbeard.totalwar.modules.totalbot.utils.PermHandler;
import com.steffbeard.totalwar.modules.totalbot.utils.Statics;
import com.steffbeard.totalwar.modules.totalbot.utils.logging.Logger;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Clear implements Command {

	private int getInt(String string){
        try{
            return Integer.parseInt(string);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean called(String[] args, GuildMessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, GuildMessageReceivedEvent event) throws ParseException, IOException {
        event.getMessage().delete().queue();
        if(PermHandler.check(2, event)) return;
        if (args.length < 1) EmbedSender.error().setDescription(help()).setTitle("ERROR!").build();
        int numb = getInt(args[0]);
        if (numb > 1 && numb <= 100) {
            try{
                MessageHistory history = new MessageHistory(event.getChannel());
                List<Message> msgs;
                msgs = history.retrievePast(numb).complete();
                event.getChannel().deleteMessages(msgs).queue();
               Message msg = event.getChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.GREEN)
                        .setDescription("Deleted " + args[0] + " Messages!")
                        .build()
                ).complete();

               new Timer().schedule(new TimerTask() {
                   @Override
                   public void run() {
                       msg.delete().queue();
                   }
               }, 3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            EmbedSender.error().setDescription(help() + "Max. 100 Message and Min. 1 Message").setTitle("ERROR!").build();
        }
    }

    @Override
    public void executed(boolean success, GuildMessageReceivedEvent event) {
        Logger.logCommand("clear", event);
        System.out.println(CalendarUtils.getCurrentSystemTime() + " [Info] [Commands]: Command " + Statics.PREFIX + "clear was executed by '" + event.getAuthor().getName() + "' (" + event.getGuild().getName() + ") in (" + event.getChannel().getId() + ") ");
    }

    @Override
    public String help() {
        return "USAGE: " + Statics.PREFIX + "clear <AmountOfMessages>";
    }

    @Override
    public String description() {
        return "Clears the amount of messages you want.";
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