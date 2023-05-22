package org.example;

import java.io.IOException;

public class Calculator {
    private final FileLogger fileLogger;

    public Calculator(FileLogger fileLogger) {
        this.fileLogger = fileLogger;
    }

    public String transform(int inputNum, int selectedSystem) throws IOException {
        String result = switch (selectedSystem) {
            case 2 -> Integer.toBinaryString(inputNum);
            case 8 -> Integer.toOctalString(inputNum);
            case 16 -> Integer.toHexString(inputNum);
            default -> "Неизвестная система счисления";

        };
        fileLogger.loggerTransform(Integer.toString(inputNum), Integer.toString(selectedSystem), result);
        return result;
    }

    public String logCalc(int number1, int number2, String operation) throws IOException {
        int result = switch (operation) {
            case "AND" -> number1 & number2;
            case "OR" -> number1 | number2;
            case "XOR" -> number1 ^ number2;
            case "NOT" -> ~number1;
            default -> throw new IllegalStateException("Ошибка");

        };
        fileLogger.loggerLog(Integer.toString(number1), Integer.toString(number2), Integer.toString(result), operation);
        return Integer.toString(result);
    }

}
