package me.hdcookie.commands.game;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import me.hdcookie.Main;
import me.hdcookie.database.Database;
import me.hdcookie.games.TruthOrDare.TODManager;
import me.hdcookie.games.TruthOrDare.TruthOrDare;

import java.sql.SQLException;

public class TODCommand extends ApplicationCommand {
    @JDASlashCommand(
            name = "truthordare",
            description = "Starts a new game of truth or dare"
    )
    public void onSlash(GuildSlashEvent event) {
        TODManager todManager = new TODManager();
        Database database = Main.getDatabase();

        try {
            if(database.getTruthOrDareID(event.getGuild().getId()).equals("0")){
                event.reply("You have to set a channel for the truth or dare game").queue();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



        TruthOrDare truthOrDare = new TruthOrDare(todManager, database);

        try {
            truthOrDare.newGame(event.getGuild());
        } catch (InterruptedException | SQLException e) {
            throw new RuntimeException(e);
        }

        event.reply("Started a new game of Truth or Dare!").queue();

    }
}
