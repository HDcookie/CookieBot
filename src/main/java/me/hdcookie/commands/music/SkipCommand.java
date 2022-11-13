package me.hdcookie.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import me.hdcookie.lavaplayer.GuildMusicManager;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SkipCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("skip")) {
            event.reply("Skipped the song").queue();

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
            AudioPlayer audioPlayer = musicManager.audioPlayer;

            if(audioPlayer.getPlayingTrack() == null) {
                event.reply("There is no song playing").queue();
                return;
            }

            musicManager.scheduler.nextTrack();




        }
    }
}
