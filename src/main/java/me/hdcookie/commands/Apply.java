package me.hdcookie.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;

public class Apply extends ListenerAdapter {

    public Modal getApplyModal(){
        TextInput subject = TextInput.create("Subject", "What position are you applying for?", TextInputStyle.SHORT)
                .setPlaceholder("Subject of ticket").setMaxLength(20).build();
        TextInput description = TextInput.create("Description", "Why should you be accepted?", TextInputStyle.PARAGRAPH).build();
        TextInput age = TextInput.create("Age", "How old are you?", TextInputStyle.SHORT).build();
        Modal modal = Modal.create("Application", "Please fill out the form below")
                .addActionRows(ActionRow.of(subject), ActionRow.of(description), ActionRow.of(age)).build();
        return modal;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("apply")){
            Modal modal = getApplyModal();
            event.replyModal(modal).queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event){
        if(event.getComponentId().equals("apply")){
            Modal modal = getApplyModal();
            event.replyModal(modal).queue();
        }
    }


    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if (event.getModalId().equals("Application")) {
            event.reply("Thank you for your application").setEphemeral(true).queue();

            String subject = event.getValue("Subject").getAsString();
            String description = event.getValue("Description").getAsString();
            String age = event.getValue("Age").getAsString();

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("New Application from " + event.getUser().getAsTag());
            embed.addField("Subject", subject, false);
            embed.setDescription(description);
            embed.addField("Age", age, false);
            embed.setAuthor(event.getUser().getAsTag(), null, event.getUser().getAvatarUrl());


            event.getGuild().getTextChannelById(959575181628686356L).sendMessage("New application"+
                    event.getGuild().getRoleById(959575223315865603L).getAsMention()).queue();
            event.getGuild().getTextChannelById(959575181628686356L).sendMessageEmbeds(embed.build()).queue();
        }
    }
}
