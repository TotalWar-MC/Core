package com.steffbeard.totalwar.modules.totalbot.utils;

import java.awt.Color;
import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PermHandler {


    public static int getLvl(Member member) {
        if (member.getUser().getId().equals("153507094933274624"))
            return 4;
        if (member.equals(member.getGuild().getOwner()))
            return 3;
        if (member.getRoles().stream().anyMatch(role -> Arrays.stream(Statics.FULLPERMS).anyMatch(s1 -> role.getName().equals(s1)))) {
            return 2;
        } else if (member.getRoles().stream().anyMatch(role -> Arrays.stream(Statics.PERMS).anyMatch(s -> role.getName().equals(s)))) {
            return 1;
        }
        return 0;
    }

    public static boolean check(int required, GuildMessageReceivedEvent event) {
        if (required > getLvl(event.getMember())) {
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setDescription(
                    "Sorry but you need permission level `" + required + "` or above!\n(Your current permission level is `" + getLvl(event.getMember()) + "`)."
            ).build()).queue();
            return true;
        }
        return false;
    }

}