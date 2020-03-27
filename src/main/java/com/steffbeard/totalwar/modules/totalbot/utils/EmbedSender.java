package com.steffbeard.totalwar.modules.totalbot.utils;

import java.awt.Color;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class EmbedSender {

    public static EmbedBuilder success() {
        return new EmbedBuilder().setColor(Color.green);
    }

    public static EmbedBuilder error() {
        return new EmbedBuilder().setColor(Color.red);
    }

    public static void sendEmbed(String content, MessageChannel channel, Color color){
        EmbedBuilder embed = new EmbedBuilder().setDescription(content).setColor(color);
        channel.sendMessage(embed.build()).complete();

    }

}