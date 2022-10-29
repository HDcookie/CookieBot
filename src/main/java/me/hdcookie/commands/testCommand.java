package me.hdcookie.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

public class testCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("test")){
            event.reply("Hello " + event.getUser().getAsMention())
                    .addActionRow(Button.primary("Button1", "Hello"))
                    .queue(); // reply immediately
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
