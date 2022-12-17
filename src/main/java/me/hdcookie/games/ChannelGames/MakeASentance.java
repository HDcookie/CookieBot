package me.hdcookie.games.ChannelGames;

import me.hdcookie.games.GameManager;
import me.hdcookie.points.PointManager;
import me.hdcookie.database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;
import java.util.HashMap;

public class MakeASentance extends ListenerAdapter {

    private final GameManager gameSaver;

    private final PointManager pointManager;
    private final Database database;

    public MakeASentance(GameManager gameSaver, PointManager pointManager, Database database) {
        this.gameSaver = gameSaver;
        this.pointManager = pointManager;
        this.database = database;
    }
    private HashMap<String, String> lastMember = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if(event.getChannel().getType().equals(event.getChannelType().PRIVATE)) return;
        String id = event.getGuild().getId();

        if(event.getAuthor().isBot()) return;
        if(event.getChannelType().equals(event.getChannelType().PRIVATE)) return;
        try {
            if (event.getMessage().getChannel().getId().equals(database.getMakeSentenceID(event.getGuild().getId()))) {
                try {
                    String message = event.getMessage().getContentRaw();
                    String[] msgWords = message.split("\\b");

                    char lastChar = message.charAt(message.length() - 1);
                    boolean isPunctuation = lastChar == '.' || lastChar == '!' || lastChar == '?';

                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Make a Sentance")
                            .setDescription("Final sentance: " + gameSaver.getSentance(event) + " sent in " + event.getJDA().getTextChannelById(database.getFinishedSentencesID(event.getGuild().getId())).getAsMention())
                            .setColor(0x00ff00);


                    if (msgWords.length == 1) { //if the message is only one word, what we want
                        if(lastMember.get(id) == null || lastMember.get(id).equals("")){
                            lastMember.put(id, event.getMember().getId());  //runs only when the game starts
                        }else if(lastMember.get(id).equals(event.getMember().getId())){ //if the last person to send a message is the same as the current person
                            event.getMessage().delete().queue();
                            return;
                        }
                        //else, the message is valid and we can continue


                        gameSaver.addWord(msgWords[0], event);
                        lastMember.put(id, event.getMember().getId());
                        event.getMessage().addReaction(Emoji.fromUnicode("U+2705")).queue();

                        if(isPunctuation){ //1 word and finishes with a punctuation mark

                            gameSaver.finishSentance(event.getGuild());

                            event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                            lastMember.put(id, "");
                            return;
                        }
                        return;

                    } else {
                        if(msgWords.length == 2) {
                            if(isPunctuation) {
                                gameSaver.addWord(msgWords[0], event);
                                event.getMessage().addReaction(Emoji.fromUnicode("U+2705")).queue();


                                gameSaver.finishSentance(event.getGuild());
                                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                                return;
                            }
                        }


                        event.getMessage().addReaction(Emoji.fromUnicode("U+274C")).queue();

                        pointManager.removePoints(event.getMember().getId(), 1);
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
