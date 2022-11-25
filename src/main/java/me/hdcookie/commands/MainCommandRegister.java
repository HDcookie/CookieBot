package me.hdcookie.commands;

import me.hdcookie.commands.methods.Fact;
import me.hdcookie.commands.methods.SetChannel;
import me.hdcookie.commands.methods.SetupCommand;
import me.hdcookie.database.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class MainCommandRegister extends ListenerAdapter {
    private final Database database;

    public MainCommandRegister(Database database) {
        this.database = database;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        //Eventually check if server is allowed to run commands, ect

        try {
            switch (event.getName()) {
                case "help":
                    //run help command
                    break;
                case "ping":
                    //run ping command
                    break;
                case "setup":
                    SetupCommand setupCommand = new SetupCommand();
                    setupCommand.setupServer(event, database);
                    database.addGameData(event.getGuild().getId());

                    event.reply("Server setup complete").queue();
                    break;

                case "addgames":

                    event.reply("this command is disabled, use /setup now instead").queue();

                    break;
                case "fact":
                    Fact fact = new Fact();
                    fact.factCommand(event);
                    break;

                case "setchannel":
                    SetChannel setChannel = new SetChannel(database);
                    setChannel.setChannel(event);
                    break;


                default:
                    return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
