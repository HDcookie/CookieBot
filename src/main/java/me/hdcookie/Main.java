package me.hdcookie;

import com.freya02.botcommands.api.CommandsBuilder;
import me.hdcookie.Utilities.Emoji;
import me.hdcookie.Utilities.Scheduler;
import me.hdcookie.Utilities.ServerSetup;
import me.hdcookie.events.ModalsInteractions;
import me.hdcookie.commands.utilities.SetChannel;
import me.hdcookie.database.ConfigManager;
import me.hdcookie.games.*;
import me.hdcookie.games.ChannelGames.Count;
import me.hdcookie.games.ChannelGames.GuessTheNumber;
import me.hdcookie.games.ChannelGames.MakeASentance;
import me.hdcookie.games.TruthOrDare.TODListener;
import me.hdcookie.games.TruthOrDare.TODManager;
import me.hdcookie.points.PointManager;
import me.hdcookie.database.Database;
import me.hdcookie.events.JoinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main extends ListenerAdapter {

    private static ConfigManager configManager = new ConfigManager();
    private static Database database = new Database(configManager);


    public static void main(String[] args) throws Exception {
        System.out.println("Starting discord bot");
        System.out.println(" don't forget to remove the config class, because we're using the config manager now");


        Token token = new Token();
        GameManager gameSaver = new GameManager(database);
        PointManager pointManager = new PointManager();
        //RadioManager radioManager = new RadioManager(database);
        Scheduler scheduler = new Scheduler();
        SetChannel setchannel = new SetChannel();



        configManager.loadConfig("config.json");
        database.connect();


        token.createFile();
        pointManager.setUp();
        database.connect();


        // get token from file
        String tokenStr = token.getToken();


        //bot setup
        JDA jda = JDABuilder.createDefault(tokenStr)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .build();
        System.out.println("Connected to discord");

        jda.awaitReady();
        // Everything below this line is executed after JDA is ready ---------------------------------------------
        scheduler.startQOTDScheduler(jda, database);

        // setup commands
        final CommandsBuilder commandsBuilder = CommandsBuilder.newBuilder(823700732904996904L)
                .textCommandBuilder(textCommandsBuilder -> textCommandsBuilder.addPrefix("!"));
        commandsBuilder.build(jda, "me.hdcookie.commands");



        //make presence
        jda.getPresence().setPresence(Activity.playing("Connected to discord"), true);

        //add event listeners
        jda.addEventListener(new ModalsInteractions(), new JoinEvent(),
                new Count(gameSaver, pointManager, database),
                new MakeASentance(gameSaver, pointManager, database), new GuessTheNumber(gameSaver, pointManager, database),
                new TODListener(database, new TODManager()),
                new ServerSetup(database), new Emoji()
                );

    }
//

    public static Database getDatabase() {
        return database;
    }

    // add getter for config manager
    public static ConfigManager getConfigManager() {
        return configManager;
    }
}