package me.hdcookie.games;

import me.hdcookie.points.PointManager;
import me.hdcookie.database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.SQLException;

public class Count extends ListenerAdapter {


    public Member lastMember = null;
    public final GameManager gameSaver;
    public final PointManager pointManager;
    public final Database database;

    private EmbedBuilder countedTwice = new EmbedBuilder()
                                    .setTitle("You can't count twice in a row!")
                                    .setDescription("You have lost 2 points.  " + "Next number is 1")
                                    .setFooter("Counting game")
                                    .setColor(0xff0000);

    private Emoji wrong = Emoji.fromUnicode("\u274C");
    private Emoji correct = Emoji.fromUnicode("\u2705");



    public Count(GameManager gameSaver, PointManager pointManager, Database database) {
        this.gameSaver = gameSaver;
        this.pointManager = pointManager;
        this.database = database;

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event){

        try {
            if(event.getChannel().getId().equals(database.getCountingID(event.getGuild().getId()))) {

                try{
                    int nextNumber = database.getCounting(event.getGuild().getId());
                    if (event.getAuthor().isBot()) {
                        return;
                    }
                    //Start  counting game

                    //Check if message is right number
                    if (event.getMessage().getContentRaw().equals(String.valueOf(nextNumber))) {
                        if(nextNumber == 1 || lastMember == null){
                            lastMember = event.getMember();
                        }else if(lastMember.equals(event.getMember())){
                            //Runs when someone counts twice in a row

                            event.getChannel().sendMessageEmbeds(countedTwice.build()).queue();

                            event.getMessage().addReaction(wrong).queue();
                            gameSaver.resetNumber(event);
                            pointManager.removePoints(event.getMember().getId(), 2);

                            return;
                        }

                        //Runs when everything is right
                        gameSaver.count(event);
                        event.getMessage().addReaction(correct).queue();
                        lastMember = null;

                    } else {
                        //Check if message is a number
                        try {
                            Integer.parseInt(event.getMessage().getContentRaw());
                        }catch (NumberFormatException e){
                            //Message is not a number, ignore it
                            return;
                        }
                        //If message is number but not right number:
                        EmbedBuilder wrongNumber = new EmbedBuilder()
                                .setTitle("Incorrect number")
                                .setDescription("You need to count from the last number")
                                .addField("Last number", String.valueOf(nextNumber - 1), false)
                                .addField("Next number: ", "1", false)
                                .addField("You lost", "1 point", false)
                                .setFooter("Counting game")
                                .setColor(0xff0000);
                        event.getChannel().sendMessageEmbeds(wrongNumber.build()).queue();
                        event.getMessage().addReaction(wrong).queue();
                        gameSaver.resetNumber(event);
                        pointManager.removePoints(event.getMember().getId(), 1);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(database.getCountingID(event.getGuild().getId()).equals("0")){
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
