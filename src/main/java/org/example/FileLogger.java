package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    public static String logger(String inputNum, String selectedSystem, String result) throws IOException {
        FileWriter writer = new FileWriter("history.csv", true);
        String record = String.format("\"%s\";\"%s\";\"%s\"\n", inputNum, selectedSystem, result);
        writer.write(record);
        writer.close();
        return record;
    }
}
