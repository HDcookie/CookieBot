package me.hdcookie.commands.music;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import me.hdcookie.lavaplayer.GuildMusicManager;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class StopCommand extends ApplicationCommand {
    @JDASlashCommand(
            name = "stop",
            description = "Stops the music and clears the queue"
    )
    public void onSlashStop(GuildSlashEvent event) {
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
