package me.hdcookie.games.TruthOrDare;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.utils.concurrent.Task;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TODManager {


    public List<Member> getRandomMembers(Guild guild) throws InterruptedException {
        final List<Member> randomMembers = new ArrayList<>();
        //gets 2 random members from the server
        Random random = new Random();

        List<Member> members = guild.getMembersWithRoles(guild.getRolesByName("TOD", true));
        List<Member> members2 = guild.getMembersWithRoles(guild.getRolesByName("Truth or Dare", true));

        members.addAll(members2);

        List<Member> noBots = new ArrayList<>();

        //Removes bots
        for (Member member : members) {
            if (!member.getUser().isBot()) {
                noBots.add(member);
            }else {
                noBots.remove(member);
            }
        }

        randomMembers.add(noBots.get(random.nextInt(noBots.size())));
        randomMembers.add(noBots.get(random.nextInt(noBots.size())));

        //Checks for duplicates
        while (true){
            if (randomMembers.get(0) == randomMembers.get(1)){
                randomMembers.set(1, noBots.get(random.nextInt(noBots.size())));
            }else{
                break;
            }
        }

        for (Member member : randomMembers) {
            System.out.println(member.getUser().getName());
        }


        return randomMembers;
    }

    public void dmAnswerer(Member answerer, Member asker, Guild guild){
        EmbedBuilder answererEmbed = new EmbedBuilder()
                .setTitle("Truth or Dare")
                .setDescription("You have been selected to answer a question, please answer below")
                .setFooter("You have 30 minutes to answer")
                .addField("Asker", asker.getUser().getName(), false)
                .setColor(0x00ff00);

        answerer.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessageEmbeds(answererEmbed.build())
                    .addActionRow(Button.primary("truth_"+guild.getId(), "Truth"), Button.primary("dare_"+guild.getId(), "Dare"))
                    .queue();
        });
    }

    public void dmAsker(Guild guild, Database database) throws SQLException {
        Member asker = guild.getMemberById(database.getAskerID(guild.getId()));
        EmbedBuilder askerEmbed = new EmbedBuilder()
                .setTitle("Truth or Dare")
                .setDescription("You have been selected to ask a question, please ask below")
                .setFooter("You have 30 minutes to ask")
                .addField("Answerer", guild.getMemberById(database.getAnswererID(guild.getId())).getUser().getName(), false)
                .setColor(0x00ff00);

        asker.getUser().openPrivateChannel().queue((channel) -> {
            channel.sendMessageEmbeds(askerEmbed.build())
                    .addActionRow(Button.primary("Answer_"+guild.getId(), "Answer"))
                    .queue();
        });
    }

    public Modal getApplyModal(String truthOrDare, Guild guild){
        TextInput question = TextInput.create("question", "What is the question for the "+truthOrDare+"?", TextInputStyle.SHORT)
                .setPlaceholder("Subject of ticket").setMaxLength(200).build();
        Modal modal = Modal.create("question_"+guild.getId(), truthOrDare + ": Ask a question or give a dare")
                .addActionRows(ActionRow.of(question)).build();
        return modal;
    }


}
