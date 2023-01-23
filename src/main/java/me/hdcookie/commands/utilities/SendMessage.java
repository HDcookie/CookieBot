package me.hdcookie.commands.utilities;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import me.hdcookie.Utilities.Embeds;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class SendMessage extends ApplicationCommand {

    @JDASlashCommand(
            name = "sendmessage",
            description = "Sends a message to a channel"
    )
    public void onSlash(GuildSlashEvent event, @AppOption(name = "message", description = "The message to send") String message) {
        if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {

            int color = 0x4E3BBB;
            Embeds embeds = new Embeds();

            String option = event.getOption("message").getAsString();
            if(option.equals("modals")) {

                EmbedBuilder modals = embeds.getModals();


                event.reply("sending message").setEphemeral(true).queue();
                event.getChannel().sendMessage("")
                        .addEmbeds(modals.build())
                        .setActionRow(Button.primary("apply", "Click here to apply"), Button.primary("appeal", "Click here to appeal"))
                        .queue();
            }else if(option.equals("rules")) {
                event.reply("sending message").setEphemeral(true).queue();

                EmbedBuilder rules = embeds.getRules();

                event.getChannel().sendMessageEmbeds(rules.build()).queue();



            }else if(option.equals("guide")){
                event.reply("sending message").setEphemeral(true).queue();

                EmbedBuilder guide = embeds.getGuide();

                event.getChannel().sendMessageEmbeds(guide.build()).queue();

            } else if(option.equals("other")) {
                event.reply("sending message").setEphemeral(true).queue();

                EmbedBuilder appeal = embeds.getOtherInfo();

                event.getChannel().sendMessageEmbeds(appeal.build()).queue();

            }else if(option.equals("tod")){
                event.reply("sending message").setEphemeral(true).queue();
                EmbedBuilder tod = embeds.getTOD();

                event.getChannel().sendMessageEmbeds(tod.build())
                        .addActionRow(Button.primary("tod", "Get the Truth or Dare role"), Button.danger("removetod", "Remove the Truth or Dare role"))
                        .queue();





            }else {
                event.reply("Invalid option").setEphemeral(true).queue();
            }

        }else {
            event.reply("You do not have permission to use this command").setEphemeral(true).queue();
        }
    }

}
