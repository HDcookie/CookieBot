package me.hdcookie.Games;

import me.hdcookie.Config;
import me.hdcookie.Games.GameSaver;
import me.hdcookie.Points.PointManager;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class GuessTheNumber extends ListenerAdapter {

    private final GameSaver gameSaver;
    private final PointManager pointManager;
    public GuessTheNumber(GameSaver gameSaver, PointManager pointManager) {
        this.gameSaver = gameSaver;
        this.pointManager = pointManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getChannel().getId().equals(Config.config.get("guessTheNumberID"))) {
            try {
                String message = event.getMessage().getContentRaw();
                int guess;
                try {
                    guess = Integer.parseInt(message);
                } catch (NumberFormatException e) {
                    return;
                }

                int number = gameSaver.getGuessNumber();
                if (guess == number) {
                    pointManager.addPoints(event.getAuthor().getId(), 1);
                    gameSaver.getNewGuessNumber();

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
    }

}
