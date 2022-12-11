package me.hdcookie.Utilities;

import com.google.gson.Gson;
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.http.HttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Emoji extends ListenerAdapter {

    public class Item {
        private String slug;

        private String character;
        private String unicodeName;
        private String codePoint;
        private String group;
        private String subGroup;

        // Getters and setters for the above fields

        public String getCharacter() {
            return character;
        }
    }

    @Override
    public void onChannelCreate(ChannelCreateEvent event) {
        //split channel name from -, and get the longest word
        String[] channelName = event.getChannel().getName().split("-");
        String longestWord = "";
        for (String word : channelName) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }
        try {
            URI uri = URI.create("https://emoji-api.com/emojis?search=" + longestWord + "&access_key=4c638fbb3982c8a30bee355d7dd99b36395d8e6b");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            Gson gson = new Gson();

            Item[] items = gson.fromJson(response.toString(), Item[].class);

            if(items != null) {
                event.getChannel().asTextChannel().getManager().setName(items[0].getCharacter() + event.getChannel().getName()).queue();
            }

            event.getChannel().asTextChannel().getManager().setTopic("Welcome to the " + event.getChannel().getName() + " channel!").queue();



        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
