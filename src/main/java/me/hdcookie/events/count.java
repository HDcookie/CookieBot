package me.hdcookie.events;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class count extends ListenerAdapter {

    public int nextNumber = 1;
    public Member lastMember = null;

    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getChannel().getId().equals("1035337062984986765")) {
            if (event.getAuthor().isBot()) {
                return;
            }
            //Start  counting game

            //Check if message is right number
            if (event.getMessage().getContentRaw().equals(String.valueOf(nextNumber))) {

                if(nextNumber == 1){
                    //Sets lastMember
                    lastMember = event.getMember();
                }else if(lastMember.equals(event.getMember())){
                    //Runs when someone counts twice in a row
                    event.getChannel().sendMessage("You can't count twice in a row.  Next number is 1" +
                            "").queue();
                    event.getMessage().addReaction(Emoji.fromUnicode("\u274C")).queue();
                    nextNumber = 1;

                    return;
                }

                //Runs when everything is right
                nextNumber++;
                event.getMessage().addReaction(Emoji.fromUnicode("\u2705")).queue();
                lastMember = event.getMember();
            } else {

                //Check if message is a number
                try {
                    Integer.parseInt(event.getMessage().getContentRaw());
                }catch (Exception e){
                    return;
                }

                //If message is number but not right number:
                event.getMessage().addReaction(Emoji.fromUnicode("\u274C")).queue();
                event.getChannel().sendMessage("Wrong number.  Next number is 1").queue();
                nextNumber = 1;
            }
        }
    }
}
