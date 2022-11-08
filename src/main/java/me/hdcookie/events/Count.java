package me.hdcookie.events;

import me.hdcookie.GameSaver;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class Count extends ListenerAdapter {


    public Member lastMember = null;
    public final GameSaver gameSaver;

    public Count(GameSaver gameSaver) {
        this.gameSaver = gameSaver;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getChannel().getId().equals("1035337062984986765")) {
            try{
                int nextNumber = gameSaver.getNumber();
                if (event.getAuthor().isBot()) {
                    return;
                }
                //Start  counting game

                //Check if message is right number
                if (event.getMessage().getContentRaw().equals(String.valueOf(nextNumber))) {
                    if(nextNumber == 1 || lastMember == null){
                        //Sets lastMember
                        lastMember = event.getMember();
                    }else if(lastMember.equals(event.getMember())){
                        //Runs when someone counts twice in a row
                        event.getChannel().sendMessage("You can't count twice in a row.  Next number is 1").queue();
                        event.getMessage().addReaction(Emoji.fromUnicode("\u274C")).queue();
                        gameSaver.resetNumber();
                        return;
                    }

                    //Runs when everything is right
                    gameSaver.count();
                    event.getMessage().addReaction(Emoji.fromUnicode("\u2705")).queue();
                    lastMember = event.getMember();

                } else {
                    //Check if message is a number
                    Integer.parseInt(event.getMessage().getContentRaw());

                    //If message is number but not right number:
                    EmbedBuilder wrongNumber = new EmbedBuilder()
                            .setTitle("Incorrect number")
                            .setDescription("You need to count from the last number")
                            .addField("Last number", String.valueOf(nextNumber - 1), false)
                            .addField("Next number: ", "1", false)
                            .setColor(0xff0000);
                    event.getChannel().sendMessageEmbeds(wrongNumber.build()).queue();
                    event.getMessage().addReaction(Emoji.fromUnicode("\u274C")).queue();
                    gameSaver.resetNumber();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
