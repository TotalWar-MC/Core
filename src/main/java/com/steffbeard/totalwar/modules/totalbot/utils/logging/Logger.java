package com.steffbeard.totalwar.modules.totalbot.utils.logging;

import java.awt.Color;

import com.steffbeard.totalwar.modules.totalbot.utils.EmbedSender;
import com.steffbeard.totalwar.modules.totalbot.utils.SQL;
import com.steffbeard.totalwar.modules.totalbot.utils.Statics;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Logger {
    public static void logCommand(String command, GuildMessageReceivedEvent event){
        Guild guild = event.getGuild();
        String prefix = SQL.getValue(guild, "prefix");
        String logchannel = SQL.getValue(guild, "logchannel");
        if (SQL.getValue(guild, "logchannel").equals("0")) return;
        String us = event.getMember().getNickname();
        TextChannel channel = guild.getTextChannelById(Statics.LOG_CHANNEL);
        if(us == null) us = event.getAuthor().getName();
        EmbedSender.sendEmbed("[Command] `" + Statics.PREFIX +  command + "` was executed by **" + us + " (" + event.getAuthor().getName()+ "#" + event.getAuthor().getDiscriminator() + ")**", channel, Color.cyan);
    }
}