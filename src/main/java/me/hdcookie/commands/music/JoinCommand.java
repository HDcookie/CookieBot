package me.hdcookie.commands.music;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand extends ApplicationCommand {
    @JDASlashCommand(
            name = "join",
            description = "Join a vc"
    )
    public void onSlash(GuildSlashEvent event) {
        TextChannel channel = event.getChannel().asTextChannel();
        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(selfVoiceState.inAudioChannel()) {
            event.reply("I'm already in a voice channel").queue();
            return;
        }

        Member member = event.getMember();
        GuildVoiceState voiceState = member.getVoiceState();

        if(!voiceState.inAudioChannel()) {
            event.reply("You need to be in a voice channel to use this command").queue();
        }

        AudioManager audioManager = event.getGuild().getAudioManager();
        AudioChannelUnion memberChannel = voiceState.getChannel();

        audioManager.openAudioConnection(memberChannel);
        event.reply("Joined " + memberChannel.getAsMention()).queue();



    }
}
