package me.hdcookie.Games;

import me.hdcookie.Points.PointManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MakeASentance extends ListenerAdapter {

    private final GameSaver gameSaver;

    private final PointManager pointManager;

    public MakeASentance(GameSaver gameSaver, PointManager pointManager) {
        this.gameSaver = gameSaver;
        this.pointManager = pointManager;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getChannel().getId().equals("1039538869667889183")) {
            try {
                String message = event.getMessage().getContentRaw();
                String[] msgWords = message.split("\\b");

                char lastChar = message.charAt(message.length() - 1);
                boolean isPunctuation = lastChar == '.' || lastChar == '!' || lastChar == '?';

                EmbedBuilder embedBuilder = new EmbedBuilder()
                        .setTitle("Make a Sentance")
                        .setDescription("Final sentance: " + gameSaver.getSentance() + "sent in" + event.getJDA().getTextChannelById("1039538930497880095").getAsMention())
                        .setColor(0x00ff00);


                if (msgWords.length == 1) { //if the message is only one word, what we want
                    gameSaver.addWord(msgWords[0]);
                    event.getMessage().addReaction(Emoji.fromUnicode("U+2705")).queue();

                    if(isPunctuation){ //1 word and finishes with a punctuation mark
                        gameSaver.finishSentance(event.getJDA());

                        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    }


                } else {
                    if(msgWords.length == 2) {
                        gameSaver.addWord(msgWords[0]);
                        event.getMessage().addReaction(Emoji.fromUnicode("U+2705")).queue();


                        gameSaver.finishSentance(event.getJDA());
                        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
                    }




                    event.getMessage().addReaction(Emoji.fromUnicode("U+274C")).queue();

                    pointManager.removePoints(event.getMember().getId(), 1);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}