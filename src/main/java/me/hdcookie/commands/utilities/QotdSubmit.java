package me.hdcookie.commands.utilities;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.Permission;

public class QotdSubmit extends ApplicationCommand {
    @JDASlashCommand(
            name = "submit",
            description = "Submit something for automated messages"
    )
    public void onSlashSubmit(GuildSlashEvent event, @AppOption(name = "message", description = "The message you want to submit") String message,
                              @AppOption(name = "type", description = "The type of submission.  ") String type) {

        if(!event.isFromGuild()) {
            event.reply("You cannot use this command in dms").queue();
            return;
        }
        if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("This command is currently only for admins.  If the beta has already started and you think this is a mistake, " +
                    "contact HDcookie").queue();
            return;
        }
        if(type.equalsIgnoreCase("qotd")) {
            event.reply("Your message has been submitted for approval").setEphemeral(true).queue();

            //send message in main channel
            event.getJDA().getGuildById("888868225088094259").getTextChannelById("1054132423564214412")
                    .sendMessage("New qotd submission: " + message).queue();

        }
        else {
            event.reply("Invalid type.  Currently only QOTD is supported").queue();
        }


    }
}
