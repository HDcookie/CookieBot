package me.hdcookie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

    public static final HashMap<String, String> config = new HashMap<>();

    public void createFile() throws IOException, IOException {
        //create file
        File file = new File("config.txt");
        if (file.createNewFile()) {
            System.out.println("File for config created: " + file.getName() + "Insert config there");
            System.out.println("Cannot continue, Exiting program");

            //Write all the default config
            FileWriter writer = new FileWriter(file);
            writer.write("useTokenFile=false\n");
            writer.write("token=YOUR_TOKEN_HERE\n");
            writer.write("makeASentenceID=0\n");
            writer.write("finishedSentencesID=0\n");
            writer.write("countingID=0\n");
            writer.write("guessTheNumberID=0\n");
            writer.write("finishedFormsChannel=0\n");
            writer.write("finishedFormsRole=0\n");
            writer.close();

            System.exit(0);
        } else {
            System.out.println("Config exists, loading config");

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                config.put(parts[0], parts[1]);
            }
            System.out.println("Config loaded" + config.toString());
        }
    }

    public String getToken() {
        return config.get("token");
    }
}
