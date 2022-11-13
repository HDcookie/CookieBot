package me.hdcookie.commands.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.hdcookie.lavaplayer.GuildMusicManager;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("queue")) {
            TextChannel channel = event.getChannel().asTextChannel();
            GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

            if (queue.isEmpty()) {
                event.reply("The queue is empty").queue();
                return;
            }

            final int trackCount = Math.min(queue.size(), 10);
            final List<AudioTrack> trackList = new ArrayList<>(queue);

            AudioPlayer audioPlayer = musicManager.audioPlayer;

            EmbedBuilder embedBuilder = new EmbedBuilder()
                    .setTitle("Current Queue")
                    .setColor(0x22ff2a)
                    .addField("Now Playing: ", audioPlayer.getPlayingTrack().getInfo().title, false);

            if(trackList.size() > 1) {
                embedBuilder.addField("Coming Up Next: ", "", false);
            }


            for (int i = 0; i < trackCount; i++) {
                AudioTrack track = trackList.get(i);
                int number = i + 1;
                embedBuilder.addField("Number "+number+ ":"+ track.getInfo().title, " By " +track.getInfo().author+ " URL: <" + track.getInfo().uri +">", false);
            }

            if(trackList.size() > 10) {
                embedBuilder.appendDescription(String.format("And %s more...", trackList.size() - 10));
            }

            event.replyEmbeds(embedBuilder.build()).queue();

        }
    }

    private String formatTime(long duration) {
        long minutes = duration / 1000 / 60;
        long seconds = duration / 1000 % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

}
