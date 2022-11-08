package me.hdcookie.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class SetMessage extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("sendmessage")) {
            event.reply("sending message").setEphemeral(true).queue();
            event.getChannel().sendMessage("click here to fill out an application or a appeal")
                    .setActionRow(Button.primary("apply", "Click here to apply"), Button.primary("appeal", "Click here to appeal"))
                    .queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("Test1")) {
            event.reply("Added role1").setEphemeral(true).queue();
            //run code to add role1
        } else if (event.getComponentId().equals("Test2")) {
            event.reply("Added Role2").setEphemeral(true).queue();
            //run code to add role2
        }
    }

    //public void onModalInteraction(ModalInteractionEvent event)
}
