package com.steffbeard.totalwar.modules.totalbot.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NewMemberListener extends ListenerAdapter {
	
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member member = event.getMember();
        Guild guild = event.getGuild();
        PrivateChannel pc = event.getMember().getUser().openPrivateChannel().complete();
        pc.sendMessage(
                "**Greetings,** " + event.getMember().getAsMention() + " and welcome on " + event.getGuild().getName() + " :wave:\n\n" +
                        "The server is currently under development and will release soon. ||If you are experienced in Java, JS, PHP, CSS, HTML or are good at building and would like to help develop the server, please contact zune#9223 on Discod saying you would like to help!||"
        ).queue();
	}

}
