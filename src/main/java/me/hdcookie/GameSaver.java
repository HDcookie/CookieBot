package me.hdcookie;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameSaver {

    public JSONObject object = new JSONObject();
    public File file = new File("game.txt");

    public JSONObject getObject() {
        return object;
    }

    public void loadFile() throws IOException {
        if(file.createNewFile()) {
            System.out.println("Game file does not exist, creating new file");
            object.put("counting", "1");

            saveFile();

        } else {
            System.out.println("loading games to previous state");
        }
        //at this point file exists
        //loading  file
        Scanner scanner = new Scanner(file);
        String data = scanner.nextLine();
        object = (JSONObject) JSONValue.parse(data);

        System.out.println("Game data" + object.toJSONString());

    }

    public void count() throws IOException {

        int number = Integer.parseInt(object.get("counting").toString());
        number++;
        object.put("counting", String.valueOf(number));
        saveFile();
    }

    public void resetNumber() throws IOException {
        object.put("counting", "1");
        saveFile();
    }

    public int getNumber() {
        return Integer.parseInt(object.get("counting").toString());
    }

    public void saveFile() throws IOException {
        FileWriter fileWriter = new FileWriter("game.txt");
        fileWriter.write(object.toJSONString());
        fileWriter.close();
    }
}
