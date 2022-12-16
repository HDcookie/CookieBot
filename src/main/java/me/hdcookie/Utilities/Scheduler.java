package me.hdcookie.Utilities;

import me.hdcookie.database.Database;
import net.dv8tion.jda.api.JDA;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Scheduler {

    public void startQOTDScheduler(JDA jda, Database database) {
        //Start the scheduler for the QOTD

        System.out.println("Starting QOTD Scheduler");


        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Get the local date and time for 7am today
        LocalDateTime startTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(),
                9, 7, 0);

        // If the start time is in the past, add one day to the start time
        if (startTime.isBefore(now)) {
            startTime = startTime.plusDays(1);
        }

        // Calculate the initial delay in milliseconds
        long initialDelay = startTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis();

        // Create a ScheduledExecutorService
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        // Define the task to be executed
        Runnable task = () -> {
            //Loop through the database's settings table and get the channel id for the QOTD for all the servers
            try {
                HashMap<String, String> qotdChannels = database.getQOTDChannels();


                for (String serverId : qotdChannels.keySet()) {
                    String channelId = qotdChannels.get(serverId);
                    jda.getTextChannelById(channelId).sendMessage("QOTD").queue();
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        };

        // Schedule the task to run at 7am every day, starting from the calculated start time
        executor.scheduleAtFixedRate(task, initialDelay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);

        ;

    }
}
