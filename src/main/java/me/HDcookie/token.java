package me.HDcookie;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class token {

    public void createFile() throws IOException {
        //create file
        File file = new File("token.txt");
        if(file.createNewFile()) {
            System.out.println("File for token created: " + file.getName() + "Insert token there");
            System.out.println("Exiting program");
            System.exit(0);
        } else {
            System.out.println("Token exists, loading token");
        }
    }

    public String getToken() throws IOException {
        File file = new File("token.txt");
        Scanner scanner = new Scanner(file);
        return scanner.nextLine();
    }

}
