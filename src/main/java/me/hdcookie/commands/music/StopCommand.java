package me.hdcookie.commands.music;

import me.hdcookie.lavaplayer.GuildMusicManager;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.GuildManager;

public class StopCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("stop")) {

            final TextChannel channel = event.getChannel().asTextChannel();

            final Member self = event.getGuild().getSelfMember();
            final GuildVoiceState selfVoiceState = self.getVoiceState();

            if (!selfVoiceState.inAudioChannel()) {
                event.reply("I need to be in a voice channel to stop music stupid").queue();
                return;
            }

            final Member member = event.getMember();
            final GuildVoiceState memberVoiceState = member.getVoiceState();

            if (!memberVoiceState.inAudioChannel()) {
                event.reply("You need to be in a voice channel to use this command").queue();
                return;
            }

            if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
                event.reply("You need to be in the same voice channel as me to use this command").queue();
                return;
            }

            GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());

            musicManager.scheduler.player.stopTrack();
            musicManager.scheduler.queue.clear();

            //leaves the voice channel
            event.getGuild().getAudioManager().closeAudioConnection();

            event.reply("Stopped the music and cleared the queue").queue();
        }

    }

}
