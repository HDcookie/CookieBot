/*
stole a bit of code from
https://github.com/zp4rker/zlevels/blob/master/src/main/java/me/zp4rker/zlevels/lstnr/MessageSendListener.java
credit where credit's due
 */
package me.hdcookie.Points;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PointListener extends ListenerAdapter {

    public final PointManager pointManager;

    public PointListener(PointManager pointManager) {
        this.pointManager = pointManager;
    }

    private static final List<String> spamFilter = new ArrayList<>();


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        //Adds 1 point to the user who sent the message with a 1/10 chance and a 1-minute cool down

        try {
            if (event.getAuthor().isBot()) return;

            if (!spamFilter.contains(event.getAuthor().getId())) {

                pointManager.addPoints(event.getAuthor().getId(), 1);

                spamFilter.add(event.getAuthor().getId());
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        spamFilter.remove(event.getAuthor().getId());
                    }
                }, 1000 * 60 * 5);


            } else if (spamFilter.contains(event.getAuthor().getId())) {
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //Test
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event){
        if(event.getMember().getUser().isBot()) return;
        if(event.getChannelType() == ChannelType.PRIVATE) return;
        if(event.getChannelType() == ChannelType.GUILD_PUBLIC_THREAD){
            ThreadChannel thread = (ThreadChannel) event.getChannel();
            if(thread.getParentChannel().getId().equals("1019711489252261938")){

                try {
                    System.out.println("worked");
                    pointManager.addPoints(event.getMember().getId(), 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

}
