package me.hdcookie;



import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Token {

    public void createFile() throws IOException {
        //create file
        File file = new File("token.txt");
        if(file.createNewFile()) {
            System.out.println("File for token created: " + file.getName() + "Insert token there, or use config.txt");

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
