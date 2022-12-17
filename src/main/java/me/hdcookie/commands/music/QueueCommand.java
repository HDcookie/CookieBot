package me.hdcookie.commands.music;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import me.hdcookie.lavaplayer.GuildMusicManager;
import me.hdcookie.lavaplayer.PlayerManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class QueueCommand extends ApplicationCommand {

    @JDASlashCommand(
            name = "queue",
            description = "Show the current queue"
    )
    public void onSlashQueue(GuildSlashEvent event) {
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

    private String formatTime(long duration) { // no clue what this does
        long minutes = duration / 1000 / 60;
        long seconds = duration / 1000 % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
