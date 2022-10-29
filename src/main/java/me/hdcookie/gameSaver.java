package me.hdcookie;

import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class gameSaver {

    public JSONObject object = new JSONObject();
    public File file = new File("game.txt");

    public JSONObject getObject() {
        return object;
    }

    public void loadFile() throws IOException {
        if(file.createNewFile()) {
            System.out.println("Game file does not exist, creating new file");

        } else {
            System.out.println("loading games to previous state");
        }
        //at this point file exists



        //creating default values
        //      game        value
        object.put("counting", "0");

        saveFile();

        System.out.println(object.toJSONString());

    }

    public void count() throws IOException {

        int number = (Integer) object.get("counting");
        number++;
        object.put("counting", String.valueOf(number));
        saveFile();
    }

    public void resetNumber() throws IOException {
        object.put("counting", "1");
        saveFile();
    }
    public void saveFile() throws IOException {
        FileWriter fileWriter = new FileWriter("game.txt");
        fileWriter.write(object.toJSONString());
        fileWriter.close();
    }
}
