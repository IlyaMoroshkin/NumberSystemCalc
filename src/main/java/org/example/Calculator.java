package org.example;

public class Calculator {

    public static String transform(String inputNum, String selectedSystem) {
        String result;
        int number = Integer.parseInt(inputNum);
        int numSys = Integer.parseInt(selectedSystem);
        switch (numSys) {
            case 2:
                result = Integer.toBinaryString(number);
                break;
            case 8:
                result = Integer.toOctalString(number);
                break;
            case 16:
                result = Integer.toHexString(number);
                break;
            default:
                result = "Неизвестная система счисления";
        }
        return result;
    }
}
