package me.hdcookie.database;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {

    // GSON object for parsing and generating JSON
    private Gson gson;

    // JSON object for storing configuration data
    private JsonObject configData;

    public ConfigManager() {
        // create a new GSON object
        gson = new Gson();

        // create a new JSON object for storing configuration data
        configData = new JsonObject();

        /*
            * Add default configuration data to the JSON object
            * Optimized for my raspberry pi setup, on my laptop password and username are different.
         */
        configData.addProperty("password", "1234567890");
        configData.addProperty("username", "Hdcookie");
        configData.addProperty("database", "cookiebot");
        configData.addProperty("host", "localhost");
        configData.addProperty("port", 3306);

    }

    // Loads configuration data from a file
    public void loadConfig(String filePath) {
        try {
            // try to load the configuration data from the file, should reset the default values
            configData = gson.fromJson(new FileReader(filePath), JsonObject.class);
        } catch (FileNotFoundException e) {
            // if the file is not found, create it and save the default configuration data
            try (FileWriter writer = new FileWriter(filePath)) {
                gson.toJson(configData, writer);
            } catch (IOException ioe) {
                // handle the IOException
            }
        }
    }

    // Saves configuration data to a file
    public void saveConfig(String filePath) throws IOException {
        // use the GSON library to generate a JSON string from the configData object and write it to the file
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(configData, writer);
        }
    }

    // Sets a configuration value
    public void setConfigValue(String key, String value) {
        configData.addProperty(key, value);
    }

    // Gets a configuration value
    public Object getConfigValue(String key) {
        return configData.get(key);
    }
}