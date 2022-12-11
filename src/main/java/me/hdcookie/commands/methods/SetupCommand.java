package me.hdcookie.commands.methods;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

import java.sql.SQLException;

public class SetupCommand {
    public void setupServer(SlashCommandInteractionEvent event, Database database) throws SQLException {
        System.out.println("Setting up server");


        Channel counting = event.getOption("counting_channel").getAsChannel();
        Channel guessing = event.getOption("guessing_channel").getAsChannel();
        Channel radio = event.getOption("radio_channel").getAsChannel();
        Channel makeSentance = event.getOption("make_a_sentence_channel").getAsChannel();
        Channel finishedSentences = event.getOption("finished_sentences").getAsChannel();

        database.addServerSettings(event.getGuild().getId());


    }


}
