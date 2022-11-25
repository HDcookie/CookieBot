package me.hdcookie.points;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class PointCommands extends ListenerAdapter {

    public final PointManager pointManager;

    public PointCommands(PointManager pointManager) {
        this.pointManager = pointManager;
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        try {
            if (event.getName().equals("getpoints") || event.getName().equals("points")) {
                /* Command to get points */
                if(event.getOption("user") != null) {
                    Member member = event.getOption("user").getAsMember();
                    event.reply("User " + member.getEffectiveName() + " has " + pointManager.getPoints(member.getId()) + " points.").queue();
                } else {
                    event.reply("You have " + pointManager.getPoints(event.getMember().getId()) + " points.").queue();
                }

            } else if (event.getName().equals("addpoints")) {
                if(event.getMember().getPermissions().contains(Permission.MANAGE_SERVER)) {
                    /* Command to add points */

                    int points = Integer.parseInt(event.getOption("points").getAsString());
                    Member member = event.getOption("user").getAsMember();
                    pointManager.addPoints(member.getId(), points);
                    event.reply("You have added " + points + " points to " + member.getAsMention()).setEphemeral(false).queue();
                }else {
                    event.reply("You do not have permission to use this command").setEphemeral(true).queue();
                }
            } else if (event.getName().equals("removepoints")) {
                if(event.getMember().getPermissions().contains(Permission.MANAGE_SERVER)) {
                    /* Command to remove points */

                    int points = Integer.parseInt(event.getOption("points").getAsString());
                    Member member = event.getOption("user").getAsMember();
                    pointManager.removePoints(member.getId(), points);
                    event.reply("You have removed " + points + " points from " + member.getAsMention()).setEphemeral(false).queue();
                }else {
                    event.reply("You do not have permission to use this command").setEphemeral(true).queue();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}