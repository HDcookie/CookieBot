package me.hdcookie.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

    public static final HashMap<String, String> configHashMap = new HashMap<>();

    public void createFile() throws IOException, IOException {
        //create file
        File file = new File("config.txt");
        if (file.createNewFile()) {
            System.out.println("File for config created: " + file.getName() + "Insert config there");
            System.out.println("Cannot continue, Exiting program");

            //Write all the default config
            FileWriter writer = new FileWriter(file);
            writer.write("password=1234567890\n");
            writer.write("username=Hdcookie\n");
            writer.write("database=cookiebot\n");
            writer.write("host=localhost\n");
            writer.write("port=3306\n");
            writer.close();

            System.exit(0);
        } else {
            System.out.println("Config exists, loading config");

            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split("=");
                if(parts[1].equals("") || parts[1].equals(" ")){
                    parts[1] = "";
                }


                configHashMap.put(parts[0], parts[1]);
            }
            System.out.println("Config loaded" + configHashMap.toString());
        }
    }

}
