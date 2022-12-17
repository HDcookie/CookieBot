package me.hdcookie.commands.utilities;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.CommandScope;
import com.freya02.botcommands.api.application.annotations.AppOption;
import com.freya02.botcommands.api.application.slash.GlobalSlashEvent;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@CommandMarker
public class HelloWorld extends ApplicationCommand {
    @JDASlashCommand(
            scope = CommandScope.GUILD,
            name = "hello",
            description = "Says hello"
    )
    public void hello(GuildSlashEvent event, @AppOption(name = "name", description = "The name to say hello to") String name) {
        System.out.println("Hello " + name);


        event.replyFormat("Hello %s", name).queue();
    }
}
