package me.hdcookie.games.TruthOrDare;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.sql.SQLException;
import java.util.List;

public class TruthOrDare {

    private TODManager todManager;
    private Database database;

    public TruthOrDare(TODManager todManager, Database database) {
        this.todManager = todManager;
        this.database = database;
    }

    public void newGame(Guild guild) throws InterruptedException, SQLException {
     /**
     This function is called on a command, it will get two random members from the server and assign them to the asker and answerer.
      It will then put that data into the database and send a message answerer to start off.
      */
        List<Member> members = todManager.getRandomMembers(guild);
        Member asker = members.get(0);
        Member answerer = members.get(1);

        if(!database.todExists(guild.getId())){
            database.addTruthOrDare(guild.getId());
        }

        database.setAnswererID(guild.getId(), answerer.getId());
        database.setAskerID(guild.getId(), asker.getId());

        todManager.dmAnswerer(answerer, asker, guild);

        //remove pins
        TextChannel channel = guild.getTextChannelById(database.getTruthOrDareID(guild.getId()));
        channel.retrievePinnedMessages().queue(messages -> {
            for (Message message : messages) {
                message.unpin().queue();
            }
        });

    }

    public void finishGame(Guild guild) throws SQLException {
        /**
         This function is called when the game is finished, it will use all the data to create an embed and send it to the Truth or Dare channel.
         */
        String guildID = guild.getId();
        String truthOrDare = database.getTruthOrDare(guildID);
        Member answerer = guild.getMemberById(database.getAnswererID(guildID));
        Member asker = guild.getMemberById(database.getAskerID(guildID));
        String question = database.getQuestion(guildID);

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("New "+truthOrDare+" game")
                .addField("Asker:", asker.getAsMention(), true)
                .addField("Answerer:", answerer.getAsMention(), true)
                .setColor(0x00ff00)
                .setDescription("Question is: "+question);

        System.out.println(asker.getUser().getName());
        System.out.println(answerer.getUser().getName());


        TextChannel channel = guild.getTextChannelById(database.getTruthOrDareID(guildID));

        channel.sendMessageEmbeds(embed.build())
                .addActionRow(Button.primary("new_game_"+guild.getId(), "New Game"))
                .queue(
                (message) -> {
                    message.pin().queue();
                }
        );

        channel.sendMessage(answerer.getAsMention()).queue();

    }



}
