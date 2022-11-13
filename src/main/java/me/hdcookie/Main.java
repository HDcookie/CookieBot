package me.hdcookie;

import kotlin.sequences.SequencesKt;
import me.hdcookie.Games.GameSaver;
import me.hdcookie.Games.GuessTheNumber;
import me.hdcookie.Games.MakeASentance;
import me.hdcookie.Points.PointCommands;
import me.hdcookie.Points.PointListener;
import me.hdcookie.Points.PointManager;
import me.hdcookie.commands.*;
import me.hdcookie.Games.Count;
import me.hdcookie.commands.music.*;
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

        Config config = new Config();
        Token token = new Token();
        GameSaver gameSaver = new GameSaver();
        PointManager pointManager = new PointManager();

        config.createFile();
        token.createFile();
        gameSaver.loadFile();
        pointManager.setUp();


        String tokenStr;
        if(Config.config.get("useTokenFile").equals("true")){
            tokenStr = token.getToken();
        } else {
            tokenStr = config.getToken();
        }

        JDA api = JDABuilder.createDefault(tokenStr).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        System.out.println("Connected to discord");

        api.getPresence().setPresence(Activity.playing("Discord"), true);

        api.updateCommands()
                .addCommands(Commands.slash("apply", "Apply for a role"))
                .addCommands(Commands.slash("appeal", "Appeal a punishment decision"))
                .addCommands(Commands.slash("sendmessage", "Send the message for roles")
                        .addOption(OptionType.STRING, "message", "The type or specific message to send", true))
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
                .addCommands(Commands.slash("points", "Get your points")
                        .addOption(OptionType.USER, "user", "The user you want to get the points of", false))
                .addCommands(Commands.slash("join", "Join your vc"))
                .addCommands(Commands.slash("play", "Play a song")
                        .addOption(OptionType.STRING, "song", "The song or url you want to play", true))
                .addCommands(Commands.slash("stop", "Stop the music"))
                .addCommands(Commands.slash("skip", "Skip the current song"))
                .addCommands(Commands.slash("nowplaying", "show what song is playing right now"))
                .addCommands(Commands.slash("queue", "show the current queue"))
                .queue();


        api.addEventListener(new TestCommand(),
                new SetMessage(),
                new JoinEvent(),
                new Apply(),
                new Appeal(),
                new Fact(),
                new Count(gameSaver, pointManager),
                new PointCommands(pointManager),
                new PointListener(pointManager),
                new MakeASentance(gameSaver, pointManager),
                new GuessTheNumber(gameSaver, pointManager),
                new JoinCommand(),
                new PlayCommand(),
                new StopCommand(),
                new SkipCommand(),
                new NowPlaying(),
                new QueueCommand()
        );
    }
}