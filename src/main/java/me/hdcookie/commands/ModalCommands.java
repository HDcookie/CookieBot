package me.hdcookie.commands;

import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

import static me.hdcookie.events.ModalsInteractions.getAppealModal;
import static me.hdcookie.events.ModalsInteractions.getApplyModal;

public class ModalCommands extends ApplicationCommand {
    @JDASlashCommand(
            name = "appeal",
            description = "make an appeal"
    )
    public void onSlashAppeal(GuildSlashEvent event) {
        event.replyModal(getAppealModal()).queue();
    }

    @JDASlashCommand(
            name = "apply",
            description = "apply for staff"
    )
    public void onSlashApply(GuildSlashEvent event) {
        event.replyModal(getApplyModal()).queue();
    }
}
