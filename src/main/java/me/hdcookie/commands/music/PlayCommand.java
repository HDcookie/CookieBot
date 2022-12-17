package me.hdcookie.commands.music;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.net.URI;
import java.net.URISyntaxException;

public class PlayCommand extends ApplicationCommand {
    @JDASlashCommand(
            name = "play",
            description = "Play a song"
    )
    public void onSlashPlay(GuildSlashEvent event, @AppOption(name = "song", description = "The song to play") String song) {
        final TextChannel channel = event.getChannel().asTextChannel();

        if(song == null) {
            event.reply("Please provide a song to play").queue();
            return;
        }


        final Member self = event.getGuild().getSelfMember();
        final GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()) {
            event.reply("I need to be in a voice channel to play music").queue();
            return;
        }

        final Member member = event.getMember();
        final GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel to use this command").queue();
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            event.reply("You need to be in the same voice channel as me to use this command").queue();
            return;
        }
        String link = String.join(" ", song);
        if(!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(channel, link);
        event.reply("Trying to play " + song).queue();

    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
