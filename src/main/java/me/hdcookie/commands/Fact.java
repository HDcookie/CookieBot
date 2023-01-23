package me.hdcookie.commands;

import com.freya02.botcommands.api.annotations.CommandMarker;
import com.freya02.botcommands.api.application.ApplicationCommand;
import com.freya02.botcommands.api.application.slash.GuildSlashEvent;
import com.freya02.botcommands.api.application.slash.annotations.JDASlashCommand;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

@CommandMarker
public class Fact extends ApplicationCommand {

    public Scanner getFactScanner() throws FileNotFoundException {
        File file = new File("fact.txt");


        return new Scanner(file);
    }


    @JDASlashCommand(
            name = "fact",
            description = "Gives user a random fact"
    )
    public void onSlashFact(GuildSlashEvent event) {
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
