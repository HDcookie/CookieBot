package me.hdcookie.commands.methods;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class SetChannel extends ListenerAdapter {

    private Database database;

    public SetChannel(Database database) {
        this.database = database;
    }


    public void setChannel(SlashCommandInteractionEvent event) {
        if (event.getName().equals("setchannel")) {
            //String channelType = event.getOption("channel_type").getAsString();


            try {
                switch (event.getOption("channel").getAsString()){
                    case "counting":
                        database.setCountingID(event.getChannel().getId(), event.getGuild().getId());
                        event.reply("Channel set to " + event.getOption("channel").getAsString()).queue();
                        break;
                    case "radio":
                        database.setRadio(event.getChannel().getId(), event.getGuild().getId());
                        event.reply("Channel set to " + event.getOption("channel").getAsString()).queue();
                        break;
                    case "guessing":
                        database.setGuessingID(event.getChannel().getId(), event.getGuild().getId());
                        event.reply("Channel set to " + event.getOption("channel").getAsString()).queue();
                        break;
                    case "make_a_sentence":
                        database.setMakeSentenceID(event.getChannel().getId(), event.getGuild().getId());
                        event.reply("Channel set to " + event.getOption("channel").getAsString()).queue();
                        break;
                    case "finished_sentences":
                        database.setFinishedSentencesID(event.getChannel().getId(), event.getGuild().getId());
                        event.reply("Channel set to " + event.getOption("channel").getAsString()).queue();
                        break;
                    default:
                        event.reply("Type of channel not found, suitable options are:").queue();
                        event.getChannel().sendMessage("counting, radio, guessing, make_a_sentence, finished_sentences").queue();

                        break;
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
