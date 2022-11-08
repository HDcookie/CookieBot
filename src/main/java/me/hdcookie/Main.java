package me.hdcookie;

import me.hdcookie.Points.PointCommands;
import me.hdcookie.Points.PointListener;
import me.hdcookie.Points.PointManager;
import me.hdcookie.commands.*;
import me.hdcookie.events.Count;
import me.hdcookie.events.JoinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting discord bot");

        Token token = new Token();
        GameSaver gameSaver = new GameSaver();
        PointManager pointManager = new PointManager();

        token.createFile();
        gameSaver.loadFile();
        pointManager.setUp();

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
                .addCommands(Commands.slash("getpoints", "Get your points")
                        .addOption(OptionType.USER, "user", "The user you want to get the points of", false))
                .addCommands(Commands.slash("addpoints", "Add points to an account")
                        .addOption(OptionType.USER, "user", "The user to add points to", true)
                        .addOption(OptionType.INTEGER, "points", "The amount of points to add", true))
                .addCommands(Commands.slash("removepoints", "Remove points from an account")
                        .addOption(OptionType.USER, "user", "The user to remove points from", true)
                        .addOption(OptionType.INTEGER, "points", "The amount of points to remove", true))
                .queue();


        api.addEventListener(new TestCommand(),
                new SetMessage(),
                new JoinEvent(),
                new Apply(),
                new Appeal(),
                new Fact(),
                new Count(gameSaver),
                new PointCommands(pointManager),
                new PointListener(pointManager) );
    }
}