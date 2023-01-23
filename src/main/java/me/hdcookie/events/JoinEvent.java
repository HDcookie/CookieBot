package me.hdcookie.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        System.out.println("Member" + event.getMember().getUser().getName() + "joined");
        Member member = event.getMember();
        if(event.getUser().isBot()){return;}
        event.getGuild().addRoleToMember(member, event.getGuild().getRolesByName("Members", true).get(0)).queue();

        event.getGuild().getTextChannelById(963072016003522610L).sendMessage("**Welcome** "+member.getAsMention()+ ".  We're happy to have you.  Your the "
                + event.getGuild().getMembers().size() +"th member to join").queue();

    }

    //Make another method for when a member leaves

    @Override
    public void onGuildMemberRemove(GuildMemberRemoveEvent event){
        System.out.println("Member" + event.getMember().getUser().getName() + "joined");
        Member member = event.getMember();
        if(event.getUser().isBot()){return;}


        event.getGuild().getTextChannelById(963072016003522610L).sendMessage(member.getAsMention()+ ", " + member.getEffectiveName() + " has left us. ").queue();

    }

}
