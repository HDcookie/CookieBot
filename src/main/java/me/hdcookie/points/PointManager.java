package me.hdcookie.points;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PointManager {
    private int points;
    public JSONObject object = new JSONObject();

    public File file = new File("points.txt");


    public void setUp() throws IOException {
        if(file.createNewFile()) {
            //load points
            System.out.println("Points file does not exist, creating new file");
        } else {
            System.out.println("Points file exists.  \n dont forget to fix all of this junk");

            Scanner scanner = new Scanner(file);
            if(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                object = (JSONObject) JSONValue.parse(line);
            }else {
                System.out.println("Points file is empty but still loaded");
            }

        }
    }



    public int getPoints(String id) throws IOException {

        if(object.containsKey(id)){
            //player in object
            return Integer.parseInt(object.get(id).toString());
        } else {
            //player not in object, adding with a default of 0
            object.put(id, "0");
            saveFile();
            return Integer.parseInt(object.get(id).toString());
        }
    }

    public void addPoints(String id, int points) throws IOException {
        int currentPoints = getPoints(id);
        object.put(id, currentPoints + points);
        saveFile();

    }

    public void removePoints(String id, int points) throws IOException {
        int currentPoints = getPoints(id);
        object.put(id, currentPoints - points);
        saveFile();

    }

    public void saveFile() throws IOException {
        FileWriter fileWriter = new FileWriter("points.txt");
        fileWriter.write(object.toJSONString());
        fileWriter.close();
    }
}
