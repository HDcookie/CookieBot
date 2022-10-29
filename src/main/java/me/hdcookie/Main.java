package me.hdcookie;

import me.hdcookie.commands.*;
import me.hdcookie.events.count;
import me.hdcookie.events.joinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting discord bot");

        token token = new token();
        gameSaver gameSaver = new gameSaver();

        token.createFile();
        gameSaver.loadFile();

        JDA api = JDABuilder.createDefault(token.getToken()).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        System.out.println("Connected to discord");

        api.getPresence().setPresence(Activity.playing("Discord"), true);

        api.updateCommands()
                .addCommands(Commands.slash("apply", "Apply for a role"))
                .addCommands(Commands.slash("appeal", "Appeal a punishment decision"))
                .addCommands(Commands.slash("sendmessage", "Send the message for roles"))
                .addCommands(Commands.slash("test", "test command"))
                .addCommands(Commands.slash("fact", "Get a random fact"))
                .addCommands(Commands.slash("ping", "check the bots ping"))
                .queue();


        api.addEventListener(new testCommand(), new setMessage(), new joinEvent(), new apply(), new appeal(), new fact(), new count());
    }
}