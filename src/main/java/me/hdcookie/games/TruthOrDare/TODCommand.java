package me.hdcookie.games.TruthOrDare;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

import java.sql.SQLException;

public class TODCommand extends ListenerAdapter {

    private final Database database;

    public TODCommand(Database database) {
        this.database = database;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("truthordare")){
            TODManager todManager = new TODManager();

            try {
                if(database.getTruthOrDareID(event.getGuild().getId()).equals("0")){
                    event.reply("You have to set a channel for the truth or dare game").queue();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



            TruthOrDare truthOrDare = new TruthOrDare(todManager, database);

            try {
                truthOrDare.newGame(event.getGuild());
            } catch (InterruptedException | SQLException e) {
                throw new RuntimeException(e);
            }

            event.reply("Started a new game of Truth or Dare!").queue();





        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if(event.getComponentId().equals("Button1")){
            System.out.println("test");

            TextInput subject = TextInput.create("Subject", "What position are you applying for?", TextInputStyle.SHORT)
                    .setPlaceholder("Subject of ticket")
                    .setMaxLength(20)
                    .build();

            TextInput description = TextInput.create("Description", "Why should you be accepted?", TextInputStyle.PARAGRAPH).build();

            TextInput age = TextInput.create("Age", "How old are you?", TextInputStyle.SHORT).build();

            Modal modal = Modal.create("Application", "Please fill out the form below")
                    .addActionRows(ActionRow.of(subject), ActionRow.of(description), ActionRow.of(age))
                    .build();

            event.replyModal(modal).queue();

        }
    }

}
