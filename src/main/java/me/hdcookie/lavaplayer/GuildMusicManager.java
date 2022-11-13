package me.hdcookie.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {
    public final AudioPlayer audioPlayer;
    public final TrackScheduler scheduler;
    private final AudioPlayerSentHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new AudioPlayerSentHandler(this.audioPlayer);

    }

    public AudioPlayerSentHandler getSendHandler() {
        return sendHandler;
    }
}
