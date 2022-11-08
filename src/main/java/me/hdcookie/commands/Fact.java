package me.hdcookie.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Fact extends ListenerAdapter {

    public Scanner getFactScanner() throws FileNotFoundException {
        File file = new File("fact.txt");
        Scanner scanner = new Scanner(file);


        return scanner;
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getName().equals("fact")){
            String fact = "";
            try{
                Scanner scanner = getFactScanner();
                int min = 1;
                int max = 2;
                ArrayList<String> facts = new ArrayList<String>();

                while(scanner.hasNextLine()){
                    max++;
                    String data = scanner.nextLine();
                    facts.add(data);
                }
                int factNumber = (int) (Math.random() * (max - min + 1) + min);
                fact = facts.get(factNumber);


            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            event.reply(fact).queue();
        }
    }
}
