package me.hdcookie.games.ChannelGames;

import me.hdcookie.games.GameManager;
import me.hdcookie.points.PointManager;
import me.hdcookie.database.Database;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class GuessTheNumber extends ListenerAdapter {

    private final GameManager gameSaver;
    private final PointManager pointManager;
    private final Database database;
    public GuessTheNumber(GameManager gameSaver, PointManager pointManager, Database database) {
        this.gameSaver = gameSaver;
        this.pointManager = pointManager;
        this.database = database;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if(event.getChannelType().equals(event.getChannelType().PRIVATE)) return;
        try {
            if (event.getMessage().getChannel().getId().equals(database.getGuessingID(event.getGuild().getId()))) {
                try {
                    String message = event.getMessage().getContentRaw();
                    int guess;
                    try {
                        guess = Integer.parseInt(message);
                    } catch (NumberFormatException e) {
                        return;
                    }

                    int number = database.getGuessing(event.getGuild().getId());
                    if (guess == number) {
                        pointManager.addPoints(event.getAuthor().getId(), 1);
                        gameSaver.getNewGuessNumber(event);

                        event.getMessage().addReaction(Emoji.fromUnicode("U+2705")).queue();
                    } else if (guess > number) {
                        event.getMessage().addReaction(Emoji.fromUnicode("U+2B07")).queue();

                    } else if (guess < number) {
                        event.getMessage().addReaction(Emoji.fromUnicode("U+2B06")).queue();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
