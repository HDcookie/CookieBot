package me.hdcookie.commands.music;

import me.hdcookie.lavaplayer.PlayerManager;
import me.hdcookie.lavaplayer.RadioManager;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class PlayCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("play")) {
            final TextChannel channel = event.getChannel().asTextChannel();

            if(event.getOption("song") == null) {
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
            String link = String.join(" ", event.getOption("song").getAsString());
            if(!isUrl(link)) {
                link = "ytsearch:" + link;
            }

            PlayerManager.getInstance().loadAndPlay(channel, link);
            event.reply("Trying to play " + event.getOption("song").getAsString()).queue();

        }

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
