package me.hdcookie.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        System.out.println("test");
        Member member = event.getMember();

        event.getGuild().getTextChannelById(963072016003522610L).sendMessage("**Welcome** "+member.getAsMention()+ ".  We're happy to have you.  Your the"
                + event.getGuild().getMembers().size() +"th member to join").queue();

    }

}
