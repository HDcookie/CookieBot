package me.hdcookie.lavaplayer;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.managers.AudioManager;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class RadioManager {

    private Database database;

    public RadioManager(Database database) {
        this.database = database;
    }

    public void startRadio(Guild guild) throws InterruptedException, SQLException {
        AudioManager audioManager = guild.getAudioManager();

        audioManager.openAudioConnection(guild.getStageChannelById(database.getRadioID(guild.getId())));

        guild.getJDA().awaitReady();

        Member self = guild.getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        TimeUnit.SECONDS.sleep(1);

        selfVoiceState.getChannel().asStageChannel().requestToSpeak().queue();

        PlayerManager.getInstance().loadAndPlay(guild.getTextChannelById("1042918022270177352"),
                "https://www.youtube.com/playlist?list=PLYlYW-C-Q3QcT7D2EaITjZnqYrv0lUYq2");

    }


}
