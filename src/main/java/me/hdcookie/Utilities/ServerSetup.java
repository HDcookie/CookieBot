package me.hdcookie.Utilities;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class ServerSetup extends ListenerAdapter {

    private Database database;

    public ServerSetup(Database database) {
        this.database = database;
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {

        System.out.println("Joined a new server: " + event.getGuild().getName());

        try {
            database.addGameData(event.getGuild().getId()); // add server to game table
            database.addServerSettings(event.getGuild().getId()); // add server to settings table
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




        event.getGuild().createCategory("Games").queue( category -> {

            category.createTextChannel("\uD83D\uDD22counting").queue(channel -> {
                channel.getManager().setTopic("Counting game").queue();
                channel.getManager().setSlowmode(5).queue();
                channel.getManager().setParent(category).queue();
                channel.sendMessage("Welcome to the counting game!").queue();

                try {
                    database.setCountingID(channel.getId(), event.getGuild().getId());
                    System.out.println("Counting channel set");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            });
            category.createTextChannel("❓guessing").queue(textChannel -> {
                textChannel.getManager().setTopic("Guessing game").queue();
                textChannel.getManager().setSlowmode(5).queue();
                textChannel.getManager().setParent(category).queue();
                textChannel.sendMessage("Guess a number between 1 and 100").queue();

                try {
                    database.setGuessingID(textChannel.getId(), event.getGuild().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            category.createTextChannel("\uD83D\uDCB0make-a-sentence").queue( textChannel -> {
                textChannel.getManager().setTopic("Make a sentence game").queue();
                textChannel.getManager().setSlowmode(5).queue();

                textChannel.getManager().setParent(category).queue();
                textChannel.sendMessage("Make a sentence game! Send 1 word to start, and end with a punctuation mark").queue();

                try {
                    database.setMakeSentenceID(textChannel.getId(), event.getGuild().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            category.createTextChannel("\uD83C\uDF6Afinished-sentences").queue( textChannel -> {
                textChannel.getManager().setTopic("Finished sentences game").queue();
                textChannel.getManager().setSlowmode(5).queue();
                textChannel.getManager().setParent(category).queue();
                textChannel.sendMessage("This is where the finished sentences will go!").queue();

                try {
                    database.setFinishedSentencesID(textChannel.getId(), event.getGuild().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
            category.createTextChannel("\uD83D\uDED1truth-or-dare").queue( textChannel -> {
                textChannel.getManager().setTopic("Truth or dare game").queue();
                textChannel.getManager().setSlowmode(5).queue();
                textChannel.getManager().setParent(category).queue();
                textChannel.sendMessage("Truth or dare game! Use /truthordare to start!").queue();

                try {
                    database.setTruthOrDareID(textChannel.getId(), event.getGuild().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });


        });


    }
}
