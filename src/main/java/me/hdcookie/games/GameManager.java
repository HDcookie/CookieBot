package me.hdcookie.games;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class GameManager {

    public JSONObject object = new JSONObject();
    public File file = new File("game.txt");

    public JSONObject getObject() {
        return object;
    }

    private Database database;

    public GameManager(Database database) {
        this.database = database;
    }


    //Counting -----------------------------------------------------------------------------------------------
    public void count(MessageReceivedEvent event) throws IOException, SQLException {
        //get number from database, add 1 to it, then set that number to the database
        int number = database.getCounting(event.getGuild().getId());
        number++;
        database.setCounting(event.getGuild().getId(), number);
    }

    public void resetNumber(MessageReceivedEvent event) throws IOException, SQLException {
        //reset number from database to 1
        database.setCounting(event.getGuild().getId(), 1);
    }


    //MakeASentance -----------------------------------------------------------------------------------------------
    public void addWord(String word, MessageReceivedEvent event) throws IOException, SQLException {

        if (database.getCurrentSentence(event.getGuild().getId()).equals("")) {//if there is no sentence
            database.setCurrentSentence(event.getGuild().getId(), word);
        } else {//is sentance
            String sentence = database.getCurrentSentence(event.getGuild().getId());
            sentence = sentence + " " + word;
            database.setCurrentSentence(event.getGuild().getId(), sentence);
        }

    }

    public void finishSentance(Guild guild) throws IOException, SQLException {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("New Sentence in " + guild.getName())
                .setDescription(database.getCurrentSentence(guild.getId()))
                .setColor(0x00ff00);

        guild.getTextChannelById(database.getFinishedSentencesID(guild.getId())).sendMessageEmbeds(builder.build()).queue();

        database.setCurrentSentence(guild.getId(), "");

    }

    public String getSentance(MessageReceivedEvent event) throws SQLException {
        if (database.getCurrentSentence(event.getGuild().getId()) == "") {
            return "";
        } else {
            return database.getCurrentSentence(event.getGuild().getId());
        }
    }

    //GuessTheNumber -----------------------------------------------------------------------------------------------
    public void getNewGuessNumber(MessageReceivedEvent event) throws IOException, SQLException {
        int number = (int) (Math.random() * 100);
        database.setGuessing(event.getGuild().getId(), number);

    }

    public int getGuessNumber() {
        return Integer.parseInt(object.get("guessNumber").toString());
    }



}
