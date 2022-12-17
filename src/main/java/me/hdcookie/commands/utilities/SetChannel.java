package me.hdcookie.commands.utilities;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import me.hdcookie.Main;
import me.hdcookie.database.Database;
import net.dv8tion.jda.api.entities.channel.Channel;

import java.sql.SQLException;

public class SetChannel extends ApplicationCommand {



    @JDASlashCommand(
            name = "setchannel",
            description = "Configures the channels the bot uses"
    )
    public void onSlash(GuildSlashEvent event,
                        @AppOption(name = "type", description = "The type of channel to set") String type
                        ) {

        System.out.println(" " + type);

        Database database = Main.getDatabase();

        try {
            switch (type){
                case "counting":
                    database.setCountingID(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "radio":
                    database.setRadio(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "guessing":
                    database.setGuessingID(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "make_a_sentence":
                    database.setMakeSentenceID(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "finished_sentences":
                    database.setFinishedSentencesID(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "tod":
                    database.setTruthOrDareID(event.getChannel().getId(), event.getGuild().getId());
                    event.reply("Channel set to " + type).queue();
                    break;
                case "qotd" :
                    database.setQOTDID(event.getGuild().getId(), event.getChannel().getId());
                    event.reply("Channel set to " + type).queue();
                    break;

                default:
                    event.reply("Type of channel not found, suitable options are:").queue();
                    event.getChannel().sendMessage("counting, radio, guessing, make_a_sentence, finished_sentences, QOTD").queue();

                    break;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
