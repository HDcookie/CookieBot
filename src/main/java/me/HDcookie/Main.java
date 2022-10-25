package me.HDcookie;

import me.HDcookie.Commands.appeal;
import me.HDcookie.Commands.apply;
import me.HDcookie.Commands.setMessage;
import me.HDcookie.Commands.testCommand;
import me.HDcookie.Events.joinEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        JDA api = JDABuilder.createDefault("OTc3OTc1NDE1MjI4NDk3OTYw.GPk-p1.4nncNbsaIDHi_pspMuwIGnwzxTEDewSyk_ERrA").build();

        api.getPresence().setPresence(Activity.playing("Discord"), true);

        api.updateCommands()
                .addCommands(Commands.slash("apply", "Apply for a role"))
                .addCommands(Commands.slash("appeal", "Appeal a punishment decision"))
                .addCommands(Commands.slash("sendmessage", "Send the message for roles"))
                .addCommands(Commands.slash("test", "test command"))
                .queue();


        api.addEventListener(new testCommand(), new setMessage(), new joinEvent(), new apply(), new appeal());
    }
}