package me.hdcookie.commands.music;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("join")) {
            TextChannel channel = event.getChannel().asTextChannel();
            Member self = event.getGuild().getSelfMember();
            GuildVoiceState selfVoiceState = self.getVoiceState();

            if(selfVoiceState.inAudioChannel()) {
                event.reply("I'm already in a voice channel").queue();
                return;
            }

            Member member = event.getMember();
            GuildVoiceState voiceState = member.getVoiceState();
            
            if(!voiceState.inAudioChannel()) {
                event.reply("You need to be in a voice channel to use this command").queue();
            }

            AudioManager audioManager = event.getGuild().getAudioManager();
            AudioChannelUnion memberChannel = voiceState.getChannel();

            audioManager.openAudioConnection(memberChannel);
            event.reply("Joined " + memberChannel.getAsMention()).queue();



        }
    }
}
