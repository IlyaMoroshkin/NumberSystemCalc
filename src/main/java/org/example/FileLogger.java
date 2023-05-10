package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    public String loggerTransform(String inputNum, String selectedSystem, String result) throws IOException {
        FileWriter writer = new FileWriter("history.csv", true);
        String record = String.format("\"%s\";\"%s\";\"%s\"\n", inputNum, selectedSystem, result);
        writer.write(record);
        writer.close();
        return record;
    }

    public String loggerLog(String number1, String number2, String result, String operation) throws IOException {
        FileWriter writer = new FileWriter("history.csv", true);
        String record;
        if (operation == "NOT") {
            record = String.format("\"%s\";\"%s\";\"%s\"\n", operation, number1, result);
        } else {
            record = String.format("\"%s\";\"%s\";\"%s\";\"%s\"\n", number1, operation, number2, result);
        }
        writer.write(record);
        writer.close();
        return record;

    }

}
