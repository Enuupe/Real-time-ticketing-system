package lk.coursework.version3;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private final String filePath;

    public Logger(String filePath) {
        this.filePath = filePath;
    }

    public synchronized void log(String message){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true))) {
            writer.write(message);
            writer.newLine();
        }
        catch(IOException e){
            System.out.println("The writing cannot be done: " + e.getMessage());
        }
    }
}
