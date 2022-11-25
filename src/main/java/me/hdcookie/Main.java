package me.hdcookie;

import me.hdcookie.games.GameManager;
import me.hdcookie.games.GuessTheNumber;
import me.hdcookie.games.MakeASentance;
import me.hdcookie.points.PointCommands;
import me.hdcookie.points.PointListener;
import me.hdcookie.points.PointManager;
import me.hdcookie.commands.*;
import me.hdcookie.games.Count;
import me.hdcookie.commands.methods.Fact;
import me.hdcookie.commands.methods.SetChannel;
import me.hdcookie.commands.methods.SetMessage;
import me.hdcookie.commands.music.*;
import me.hdcookie.commands.other.TestCommand;
import me.hdcookie.database.Config;
import me.hdcookie.database.Database;
import me.hdcookie.events.JoinEvent;
import me.hdcookie.lavaplayer.RadioManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting discord bot");

        Database database = new Database();

        Config config = new Config();
        Token token = new Token();
        GameManager gameSaver = new GameManager(database);
        PointManager pointManager = new PointManager();
        RadioManager radioManager = new RadioManager(database);

        database.connect();

        config.createFile();
        token.createFile();
        pointManager.setUp();
        database.connect();






        //token setup
        String tokenStr = token.getToken();


        //bot setup
        JDA api = JDABuilder.createDefault(tokenStr).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();
        System.out.println("Connected to discord");

        //start radio after JDA successfully connects
        api.awaitReady();
        //radioManager.startRadio(api.getGuildById("977977923569070120"));



        //make presence
        api.getPresence().setPresence(Activity.playing("Connected to discord"), true);



        //register commands
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
                .addCommands(Commands.slash("setchannel", "Set the channel for specific activities channel")
                        .addOption(OptionType.STRING, "channel", "The channel you want to set as specific game channels", true)
                        .addOption(OptionType.CHANNEL, "destination", "The channel you want to set as specific game channels", false))
                .addCommands(Commands.slash("setup", "Setup the bot")
                        .addOption(OptionType.CHANNEL, "counting_channel", "The channel you want the Counting channel to be in", true)
                        .addOption(OptionType.CHANNEL, "guessing_channel", "The channel you want the Guessing channel to be in", true)
                        .addOption(OptionType.CHANNEL, "make_a_sentence_channel", "The channel you want the make a sentence channel to be in", true)
                        .addOption(OptionType.CHANNEL, "finished_sentences", "The channel you want the finished sentences channel to be in", true)
                        .addOption(OptionType.CHANNEL, "radio_channel", "The channel you want the Radio channel to be in", true))
                .queue();


        //register events
        api.addEventListener(new TestCommand(),
                new SetMessage(), new JoinEvent(),
                new Apply(), new Appeal(),
                new Fact(), new Count(gameSaver, pointManager, database),
                new PointCommands(pointManager), new PointListener(pointManager),
                new MakeASentance(gameSaver, pointManager, database), new GuessTheNumber(gameSaver, pointManager, database),
                new JoinCommand(), new PlayCommand(),
                new StopCommand(), new SkipCommand(),
                new NowPlaying(), new QueueCommand(),
                new SetChannel(database)
        );

        api.addEventListener(new MainCommandRegister(database));

    }
}