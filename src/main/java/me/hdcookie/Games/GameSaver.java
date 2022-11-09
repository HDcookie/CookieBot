package me.hdcookie.Games;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameSaver {

    public JSONObject object = new JSONObject();
    public File file = new File("game.txt");

    public JSONObject getObject() {
        return object;
    }

    public void loadFile() throws IOException {
        if(file.createNewFile()) {
            System.out.println("Game file does not exist, creating new file");
            object.put("counting", "1");

            saveFile();

        } else {
            System.out.println("loading games to previous state");
        }
        //at this point file exists
        //loading  file
        Scanner scanner = new Scanner(file);
        String data = scanner.nextLine();
        object = (JSONObject) JSONValue.parse(data);

        System.out.println("Game data" + object.toJSONString());

    }
//Counting -----------------------------------------------------------------------------------------------
    public void count() throws IOException {

        int number = Integer.parseInt(object.get("counting").toString());
        number++;
        object.put("counting", String.valueOf(number));
        saveFile();
    }

    public void resetNumber() throws IOException {
        object.put("counting", "1");
        saveFile();
    }

    public int getNumber() {
        return Integer.parseInt(object.get("counting").toString());
    }

//MakeASentance -----------------------------------------------------------------------------------------------
    public void addWord(String word) throws IOException {
        if(object.get("sentence") == null) {
            object.put("sentence", word);
        } else {
            String sentence = object.get("sentence").toString();
            sentence = sentence + " " + word;
            object.put("sentence", sentence);
        }
        saveFile();
    }

    public void finishSentance(JDA jda) throws IOException {

        EmbedBuilder builder = new EmbedBuilder()
                .setTitle("New Sentence in " + jda.getGuildById("959573676892774461").getName())
                .setDescription(object.get("sentence").toString())
                .setColor(0x00ff00);

        jda.getTextChannelById(1039538930497880095L).sendMessageEmbeds(builder.build()).queue();

        object.put("sentence", null);
        saveFile();
    }

    public void saveFile() throws IOException {
        FileWriter fileWriter = new FileWriter("game.txt");
        fileWriter.write(object.toJSONString());
        fileWriter.close();
    }

    public String getSentance() {
        if(object.get("sentence") == null) {
            return "";
        } else {
            return object.get("sentence").toString();
        }
    }
}
