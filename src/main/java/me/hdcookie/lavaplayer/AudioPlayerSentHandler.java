package me.hdcookie.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;

import java.nio.ByteBuffer;

public class AudioPlayerSentHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    public AudioPlayerSentHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    @Override
    public boolean canProvide() {
        return this.audioPlayer.provide(this.frame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return this.buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}