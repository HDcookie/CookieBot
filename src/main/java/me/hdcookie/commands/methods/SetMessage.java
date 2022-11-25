package me.hdcookie.commands.methods;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class SetMessage extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("sendmessage")) {
            if(event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {

                String option = event.getOption("message").getAsString();
                if(option.equals("modals")) {

                    EmbedBuilder modals = new EmbedBuilder()
                            .setTitle("Applications and Appeals")
                            .setDescription("Click the buttons below to open the application or appeal form.  \n" +
                                    "If you have any questions, please contact a staff member.")
                            .setColor(0x4E3BBB)
                            .setThumbnail("https://cdn.discordapp.com/attachments/959575223315865600/959575226100469760/unknown.png");


                    event.reply("sending message").setEphemeral(true).queue();
                    event.getChannel().sendMessage("")
                            .addEmbeds(modals.build())
                            .setActionRow(Button.primary("apply", "Click here to apply"), Button.primary("appeal", "Click here to appeal"))
                            .queue();
                }else if(option.equals("rules")) {
                    event.reply("sending message").setEphemeral(true).queue();

                    EmbedBuilder rules = new EmbedBuilder()
                            .setTitle("**Discord Rules**")
                            .setDescription("1. No swearing. Anything you wouldn't say in front of your mother qualifies.\n" +
                                    "2. Keep private information private. A lot of us know each other's names irl, but if that person is not comfortable with you using it in public chat then don't use it.  No leaking faces, addresses, or any other personal information.  \n" +
                                    "3. No spamming, advertising outside specific channels, or abusing bots.\n" +
                                    "4. Moderators have the right to change rules for each situation.\n" +
                                    "5. Strive to be kind and helpful to everyone.  While making fun is one thing, sometimes those can go too far and it makes the server toxic and unappealing.  Moderators can decide when you're taking it too far and tell you to stop or punish you.  \n" +
                                    "6. No advertising, outside of specific channels, or if the conversation ends up there\n" +
                                    "\n" +
                                    "Moderators will give you a verbal warning, if you continue, you will receive a warning with @Dyno#3861.  Everyone has three warnings, and once you lose them all you will be muted for a day.  After that you will be muted indefinitely and would have to appeal, appeal link is below.  ")
                            .setFooter("We reserve the right to mute, kick and ban members in violation of these rules.  If you have any questions, please contact a moderator or admin.  ")
                            .setColor(0x4E3BBB)
                            .setThumbnail("https://cdn.discordapp.com/emojis/969980591825100800.webp?size=160&quality=lossless");

                    event.getChannel().sendMessageEmbeds(rules.build()).queue();



                }else if(option.equals("guide")){
                    event.reply("sending message").setEphemeral(true).queue();

                    EmbedBuilder guide = new EmbedBuilder()
                            .setTitle("**Server Channel and Roles Guide**")
                            .setDescription("> **Server Channels**\n" +
                                    "<#959573677375123491> Main chat where you can talk about anything.\n" +
                                    "<#962689210790342656> Post your memes here for a chance to win a prize. \n" +
                                    "<#961379158972305428> Keep bot commands/spam in here. \n" +
                                    "<#999724604962639994> Count upwards and reach the highest number.  \n" +
                                    "<#971063139267403796> Keep up to date with all the event announcments\n" +
                                    "<#1039977596944916540> Introduce yourself here, however, this is totally optional.  \n" +
                                    "<#961379158972305428> Keep bot commands/spam in here. \n" +
                                    "<#961384439181635594> A custom channel where you can send messages here by reacting with :star:\n" +
                                    "<#1039977396096479385> A place to advertise live streams, personal stores, or anything that is not a discord server. (to advertise a discord discuss with a staff member)\n" +
                                    "\n" +
                                    "> **Forms** \n" +
                                    "<#1019711489252261938> is a place to start a specific conversation, showcase an achievement or accomplishment, or make a suggest, advertise, or make a introduction.  \n" +
                                    "\n" +
                                    "> Townsquare \n" +
                                    "<#1039973092853813269> Is a place for all the channels that aren't frequently used.  Click on the channel and you'll see all the sub channels that are available.  They include <#1039977596944916540>, <#1039977396096479385>, <#1039977061885939712>, and <#1039973627002622034>\n" +
                                    "\n" +
                                    "> **Server Roles**\n" +
                                    "<@&959576564675252243> Owner of the server\n" +
                                    "<@&959574594757468170> Helps run the server and does things when the Owner is not there\n" +
                                    "Moderator Makes sure the chat is running smoothly\n" +
                                    "<@&971918313682780240> Has all the permissions of Admin, but is called jr admin so I can make fun of ppl\n" +
                                    "<@&959575223315865603> Moderators of the server, helps keep it running\n" +
                                    "<@&971768621560918066> Managers of QOTD and events\n")
                            .setFooter("We reserve the right to mute, kick and ban members in violation of these rules.  If you have any questions, please contact a moderator or admin.  ")
                            .setColor(0x4E3BBB)
                            .setThumbnail("https://cdn.discordapp.com/emojis/969980591825100800.webp?size=160&quality=lossless");

                    event.getChannel().sendMessageEmbeds(guide.build()).queue();

                } else if(option.equals("other")) {
                    event.reply("sending message").setEphemeral(true).queue();

                    EmbedBuilder appeal = new EmbedBuilder()
                            .setTitle("**Other Info**")
                            .setDescription("**Welcome to People's Utopia.** Here's some quick info to get you started\n")
                            .addField("Server Invite", "https://discord.gg/5PEpz8EN", false)
                            .addField("placeholder", "placeholder", false)
                            .setFooter("We reserve the right to mute, kick and ban members in violation of these rules.  If you have any questions, please contact a moderator or admin.  ")
                            .setColor(0x4E3BBB)
                            .setThumbnail("https://cdn.discordapp.com/emojis/969980591825100800.webp?size=160&quality=lossless");

                    event.getChannel().sendMessageEmbeds(appeal.build()).queue();

                }else {
                    event.reply("Invalid option").setEphemeral(true).queue();
                }

            }else {
                event.reply("You do not have permission to use this command").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("Test1")) {
            event.reply("Added role1").setEphemeral(true).queue();
            //run code to add role1
        } else if (event.getComponentId().equals("Test2")) {
            event.reply("Added Role2").setEphemeral(true).queue();
            //run code to add role2
        }
    }

    //public void onModalInteraction(ModalInteractionEvent event)
}
